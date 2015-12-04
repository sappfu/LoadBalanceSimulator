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
	
	
	
	private int rank;
	private int node;
	
	private ArrayList<Task> assignedTasks;
	
	
	public Processor(int rank, int node){
		if (factory == null){
			Processor.getFactory();
		}
		this.rank = rank;
		this.node = node;
		assignedTasks = new ArrayList<Task>();
		factory.add(this);
	}

	public int[] calculateDistance(Processor p){
		int[] distance = new int[2]; //inner distance and outer distance
		distance[0] = Math.abs(this.getRank() - p.getRank());
		distance[1] = Math.abs(this.getNode() - p.getNode());
		return distance;
	}
	
	public double getExecutionTimeForTasks(){
		double time = 0.0;
		for (Task t : this.assignedTasks){
			time += t.getTaskSize();
		}
		return time;
	}
	
	public String toString(){
		String tmp = "";
		for (Task task : assignedTasks){
			tmp += task.toString() + " | ";
		}
		return "Rank: " + rank + " Node: " + node + " Task list: " + tmp;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
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
