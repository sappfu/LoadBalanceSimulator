package com.tabletop.uta.machtopology;

import java.util.ArrayList;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.Task;

public class Processor {

	private static ArrayList<Processor> factory = null;
	private static double rangeMin = 1000.0;
	private static double rangeMax = 2000.0;
	public static double innerNodeCommunicationCost = 3;
	public static double outerNodeCommunicationCost = 5;
	
	//Singleton pattern
	public static ArrayList<Processor> getFactory(){
		if (factory == null){
			factory = new ArrayList<Processor>();
		}
		return factory;
	}
	
	public static Processor getProcessor(int index){
		if (factory == null){
			Algorithm.getFactory();
			for (int i=0;i<index;i++){
				Algorithm.generateAlgorithm(i);
			}
		}
		return factory.get(index-1);
	}
	
	public static void printAllProcessors(){
		for (Processor p : factory){
			System.out.println(p.toString());
		}
	}
	
	
	
	private int core;
	private int processor;
	
	private ArrayList<Task> assignedTasks;
	
	
	public Processor(int core, int processor){
		if (factory == null){
			Processor.getFactory();
		}
		this.core = core;
		this.processor = processor;
		assignedTasks = new ArrayList<Task>();
		factory.add(this);
	}

	public int[] calculateDistance(Processor p){
		int[] distance = new int[2]; //inner distance and outer distance
		distance[1] = Math.abs(this.getProcessor() - p.getProcessor());
		if (distance[1] > 0){
			distance[0] = 0;
		}
		else{
			distance[0] = Math.abs(this.getCore() - p.getCore());
		}
		return distance;
	}
	
	public double getExecutionTimeForTasks(){
		double time = 0.0;
		for (Task t : this.assignedTasks){
			time += t.getTaskSize();
		}
		return time;
	}
	
	public double getRemainingExecutionTimeForTasks(){
		double time = 0.0;
		for (Task t : this.assignedTasks){
			time += t.getRemainingTask();
		}
		return time;
	}
	
	
	
	public Task getNextTask(){
		for (int i=0;i<assignedTasks.size();i++){
			if (assignedTasks.get(i).getRemainingTask() > 0){
				return assignedTasks.get(i);
			}
		}
		return null;
	}
	
	public String toString(){
		String tmp = "";
		for (Task task : assignedTasks){
			tmp += task.toString() + " | ";
		}
		return "Core: " + core + " Processor: " + processor + " Task list: " + tmp;
	}


	public int getCore() {
		return core;
	}

	public void setCore(int core) {
		this.core = core;
	}

	public int getProcessor() {
		return processor;
	}

	public void setProcessor(int processor) {
		this.processor = processor;
	}

	public double getInnerNodeCommunicationCost() {
		return innerNodeCommunicationCost;
	}

	public void setInnerNodeCommunicationCost(double innerNodeCommunicationCost) {
		this.innerNodeCommunicationCost = innerNodeCommunicationCost;
	}

	public double getOuterNodeCommunicationCost() {
		return outerNodeCommunicationCost;
	}

	public void setOuterNodeCommunicationCost(double outerNodeCommunicationCost) {
		this.outerNodeCommunicationCost = outerNodeCommunicationCost;
	}

	public Task getTask(int index) {
		return assignedTasks.get(index);
	}

	public int addAssignedTasks(Task task) {
		this.assignedTasks.add(task);
		return assignedTasks.size()-1;
	}


	
	
}
