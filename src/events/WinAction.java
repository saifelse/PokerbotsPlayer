package events;

import poker.State;

public class WinAction extends PlayerAction {
	private int chips;

	public WinAction(int pid, int chips) {
		super(ActionType.WIN, pid);
		this.chips = chips;
	}

	public int getChips() {
		return chips;
	}

	// chips+, bet0, pot0, call0, dealt0
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips().mod(getPID(), chips), s.getBets(),
				s.getPot(), s.getChipsToCall(), s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}
