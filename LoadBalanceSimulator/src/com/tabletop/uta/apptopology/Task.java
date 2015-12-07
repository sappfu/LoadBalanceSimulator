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
	
	public static Task getTask(int index){
		try {
			return factory.get(index);
		}
		catch(Exception e){
			throw new RuntimeException("No such task exists!");
		}
	}
	
	public static void clear(){
		for (Task t : factory){
			t.unassignFromProcessor();
		}
		factory.clear();
	}


	
	public int getTaskId() {
		return taskId;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public static void printAllTasks(){
		for (int i=0;i<factory.size();i++){
			System.out.println(factory.get(i).toString());
		}
	}
	
	int taskSize;
	int taskId;
	int taskRemaining;
	Processor assignedProcessor;
	ArrayList<Integer> linkedTasks;
	int stepSize;
	boolean assigned;
	
	
	public Task(int taskSize, int stepSize, Integer... linkedTasks){
		if (factory == null){
			Task.getFactory();
		}
		this.taskSize = taskSize;
		this.taskRemaining = taskSize;
		this.taskId = count++;
		this.stepSize = stepSize;
		this.linkedTasks = new ArrayList<Integer>();
		for (Integer linkedTask : linkedTasks){
			this.linkedTasks.add(linkedTask);
		}
		boolean assigned = false;
		factory.add(this);
	}
	
	public void assignToProcessor(Processor p){
		this.assignedProcessor = p;
		this.assigned = true;
	}
	
	public void unassignFromProcessor(){
		this.assignedProcessor = null;
		this.assigned = false;
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
	
	public int getTaskSize() {
		return taskSize;
	}
	
	public int getRemainingTask() {
		return taskRemaining;
	}

	public void setRemainingTask(int taskSize) {
		this.taskRemaining = taskSize;
	}
	
	

	public int[] calculateDistance(int taskId){
		return this.assignedProcessor.calculateDistance(factory.get(taskId).assignedProcessor);
	}
	
	public String toString(){
		String tmp = "Task Id: " + this.taskId + " Task size: " + this.taskRemaining;
		for (Integer task : linkedTasks){
			tmp = tmp + " Linked task: " + task;
		}
		return tmp;
	}

	
	
	
	
	
}
