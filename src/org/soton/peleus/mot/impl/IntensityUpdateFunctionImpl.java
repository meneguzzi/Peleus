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

import org.soton.peleus.mot.IntensityUpdateFunction;

/**
 * An intensity update function that monitors positive and negative beliefs in 
 * the belief base, updating the motivation intensity when certain predicates are
 * believed (positiveBeliefs) or not (negativeBeliefs).
 * 
 * @author Felipe Rech Meneguzzi
 *
 */
public class IntensityUpdateFunctionImpl extends MotivationFunction implements IntensityUpdateFunction {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(IntensityUpdateFunction.class.getName());
	
	protected Hashtable<Literal, NumberTerm> positiveBeliefs;
	
	protected Hashtable<Literal, NumberTerm> negativeBeliefs;
	
	public IntensityUpdateFunctionImpl() {
		positiveBeliefs = new Hashtable<Literal, NumberTerm>();
		negativeBeliefs = new Hashtable<Literal, NumberTerm>();
		
		//For testing purposes only
		//addTestValues();
	}
	
	@SuppressWarnings("unused")
	private void addTestValues() {
		/*Literal l = Literal.parseLiteral("over(block1,feedBelt)");
		positiveBeliefs.put(l, 1);
		
		negativeBeliefs.put(l, -1);
		
		l = Literal.parseLiteral("finished(block1)");
		positiveBeliefs.put(l, -1);*/
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.mot.IntensityUpdateFunction#updateIntensity(jason.bb.BeliefBase)
	 */
	public int updateIntensity(BeliefBase beliefBase) {
		int motivationDelta = 0;
		for (Literal positiveLiteral : positiveBeliefs.keySet()) {
			//logger.info("Updating intensity regarding "+positiveLiteral);
			Literal literal = (Literal) positiveLiteral.clone();
			if(supportedByBeliefBase(literal, beliefBase)) {
				//logger.info(positiveLiteral+"is supported by the belief base");
				List<Unifier> unifiers = unifies(literal, beliefBase);
				NumberTerm value = positiveBeliefs.get(positiveLiteral);
				for (Unifier unifier : unifiers) {
					NumberTerm value2 = (NumberTerm) value.clone();
					unifier.apply(value2);
					motivationDelta+=value2.solve();
				}
			}
		}
		
		for (Literal negativeLiteral : negativeBeliefs.keySet()) {
			if(beliefBase.getRelevant(negativeLiteral) == null) {
				//TODO Review the negative Literals Part
				//motivationDelta += negativeBeliefs.get(negativeLiteral);
			}
		}
		logger.finer("Net motivation is: "+motivationDelta);
		return motivationDelta;
	}

	public void addBeliefToIntegerMapping(Literal literal, NumberTerm value) {
		this.positiveBeliefs.put(literal, value);
		
	}

	public void removeBeliefToIntegerMapping(Literal literal) {
		this.positiveBeliefs.remove(literal);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("IntensityUpdate ");
		sb.append(IntensityUpdateFunctionImpl.class.getName());
		sb.append("{");
		
		for (Literal literal : positiveBeliefs.keySet()) {
			sb.append(System.getProperty("line.separator"));
			sb.append("   ");
			sb.append(literal);
			sb.append(" -> ");
			sb.append(positiveBeliefs.get(literal));
		}
		
		sb.append(System.getProperty("line.separator"));
		sb.append("   }");
		return sb.toString();
	}

}
