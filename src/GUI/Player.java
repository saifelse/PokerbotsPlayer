package GUI;

import java.awt.Graphics;
import java.awt.Image;
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
	
	private static final int CARD_WIDTH = 38;
	private static final int CARD_HEIGHT = 54;
	
	private static final long serialVersionUID = 1L;
	private final JLabel nameLabel;
	private final JLabel dealerButton;
	private final JLabel chipStack;
	private final JLabel bet;
	private final List<JLabel> cards;
	
	private String playerName;
	
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
		setIsDealer(true);
	}
	
	private void initComponents(){
		setLayout(null);
		Insets insets = getInsets();
		
		dealerButton.setBounds(insets.left+30, insets.top, 22, 20);
		add(dealerButton);
		
		nameLabel.setBounds(insets.left, insets.top, 200, 50);
		add(nameLabel);
		chipStack.setBounds(insets.left, insets.top+20, 200, 50);
		add(chipStack);
		bet.setBounds(insets.left, insets.top+40, 200, 50);
		add(bet);
		for(int i=0; i<2; i++){
			cards.get(i).setBounds(insets.left+(CARD_WIDTH+5)*i, insets.top+40, CARD_WIDTH, CARD_HEIGHT);
			add(cards.get(i));
		}
		
	}
	
	public void setIsDealer(boolean b) {
		if(b){
			BufferedImage dealerImg = null;
			try {
				dealerImg = ImageIO.read(new File("DealerControl.gif"));
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
		playerName = name;
		nameLabel.setText(name);
	}
	public String getPlayerName(){
		return playerName;
	}
	public void setChips(int chips){
		chipStack.setText(""+chips);
	}
	public void setBet(int chips){
		bet.setText(""+chips+" being bet.");
	}
	public void setCards(Card[] cards) {
		// Clear out
		for(JLabel c : this.cards){
			c.setIcon(null);
		}

		// Draw
		for(int i=0;i<cards.length;i++){
			BufferedImage cardImg = null;
			try {
				cardImg = ImageIO.read(new File("cards/"+cards[i].toFilename()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(cardImg != null){
				BufferedImage bi = new BufferedImage(cardImg.getWidth(null), cardImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				Graphics g = bi.createGraphics();
				g.drawImage(cardImg, 0, 0, CARD_WIDTH, CARD_HEIGHT, null);
				
				this.cards.get(i).setIcon(new ImageIcon(bi));
				
			}
			
		}
	}
}
