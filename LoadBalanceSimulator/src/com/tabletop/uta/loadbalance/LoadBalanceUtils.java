package com.tabletop.uta.loadbalance;

import java.util.ArrayList;

import com.tabletop.uta.machtopology.Processor;

public class LoadBalanceUtils {

	
	public static void calculateNumaNodes(int maxDistance){
		ArrayList<Processor> processors = Processor.getFactory();
		int assigned = 0, start = 0, current = 0;
		double[][] graph = Processor.constructDistanceGraph();
		while (processors.size() > start){
			NumaNode node = new NumaNode();
			for (int i=0; i<processors.size();i++){
				if (graph[current][i] < maxDistance){
					node.addProcessor(processors.get(i));
					for (int j=0;j<processors.size();j++){
						graph[j][i] = 99999;
					}
					start++;
				}
			}
			current = start;
		}
	}
	
	
	
}
