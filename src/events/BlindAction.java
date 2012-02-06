package events;

import poker.State;

public class BlindAction extends PlayerAction {
	private int chips;

	public BlindAction(int pid, int chips, String line) {
		super(ActionType.BLIND, pid, line);
		this.chips = chips;
	}

	public int getChips() {
		return chips;
	}

	// chips-, bet+, pot0, call+
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips().mod(getPID(), -chips), s.getBets()
				.mod(getPID(), chips), s.getPot(), Math.max(chips,
				s.getChipsToCall()), s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}
