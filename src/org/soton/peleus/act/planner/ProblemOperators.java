/**
 * 
 */
package org.soton.peleus.act.planner;

import jason.asSyntax.Plan;
import jason.asSyntax.Pred;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

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
	
	public static List<Plan> getLabelledPlans(Collection<Plan> plans, String regex) {
		List<Plan> matchingPlans = new ArrayList<Plan>();
		
		Pattern pattern = Pattern.compile(regex);
		
		for(Plan plan : plans) {
			Pred label = plan.getLabel();
			if(label != null && pattern.matcher(label.getFunctor()).matches()) {
				matchingPlans.add(plan);
			}
		}
		
		return matchingPlans;
	}
	
	public String toString() {
		return toPlannerString();
	}
	
	public abstract String toPlannerString();
}