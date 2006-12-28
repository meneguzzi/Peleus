/**
 * 
 */
package org.soton.peleus.mot;

import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.bb.BeliefBase;

/** 
 * A strategy pattern defining the interface to the 
 * intensity update function for a given motivation.
 * 
 * @author Felipe Rech Meneguzzi
 */
public interface IntensityUpdateFunction {
	
	/**
	 * Adds a mapping from a belief <code>Literal</code> into an
	 * integer value used to update the intensity of a given 
	 * motivation. Existing mappings are removed by multiple calls.
	 * 
	 * @param literal
	 * @param value
	 */
	public void addBeliefToIntegerMapping(Literal literal, NumberTerm value);
	
	/**
	 * Removes the mapping of the supplied <code>Literal</code> from 
	 * this function.
	 * @param literal
	 */
	public void removeBeliefToIntegerMapping(Literal literal);
	
	/**
	 * A delegate function used by <code>Motivation</code> to calculate
	 * variations to a given motivation.
	 * 
	 * @param beliefBase
	 * @return A value to be added to a motivation's intesity.
	 */
	public int updateIntensity(BeliefBase beliefBase);
}
