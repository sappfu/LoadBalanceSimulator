package com.tabletop.uta;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.Mapping;
import com.tabletop.uta.apptopology.Task;
import com.tabletop.uta.machtopology.Processor;


public class Main {
	
	public static void main(String[] args){
		Processor processor = new Processor(1, 1);
		Processor processor2 = new Processor(2, 1);
		Processor processor3 = new Processor(1, 2);
		Processor processor4 = new Processor(1, 3);
		
		Task task0 = new Task(10, 1);
		Task task1 = new Task(5, 0);
		Task task2 = new Task(15, 0,1,3);
		Task task3 = new Task(12, 1,2);
		
		Mapping.assignToProcessor(processor, task0);
		Mapping.assignToProcessor(processor2, task1);
		Mapping.assignToProcessor(processor3, task2);
		Mapping.assignToProcessor(processor4, task3);
		
		
		Algorithm algorithm = new Algorithm("Test", 3, 10, 200, 100);
		System.out.println(algorithm.calculateExecutionTime());
		
	}
}
