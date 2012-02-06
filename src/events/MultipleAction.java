package events;

import poker.State;

// TODO: IMPLEMENT
public class MultipleAction extends PlayerAction {
	private int chips;

	public MultipleAction(int pid, int chips, String line) {
		super(ActionType.BET, pid, line);
		this.chips = chips;
	}

	public int getChips() {
		return chips;
	}
	
	// chips-, bet+, pot0, call0
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips().mod(getPID(), -chips), s.getBets()
				.mod(getPID(), chips), s.getPot(), chips, s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}
