/**
 * 
 */
package org.soton.peleus.act.planner;

import jason.asSyntax.Plan;

import java.util.ArrayList;
import java.util.List;

public abstract class ProblemOperators {
	protected List<Plan> plans;
	
	public ProblemOperators() {
		plans = new ArrayList<Plan>();
	}
	
	/**
	 * Allows subclasses to access the <code>operators</code> field.
	 * @return
	 */
	public List<Plan> getPlans() {
		return plans;
	}
	
	public void add(Plan plan) {
		plans.add(plan);
	}
	
	public String toPlainString() {
		StringBuffer sb = new StringBuffer();
		for (Plan plan : plans) {
			sb.append(plan.toString());
		}
		return sb.toString();
	}
	
	public String toString() {
		return toPlannerString();
	}
	
	public abstract String toPlannerString();
}