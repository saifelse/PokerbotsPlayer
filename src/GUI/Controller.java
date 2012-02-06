package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import player.MatchReplayer;
import player.HandHistoryParser.UnparseableStringException;

public class Controller extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MatchReplayer matchReplayer;
	private final JButton prevHand;
	private final JButton nextHand;
	private final JButton prevStep;
	private final JButton nextStep;
	private final JTextField jumpBox;
	private final JButton jumpBtn;
	private final JButton playBtn;
	private final JButton pauseBtn;
	private final JLabel handTxt;
	private final PokerReplayer pokerReplayer;
	public Controller(PokerReplayer pr){
		prevHand = new JButton("<<");
		nextHand = new JButton(">>");
		prevStep = new JButton("<");
		nextStep = new JButton(">");
		jumpBox = new JTextField("");
		handTxt = new JLabel("Hand #:");
		jumpBtn = new JButton("Go.");
		playBtn = new JButton("|>");
		pauseBtn = new JButton("||");
		pokerReplayer = pr;
		
		initComponents();
		setupListeners();
	}
	private void initComponents(){
		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(prevHand)
				.addComponent(prevStep)
				.addComponent(playBtn)
				.addComponent(pauseBtn)
				.addComponent(nextStep)
				.addComponent(nextHand)
				.addComponent(handTxt)
				.addComponent(jumpBox, 50, 50, 50)
				.addComponent(jumpBtn));
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(prevHand)
				.addComponent(prevStep)
				.addComponent(playBtn)
				.addComponent(pauseBtn)
				.addComponent(nextStep)
				.addComponent(nextHand)
				.addComponent(handTxt)
				.addComponent(jumpBox)
				.addComponent(jumpBtn));
		setLayout(layout);
		setControllerEnabled(false);
	}
	public void setControllerEnabled(boolean b){
		for(Component c : this.getComponents()){
			c.setEnabled(b);
		}
	}
	public void setMatchReplayer(MatchReplayer mr){
		matchReplayer = mr;
		pokerReplayer.getGraph().addData(mr.getScores());
		syncWithReplayer();
	}
	private void syncWithReplayer(){
		if(matchReplayer == null){
			setControllerEnabled(false);
			jumpBox.setText("");
		}else {
			setControllerEnabled(true);
			prevHand.setEnabled(!matchReplayer.isAtBeginning());
			nextHand.setEnabled(!matchReplayer.isAtEnd());
			jumpBox.setText(Integer.toString(matchReplayer.getHandIndex()+1));
			
			pokerReplayer.setPlayers(matchReplayer.getSeats());
			pokerReplayer.displayStates(matchReplayer.getStates());
			pokerReplayer.setButtons(matchReplayer.getButtons());
		}
	}
	
	private void setupListeners() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == prevHand){
					matchReplayer.prevHand();
				}else if(e.getSource() == nextHand){
					matchReplayer.nextHand();
				}else if(e.getSource() == prevStep){
					matchReplayer.prevStep();
				}else if(e.getSource() == nextStep){
					matchReplayer.nextStep();
				}else if(e.getSource() == playBtn){
					// TODO : implement if there is time.
				}else if(e.getSource() == pauseBtn){
					// TODO : implement if there is time.
				}else if(e.getSource() == jumpBtn){
					try {
						int handIndex = Integer.parseInt(jumpBox.getText())-1;
						matchReplayer.jumpToHand(handIndex);
					} catch (NumberFormatException e1){
						JOptionPane.showMessageDialog(Controller.this, "Please enter a valid hand number.");
						return;
					}
				}
				// Update Controller
				syncWithReplayer();
			}
			
		};
		prevHand.addActionListener(al);
		nextHand.addActionListener(al);
		prevStep.addActionListener(al);
		nextStep.addActionListener(al);
		jumpBtn.addActionListener(al);
		playBtn.addActionListener(al);
		pauseBtn.addActionListener(al);
	}
}
