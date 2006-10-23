/**
 * 
 */
package org.soton.peleus.mot.test;

import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.environment.Environment;

/**
 * @author Felipe Rech Meneguzzi
 *
 */
public class MotivationTestEnvironment extends Environment {
	@Override
	public void init(String[] args) {
		super.init(args);
		clearPercepts();
		addPercept(Literal.parseLiteral("over(block1, feedBelt)"));
	}
	
	@Override
	public boolean executeAction(String agName, Term act) {
		//return super.executeAction(agName, act);
		if(act.getFunctor().equals("move")) {
			//act.getTerm(i);
			removePercept(Literal.parseLiteral("over(block1, feedBelt)"));
		}
		return true;
	}
	
	
}
