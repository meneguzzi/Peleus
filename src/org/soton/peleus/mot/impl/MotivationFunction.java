package org.soton.peleus.mot.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.bb.BeliefBase;

public abstract class MotivationFunction {
	
	/**
	 * Tests whether or not the supplied literal is supported by the BeliefBase.
	 * @param literal A literal which is to be tested by the belief base.
	 * @param beliefBase An agent's <code>BeliefBase</code>
	 * @return Whether or not the supplied literal is supported by the BeliefBase
	 */
	public boolean supportedByBeliefBase(Literal literal, BeliefBase beliefBase) {
		return (unifyWithBeliefBase(literal, beliefBase, true) != null);
	}
	
	/**
	 * Tests whether or not the supplied literal unifies with any literal in the BeliefBase,
	 * returning all of the valid unifications.
	 * @param literal A literal which is to be unified with literals in the belief base.
	 * @param beliefBase An agent's <code>BeliefBase</code>
	 * @return All valid unifications found
	 */
	public List<Unifier> unifies(Literal literal, BeliefBase beliefBase) {
		return unifyWithBeliefBase(literal, beliefBase, false);
	}
	
	private List<Unifier> unifyWithBeliefBase(Literal literal, BeliefBase beliefBase, boolean matchFirst) {
		Iterator<Literal> relevant = beliefBase.getRelevant(literal);
		if(relevant == null) {
			return null;
		}
		
		List<Unifier> unifiers = null;
		
		while (relevant.hasNext()) {
			Unifier unifier = new Unifier();
			Literal relevantLiteral = (Literal) relevant.next();
			if(unifier.unifies(relevantLiteral, literal)) {
				if(unifiers == null) {
					unifiers = new ArrayList<Unifier>();
				}
				unifiers.add(unifier);
				if(matchFirst)
					break;
			}
		}
		
		return unifiers;
	}
}
