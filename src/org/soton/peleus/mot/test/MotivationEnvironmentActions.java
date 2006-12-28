package org.soton.peleus.mot.test;

import java.util.List;
import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;

public class MotivationEnvironmentActions implements EnvironmentActions {
	protected Logger logger = Logger.getLogger(MotivationTestEnvironment.class.getName());
	
	public MotivationTestEnvironment environment;
	
	public MotivationEnvironmentActions(MotivationTestEnvironment environment) {
		this.environment = environment;
	} 

	public boolean executeAction(String agName, Term act) {
		boolean success = true;
		if(act.getFunctor().equals("move")) {
			Term t0 = act.getTerm(0);
			Term t1 = act.getTerm(1);
			List<Literal> percepts = environment.getPercepts("johnny");
			
			logger.info("Moving "+agName+" from "+t0+" to "+t1);
			Literal precond0 = Literal.parseLiteral("at("+t0.toString()+")");
			Literal effect0 = Literal.parseLiteral("at("+t1.toString()+")");
			
			/*if(findMatchingLiteral(precond0, percepts) == null)
				return false;*/
			
			environment.removePercept(precond0);
			environment.addPercept(effect0);
			
			Literal batt = environment.findLiteralByFunctor("batt", percepts);
			if(batt != null) {
				environment.removePercept(batt);
				environment.addPercept(updateBattery(batt));
			}
		} else if(act.getFunctor().equals("charge")) {
			List<Literal> percepts = environment.getPercepts("johnny");
			
			//Literal precond0 = Literal.parseLiteral("at(A)");
			/*if(findMatchingLiteral(precond0, percepts) == null)
				return false;*/
			
			Literal batt = environment.findLiteralByFunctor("batt", percepts);
			environment.removePercept(agName, batt);
			//environment.addPercept(Literal.parseLiteral("batt(full)"));
			environment.addPercept(Literal.parseLiteral("batt(10)"));
			logger.info("Charging "+agName);
		} else if(act.getFunctor().equals("pickup")) {
			List<Literal> percepts = environment.getPercepts("johnny");
			
			Term t0 = act.getTerm(0);
			Literal prot = Literal.parseLiteral("over("+t0+", A)");
			Literal remove = environment.findMatchingLiteral(prot, percepts);
			if(remove != null) {
				environment.removePercept(remove);
				environment.addPercept(Literal.parseLiteral("held("+t0+")"));
				logger.info(agName+" picking up "+t0+" from "+remove.getTerm(1));
			} else {
				success = false;
			}
		} else if(act.getFunctor().equals("drop")) {
			List<Literal> percepts = environment.getPercepts("johnny");
			Term t0 = act.getTerm(0);
			
			Literal prot = Literal.parseLiteral("held("+t0+")");
			Literal remove = environment.findMatchingLiteral(prot, percepts);
			if(remove != null) {
				environment.removePercept(remove);
			} else {
				success = false;
			}
			
			prot = Literal.parseLiteral("at(A)");
			remove = environment.findMatchingLiteral(prot, percepts);
			if(remove != null) {
				Term t1 = remove.getTerm(0);
				environment.addPercept(Literal.parseLiteral("over("+t0+","+t1+")"));
				logger.info(agName+" dropping "+t0+" at "+t1);
			} else {
				success = false;
			}
		}
		if(!success) {
			logger.info("Action "+act.toString()+" failed");
		}
		return success;
	}
	
	public Literal updateBattery(Literal battLiteral) {
		Term t0 = battLiteral.getTerm(0);
		Literal retLiteral = battLiteral;
		if(t0.isNumeric()) {
			NumberTerm term = (NumberTerm) t0;
			int battCondition = (int) term.solve();
			battCondition--;
			retLiteral = Literal.parseLiteral("batt("+battCondition+")");
			//logger.info("Batt is "+battCondition);
		}
		
		return retLiteral;
	}

}
