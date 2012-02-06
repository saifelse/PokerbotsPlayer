package events;

import poker.State;

public class RaiseAction extends PlayerAction {
	private int chips;

	public RaiseAction(int pid, int chips) {
		super(ActionType.RAISE, pid);
		this.chips = chips;
	}

	public int getChips() {
		return chips;
	}

	// chips-, bet+, pot0, call+, dealt0
	@Override
	public State applyToState(State s) {
		int chipsAdded = chips-s.getBets().get(getPID());
		return new State(this, s.getChips().mod(getPID(), -chipsAdded), s.getBets()
				.set(getPID(), chips), s.getPot(), chips,
				s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}