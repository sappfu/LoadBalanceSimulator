package com.tabletop.uta.apptopology;

import java.util.ArrayList;

import com.tabletop.uta.loadbalance.NumaNode;
import com.tabletop.uta.machtopology.Processor;

public class Mapping {

	public static void assignToProcessor(Processor p, Task t){
		t.assignToProcessor(p);
		p.addAssignedTasks(t);
	}
	
	public static void unassignAllProcessors(){
		ArrayList<Processor> processors = Processor.getFactory();
		ArrayList<Task> tasks = Task.getFactory();
		for (Processor p : processors){
			p.unassignTasks();
		}
		for (Task task : tasks){
			task.unassignFromProcessor();
		}
	}
	
	public static void calculateInitialMapping(){
		ArrayList<Processor> processors = Processor.getFactory();
		ArrayList<Task> tasks = Task.getFactory();
		
		Processor shortestQueueProcessor = processors.get(0);
		for (Task task : tasks){
			for (Processor p : processors){
				if (shortestQueueProcessor.getExecutionTimeForTasks() > p.getExecutionTimeForTasks()){
					shortestQueueProcessor = p;
				}
			}
			Mapping.assignToProcessor(shortestQueueProcessor, task);
		}
		
	}
	
	public static void calculateInitialNumaMapping(){
		ArrayList<NumaNode> nodes = NumaNode.getFactory();
		ArrayList<Task> tasks = Task.getFactory();
		
		
		for (Task task : tasks){
			if (task.isAssigned()){
				continue;
			}
			NumaNode shortestQueueNode = nodes.get(0);
			for (NumaNode n : nodes){
				if (shortestQueueNode.getAllRemainingTask() > n.getAllRemainingTask()){
					shortestQueueNode = n;
				}
			}
			Mapping.assignToNode(shortestQueueNode, task);
			for (Integer index : task.linkedTasks){
				if (!Task.getTask(index).isAssigned()){
					Mapping.assignToNode(shortestQueueNode, Task.getTask(index));
				}
			}
		}
	}
	
	public static double calculateNumaMapping(){
		ArrayList<Processor> processors = Processor.getFactory();
		ArrayList<Task> tasks = Task.getFactory();
		
		Task tmp = tasks.get(0);
		int tasksAssigned = 0;
		double loadBalanceCost = 0.0;
		
		while (tasks.size() > tasksAssigned){	
			
			for (Task task : tasks){
				task.setAssigned(false);
			}
			
			//get largest task load
			for (Task task : tasks){
				if (task.isAssigned()){
					continue;
				}
				if (task.getRemainingTask() > tmp.getRemainingTask()){
					tmp = task;
				}
			}
			loadBalanceCost += .2;
			
			//T.remove(t)
			//c = M.get(t)
			//c.setCoreLoad(c.getCoreLoad – t.getTaskLoad)
			//M.remove(t,c)
			tmp.unassign();
			
			//cPrime = getSmallestCore(C) : grab the core q from C that has the minimum cost(q,t)
			Processor shortestQueueProcessor = processors.get(0);
			for (Processor p : processors){
				loadBalanceCost += .1;
				if (Mapping.cost(shortestQueueProcessor, tmp) > Mapping.cost(p, tmp)){
					shortestQueueProcessor = p;
				}
			}
			//cPrime.coreLoad = cPrime.coreLoad + t.taskLoad
			Mapping.assignToProcessor(shortestQueueProcessor, tmp);
			tasksAssigned++;
		}
		return loadBalanceCost; //should end up (.2 + .1 * num processors) * num of tasks
	}
	
	
	public static double cost(Processor p, Task t){
		t.assignToProcessor(p);
		int[] distance = t.getTotalDistanceToLinkedTasks();
		t.unassign();
		return distance[0] * p.getInnerNodeCommunicationCost() + distance[1] * p.getOuterNodeCommunicationCost() + t.taskRemaining + p.getRemainingExecutionTimeForTasks();
	}
	
	public static void assignToNode(NumaNode node, Task task){
		node.assign(task);
	}
}
