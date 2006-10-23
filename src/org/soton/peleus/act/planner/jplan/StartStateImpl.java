package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.Term;
import java.util.Iterator;
import org.soton.peleus.act.planner.PlannerConverter;
import org.soton.peleus.act.planner.StartState;

/**
 * A class representing the start state of a planning problem
 */
public class StartStateImpl extends StartState {

	/**
	 * 
	 */
	private final PlannerConverter converter;

	/**
	 * @param plan
	 */
	public StartStateImpl(PlannerConverter converter) {
		this.converter = converter;
	}

	@Override
	public String toPlannerString() {
		StringBuffer sb = new StringBuffer();
		sb.append("init: ");
		for (Iterator<Term> iter = terms.iterator(); iter.hasNext();) {
			Term term = iter.next();
			sb.append(this.converter.toStripsString(term));
			if (iter.hasNext()) {
				sb.append(" & ");
			}
		}
		
		sb.append(System.getProperty("line.separator"));

		return sb.toString();
	}
}