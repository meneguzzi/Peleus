/**
 * 
 */
package org.soton.peleus.mot.impl;

import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Trigger;
import jason.bb.BeliefBase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.soton.peleus.mot.GoalGenerationFunction;

/**
 * @author Felipe Rech Meneguzzi
 *
 */
public class GoalGenerationFunctionImpl implements GoalGenerationFunction {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(GoalGenerationFunction.class.getName());
	
	protected Hashtable<Literal,Trigger> goals;
	
	public GoalGenerationFunctionImpl() {
		this.goals = new Hashtable<Literal,Trigger>();
	}
	
	@SuppressWarnings("unused")
	private void addTestValues() {
		Trigger t = Trigger.parseTrigger("+!finish(block1)");
		goals.put(Literal.LTrue, t);
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.mot.GoalGenerationFunction#generateGoals(jason.bb.BeliefBase)
	 */
	public List<Trigger> generateGoals(BeliefBase beliefBase) {
		logger.fine("Generating goals");
		List<Trigger> list = new ArrayList<Trigger>();
		Unifier unifier = null;
		for (Literal literal : goals.keySet()) {
			if(literal.equals(Literal.LTrue) || 
				(unifier = getUnifier(literal, beliefBase)) != null) {
				Trigger trigger = (Trigger) goals.get(literal).clone();
				unifier.apply(trigger.getLiteral());
				list.add(trigger);
			}
		}
		return list;
	}
	
	public Unifier getUnifier(Literal literal, BeliefBase beliefBase) {
		Iterator<Literal> iter = beliefBase.getRelevant(literal);
		Unifier unifier = new Unifier();
		if(iter != null) {
			for(;iter.hasNext();) {
				Literal l = iter.next();
				l = (Literal) l.clone();
				l.clearAnnots();
				if(unifier.unifies(l, literal))
					return unifier;
			}
		}
		
		return null;
	}

	public void addBeliefToGoalMapping(Literal literal, Trigger trigger) {
		this.goals.put(literal, trigger);
		
	}

	public void removeBeliefToGoalMapping(Literal literal) {
		this.goals.remove(literal);
	}

	public void removeGoal(Trigger trigger) {
		for (Iterator<Trigger> iter = this.goals.values().iterator(); iter.hasNext();) {
			Trigger t = iter.next();
			if(t.equals(trigger)) {
				iter.remove();
			}
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GoalGeneration ");
		sb.append(IntensityUpdateFunctionImpl.class.getName());
		sb.append("{");
		
		for (Literal literal : goals.keySet()) {
			sb.append(System.getProperty("line.separator"));
			sb.append("   ");
			sb.append(literal);
			sb.append(" -> ");
			sb.append(goals.get(literal));
		}
		
		sb.append(System.getProperty("line.separator"));
		sb.append("   }");
		return sb.toString();
	}
}
