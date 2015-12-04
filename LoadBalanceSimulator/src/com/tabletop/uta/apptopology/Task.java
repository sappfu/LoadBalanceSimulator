package com.tabletop.uta.apptopology;

import java.util.ArrayList;
import java.util.Random;
import com.tabletop.uta.machtopology.Processor;

public class Task {

	private static ArrayList<Task> factory = null;
	private static double rangeMin = 1000.0;
	private static double rangeMax = 2000.0;
	private static int count = 0;
	
	//Singleton pattern
	public static ArrayList<Task> getFactory(){
		if (factory == null){
			factory = new ArrayList<Task>();
		}
		return factory;
	}
	
	public static Task getWorkLoad(int index){
		if (factory == null){
			Task.getFactory();
			for (int i=0;i<index;i++){
				Task.generateWorkLoad(i);
			}
		}
		return factory.get(index-1);
	}
	
	public static int generateWorkLoad(){
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		new Task(randomValue/10);
		return factory.size()-1; //new workload will be added at this index 
	}
	
	public static Task generateWorkLoad(int index){
		return new Task(rangeMin/10 + index*10);
	}
	
	double taskSize;
	int taskId;
	Processor assignedProcessor;
	ArrayList<Integer> linkedTasks;
	
	public Task(double taskSize, Integer... linkedTasks){
		if (factory == null){
			Task.getFactory();
		}
		this.taskSize = taskSize;
		this.taskId = count++;
		this.linkedTasks = new ArrayList<Integer>();
		for (Integer linkedTask : linkedTasks){
			this.linkedTasks.add(linkedTask);
		}
		factory.add(this);
	}
	
	public void assignToProcessor(Processor p){
		this.assignedProcessor = p;
	}
	
	public int[] getTotalDistanceToLinkedTasks(){
		int[] totalDistance = new int[2];
		totalDistance[0] = 0;
		totalDistance[1] = 0;
		int[] tmp;
		for (Integer i : this.linkedTasks){
			tmp = this.calculateDistance(i);
			totalDistance[0] += tmp[0];
			totalDistance[1] += tmp[1];
		}
		return totalDistance;
	}
	
	public double getTaskSize() {
		return taskSize;
	}

	public int[] calculateDistance(int taskId){
		return this.assignedProcessor.calculateDistance(factory.get(taskId).assignedProcessor);
	}
	
	public String toString(){
		return "Task size: " + this.taskSize;
	}

	
	
	
	
	
}
