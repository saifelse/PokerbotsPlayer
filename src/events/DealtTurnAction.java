package events;

import poker.Card;
import poker.Chips;
import poker.State;

public class DealtTurnAction extends Action {
	private final Card[] cards;
	public DealtTurnAction(Card[] cards){
		super(ActionType.TURN);
		this.cards = cards;
	}
	public Card[] getCards(){
		return cards;
	}
	// chips0, bet0, pot0, call0, dealt+
	@Override
	public State applyToState(State s) {
		return new State(this, s.getChips(), new Chips(0,0,0), s.getPot()+s.getBets().total(),
				s.getChipsToCall(), cards, s.getP1(), s.getP2(), s.getP3());
	}
}
