package events;

import poker.State;

public class UncalledBetAction extends PlayerAction {
	private int chips;
	public UncalledBetAction(int pid, int chips, String line){
		super(ActionType.BET, pid, line);
		this.chips = chips;
	}
	public int getChips(){
		return chips;
	}
	// chips+, bet-, pot0, call0, dealt0
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips().mod(getPID(), chips), s.getBets(), s.getPot(),
				s.getChipsToCall(), s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}
}
