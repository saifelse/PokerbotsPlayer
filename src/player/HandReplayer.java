package player;

import java.util.ArrayList;
import java.util.List;

import events.Action.ActionType;
import events.DelayAction;
import poker.Hand;
import poker.State;

public class HandReplayer {
	private Hand hand;
	private int pos;

	public HandReplayer(Hand hand) {
		this.hand = hand;
		this.pos = 0;
	}

	public State getState() {
		return hand.getState(pos);
	}
	public Hand getHand(){
		return hand;
	}
	public void prev() {
		if(pos > 0)
			pos--;
	}
	public void next() {
		if (pos < hand.size() - 1)
			pos++;
	}

	// SYNCHRONIZING
	
	private ActionType next(ActionType waitOnAction) {
		// Delay if not ready / at end.
		if (pos >= hand.size() - 1
				|| hand.getState(pos + 1).getAction().getActionType() == waitOnAction) {
			hand.addDelayAfter(pos);
		}
		pos++;
		// Return next ActionType
		if (pos + 1 < hand.size())
			return hand.getState(pos + 1).getAction().getActionType();
		else
			return hand.getState(pos).getAction().getActionType();
	}
	
	public static void synchronizeHands(List<Hand> hands) {
		List<HandReplayer> handReplayers = new ArrayList<HandReplayer>();
		boolean finished = false;
		boolean synched = true;
		ActionType waitType = ActionType.FLOP;
		for (Hand h : hands) {
			HandReplayer hr = new HandReplayer(h);
			handReplayers.add(hr);
		}
		while(!finished){
			//System.out.println("Trying to do shit...");
			finished = true;
			synched = true;
			//System.out.println("-------");
			for (HandReplayer hr : handReplayers) {
				ActionType nextActionType = hr.next(waitType);
				synched = synched && (nextActionType == waitType || nextActionType == ActionType.END_OF_HAND);
				finished = finished && nextActionType == ActionType.END_OF_HAND;
				//System.out.println(nextActionType);
			}
			//Thread.sleep(500);
			if (synched) {
				waitType = nextWaitType(waitType);
			}
		}
	}
	
	private static ActionType nextWaitType(ActionType type) {
		switch (type) {
		case FLOP:
			return ActionType.TURN;
		case TURN:
			return ActionType.RIVER;
		case RIVER:
		default:
			return ActionType.END_OF_HAND;
		}
	}
}
