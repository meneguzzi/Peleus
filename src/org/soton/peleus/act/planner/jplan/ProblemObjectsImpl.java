/**
 * 
 */
package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.Term;

import org.soton.peleus.act.planner.ProblemObjects;


public class ProblemObjectsImpl extends ProblemObjects {

	@Override
	public String toPlannerString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("objects:");
		sb.append(System.getProperty("line.separator"));
		for (Term term : terms) {
			sb.append(term.toString());
			sb.append(";");
			sb.append(System.getProperty("line.separator"));
		}
		
		sb.append(System.getProperty("line.separator"));
		
		return sb.toString();
	}
}