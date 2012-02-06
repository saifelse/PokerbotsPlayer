package player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import poker.Hand;
import poker.Match;
import poker.State;

public class MatchReplayer {
	private int handIndex;

	private List<Match> listOfHands;
	private List<HandReplayer> handReplayers;
	private int numberOfHands;
	private boolean atBeginning;
	private boolean atEnd;
	
	public static MatchReplayer fromFiles(List<File> files) throws FileNotFoundException, IOException{
		List<Match> loh = new ArrayList<Match>();
		for(File f : files){
			Match m = new HandHistoryParser(new FileInputStream(f)).parse();
			loh.add(m);
		}
		return new MatchReplayer(loh);
	}
	public MatchReplayer(List<Match> loh) {
		handIndex = 0;
		listOfHands = loh;
		numberOfHands = loh.get(0).getHands().size(); 
		updateBeginEnd();
		// assert each list has the same number of hands?
		
		// Synchronize all hands
		for(int i=0;i<numberOfHands;i++){
			List<Hand> syncHands = new ArrayList<Hand>();
			for(Match m : listOfHands){
				List<Hand> hands = m.getHands();
				syncHands.add(hands.get(i));
			}
			HandReplayer.synchronizeHands(syncHands);
		}
		updateToHand();
	}
	public void prevStep(){
		for(HandReplayer hr : handReplayers){
			hr.prev();
		}
	}
	public void nextStep(){
		for(HandReplayer hr : handReplayers){
			hr.next();
		}
	}
	public void prevHand(){
		if(handIndex > 0){
			handIndex--;
			updateToHand();
		}
		updateBeginEnd();
	}
	public void nextHand(){
		if(handIndex < numberOfHands-1){
			handIndex++;
			updateToHand();
		}
		updateBeginEnd();
	}
	public void updateToHand(){
		handReplayers = new ArrayList<HandReplayer>();
		for(Match m : listOfHands){
			List<Hand> hands = m.getHands();
			handReplayers.add(new HandReplayer(hands.get(handIndex)));
		}
	}
	public List<Map<String, Integer>> getSeats(){
		List<Map<String, Integer>> seats = new ArrayList<Map<String, Integer>>();
		for(Match m : listOfHands){
			seats.add(m.getSeat());
		}
		return seats;
	}
	public Map<String, Integer> getScores(){
		Map<String, Integer> scores = new HashMap<String, Integer>();
		for(String name : listOfHands.get(0).getSeat().keySet()){
			int score = 0;
			for(int i=0; i < listOfHands.size(); i++){
				score += handReplayers.get(i).getHand().getNetChips().get(listOfHands.get(i).getSeat().get(name));
			}
			scores.put(name, score);
		}
		return scores;
	}
	public List<State> getStates(){
		List<State> states = new ArrayList<State>();
		for(HandReplayer hr : handReplayers){
			states.add(hr.getState());
		}
		return states;
	}
	public void jumpToHand(int handNumber) {
		handIndex = Math.max(0, Math.min(numberOfHands-1, handNumber));
		updateBeginEnd();
		updateToHand();
	}
	public boolean isAtBeginning() {
		return atBeginning;
	}
	public boolean isAtEnd() {
		return atEnd;
	}
	
	public int getHandIndex() {
		return handIndex;
	}
	private void updateBeginEnd(){
		atBeginning = handIndex == 0;
		atEnd = handIndex == numberOfHands - 1;
	}
}
