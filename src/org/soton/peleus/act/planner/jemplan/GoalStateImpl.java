package org.soton.peleus.act.planner.jemplan;

import java.util.Iterator;

import jason.asSyntax.Term;

import org.soton.peleus.act.planner.GoalState;

public class GoalStateImpl extends GoalState {

	@Override
	public String toPlannerString() {
		StringBuffer sbGoal = new StringBuffer();
		
		sbGoal.append("goal(");
		
		for (Iterator<Term> iter = terms.iterator(); iter.hasNext();) {
			Term term = iter.next();
			sbGoal.append(term);
			if(iter.hasNext())
				sbGoal.append(", ");
		}
		
		sbGoal.append(")");
		sbGoal.append(System.getProperty("line.separator"));
		
		return sbGoal.toString();
	}

}
