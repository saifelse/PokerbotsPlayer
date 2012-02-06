package player;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import events.*;

import poker.Card;
import poker.Chips;
import poker.Config;
import poker.Hand;
import poker.Match;
import poker.State;

public class HandHistoryParser {
	private BufferedReader in;
	
	// TODO: ALL-ins?
	private static final State INIT_STATE = new State(null, new Chips(Config.CHIPSTACK, Config.CHIPSTACK, Config.CHIPSTACK), new Chips(0,0,0), 0, 0, new Card[0], new Card[0], new Card[0], new Card[0]);
	private static final String header = "6\\.S912 MIT Pokerbots - .+";
	private static final String seat1 = "Seat 1 \\((Button|SB|BB)\\): (.+?) (-?\\d+)"; // Seat 1: saif (100)
	private static final String seat2 = "Seat 2 \\((Button|SB|BB)\\): (.+?) (-?\\d+)"; // Seat 2: saji (100)
	private static final String seat3 = "Seat 3 \\((Button|SB|BB)\\): (.+?) (-?\\d+)"; // Seat 3: kpng (100)
	
	private static final String deal_flop = "\\*\\*\\* FLOP \\*\\*\\* \\[(..) (..) (..)\\]";
	private static final String deal_turn = "\\*\\*\\* TURN \\*\\*\\* \\[(..) (..) (..)\\] \\[(..)\\]";
	private static final String deal_river = "\\*\\*\\* RIVER \\*\\*\\* \\[(..) (..) (..) (..)\\] \\[(..)\\]";
	private static final String dealt = "Dealt to (.+?) \\[(..) (..)\\]";
	
	private static final String blind = "(.+?) posts the blind of (\\d+)";
	private static final String bets = "(.+?) bets (\\d+)"; 
	private static final String calls = "(.+?) calls"; 
	private static final String raises = "(.+?) raises to (\\d+)"; 
	private static final String checks = "(.+?) checks"; 
	private static final String folds = "(.+?) folds"; 
	private static final String uncalled = "Uncalled bet of (\\d+) returned to (.+)";
	private static final String shows = "(.+?) shows (..) (..) \\((.+?)\\)";
	private static final String wins = "(.+?) wins the pot \\((\\d+)\\)"; 
	private static final String ties = "(.+?) ties for the pot \\((\\d+)\\)";
	private static final String hand_start = "Hand #(\\d+)";
	private static final String hand_end = "";
	
	private static final Pattern p_header = Pattern.compile(header);
	private static final Pattern p_seat1 = Pattern.compile(seat1);
	private static final Pattern p_seat2 = Pattern.compile(seat2);
	private static final Pattern p_seat3 = Pattern.compile(seat3);
	private static final Pattern p_blind = Pattern.compile(blind);
	private static final Pattern p_bets = Pattern.compile(bets);
	private static final Pattern p_calls = Pattern.compile(calls);
	private static final Pattern p_raises = Pattern.compile(raises);
	private static final Pattern p_checks = Pattern.compile(checks);
	private static final Pattern p_folds = Pattern.compile(folds);
	private static final Pattern p_wins = Pattern.compile(wins);
	private static final Pattern p_dealt = Pattern.compile(dealt);
	private static final Pattern p_deal_flop = Pattern.compile(deal_flop);
	private static final Pattern p_deal_river = Pattern.compile(deal_river);
	private static final Pattern p_deal_turn = Pattern.compile(deal_turn);
	private static final Pattern p_uncalled = Pattern.compile(uncalled);
	private static final Pattern p_shows = Pattern.compile(shows);
	private static final Pattern p_ties = Pattern.compile(ties);
	
	private static final Pattern p_hand_start = Pattern.compile(hand_start);
	private static final Pattern p_hand_end = Pattern.compile(hand_end);
	
