package com.tabletop.uta.loadbalance;

import java.util.ArrayList;

import com.tabletop.uta.apptopology.Mapping;
import com.tabletop.uta.apptopology.Task;
import com.tabletop.uta.machtopology.Processor;

public class NumaNode {

	private static ArrayList<NumaNode> factory;
	private static int count = 0;
	
	//Singleton pattern
		public static ArrayList<NumaNode> getFactory(){
			if (factory == null){
				factory = new ArrayList<NumaNode>();
			}
			return factory;
		}
		
		public static NumaNode getNumaNode(int index){
			try{
				NumaNode n = factory.get(index);
				return n;
			}
			catch(Exception e){
				throw new RuntimeException("No such NumaNode!");
			}
		}
		
		public static void printAllNumaNodes(){
			for (NumaNode n : factory){
				System.out.println(n);
			}
		}
		
		public static void clear(){
			factory.clear();
		}
		
		private ArrayList<Processor> numaProcessors;
		private int node;
		
		public NumaNode(){
			if (factory == null){
				NumaNode.getFactory();
			}
			numaProcessors = new ArrayList<Processor>();
			node = count++;
			factory.add(this);
		}
		
		public void addProcessor(Processor p){
			numaProcessors.add(p);
		}
		
		public int getNumCores(){
			return numaProcessors.size();
		}
		
		public int getAllRemainingTask(){
			int tmp = 0;
			for (Processor p : numaProcessors){
				tmp += p.getRemainingExecutionTimeForTasks();
			}
			return tmp;
		}
		
		public void assign(Task task){
			Processor tmp = numaProcessors.get(0);
			for (Processor p : numaProcessors){
				if (tmp.getRemainingExecutionTimeForTasks() > p.getRemainingExecutionTimeForTasks()){
					tmp = p;
				}
			}
			Mapping.assignToProcessor(tmp, task);
		}
		
		public String toString(){
			String tmp = "Node: " + this.node + " : \n";
			for (Processor p : numaProcessors){
				tmp = tmp + p + " \n";
			}
			
			return tmp;
		}
	
}
