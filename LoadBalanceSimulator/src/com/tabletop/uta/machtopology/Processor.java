package com.tabletop.uta.machtopology;

import java.util.ArrayList;

public class Processor {

	private int rank;
	private int node;
	private double grainAmount;
	private double grainSize;
	
	public Processor(int rank, int node){
		this.rank = rank;
		this.node = node;
	}
	
	public String toString(){
		return "Rank: " + rank + " Node: " + node + " grainAmount: " + grainAmount + " grainSize: " + grainSize;
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

	public double getGrainAmount() {
		return grainAmount;
	}

	public void setGrainAmount(double grainAmount) {
		this.grainAmount = grainAmount;
	}

	public double getGrainSize() {
		return grainSize;
	}

	public void setGrainSize(double grainSize) {
		this.grainSize = grainSize;
	}
	
	
}
