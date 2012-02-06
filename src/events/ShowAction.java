package events;

import poker.Card;
import poker.Chips;
import poker.State;

public class ShowAction extends PlayerAction {
	private final Card[] cards;
	private final String rank;
	public ShowAction(int pid, Card[] cards, String rank) {
		super(ActionType.WIN, pid);
		this.cards = cards;
		this.rank = rank;
	}
	public String getRank(){
		return rank;
	}
	public Card[] getCards() {
		return cards;
	}
	// FIXME should this happen on a *** SHOWDOWN *** instead?
	// chips0, bet0, pot0, call0, dealt0
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips(), new Chips(0,0,0), s.getPot()+s.getBets().total(),
				s.getChipsToCall(), s.getDealt(), s.getP1(), s.getP2(), s.getP3());
	}

}
