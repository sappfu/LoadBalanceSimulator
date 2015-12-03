package com.tabletop.uta;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.WorkLoad;
import com.tabletop.uta.machtopology.Processor;


public class Main {
	
	public static void main(String[] args){
		Processor processor = new Processor(1, 1);
		Processor processor2 = new Processor(2, 1);
		Processor processor3 = new Processor(3, 1);
		Processor processor4 = new Processor(4, 1);
		
		WorkLoad workload = new WorkLoad(3, 30000000);
		Algorithm algorithm = new Algorithm(3, 200, 100);
		
		workload.divideWorkLoad(processor, processor2, processor3, processor4);
		
		System.out.println(processor);
		System.out.println(processor2);
		System.out.println(processor3);
		System.out.println(processor4);
	}
}
