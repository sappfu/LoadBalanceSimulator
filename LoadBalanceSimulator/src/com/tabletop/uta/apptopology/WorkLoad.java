package com.tabletop.uta.apptopology;

import java.util.ArrayList;
import java.util.Random;

public class WorkLoad {

	private static ArrayList<WorkLoad> factory = null;
	private static double rangeMin = 1000.0;
	private static double rangeMax = 2000.0;
	
	
	//Singleton pattern
	public static ArrayList<WorkLoad> getFactory(){
		if (factory == null){
			factory = new ArrayList<WorkLoad>();
		}
		return factory;
	}
	
	public static WorkLoad getWorkLoad(int index){
		if (factory == null){
			WorkLoad.getFactory();
			for (int i=0;i<index;i++){
				WorkLoad.generateWorkLoad(i);
			}
		}
		return factory.get(index-1);
	}
	
	public static int generateWorkLoad(){
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		new WorkLoad(randomValue/10, randomValue);
		return factory.size()-1; //new workload will be added at this index 
	}
	
	public static WorkLoad generateWorkLoad(int index){
		return new WorkLoad(rangeMin/10 + index*10, (rangeMax+index*200));
	}
	
	double grainSize;
	double grainAmount;
	
	public WorkLoad(double grainSize, double grainAmount){
		this.grainSize = grainSize;
		this.grainAmount = grainAmount;
		factory.add(this);
	}
	
	public String toString(){
		return "Grain size: " + this.grainSize + " Grain amount: " + this.grainAmount;
	}
	
	
	
	
	
}
