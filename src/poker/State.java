package poker;

import java.util.Arrays;

import events.Action;

// TODO avoid using Card[] ? List<Card> ?
public class State {
	private final Action action;
	private final Chips chips;
	private final Chips bets;
	private final int pot;
	private final int chipsToCall;
	private final Card[] dealt;
	private final Card[] p1;
	private final Card[] p2;
	private final Card[] p3;

	public State(Action action, Chips chips, Chips bets, int pot,
			int chipsToCall, Card[] dealt, Card[] p1, Card[] p2, Card[] p3) {
		this.action = action;
		this.chips = chips;
		this.bets = bets;
		this.pot = pot;
		this.chipsToCall = chipsToCall;
		this.dealt = dealt.clone();
		this.p1 = p1.clone();
		this.p2 = p2.clone();
		this.p3 = p3.clone();
	}

	public Action getAction() {
		return action;
	}

	public Chips getChips() {
		return chips;
	}

	public Chips getBets() {
		return bets;
	}

	public int getPot() {
		return pot;
	}

	public int getChipsToCall() {
		return chipsToCall;
	}

	public Card[] getDealt() {
		return dealt;
	}

	public String toString() {
		return "State(action=" + action + ",chips=" + chips + ",bets=" + bets
				+ ",pot=" + pot + ",chipsToCall=" + chipsToCall + ",dealt="
				+ Arrays.toString(dealt) + ",p1=" + Arrays.toString(p1)
				+ ",p2=" + Arrays.toString(p2) + ",p3=" + Arrays.toString(p3)
				+ ")";
	}

	public Card[] getP1() {
		return p1;
	}

	public Card[] getP2() {
		return p2;
	}

	public Card[] getP3() {
		return p3;
	}
}
