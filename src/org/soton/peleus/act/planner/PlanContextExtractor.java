/**
 * 
 */
package org.soton.peleus.act.planner;

import jason.asSyntax.LogExprTerm;
import jason.asSyntax.Plan;
import jason.asSyntax.RelExprTerm;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Helper class to extract single context conjunction
 * Since Jason 0.9, plan contexts have been changed to include 
 * disjunction, hence the internal representation is no longer a 
 * simple list of literals. 
 * 
 * TODO Implement provisions to actually deal with the disjunction
 * TODO Probably generating more operators as a result
 * @author Felipe Rech Meneguzzi
 *
 */
public class PlanContextExtractor {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PlanContextExtractor.class.getName());
	
	protected List<Term> contextTerms;
	
	/**
	 * This class might become a singleton for efficiency reasons,
	 * so we block direct intstantiation.
	 *
	 */
	protected PlanContextExtractor() {
		
	}

	/**
	 * Extracts the context from a plan and stores it in an
	 * internal variable.
	 * @param plan
	 */
	public void extractContext(Plan plan) {
		contextTerms = new ArrayList<Term>();
		Term context = plan.getContext();
		//There has got to be a better way of checking a Term to
		//see if it is a Logical Expression rathern than using 
		//instanceof
		if(context instanceof LogExprTerm) {
			processTerm((LogExprTerm) context);
		} else {
			contextTerms.add(context);
		}
		
	}
	
	/**
	 * Traverses the <em>tree</em> of logical expressions in the context 
	 * condition in order to generate a {@link List} of Predicates for
	 * use in the planning problem generator.
	 * 
	 * @param term
	 */
	private void processTerm(Term term) {
		if(! (term instanceof LogExprTerm)) {
			contextTerms.add(term);
			return;
		}
		LogExprTerm exprTerm = (LogExprTerm) term;
		switch(exprTerm.getOp()) {
				case not:
				case none:
					contextTerms.add(exprTerm);
					break;
				case and:
					processTerm(exprTerm.getLHS());
					processTerm(exprTerm.getRHS());
					break;
				case or:
					logger.warning("Disjunction resolution not implemented");
					break;
		}
		
		return;
	}
	
	public List<Term> getContext() {
		return contextTerms;
	}
	
	public static PlanContextExtractor getPlanContextExtractor() {
		return new PlanContextExtractor();
	}
}
