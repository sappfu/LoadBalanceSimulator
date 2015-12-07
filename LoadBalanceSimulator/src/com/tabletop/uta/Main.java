package com.tabletop.uta;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.Mapping;
import com.tabletop.uta.apptopology.Task;
import com.tabletop.uta.config.Config;
import com.tabletop.uta.loadbalance.LoadBalanceUtils;
import com.tabletop.uta.loadbalance.NumaNode;
import com.tabletop.uta.machtopology.Processor;


public class Main {
	
	public static void main(String[] args){		
		Algorithm algorithm = new Algorithm("Test", 3, 10, 200);
//		Config.initProcessors(2, 2);
//		Config.initTasks(5, 2, 200);
//		Mapping.calculateInitialMapping();
//		int i=1;
//		while (algorithm.runOneExecutionStep(i++));		
//		System.out.println(algorithm.getExecutionTime() + " | " +  algorithm.getCommunicationCost());
//		
//		Config.clearConfig(true, true, false, true);
		
		Config.initProcessors(8, 5);
		Config.initTasks(15, 5, 2000);
		
//		Task.printTaskGraph();
//		Task.printAllTasks();
		
		LoadBalanceUtils.calculateNumaNodes(5);
		Mapping.calculateInitialNumaMapping();
		
		
		int j=1;
		boolean debug = false;
		double loadBalanceCost = 0.0;
		while (algorithm.runOneExecutionStep(j)){
			if (j++ % 1000 == 0){
				if (debug){
					Processor.printAllProcessors();
					System.out.println("  End Print Before Mapping ");
				}
				loadBalanceCost += Mapping.calculateNumaMapping();
				if (debug){
					Processor.printAllProcessors();
					System.out.println("  End Print After Mapping ");
				}
			}
		}
		
		System.out.println(algorithm.getExecutionTime() + " | " +  algorithm.getCommunicationCost() + " | " + loadBalanceCost);
		System.out.println("Total Execution Time: " + (algorithm.getExecutionTime() + algorithm.getCommunicationCost() + loadBalanceCost));
		
//		NumaNode.printAllNumaNodes();33605.0 | 21540.000000040996
		
		//divide algorithm into steps - done
		//calculate numa nodes - done
		//calculate compute or communication bound
		//calculate new mapping
		//calculate load balancing cost
		
		//migration of data cost?
		
		
	}
}
