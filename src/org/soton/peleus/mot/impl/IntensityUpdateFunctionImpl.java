/**
 * 
 */
package org.soton.peleus.mot.impl;

import jason.asSyntax.Literal;
import jason.bb.BeliefBase;

import java.util.Hashtable;
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
public class IntensityUpdateFunctionImpl implements IntensityUpdateFunction {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(IntensityUpdateFunction.class.getName());
	
	protected Hashtable<Literal, Integer> positiveBeliefs;
	
	protected Hashtable<Literal, Integer> negativeBeliefs;
	
	public IntensityUpdateFunctionImpl() {
		positiveBeliefs = new Hashtable<Literal, Integer>();
		negativeBeliefs = new Hashtable<Literal, Integer>();
		
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
			if(beliefBase.contains(positiveLiteral) != null) {
				motivationDelta+=positiveBeliefs.get(positiveLiteral);
			}
		}
		
		for (Literal negativeLiteral : negativeBeliefs.keySet()) {
			if(beliefBase.contains(negativeLiteral) == null) {
				motivationDelta += negativeBeliefs.get(negativeLiteral);
			}
		}
		logger.finer("Net motivation is: "+motivationDelta);
		return motivationDelta;
	}

	public void addBeliefToIntegerMapping(Literal literal, int value) {
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
