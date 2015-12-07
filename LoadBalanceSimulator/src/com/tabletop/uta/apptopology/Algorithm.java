package com.tabletop.uta.apptopology;

import java.util.ArrayList;
import java.util.Random;

import com.tabletop.uta.machtopology.Processor;

public class Algorithm {
	
	private static ArrayList<Algorithm> factory = null;
	private static boolean debug = false;
	
	//Singleton pattern
	public static ArrayList<Algorithm> getFactory(){
		if (factory == null){
			factory = new ArrayList<Algorithm>();
		}
		return factory;
	}	
	
	public static void clear(){
		factory.clear();
	}

	String name;
	double innerNodeCommunicationAmount;
	double outerNodeCommunicationAmount;
	double sequentialTime;
	double executionTime;
	double communicationCost;
	
	public Algorithm(String name, double innerNodeCommunicationAmount, double outerNodeCommunicationAmount, double sequentialTime){
		if (factory == null){
			Algorithm.getFactory();
		}
		this.name = name;
		this.innerNodeCommunicationAmount = innerNodeCommunicationAmount;
		this.outerNodeCommunicationAmount = outerNodeCommunicationAmount;
		this.sequentialTime = sequentialTime;
		this.executionTime = 0.0;
		this.communicationCost = 0.0;
		factory.add(this);
	}
	
	public boolean runOneExecutionStep(int index){
		int[] tmp;
		
		ArrayList<Processor> processors = Processor.getFactory();
		boolean stillProcessing = false;
		int i=0;
		for (Processor p : processors){
			if (p.getNextTask() != null){
				tmp = p.getNextTask().getTotalDistanceToLinkedTasks();
				communicationCost += tmp[0] * this.innerNodeCommunicationAmount * Processor.innerNodeCommunicationCost / p.getNextTask().getTaskSize();
				communicationCost += tmp[1] * this.outerNodeCommunicationAmount * Processor.outerNodeCommunicationCost / p.getNextTask().getTaskSize();
				if (index % p.getNextTask().getStepSize() == 0){
					p.getNextTask().setRemainingTask(p.getNextTask().getRemainingTask() - 1);
				}
				stillProcessing = true;
			}
		}
		if (stillProcessing){
			this.executionTime++;
		}
		return stillProcessing;
	}
	
	public double calculateExecutionTime(){
		double totalExecutionTime = 0.0;
		double totalCommunicationCost = 0.0;
		
		
		//get total distance between tasks
		int[] distance = new int[2];
		distance[0] = 0;
		distance[1] = 0;
		int[] tmp;
		for (Task t : Task.getFactory()){
			tmp = t.getTotalDistanceToLinkedTasks();
			distance[0] += tmp[0];
			distance[1] += tmp[1];
		}
		if (debug)
		System.out.println("Total inner distance: " + distance[0] + " Total outer distance: " + distance[1]);
		
		//get highest task time for processors
		double taskTime = 0.0;
		double temp = 0.0;
		for (Processor p : Processor.getFactory()){
			temp = p.getExecutionTimeForTasks();
			if (temp > taskTime){
				if (debug)
					System.out.println("Processor: " + p.getProcessor() + " Core: " + p.getCore() + " Time: " + temp);
				taskTime = temp;
			}
		}
		System.out.println("Highest processor task time: " + taskTime);
		 
		totalCommunicationCost += distance[0] * this.innerNodeCommunicationAmount * Processor.innerNodeCommunicationCost;
		totalCommunicationCost += distance[1] * this.outerNodeCommunicationAmount * Processor.outerNodeCommunicationCost;
		totalExecutionTime += taskTime;
		totalExecutionTime += this.sequentialTime;
		if (debug)
			System.out.println("Execution Time: " + totalExecutionTime + " Communication Cost: " + totalCommunicationCost + " Total Time: " + (totalExecutionTime + totalCommunicationCost));
		return totalExecutionTime + totalCommunicationCost;
	}
	
	public double getCommunicationCost() {
		return communicationCost;
	}

	public double getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(double executionTime) {
		this.executionTime = executionTime;
	}

	public String toString(){
		return "Name: " + name + " innerNodeCommunicationAmount: " + this.innerNodeCommunicationAmount + " outerNodeCommunicationAmount : " + this.outerNodeCommunicationAmount;
	}
	
	
}
