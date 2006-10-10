/**
 * 
 */
package org.soton.peleus.act.planner;



public abstract class ProblemObjects extends ProblemTerms {
	public String toString() {
		return toPlannerString();
	}
	
	public abstract String toPlannerString();
}