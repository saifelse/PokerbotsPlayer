package poker;

import java.util.List;
import java.util.Map;

public class Match {
	private List<Hand> hands;
	private Map<String, Integer> seat;
	public Match(List<Hand> hands, Map<String, Integer> seat){
		this.hands = hands;
		this.seat = seat;
	}
	public List<Hand> getHands() {
		return hands;
	}
	public Map<String, Integer> getSeat() {
		return seat;
	}
}
