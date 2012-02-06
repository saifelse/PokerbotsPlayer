package GUI;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import poker.Card;
import poker.State;


public class PokerTable extends JPanel {
	/**
	 * 
	 */
	
	private static final int CARD_WIDTH = 38;
	private static final int CARD_HEIGHT = 54;
	
	private static final long serialVersionUID = 1L;
	private final JLabel background;
	private BufferedImage table;
	
	private JLabel pot;
	private List<JLabel> dealtCards;
	private JLabel action;
	private List<Player> players;
	/*
	private List<JLabel> p1;
	private List<JLabel> p2;
	private List<JLabel> p3;
	*/
	
	public PokerTable(PokerReplayer pr) throws IOException {
		table = ImageIO.read(new File("table.jpg"));
		background = new JLabel(new ImageIcon(table));
		action = new JLabel();
		dealtCards = new ArrayList<JLabel>();
		pot = new JLabel();
		for(int i=0; i<5; i++){
			dealtCards.add(new JLabel());
		}
		players = new ArrayList<Player>();
		for(int i=0; i<3; i++){
			players.add(new Player());
		}
		
		initComponents();
		setupListeners();
		
	}
	public void initComponents(){
		setLayout(null);
		Insets insets = getInsets();
		
		pot.setBounds(insets.left+280+(CARD_WIDTH+5)*3, insets.top+150+CARD_HEIGHT, 100, 20);
		add(pot);
		
		action.setBounds(insets.left, insets.top, 400, 20);
		add(action);
		
		background.setBounds(insets.left, insets.top, table.getWidth(), table.getHeight());
		add(background);
		for(int i=0; i<5; i++){
			dealtCards.get(i).setBounds(insets.left+280+(CARD_WIDTH+5)*i, insets.top+150, CARD_WIDTH, CARD_HEIGHT);
			add(dealtCards.get(i));
		}

		for(int i=0;i<3;i++){
			Player p = players.get(i);
			if(i==0){
				p.setBounds(insets.left+565, insets.top+150, 400, 400);
			}else if(i==1){
				p.setBounds(insets.left+125, insets.top+150, 400, 400);
			}else{
				p.setBounds(insets.left+310, insets.top+250, 400, 400);
			}
			add(p);
		}
		int compsMax = 9;
		setComponentZOrder(background, compsMax);
		for(int i=0;i<5;i++){
			setComponentZOrder(dealtCards.get(i), compsMax-1-i);
		}
		for(int i=0;i<3;i++){
			setComponentZOrder(players.get(i), compsMax-6-i);
		}
		setComponentZOrder(action, compsMax-9);
		
	}
	public void setupListeners(){
		
	}
	public void updatePlayer(int seatNumber, String name, int chips, int bet, Card[] cards, Boolean isDealer){
		Player p = players.get(seatNumber);
		p.setName(name);
		p.setChips(chips);
		p.setBet(bet);
		p.setCards(cards);
		p.setIsDealer(isDealer);
	}
	public void displayState(State state) {
		if(state.getAction() == null){
			action.setText("");
		}else {
			action.setText(state.getAction().getLine());
		}
		// Show cards of players
		List<Card[]> pcards = new ArrayList<Card[]>();
		pcards.add(state.getP1());
		pcards.add(state.getP2());
		pcards.add(state.getP3());
		
		for(int i=0;i<players.size(); i++){
			Player p = players.get(i);
			p.setCards(pcards.get(i));
		}
		
		// Clear out old cards.
		for(JLabel c : dealtCards){
			c.setIcon(null);
		}
		// Show dealt cards.
		Card[] cards = state.getDealt();
		for(int i=0; i< cards.length; i++){
			BufferedImage cardImg = null;
			try {
				cardImg = ImageIO.read(new File("cards/"+cards[i].toFilename()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(cardImg != null)
				dealtCards.get(i).setIcon(new ImageIcon(cardImg));
		}
		
		// Update bets.
		// Update chips.
		
		for(int i=0; i<players.size(); i++){
			players.get(i).setBet(state.getBets().get(i));
			players.get(i).setChips(state.getChips().get(i));
		}
		pot.setText("Pot: "+state.getPot());
	}
	public void setPlayers(Map<String, Integer> map) {
		for(String name : map.keySet()){
			updatePlayer(map.get(name), name, 0, 0, new Card[0], false);
		}
	}
	public void setButton(Integer pid) {
		for(int i=0; i < players.size(); i++){
			players.get(i).setIsDealer(i == pid);
		}
		// TODO Auto-generated method stub
		
	}
	
}
