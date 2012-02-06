package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JFrame;

import player.MatchReplayer;
import poker.State;

public class PokerReplayer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MatchLoader matchLoader;
	private List<PokerTable> pokerTables;
	private Graph graph;
	private Controller controller;
	public static void main(String args[]) throws IOException{
		PokerReplayer pr = new PokerReplayer();
		pr.setSize(500,500);
		pr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pr.setTitle("Pokerbots Replayer");
		pr.setVisible(true);
		
		// Open loader.
		pr.launchBrowser();
	}
	public PokerReplayer() throws IOException{
		matchLoader = new MatchLoader(this);
		graph = new Graph(this);
		controller = new Controller(this);
		
		pokerTables = new ArrayList<PokerTable>();		
		for(int i=0; i<3; i++)
			pokerTables.add(new PokerTable(this));
		initComponents();
	}
	public void initComponents(){
		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
					.addComponent(pokerTables.get(0))
					.addComponent(pokerTables.get(1)))
			.addGroup(layout.createParallelGroup()
					.addComponent(pokerTables.get(2))
					.addComponent(graph))
			.addComponent(controller, 30, 30, 30));
		layout.setHorizontalGroup(layout.createParallelGroup()
			.addGroup(layout.createSequentialGroup()
					.addComponent(pokerTables.get(0))
					.addComponent(pokerTables.get(1)))
			.addGroup(layout.createSequentialGroup()
					.addComponent(pokerTables.get(2))
					.addComponent(graph))
			.addComponent(controller));
		setLayout(layout);
	}
	public void displayStates(List<State> states) {
		for(int i=0; i < states.size(); i++){
			pokerTables.get(i).displayState(states.get(i));
		}
	}
	private void launchBrowser(){	
		matchLoader.setVisible(true);
	}
	public MatchLoader getMatchLoader() {
		return matchLoader;
	}
	public List<PokerTable> getPokerTables() {
		return pokerTables;
	}
	public Graph getGraph() {
		return graph;
	}
	public Controller getController() {
		return controller;
	}
	public void setPlayers(List<Map<String, Integer>> seats) {
		for(int i=0; i<seats.size(); i++){
			pokerTables.get(i).setPlayers(seats.get(i));
		}
	}
	public void setButtons(List<Integer> buttons) {
		for(int i=0; i<buttons.size(); i++){
			pokerTables.get(i).setButton(buttons.get(i));
		}
		
	}
}
