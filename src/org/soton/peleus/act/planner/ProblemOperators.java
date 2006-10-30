/**
 * 
 */
package org.soton.peleus.act.planner;

import jason.asSyntax.Plan;

import java.util.ArrayList;
import java.util.List;

public abstract class ProblemOperators {
	protected List<Plan> operators;
	
	public ProblemOperators() {
		operators = new ArrayList<Plan>();
	}
	
	public void add(Plan plan) {
		operators.add(plan);
	}
	
	public String toPlainString() {
		StringBuffer sb = new StringBuffer();
		for (Plan plan : operators) {
			sb.append(plan.toString());
		}
		return sb.toString();
	}
	
	public String toString() {
		return toPlannerString();
	}
	
	public abstract String toPlannerString();
}