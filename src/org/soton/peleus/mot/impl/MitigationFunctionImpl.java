/**
 * 
 */
package org.soton.peleus.mot.impl;

import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.bb.BeliefBase;

import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import org.soton.peleus.mot.MitigationFunction;

/**
 * @author Felipe Rech Meneguzzi
 *
 */
public class MitigationFunctionImpl extends MotivationFunction implements MitigationFunction {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MotivationFunction.class.getName());
	
	protected Hashtable<Literal, NumberTerm> mapping;
	
	public MitigationFunctionImpl() {
		this.mapping = new Hashtable<Literal, NumberTerm>();
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.mot.MitigationFunction#mitigate(jason.bb.BeliefBase)
	 */
	public int mitigate(BeliefBase beliefBase) {
		int mitigation = 0;
		
		//logger.info("Belief Base at time of mitigation "+beliefBase);
		
		for (Literal literal : mapping.keySet()) {
			if(supportedByBeliefBase(literal, beliefBase)) {
				logger.info(literal+" is supported by the belief base");
				List<Unifier> unifiers = unifies(literal, beliefBase);
				//logger.info("unifiers: "+unifiers);
				NumberTerm value = mapping.get(literal);
				for (Unifier unifier : unifiers) {
					NumberTerm value2 = (NumberTerm) value.clone();
					unifier.apply(value2);
					mitigation+=value2.solve();
				}
			}
		}
		
		return mitigation;
	}

	public void addBeliefToIntegerMapping(Literal literal, NumberTerm value) {
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
