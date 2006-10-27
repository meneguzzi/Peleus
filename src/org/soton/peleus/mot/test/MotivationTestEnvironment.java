/**
 * 
 */
package org.soton.peleus.mot.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.environment.Environment;

/**
 * @author Felipe Rech Meneguzzi
 *
 */
public class MotivationTestEnvironment extends Environment {
	protected Logger logger = Logger.getLogger(MotivationTestEnvironment.class.getName());
	@Override
	public void init(String[] args) {
		super.init(args);
		clearPercepts();
		//addPercept(Literal.parseLiteral("over(block1, feedBelt)"));
		/*addPercept(Literal.parseLiteral("empty(bay1)"));
		addPercept(Literal.parseLiteral("empty(bay2)"));
		addPercept(Literal.parseLiteral("empty(bay3)"));*/
		addPercept(Literal.parseLiteral("empty(charger)"));
		
		addPercept(Literal.parseLiteral("packet(packet1)"));
		addPercept(Literal.parseLiteral("packet(packet2)"));
		addPercept(Literal.parseLiteral("packet(packet3)"));
		
		addPercept(Literal.parseLiteral("over(packet1, bay1)"));
		//addPercept(Literal.parseLiteral("over(packet2, bay2)"));
		//addPercept(Literal.parseLiteral("over(packet3, bay3)"));
		
		addPercept(Literal.parseLiteral("robot(motivated1)"));
		addPercept(Literal.parseLiteral("at(corridor)"));
		addPercept(Literal.parseLiteral("batt(full)"));
		//addPercept("battRobot", Literal.parseLiteral("held(full)"));
	}
	
	@Override
	public boolean executeAction(String agName, Term act) {
		//return super.executeAction(agName, act);
		boolean success = true;
		if(act.getFunctor().equals("move")) {
			Term t0 = act.getTerm(0);
			Term t1 = act.getTerm(1);
			List<Literal> percepts = getPercepts("johnny");
			
			logger.info("Moving "+agName+" from "+t0+" to "+t1);
			Literal precond0 = Literal.parseLiteral("at("+t0.toString()+")");
			Literal effect0 = Literal.parseLiteral("at("+t1.toString()+")");
			
			/*if(findMatchingLiteral(precond0, percepts) == null)
				return false;*/
			
			removePercept(precond0);
			addPercept(effect0);
			
			Literal batt = findLiteralByFunctor("batt", percepts);
			if(batt != null) {
				removePercept(batt);
				addPercept(updateBattery(batt));
			}
		} else if(act.getFunctor().equals("charge")) {
			List<Literal> percepts = getPercepts("johnny");
			
			Literal precond0 = Literal.parseLiteral("at(A)");
			/*if(findMatchingLiteral(precond0, percepts) == null)
				return false;*/
			
			Literal batt = findLiteralByFunctor("batt", percepts);
			removePercept(agName, batt);
			addPercept(Literal.parseLiteral("batt(full)"));
		} else if(act.getFunctor().equals("pickup")) {
			List<Literal> percepts = getPercepts("johnny");
			
			Term t0 = act.getTerm(0);
			Literal prot = Literal.parseLiteral("over("+t0+", A)");
			Literal remove = findMatchingLiteral(prot, percepts);
			if(remove != null) {
				removePercept(remove);
				addPercept(Literal.parseLiteral("held("+t0+")"));
			} else {
				success = false;
			}
		} else if(act.getFunctor().equals("drop")) {
			List<Literal> percepts = getPercepts("johnny");
			Term t0 = act.getTerm(0);
			
			Literal prot = Literal.parseLiteral("held("+t0+")");
			Literal remove = findMatchingLiteral(prot, percepts);
			if(remove != null) {
				removePercept(remove);
			} else {
				success = false;
			}
			
			prot = Literal.parseLiteral("at(A)");
			remove = findMatchingLiteral(prot, percepts);
			if(remove != null) {
				Term t1 = remove.getTerm(0);
				addPercept(Literal.parseLiteral("over("+t0+","+t1+")"));
			} else {
				success = false;
			}
		}
		if(!success) {
			logger.info("Action "+act.toString()+" failed");
		}
		return success;
	}
	
	protected Literal findMatchingLiteral(Literal prototype, List<Literal> literals) {
		if(literals == null) {
			return null;
		}
		Unifier unifier = new Unifier();
		for (Literal literal : literals) {
			if(unifier.unifies(prototype, literal))
				return literal;
			unifier.clear();
		}
		return null;
	}
	
	protected Literal findLiteralByFunctor(String key, List<Literal> literals) {
		if(literals == null)
			return null;
		for (Literal literal : literals) {
			if(literal.getFunctor().equals(key)) {
				return literal;
			}
		}
		return null;
	}
	
	protected List<Literal> findLiteralsByFunctor(String key, List<Literal> literals) {
		List <Literal> ret = new ArrayList<Literal>();
		for (Literal literal : literals) {
			if(literal.getFunctor().equals(key)) {
				ret.add(literal);
			}
		}
		return ret;
	}
	
	public Literal updateBattery(Literal battLiteral) {
		Term t0 = battLiteral.getTerm(0);
		String battCondition = t0.toString();
		
		if(battCondition.equals("full")) {
			battCondition = "half";
		} else if(battCondition.equals("half")) {
			battCondition = "critical";
		} else if(battCondition.equals("critical")) {
			battCondition = "empty";
		}
		
		Literal retLiteral = Literal.parseLiteral("batt("+battCondition+")");
		
		return retLiteral;
	}
	
}
