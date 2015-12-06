package com.tabletop.uta;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.Mapping;
import com.tabletop.uta.apptopology.Task;
import com.tabletop.uta.config.Config;
import com.tabletop.uta.machtopology.Processor;


public class Main {
	
	public static void main(String[] args){		
		Config.initProcessors(4, 5);
		Config.initTasks(250, 2, 20);
		
		Mapping.calculateInitialMapping();
		
		
		Algorithm algorithm = new Algorithm("Test", 3, 10, 200);
		
		
		while (algorithm.runOneExecutionStep()){
			System.out.println(algorithm.getExecutionTime());
		}
		
		System.out.println(algorithm.calculateExecutionTime());
		
		
		
		//divide algorithm into steps - done
		//calculate numa nodes
		//calculate compute or communication bound
		//calculate new mapping
		//calculate load balancing cost
		
		//migration of data cost?
		
		
	}
}
