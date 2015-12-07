package com.tabletop.uta;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.Mapping;
import com.tabletop.uta.apptopology.Task;
import com.tabletop.uta.config.Config;
import com.tabletop.uta.loadbalance.LoadBalanceUtils;
import com.tabletop.uta.loadbalance.NumaNode;
import com.tabletop.uta.machtopology.Processor;


public class Main {
	
	public static int LOAD_BALANCE_STEPS = 6100;
	public static int RUN_WHAT = 1;
	public static int CORES = 8;
	public static int PROCESSORS = 5;
	public static int TASKS = 150;
	public static int STEPSIZE = 5;
	public static int AMOUNT = 2000;
	public static boolean DEBUG = false;
	public static int NUMA_NODE_DISTANCE = 2;
	
	public static void main(String[] args){		
		Algorithm algorithm = new Algorithm("Test", 10, 3, 200);
		double loadBalanceCost;
		int i, j;
		switch(RUN_WHAT){
		case 1:
			Config.initProcessors(CORES, PROCESSORS);
			Config.initTasks(TASKS, STEPSIZE, AMOUNT);
			Mapping.calculateInitialMapping();
			i=1;
			while (algorithm.runOneExecutionStep(i++));		
			System.out.println(algorithm.getExecutionTime() + " | " +  algorithm.getCommunicationCost());
			System.out.println("Total Execution Time: " + (algorithm.getExecutionTime() + algorithm.getCommunicationCost()));
			break;
		case 2:		
			Config.initProcessors(CORES, PROCESSORS);
			Config.initTasks(TASKS, STEPSIZE, AMOUNT);
	
			LoadBalanceUtils.calculateNumaNodes(NUMA_NODE_DISTANCE);
			Mapping.calculateInitialNumaMapping();
		
		
			j=1;
			while (algorithm.runOneExecutionStep(j++));
			System.out.println(algorithm.getExecutionTime() + " | " +  algorithm.getCommunicationCost());
			System.out.println("Total Execution Time: " + (algorithm.getExecutionTime() + algorithm.getCommunicationCost()));
			break;
		case 3:		
			Config.initProcessors(CORES, PROCESSORS);
			Config.initTasks(TASKS, STEPSIZE, AMOUNT);
	
			LoadBalanceUtils.calculateNumaNodes(NUMA_NODE_DISTANCE);
			Mapping.calculateInitialNumaMapping();
		
		
			j=1;
			loadBalanceCost = 0.0;
			while (algorithm.runOneExecutionStep(j)){
				if (j++ % LOAD_BALANCE_STEPS == 0){
					if (DEBUG){
						Processor.printAllProcessors();
						System.out.println("  End Print Before Mapping ");
					}
					loadBalanceCost += Mapping.calculateNumaMapping();
					if (DEBUG){
						Processor.printAllProcessors();
						System.out.println("  End Print After Mapping ");
					}
				}
			}
			System.out.println(algorithm.getExecutionTime() + " | " +  algorithm.getCommunicationCost() + " | " + loadBalanceCost);
			System.out.println("Total Execution Time: " + (algorithm.getExecutionTime() + algorithm.getCommunicationCost() + loadBalanceCost));
			break;
		}

		//divide algorithm into steps - done
		//calculate numa nodes - done
		//calculate compute or communication bound - not done
		//calculate new mapping - done
		//calculate load balancing cost - done
		
		
		
	}
}
