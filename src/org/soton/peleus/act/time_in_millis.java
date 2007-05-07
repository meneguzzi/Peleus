package org.soton.peleus.act;

import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

/**
 * <p>
 * Internal action: <b><code>.time(M)</code></b>.
 * 
 * <p>
 * Description: gets the current system time in milliseconds.
 * 
 * <p>
 * Parameters:
 * <ul>
 * 
 * <li>+/- milliseconds (number): the current time.</li>
 * 
 * </ul>
 * 
 * <p>
 * Examples:
 * <ul>
 * 
 * <li> <code>.time(M)</code>: unifies M with the current system time.</li>
 * 
 * </ul>
 * 
 * @author Felipe
 * 
 */
public class time_in_millis extends DefaultInternalAction {
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		long time = System.currentTimeMillis();
		if(args.length == 1) {
			return un.unifies(args[0], new NumberTermImpl(time));
		} else {
			throw new JasonException("Internal action time_in_mil requires a parameter");
		}
		
	}
}