	/*
	public static void main(String[] args) throws FileNotFoundException{
		FileInputStream f = new FileInputStream("test.txt");
		new HandHistoryParser(f);
	}
	*/
	public HandHistoryParser(InputStream history){
		in = new BufferedReader(new InputStreamReader(history));
		/*
		try {
			parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnparseableStringException e){
			e.printStackTrace();
		}*/
	}
	public Match parse() throws IOException {
		Map<String, Integer> seats = new HashMap<String, Integer>();
		List<Hand> hands = new ArrayList<Hand>();
		
		String line;
		Matcher m;
		Hand h = null;
		while((line = in.readLine())!=null){
			m = p_header.matcher(line);
			if(m.matches()){
				// FIRST LINE OF FILE
				continue;
			}
			m = p_hand_start.matcher(line);
			if(m.matches()){
				h = new Hand(INIT_STATE, new Chips(0,0,0));
				continue;
			}
			// Hand must have been started by this point
			if(h == null) throw new UnparseableStringException(line);
			
			m = p_hand_end.matcher(line);
			if(m.matches()){
				h.addAction(new EndOfHandAction(line));
				hands.add(h);
				continue;
			}
			m = p_seat1.matcher(line);
			if(m.matches()){
				seats.put(m.group(2), 0);
				String b = m.group(1);
				int chips = Integer.parseInt(m.group(3));
				h.setNetChips(h.getNetChips().set(0, chips));
				continue;
			}
			m = p_seat2.matcher(line);
			if(m.matches()){
				seats.put(m.group(2), 1);
				String b = m.group(1);
				int chips = Integer.parseInt(m.group(3));
				h.setNetChips(h.getNetChips().set(1, chips));
				continue;
			}
			m = p_seat3.matcher(line);
			if(m.matches()){
				seats.put(m.group(2), 2);
				String b = m.group(1);
				int chips = Integer.parseInt(m.group(3));
				h.setNetChips(h.getNetChips().set(2, chips));
				continue;
			}		
			m = p_blind.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				int chips = Integer.parseInt(m.group(2));
				h.addAction(new BlindAction(pid, chips, line));
				continue;
			}
			m = p_bets.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				int chips = Integer.parseInt(m.group(2));
				h.addAction(new BetAction(pid, chips, line));
				continue;
			}
			m = p_calls.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				h.addAction(new CallAction(pid, line));
				continue;
			}
			m = p_raises.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				int chips = Integer.parseInt(m.group(2));
				h.addAction(new RaiseAction(pid, chips, line));
				continue;
			}
			m = p_checks.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				h.addAction(new CheckAction(pid, line));
				continue;
			}
			m = p_folds.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				h.addAction(new FoldAction(pid, line));
				continue;
			}
			m = p_wins.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				int chips = Integer.parseInt(m.group(2));
				h.addAction(new WinAction(pid, chips, line));
				continue;
			}
			m = p_ties.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				int chips = Integer.parseInt(m.group(2));
				h.addAction(new TieAction(pid, chips, line));
				continue;
			}
			
			m = p_dealt.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				Card[] cards = new Card[]{Card.get(m.group(2)), Card.get(m.group(3))};
				h.addAction(new DealtPlayerAction(pid, cards, line));
				continue;
			}
			m = p_deal_flop.matcher(line);
			if(m.matches()){
				Card[] cards = new Card[]{Card.get(m.group(1)), Card.get(m.group(2)), Card.get(m.group(3))};
				h.addAction(new DealtFlopAction(cards, line));
				continue;
			}
			m = p_deal_turn.matcher(line);
			if(m.matches()){
				Card[] cards = new Card[]{Card.get(m.group(1)), Card.get(m.group(2)), Card.get(m.group(3)), Card.get(m.group(4))};
				h.addAction(new DealtTurnAction(cards, line));
				continue;
			}
			m = p_deal_river.matcher(line);
			if(m.matches()){
				Card[] cards = new Card[]{Card.get(m.group(1)), Card.get(m.group(2)), Card.get(m.group(3)), Card.get(m.group(4)), Card.get(m.group(5))};
				h.addAction(new DealtRiverAction(cards, line));
				continue;
			}
			m = p_uncalled.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(2));
				int chips = Integer.parseInt(m.group(1));
				h.addAction(new UncalledBetAction(pid, chips, line));
				continue;
			}
			m = p_shows.matcher(line);
			if(m.matches()){
				int pid = seats.get(m.group(1));
				String rank = m.group(4);
				Card[] cards = new Card[]{Card.get(m.group(2)), Card.get(m.group(3))};
				h.addAction(new ShowAction(pid, cards, rank, line));
				continue;
			}

			throw new UnparseableStringException(line);
		}
		
		return new Match(hands, seats);
	}
	
	public class UnparseableStringException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public UnparseableStringException(String msg){
			super("Unexpected string encountered while parsing: ["+msg+"]");
		}
	}
}
