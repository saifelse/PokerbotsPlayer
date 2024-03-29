package events;

import poker.State;

public class DelayAction extends Action {

	public DelayAction(String line) {
		super(ActionType.DELAY, line);
	}
	// chips0, bet0, pot0, call0
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips(), s.getBets(), s.getPot(),
				s.getChipsToCall(), s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}