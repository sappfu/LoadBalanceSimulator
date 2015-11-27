package com.tabletop.uta;

import com.tabletop.uta.apptopology.Algorithm;
import com.tabletop.uta.apptopology.WorkLoad;

public class Main {
	
	public static void main(String[] args){
		WorkLoad.getWorkLoad(25);
		for (WorkLoad workLoad : WorkLoad.getFactory()){
			System.out.println(workLoad.toString());
		}
		
		Algorithm.getAlgorithm(25);
		for (Algorithm algorithm : Algorithm.getFactory()){
			System.out.println(algorithm.toString());
		}
	}
}
