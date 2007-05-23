/**
 * 
 */
package org.soton.peleus.act.planner.jemplan;

import jason.asSyntax.Term;

import java.util.Iterator;

import org.soton.peleus.act.planner.StartState;

/**
 * @author  frm05r
 */
public class StartStateImpl extends StartState {
	
	protected final EMPlanPlannerConverter converter;
	
	public StartStateImpl(EMPlanPlannerConverter converter) {
		this.converter = converter;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.StartState#toPlannerString()
	 */
	@Override
	public String toPlannerString() {
		StringBuffer sbStart = new StringBuffer();
		
		sbStart.append("start(");
		
		for (Iterator<Term> iter = terms.iterator(); iter.hasNext();) {
			Term term = iter.next();
			sbStart.append(converter.toStripsString(term));
			if(iter.hasNext())
				sbStart.append(", ");
		}
		
		if(terms.size() == 0) {
			sbStart.append("true");
		}
		
		sbStart.append(")");
		sbStart.append(System.getProperty("line.separator"));
		
		return sbStart.toString();
	}

}
