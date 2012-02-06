package events;

import poker.State;

// TODO: move bets into pot by checking for full round of betting.

public abstract class Action {
	private final ActionType type;
	private final String line;
	public Action(ActionType type, String line){
		this.type = type;
		this.line = line;
	}
	public ActionType getActionType(){
		return this.type;
	}
	public abstract State applyToState(State s);
	
	public String getLine(){
		return line;
	}
	
	@Override
	public String toString(){
		return "Action("+type.name()+")";
	}
	public enum ActionType {
		END_OF_HAND,
		BET,
		FOLD,
		CALL,
		RAISE,
		CHECK,
		BLIND,
		WIN, 
		FLOP, RIVER, TURN, DEALT, DELAY
	}
}
