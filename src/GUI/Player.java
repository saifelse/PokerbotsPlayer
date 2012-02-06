package GUI;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import poker.Card;

public class Player extends JPanel {
	/**
	 * 
	 */
	
	private static final int CARD_WIDTH = 50;
	private static final int CARD_HEIGHT = 70;
	
	private static final long serialVersionUID = 1L;
	private final JLabel nameLabel;
	private final JLabel dealerButton;
	private final JLabel chipStack;
	private final JLabel bet;
	private final List<JLabel> cards;
	
	
	public Player(){
		nameLabel = new JLabel("");
		dealerButton = new JLabel();
		chipStack  = new JLabel();
		bet = new JLabel();
		cards = new ArrayList<JLabel>();
		for(int i=0;i<2;i++){
			cards.add(new JLabel());
		}
		setOpaque(false);
		initComponents();
	}
	
	private void initComponents(){
		setLayout(null);
		Insets insets = getInsets();
		nameLabel.setBounds(insets.left, insets.top, 200, 50);
		add(nameLabel);
		for(int i=0; i<2; i++){
			cards.get(i).setBounds(insets.left+300+(CARD_WIDTH+5)*i, insets.top+150, CARD_WIDTH, CARD_HEIGHT);
			add(cards.get(i));
		}
	}
	
	public void setIsDealer(boolean b) {
		if(b){
			BufferedImage dealerImg = null;
			try {
				dealerImg = ImageIO.read(new File("misc/button.gif"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(dealerImg != null)
				this.dealerButton.setIcon(new ImageIcon(dealerImg));
		}else{
			this.dealerButton.setIcon(null);
		}
	}
	public void setName(String name){
		System.out.println("My name is "+name);
		nameLabel.setText(name);
	}
	public void setChips(int chips){
		chipStack.setText(""+chips);
	}
	public void setBet(int chips){
		bet.setText(""+chips+" being bet.");
	}
	public void setCards(Card[] cards) {
		for(int i=0;i<cards.length;i++){
			BufferedImage cardImg = null;
			try {
				cardImg = ImageIO.read(new File("cards/"+cards[i].toFilename()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(cardImg != null)
				this.cards.get(i).setIcon(new ImageIcon(cardImg));
		}
	}
}
