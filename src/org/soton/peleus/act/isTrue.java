/**
 * 
 */
package org.soton.peleus.act;

import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

import java.util.logging.Logger;

/**
 * An <code>InternalAction</code> that verifies whether or not a conjunction
 * of literals is valid in the agent's current <code>BeliefBase</code>.
 * @author Felipe Meneguzzi
 *
 */
public class isTrue implements InternalAction {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(InternalAction.class.getName());
	
	public boolean suspendIntention() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see jason.asSemantics.InternalAction#execute(jason.asSemantics.TransitionSystem, jason.asSemantics.Unifier, jason.asSyntax.Term[])
	 */
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		logger.info("Belief Base: \n"+ts.getAg().getBB().getAll());
		logger.info("Got "+args[0]);
		Literal literal = Literal.parseLiteral(args[0].toString());
		logger.info("Created "+literal);
		Literal lit = ts.getAg().getBB().contains(literal);
		logger.info("Beliefs "+lit);
		return (lit!= null);
	}

}
