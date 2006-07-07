package org.soton.peleus.act.planner;


/**
 * A class representing the start state of a planning problem
 */
public abstract class StartState extends ProblemTerms{
	
	public String toString() {
		return toPlannerString();
	}
	
	public abstract String toPlannerString();
}