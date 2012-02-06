package events;

import poker.State;

public class CheckAction extends PlayerAction {

	public CheckAction(int pid) {
		super(ActionType.CHECK, pid);
	}

	// chips0, bet0, pot0, call0
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips(), s.getBets(), s.getPot(),
				s.getChipsToCall(), s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}