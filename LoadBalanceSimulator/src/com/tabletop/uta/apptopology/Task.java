package com.tabletop.uta.apptopology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.tabletop.uta.machtopology.Processor;

public class Task {

	private static ArrayList<Task> factory = null;
	private static double rangeMin = 1000.0;
	private static double rangeMax = 2000.0;
	private static int communicationAmountModifier = 10;
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
	
	public static void printAllTasks(){
		for (int i=0;i<factory.size();i++){
			System.out.println(factory.get(i).toString());
		}
	}
	
	public static void clear(){
		for (Task t : factory){
			t.unassignFromProcessor();
		}
		factory.clear();
	}
	
	public static int[][] createTaskGraph(){
		int[][] graph = new int[factory.size()][factory.size()];
		
		for (int i=0;i<factory.size();i++){
			for (int j=0;j<factory.get(i).linkedTasks.size();j++){
				graph[i][factory.get(i).linkedTasks.get(j)] = 1;
			}
		}
		return graph;
	}
	
	public static void printTaskGraph(){
		int[][] graph = Task.createTaskGraph();
		System.out.print("    *");
		for (int i=0;i<factory.size();i++){
			System.out.printf("%3d *", i);
		}
		System.out.print("\n");
		
		for (int i=0;i<factory.size();i++){
			System.out.printf("%3d *", i);
			for (int j=0;j<factory.size();j++){
				System.out.printf("%3d |" , graph[i][j]);
			}
			System.out.print("\n");
		}
	}

	int taskSize;
	int taskId;
	int taskRemaining;
	Processor assignedProcessor;
	HashMap<Integer,Integer> linkedTasks; // maps the task to the amount of communication (frequency)
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
		this.linkedTasks = new HashMap<Integer,Integer>();
		for (Integer linkedTask : linkedTasks){
			this.linkedTasks.put(linkedTask,linkedTask%communicationAmountModifier);
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
	
	public void unassign(){
		this.assignedProcessor.unassignTask(this);
		this.assignedProcessor = null;
		this.assigned = false;
	}

	public int[] getTotalDistanceToLinkedTasks(){
		int[] totalDistance = new int[2];
		totalDistance[0] = 0;
		totalDistance[1] = 0;
		int[] tmp;
		for (Integer i : this.linkedTasks.keySet()){
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
	
	public int getTaskId() {
		return taskId;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public int getStepSize() {
		return stepSize;
	}
	
	public int[] calculateDistance(int taskId){
		return this.assignedProcessor.calculateDistance(factory.get(taskId).assignedProcessor);
	}
	
	// assumes Nodes only have one processor
	public int getInnerNodeCommunicationAmount() {
		int sum = 0;
		for( Integer linkedTask : this.linkedTasks.keySet() ) {
			if ( this.assignedProcessor == factory.get(linkedTask).assignedProcessor ) {
				sum += this.linkedTasks.get(linkedTask);
			}
		}
		return sum;
	}

	// assumes Nodes only have one processor
	public int getOuterNodeCommunicationAmount() {
		int sum = 0;
		for( Integer linkedTask : this.linkedTasks.keySet() ) {
			if ( this.assignedProcessor != factory.get(linkedTask).assignedProcessor ) {
				sum += this.linkedTasks.get(linkedTask);
			}
		}
		return sum;
	}

	public String toString(){
		String tmp = "Task Id: " + this.taskId + " Task size: " + this.taskRemaining;
		for (Integer task : linkedTasks.keySet()){
			tmp = tmp + " Linked task: " + task;
		}
		return tmp;
	}

	
	
	
	
	
}
