package com.tabletop.uta.machtopology;

import java.util.ArrayList;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.Task;

public class Processor {

	private static ArrayList<Processor> factory = null;
	public static double innerNodeCommunicationCost = 3;
	public static double outerNodeCommunicationCost = 10;
	public static boolean debug = false;
	
	//Singleton pattern
	public static ArrayList<Processor> getFactory(){
		if (factory == null){
			factory = new ArrayList<Processor>();
		}
		return factory;
	}
	
	public static Processor getProcessor(int index){
		try{
			Processor p = factory.get(index);
			return p;
		}
		catch(Exception e){
			throw new RuntimeException("No such processor!");
		}
	}
	
	public static void printAllProcessors(){
		for (Processor p : factory){
			System.out.println(p.toString());
		}
	}
	
	public static double[][] constructDistanceGraph(){
		ArrayList<Processor> processors = Processor.getFactory();
		double[][] graph = new double[processors.size()][processors.size()];
		for (int i=0;i<processors.size();i++){
			for (int j=0;j<processors.size();j++){
				graph[i][j] = processors.get(i).getDistance(processors.get(j));
				if (debug)
					System.out.printf("%3d | ", (int)graph[i][j]);
			}
			if (debug)
			System.out.print("\n");
		}
		return graph;
	}
	
	public static void clear(){
		for (Processor p : factory){
			p.unassignTasks();
		}
		factory.clear();
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
		distance[0] = Math.abs(this.getCore() - p.getCore());
		return distance;
	}
	
	public double getDistance(Processor p){
		int[] distance = this.calculateDistance(p);
		return distance[0] * Processor.innerNodeCommunicationCost + distance[1] * Processor.outerNodeCommunicationCost;
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
	
	public void unassignTasks(){
		assignedTasks.clear();
	}
	
	public void unassignTask(Task t){
		assignedTasks.remove(t);
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
			tmp += task.getTaskId() + " | ";
		}
		return "Processor: " + processor + " Core: " + core + " Task list: " + tmp;
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
