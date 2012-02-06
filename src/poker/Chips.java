package poker;

import java.util.Arrays;

public class Chips {
	int[] chips;

	public Chips(int a, int b, int c) {
		this.chips = new int[] { a, b, c };
	}

	public int get(int i) {
		return chips[i];
	}
	public Chips set(int i, int v) {
		switch (i) {
		case 0:
			return new Chips(v, chips[1], chips[2]);
		case 1:
			return new Chips(chips[0], v, chips[2]);
		case 2:
			return new Chips(chips[0], chips[1], v);
		default:
			return new Chips(chips[0], chips[1], chips[2]);
		}
	}
	public Chips mod(int i, int d) {
		switch (i) {
		case 0:
			return new Chips(chips[0] + d, chips[1], chips[2]);
		case 1:
			return new Chips(chips[0], chips[1] + d, chips[2]);
		case 2:
			return new Chips(chips[0], chips[1], chips[2] + d);
		default:
			return new Chips(chips[0], chips[1], chips[2]);
		}
	}
	public int total(){
		return chips[0]+chips[1]+chips[2];
	}
	public String toString(){
		return "Chips("+Arrays.toString(chips)+")";
	}
}
