package GUI;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.util.ColorIterator;

import javax.swing.*;
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
	    data = new HashMap<String, int[]>();
	    // Create an ITrace: 
	    ITrace2D trace = new Trace2DSimple(); 
	    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
	    chart.addTrace(trace);    
	    // Add all points, as it is static: 
	   
	    Random random = new Random();
	    int[] testData = new int[101];
	    for(int i=0;i<20;i++){
	     testData[i] = Math.abs((random.nextInt()*10+i) % 20);
	     System.out.println(testData[i]);
	    }
	    addDataPoints("masturbation", testData);
	    
	    testData = new int[101];
	    for(int i=0;i<20;i++){
	     testData[i] = Math.abs((random.nextInt()*10+i) % 20);
	     System.out.println(testData[i]);
	    }
	    addDataPoints("hello", testData);
	    
	    setHand(10);
	    
	    GroupLayout layout = new GroupLayout(this);
	    layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(chart));
	    layout.setVerticalGroup(layout.createParallelGroup().addComponent(chart));
	    setLayout(layout);
	    //add(chart);
		/*
        setLayout(null);
        this.setSize(400, 400);
        this.data = new HashMap<String, int[]>();
        this.chart = new Chart2D();
        ITrace2D trace = new Trace2DSimple(); 
        // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
        chart.addTrace(trace);    
        Random random = new Random();
        for(int i=100;i>=0;i--){
          trace.addPoint(i,random.nextDouble()*10.0+i);
        }
        //this.addDataPoints("test", new int[] {1, 1});
        //this.setHand(80);
        this.add(chart);
        this.setVisible(true);
        */

	}

    public void addDataPoints(String name, int [] data) {
        this.data.put(name, data);
    }
    
    public void setHand(int i) {
        this.chart.removeAllTraces();
        ColorIterator ci = new ColorIterator();
        //for (int j = 0; j < data.size(); j++) {
            for (String player : data.keySet()) {
                ITrace2D trace = new Trace2DSimple();
                trace.setColor(ci.next());
                this.chart.addTrace(trace);
                int[] bankroll = data.get(player);
                System.out.println("Doing trace for "+player);
                for (int k = 0; k < i; k++) {
                    trace.addPoint(k, bankroll[k]);
                    System.out.println("bank roll: "+bankroll[k]);
                }

            }
        //}
    }

}
