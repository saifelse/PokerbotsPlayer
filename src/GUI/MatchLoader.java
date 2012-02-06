package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import player.HandHistoryParser;
import player.HandHistoryParser.UnparseableStringException;
import player.MatchReplayer;

public class MatchLoader extends JFrame {

	/**
	 * 
	 */
	private static final String BROWSE_TEXT = "<Browse to select match file.>";
	private static final int HEIGHT = 28;
	private static final long serialVersionUID = 1L;
	private final JFileChooser fc;
	private final JButton b1;
	private final JButton b2;
	private final JButton b3;
	private final JTextField tf1;
	private final JTextField tf2;
	private final JTextField tf3;
	private final JButton load;
	private File file1;
	private File file2;
	private File file3;
	private PokerReplayer pokerReplayer;
	
	public MatchLoader(PokerReplayer pr) {
		fc = new JFileChooser();
		b1 = new JButton("Select Match #1 file");
		b2 = new JButton("Select Match #2 file");
		b3 = new JButton("Select Match #3 file");
		load = new JButton("Load match.");
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		pokerReplayer = pr;
		initComponents();
		setupListeners();
		updateStates();	
		
		setupWindow();
		
		
		
		
		
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				MatchReplayer mr = null;
				String error = "Unexpected error.";
				try {
					file1 = new File("Match #1 - FoldBot vs. Halos vs. VictoriousSecret.txt");
					file2 = new File("Match #1 - Halos vs. VictoriousSecret vs. FoldBot.txt");
					file3 = new File("Match #1 - VictoriousSecret vs. FoldBot vs. Halos.txt");
					mr = getReplayer(file1, file2, file3);
				} catch (FileNotFoundException e1) {
					error = e1.getMessage();
				} catch (IOException e1) {
					error = e1.getMessage();
				} catch (UnparseableStringException e1){
					error = e1.getMessage();
				} finally {
					if(mr != null){
						final MatchReplayer replayer = mr;
						SwingUtilities.invokeLater(new Runnable(){
							@Override
							public void run() {
								pokerReplayer.getController().setMatchReplayer(replayer);
							}
						});
					}else {
						JOptionPane.showMessageDialog(MatchLoader.this, error);
					}
				}
			}
			
		}).start();
	}
	private void setupWindow(){
		setSize(600, 150);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Load files.");
	}
	private void initComponents() {
		// Layout
		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup().addComponent(tf1,HEIGHT,HEIGHT,HEIGHT)
								.addComponent(b1,HEIGHT,HEIGHT,HEIGHT))
				.addGroup(
						layout.createParallelGroup().addComponent(tf2,HEIGHT,HEIGHT,HEIGHT)
								.addComponent(b2,HEIGHT,HEIGHT,HEIGHT))
				.addGroup(
						layout.createParallelGroup().addComponent(tf3,HEIGHT,HEIGHT,HEIGHT)
								.addComponent(b3,HEIGHT,HEIGHT,HEIGHT))
				.addGroup(layout.createParallelGroup().addComponent(load)));
		
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup().addComponent(tf1).addComponent(tf2).addComponent(tf3))
				.addGroup(
						layout.createParallelGroup().addComponent(b1).addComponent(b2).addComponent(b3).addComponent(load)));
		this.setLayout(layout);
		
		// Setup
		tf1.setEditable(false);
		tf2.setEditable(false);
		tf3.setEditable(false);
	}

	private void setupListeners() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == b1 || e.getSource() == b2
						|| e.getSource() == b3) {
					fc.showOpenDialog(MatchLoader.this);
					File file = fc.getSelectedFile();
					if (e.getSource() == b1) {
						file1 = file;
					} else if (e.getSource() == b2) {
						file2 = file;
					} else {
						file3 = file;
					}
					updateStates();
				}
				if(e.getSource() == load){
					// Shouldn't be in the UI thread.
					
					new Thread(new Runnable(){

						@Override
						public void run() {
							MatchReplayer mr = null;
							String error = "Unexpected error.";
							try {
								mr = getReplayer(file1, file2, file3);
							} catch (FileNotFoundException e1) {
								error = e1.getMessage();
							} catch (IOException e1) {
								error = e1.getMessage();
							} catch (UnparseableStringException e1){
								error = e1.getMessage();
							} finally {
								if(mr != null){
									final MatchReplayer replayer = mr;
									SwingUtilities.invokeLater(new Runnable(){
										@Override
										public void run() {
											pokerReplayer.getController().setMatchReplayer(replayer);
										}
									});
								}else {
									JOptionPane.showMessageDialog(MatchLoader.this, error);
								}
							}
						}
						
					}).start();
				}
			}
			
		};
		b1.addActionListener(al);
		b2.addActionListener(al);
		b3.addActionListener(al);
		load.addActionListener(al);
	}
	private void updateStates(){
		tf1.setText(file1 == null ? BROWSE_TEXT : file1.getName());
		tf2.setText(file2 == null ? BROWSE_TEXT : file2.getName());
		tf3.setText(file3 == null ? BROWSE_TEXT : file3.getName());
		load.setEnabled(file1 != null && file2 != null && file3 != null);
	}
	
	public MatchReplayer getReplayer(File f1, File f2, File f3) throws FileNotFoundException, IOException{
		List<File> files = new ArrayList<File>();
		files.add(f1);
		files.add(f2);
		files.add(f3);
		return MatchReplayer.fromFiles(files);
	}
}
