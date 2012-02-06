package poker;

import java.util.ArrayList;
import java.util.List;

import events.Action;
import events.DelayAction;



public class Hand {
	private List<State> states;
	private Chips netChips;
	private int button;

	public Hand(State initState, Chips netChips){
		this.netChips = netChips;
		states = new ArrayList<State>();
		states.add(initState);
	}
	
	public void addAction(Action action){
		states.add(action.applyToState(states.get(states.size()-1)));
	}
	public void addDelayAfter(int i){
		states.add(i+1, states.get(i));
		//states.add(i+1, new DelayAction().applyToState(states.get(i)));
	}
	public State getState(int i){
		return states.get(i);
	}
	public int size(){
		return states.size();
	}
	public void setNetChips(Chips netChips){
		this.netChips = netChips;
	}
	public Chips getNetChips(){
		return netChips;
	}
	public int getButton() {
		return button;
	}
	public void setButton(int button) {
		this.button = button;
	}

}
