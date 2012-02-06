package GUI;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.util.ColorIterator;

import javax.swing.*;
import java.util.HashMap;
import java.util.Random;

public class Graph extends JPanel {

    public HashMap<String, int[]> data;
    public Chart2D chart;

	private static final long serialVersionUID = 1L;

	public Graph(PokerReplayer pr) {
        setLayout(null);
        this.setSize(400, 400);
        this.data = new HashMap<String, int[]>();
        this.chart = new Chart2D();
        this.addDataPoints("test", new int[] {1, 1});
        this.setHand(80);
        this.add(chart);
        this.setVisible(true);

	}

    public void addDataPoints(String name, int [] data) {
        data = new int[100];
        Random g = new Random();
        for (int i = 0; i < 100; i++) {
            data[i] = (int) g.nextDouble()*10 + i;
        }
        this.data.put(name, data);

    }

    public void setHand(int i) {
        this.chart.removeAllTraces();
        ColorIterator ci = new ColorIterator();
        for (int j = 0; j < data.size(); j++) {
            for (String player : data.keySet()) {
                ITrace2D trace = new Trace2DSimple();
                trace.setColor(ci.next());
                this.chart.addTrace(trace);
                int[] bankroll = data.get(player);
                for (int k = 0; k < i; k++) {
                    trace.addPoint(k, bankroll[k]);
                    System.out.println(bankroll[k]);
                }

            }
        }
    }

}
