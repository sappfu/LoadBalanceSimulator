package com.tabletop.uta.apptopology;

import java.util.ArrayList;
import java.util.Random;

import com.tabletop.uta.machtopology.Processor;

public class Algorithm {
	
	private static ArrayList<Algorithm> factory = null;
	private static double rangeMin = 1000.0;
	private static double rangeMax = 2000.0;
	
	//Singleton pattern
	public static ArrayList<Algorithm> getFactory(){
		if (factory == null){
			factory = new ArrayList<Algorithm>();
		}
		return factory;
	}
	
	public static Algorithm getAlgorithm(int index){
		if (factory == null){
			Algorithm.getFactory();
			for (int i=0;i<index;i++){
				Algorithm.generateAlgorithm(i);
			}
		}
		return factory.get(index-1);
	}
	
	public static int generateAlgorithm(){
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		new Algorithm("" + randomValue, randomValue/10, randomValue, 200);
		return factory.size()-1;
	}
	
	public static int generateAlgorithm(int value){
		new Algorithm("" + value, rangeMax/10 + value*20, rangeMax/10 + value*100, 200);
		return factory.size()-1;
	}
	

	
	
	
	
	
	String name;
	double innerNodeCommunicationAmount;
	double outerNodeCommunicationAmount;
	double sequentialTime;
	double executionTime;
	
	public Algorithm(String name, double innerNodeCommunicationAmount, double outerNodeCommunicationAmount, double sequentialTime){
		if (factory == null){
			Algorithm.getFactory();
		}
		this.name = name;
		this.innerNodeCommunicationAmount = innerNodeCommunicationAmount;
		this.outerNodeCommunicationAmount = outerNodeCommunicationAmount;
		this.sequentialTime = sequentialTime;
		this.executionTime = 0.0;
		factory.add(this);
	}
	
	public boolean runOneExecutionStep(){
		int[] tmp;
		
		ArrayList<Processor> processors = Processor.getFactory();
		boolean stillProcessing = false;
		for (Processor p : processors){
			if (p.getNextTask() != null){
				tmp = p.getNextTask().getTotalDistanceToLinkedTasks();
				executionTime += tmp[0] * this.innerNodeCommunicationAmount * Processor.innerNodeCommunicationCost / p.getNextTask().getTaskSize();
				executionTime += tmp[1] * this.outerNodeCommunicationAmount * Processor.outerNodeCommunicationCost / p.getNextTask().getTaskSize();
				p.getNextTask().setRemainingTask(p.getNextTask().getRemainingTask() - 1);
				stillProcessing = true;
			}
		}
		if (stillProcessing){
			this.executionTime++;
		}
		return stillProcessing;
	}
	
	public double calculateExecutionTime(){
		double executionTime = 0.0;
		
		
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
		System.out.println("Total inner distance: " + distance[0] + " Total outer distance: " + distance[1]);
		
		//get highest task time for processors
		double taskTime = 0.0;
		double temp = 0.0;
		for (Processor p : Processor.getFactory()){
			temp = p.getExecutionTimeForTasks();
			if (temp > taskTime){
				taskTime = temp;
			}
		}
		System.out.println("Highest processor task time: " + taskTime);
		
		executionTime += distance[0] * this.innerNodeCommunicationAmount * Processor.innerNodeCommunicationCost;
		executionTime += distance[1] * this.outerNodeCommunicationAmount * Processor.outerNodeCommunicationCost;
		executionTime += taskTime;
		executionTime += this.sequentialTime;
		return executionTime;
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
