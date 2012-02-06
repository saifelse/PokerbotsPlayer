package GUI;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.util.ColorIterator;

import javax.swing.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Graph extends JPanel {

    public Map<String, int[]> data;
    public Chart2D chart;

	private static final long serialVersionUID = 1L;

	public Graph(PokerReplayer pr) {
		// Create a chart:  
	    chart = new Chart2D();
	    chart.setBackground(Color.BLACK);
	    chart.setForeground(Color.WHITE);
	    data = new HashMap<String, int[]>();
	    // Create an ITrace: 
	    ITrace2D trace = new Trace2DSimple(); 
	    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
	    chart.addTrace(trace);    
	    // Add all points, as it is static: 
	    setHand(0);
	    
	    // Layout
	    GroupLayout layout = new GroupLayout(this);
	    layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(chart));
	    layout.setVerticalGroup(layout.createParallelGroup().addComponent(chart));
	    setLayout(layout);

	}
	public void addData(Map<String, int[]> mapData){
		data.clear();
		for(String username : mapData.keySet()){
			addDataPoints(username, mapData.get(username));
		}
	}
    public void addDataPoints(String name, int [] data) {
        this.data.put(name, data);
    }
    
    public void setHand(int i) {
        this.chart.removeAllTraces();
        ColorIterator ci = new ColorIterator();
        ci.setSteps(2);
        for (String player : data.keySet()) {
            ITrace2D trace = new Trace2DSimple();
            trace.setName(player);
            trace.setColor(ci.next());
            this.chart.addTrace(trace);
            int[] bankroll = data.get(player);
            for (int k = 0; k < i; k++) {
                trace.addPoint(k, bankroll[k]);
            }
        }
    }

}
