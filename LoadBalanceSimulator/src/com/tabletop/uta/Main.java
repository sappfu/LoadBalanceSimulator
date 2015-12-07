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
//		Config.initProcessors(4, 5);
//		Config.initTasks(500, 2, 20);
//		Mapping.calculateInitialMapping();
//		int i=1;
//		while (algorithm.runOneExecutionStep()){
//			if (i % 15 == 0){

//			}
//		}
		
//		System.out.println(algorithm.getExecutionTime() + " | " +  algorithm.getCommunicationCost());
		
//		Config.clearConfig(true, true, false, true);
		
		Config.initProcessors(4, 5);
		Config.initTasks(500, 2, 20);
		
		LoadBalanceUtils.calculateNumaNodes(5);
		Mapping.calculateNumaMapping();
		
		
		int j=1;
		while (algorithm.runOneExecutionStep()){
			if (j++ % 150 == 0){
				Mapping.unassignAllProcessors();
				Mapping.calculateNumaMapping();
			}
		}
		
		System.out.println(algorithm.getExecutionTime() + " | " +  algorithm.getCommunicationCost());
		
//		NumaNode.printAllNumaNodes();33605.0 | 21540.000000040996
		
		//divide algorithm into steps - done
		//calculate numa nodes - done
		//calculate compute or communication bound
		//calculate new mapping
		//calculate load balancing cost
		
		//migration of data cost?
		
		
	}
}
