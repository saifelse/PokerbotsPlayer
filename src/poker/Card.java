package poker;
import java.util.HashMap;
import java.util.Map;


public class Card {
	private static Map<String, Card> cards = new HashMap<String, Card>();
	private static final char[] VALUE_NAMES = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
	
	private Suit suit;
	private int value;
	
	private Card(Suit s, int v){
		suit = s;
		value = v;
	}
	public static Card get(String c){
		if(!cards.containsKey(c)){
			int v;
			Suit s;
			switch(c.charAt(0)){
			case 'A':
				v = 1; break;
			case '2':
				v = 2; break;
			case '3':
				v = 3; break;
			case '4':
				v = 4; break;
			case '5':
				v = 5; break;
			case '6':
				v = 6; break;
			case '7':
				v = 7; break;
			case '8':
				v = 8; break;
			case '9':
				v = 9; break;
			case 'T':
				v = 10; break;
			case 'J':
				v = 11; break;
			case 'Q':
				v = 12; break;
			case 'K':
				v = 13; break;
			default:
				throw new UnexpectedCardException(c);
			}
			switch(c.charAt(1)){
			case 'c':
				s = Suit.CLUB; break;
			case 'd':
				s = Suit.DIAMOND; break;
			case 'h':
				s = Suit.HEART; break;
			case 's':
				s = Suit.SPADE; break;
			default:
				throw new UnexpectedCardException(c);
			}
			cards.put(c, new Card(s, v));
		}	
		return cards.get(c);
	}
	public String toFilename(){
		return getValueString()+getSuitString()+".gif";
	}
	public enum Suit {
		HEART,
		CLUB,
		DIAMOND,
		SPADE
	}
	public String getValueString(){
		return ""+VALUE_NAMES[value-1];
	}
	public String getSuitString(){
		switch(suit){
		case CLUB:
			return "c";
		case DIAMOND:
			return "d";
		case HEART:
			return "h";
		case SPADE: default:
			return "s";
		}
	}
	public String toString(){
		 return getValueString()+getSuitString();
	}
	public static class UnexpectedCardException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UnexpectedCardException(String msg){
			super("Unexpected card: ["+msg+"]");
		}
	}
}
