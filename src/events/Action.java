package events;

import poker.State;

// TODO: move bets into pot by checking for full round of betting.

public abstract class Action {
	private final ActionType type;
	
	public Action(ActionType type){
		this.type = type;
	}
	public ActionType getActionType(){
		return this.type;
	}
	public abstract State applyToState(State s);
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
