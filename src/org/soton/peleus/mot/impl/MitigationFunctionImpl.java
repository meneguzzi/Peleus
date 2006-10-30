/**
 * 
 */
package org.soton.peleus.mot.impl;

import jason.asSyntax.Literal;
import jason.bb.BeliefBase;

import java.util.Hashtable;

import org.soton.peleus.mot.MitigationFunction;

/**
 * @author Felipe Rech Meneguzzi
 *
 */
public class MitigationFunctionImpl implements MitigationFunction {
	
	protected Hashtable<Literal, Integer> mapping;
	
	public MitigationFunctionImpl() {
		this.mapping = new Hashtable<Literal, Integer>();
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.mot.MitigationFunction#mitigate(jason.bb.BeliefBase)
	 */
	public int mitigate(BeliefBase beliefBase) {
		int mitigation = 0;
		
		for (Literal literal : mapping.keySet()) {
			if(beliefBase.getRelevant(literal) != null) {
				mitigation += mapping.get(literal);
			}
		}
		
		return mitigation;
	}

	public void addBeliefToIntegerMapping(Literal literal, int value) {
		this.mapping.put(literal, value);
	}

	public void removeBeliefToIntegerMapping(Literal literal) {
		this.mapping.remove(literal);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Mitigation ");
		sb.append(IntensityUpdateFunctionImpl.class.getName());
		sb.append("{");
		
		for (Literal literal : mapping.keySet()) {
			sb.append(System.getProperty("line.separator"));
			sb.append("   ");
			sb.append(literal);
			sb.append(" -> ");
			sb.append(mapping.get(literal));
		}
		
		sb.append(System.getProperty("line.separator"));
		sb.append("   }");
		return sb.toString();
	}

}
