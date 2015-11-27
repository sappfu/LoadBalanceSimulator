package com.tabletop.uta.apptopology;

import java.util.ArrayList;
import java.util.Random;

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
		new Algorithm(randomValue/10, randomValue);
		return factory.size()-1;
	}
	
	public static int generateAlgorithm(int value){
		new Algorithm(rangeMax/10 + value*20, (rangeMin+value*100));
		return factory.size()-1;
	}
	
	
	
	double communicationCost;
	double runTime;
	
	public Algorithm(double communicationCost, double runTime){
		this.communicationCost = communicationCost;
		this.runTime = runTime;
		factory.add(this);
	}
	
	public String toString(){
		return "Communication cost: " + this.communicationCost + " Runtime: " + this.runTime;
	}
	
	
}
