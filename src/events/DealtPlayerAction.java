package events;

import java.util.Arrays;

import poker.Card;
import poker.State;

public class DealtPlayerAction extends PlayerAction {
	private final Card[] cards;
	public DealtPlayerAction(int pid, Card[] cards, String line){
		super(ActionType.DEALT, pid, line);
		this.cards = cards;
	}
	public Card[] getCards(){
		return cards;
	}
	@Override
	
	public State applyToState(State s) {
		// FIXME ? is this broken?
		Card[][] cards = new Card[3][2];
		cards[0] = s.getP1();
		cards[1] = s.getP2();
		cards[2] = s.getP3();
		cards[getPID()] = this.cards;
		
		return new State(this, s.getChips(), s.getBets(), s.getPot(),
				s.getChipsToCall(), s.getDealt(), cards[0], cards[1], cards[2]);
	}
}
