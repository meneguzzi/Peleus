/**
 * 
 */
package org.soton.peleus.act;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Pred;
import jason.asSyntax.Term;

import java.util.logging.Logger;

/**
 * An <code>InternalAction</code> that verifies whether or not a conjunction
 * of literals is valid in the agent's current <code>BeliefBase</code>.
 * @author Felipe Meneguzzi
 *
 */
public class isTrue extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(InternalAction.class.getName());
	protected static final Term trueTerm = Pred.LTrue;
	protected static final Term remote = ASSyntax.createAtom("remote");
	
	public boolean suspendIntention() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see jason.asSemantics.InternalAction#execute(jason.asSemantics.TransitionSystem, jason.asSemantics.Unifier, jason.asSyntax.Term[])
	 */
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
	
		logger.info("Belief Base: \n"+ts.getAg().getBB().toString());
        logger.info("Got "+args[0]);
		Literal literal = Literal.parseLiteral(args[0].toString());
		logger.info("Created "+literal);
		Literal lit = ts.getAg().getBB().contains(literal);

		boolean b;
		if (lit == null) b = true; else b = false;
		Boolean boolObj = new Boolean(!b);
		logger.info("DEBUG lit: "+boolObj);
		return boolObj;
	}

	public boolean canBeUsedInContext() {
		return false;
	}

	/* (non-Javadoc)
	 * @see jason.asSemantics.InternalAction#prepareArguments(jason.asSyntax.Literal, jason.asSemantics.Unifier)
	 */
	//public Term[] prepareArguments(Literal body, Unifier un) {
		// TODO Fix this to comply with latest Jason architecture
		//return null;
	//}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
