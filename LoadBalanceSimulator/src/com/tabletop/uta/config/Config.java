package com.tabletop.uta.config;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.Mapping;
import com.tabletop.uta.apptopology.Task;
import com.tabletop.uta.loadbalance.NumaNode;
import com.tabletop.uta.machtopology.Processor;

public class Config {
	
	public static void clearConfig(boolean a, boolean p, boolean n, boolean t){
		if (a)
			Algorithm.clear();
		if (p)
			Processor.clear();
		if (n)
			NumaNode.clear();
		if (t)
			Task.clear();
	}

	//total num processors is cores * processors
	public static void initProcessors(int cores, int processors){
		for (int i=0;i<processors;i++){
			for (int j=0;j<cores;j++){
				Processor processor = new Processor(j, i);
			}
		}
	}
	
	public static void initTasks(int tasks, int step, int amount){
		int nextFour, nextThree, nextTwo, next, size, stepSize;
		Task task;
		for (int i=0;i<tasks;i++){
			stepSize = step + i % 10;
			if (i % 2 == 0){
				size = amount + i * 5;
				if (i == tasks - 1){
					nextFour = 3;
					nextThree = 2;
					nextTwo = 1;
					next = 0;
				}
				else if (i == tasks - 2){
					nextFour = 2;
					nextThree = 1;
					nextTwo = 0;
					next = i + 1;
				}
				else if (i == tasks - 3){
					nextFour = 1;
					nextThree = 0;
					nextTwo = i + 2;
					next = i + 1;
				}
				else if (i == tasks - 4){
					nextFour = 0;
					nextThree = i + 3;
					nextTwo = i + 2;
					next = i + 1;
				}
				else {
					nextFour = i + 4;
					nextThree = i + 3;
					nextTwo = i + 2;
					next = i + 1;
				}
				switch(i % 6){
					case 0:
						task = new Task(size, stepSize, next);
						break;
					case 2:
						task = new Task(size, stepSize, next, nextTwo, nextThree);
						break;
					case 4:
						task = new Task(size, stepSize);
						break;
				}
			}
			else {
				size = Math.abs(amount - i * 5);
				if (i == 3){
					nextFour = tasks - 1;
					nextThree = i - 3;
					nextTwo = i - 2;
					next = i - 1;
				}
				else if (i == 2){
					nextFour = tasks - 2;
					nextThree = tasks - 1;
					nextTwo = i - 2;
					next = i - 1;
				}
				else if (i == 1){
					nextFour = tasks - 3;
					nextThree = tasks - 2;
					nextTwo = tasks - 1;
					next = i - 1;
				}
				if (i == 0){
					nextFour = tasks - 4;
					nextThree = tasks - 3;
					nextTwo = tasks - 2;
					next = tasks - 1;
				}
				else {
					nextFour = i - 4;
					nextThree = i - 3;
					nextTwo = i - 2;
					next = i - 1;
				}
				switch(i % 6){
					case 1:
						task = new Task(size, stepSize, next);
						break;
					case 3:
						task = new Task(size, stepSize, next, nextTwo, nextThree);
						break;
					case 5:
						task = new Task(size, stepSize);
						break;
				}
			}
		}
	}
	
	
	
}
