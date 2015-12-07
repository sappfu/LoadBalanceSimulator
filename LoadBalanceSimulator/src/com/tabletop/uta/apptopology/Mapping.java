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
	
	public static void calculateNumaMapping(){
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
	
	public static void assignToNode(NumaNode node, Task task){
		node.assign(task);
	}
}
