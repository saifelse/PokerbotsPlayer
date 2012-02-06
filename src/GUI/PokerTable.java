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
	
	private static final int CARD_WIDTH = 50;
	private static final int CARD_HEIGHT = 70;
	
	private static final long serialVersionUID = 1L;
	private final JLabel background;
	private BufferedImage table;
	
	private List<JLabel> dealtCards;
	
	private List<Player> players;
	/*
	private List<JLabel> p1;
	private List<JLabel> p2;
	private List<JLabel> p3;
	*/
	
	public PokerTable(PokerReplayer pr) throws IOException {
		table = ImageIO.read(new File("table.jpg"));
		background = new JLabel(new ImageIcon(table));
		
		dealtCards = new ArrayList<JLabel>();
		for(int i=0; i<5; i++){
			dealtCards.add(new JLabel());
		}
		players = new ArrayList<Player>();
		for(int i=0; i<5; i++){
			players.add(new Player());
		}
		
		initComponents();
		setupListeners();
		
	}
	public void initComponents(){
		setLayout(null);
		Insets insets = getInsets();
		background.setBounds(insets.left, insets.top, table.getWidth(), table.getHeight());
		add(background);
		for(int i=0; i<5; i++){
			dealtCards.get(i).setBounds(insets.left+300+(CARD_WIDTH+5)*i, insets.top+150, CARD_WIDTH, CARD_HEIGHT);
			add(dealtCards.get(i));
		}

		for(int i=0;i<3;i++){
			Player p = players.get(i);
			if(i==0){
				p.setBounds(insets.left+400, insets.top+150, p.getWidth(), p.getHeight());
			}else if(i==1){
				p.setBounds(insets.left+100, insets.top+150, p.getWidth(), p.getHeight());
			}else{
				p.setBounds(insets.left+250, insets.top+300, p.getWidth(), p.getHeight());
			}
			add(p);
		}
		
		setComponentZOrder(background, 8);
		for(int i=0;i<5;i++){
			setComponentZOrder(dealtCards.get(i), 7-i);
		}
		for(int i=0;i<3;i++){
			setComponentZOrder(players.get(i), i);
		}
		
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
		System.out.println("I will display "+state);
	}
	public void setPlayers(Map<String, Integer> map) {
		System.out.println("Name in map: "+map.keySet().size()+": ");
		for(String name : map.keySet()){
			System.out.println(name);
			updatePlayer(map.get(name), name, 0, 0, new Card[0], false);
		}
	}
	
}
