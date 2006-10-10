/**
 * 
 */
package org.soton.peleus.act.planner;

/**
 * A class representing the goal state of a planning problem
 * 
 * @author Felipe Meneguzzi
 */
public abstract class GoalState extends ProblemTerms {
	public GoalState() {
		
	}
	
	public String toString() {
		return toPlannerString();
	}
	
	public abstract String toPlannerString();
}
