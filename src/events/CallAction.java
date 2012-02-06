package events;

import poker.State;

public class CallAction extends PlayerAction {
	
	public CallAction(int pid, String line){
		super(ActionType.CALL, pid, line);
		
	}

	// chips-, bet+, pot0, call0
	@Override
	public State applyToState(State s) {
		int chips = s.getChipsToCall();
		return new State(this, s.getChips().mod(getPID(), -chips), s.getBets()
				.mod(getPID(), chips), s.getPot(), chips, s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}