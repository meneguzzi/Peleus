/**
 * 
 */
package org.soton.peleus.act.planner;

import jason.asSyntax.Literal;
import jason.asSyntax.LogExpr;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;
import jason.asSyntax.RelExpr;
import jason.asSyntax.RelExpr.RelationalOp;

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
	
	protected List<LogicalFormula> contextLiterals;
	
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
		contextLiterals = new ArrayList<LogicalFormula>();
		LogicalFormula context = plan.getContext();
		
		processTerm(context);
	}
	
	/**
	 * Traverses the <em>tree</em> of logical expressions in the context 
	 * condition in order to generate a {@link List} of Predicates for
	 * use in the planning problem generator.
	 * 
	 * @param term
	 */
	private void processTerm(LogicalFormula formula) {
		if(!(formula instanceof LogExpr)) {
			LogicalFormula formulaNew = (LogicalFormula) formula.clone(); 
			contextLiterals.add(formulaNew);
			return;
		} else {
			LogExpr expr = (LogExpr) formula;
			switch (expr.getOp()) {
			case not:
				processNegatedTerm(expr.getRHS());
				break;
			case none:
				processTerm(expr.getRHS());
				break;
			case and:
				processTerm(expr.getLHS());
				processTerm(expr.getRHS());
				break;
			case or:
				logger.warning("Disjunction resolution not implemented");
				break;
			}
		}
		return;
	}
	
	private void processNegatedTerm(LogicalFormula formula) {
		if(formula instanceof Literal) {
			Literal literal = (Literal) formula.clone();
			literal.setNegated(true);
			contextLiterals.add(literal);
			return;
		} else if(formula instanceof RelExpr) {
			//invert the formula and add?
			RelExpr expr = (RelExpr) formula.clone();
			expr = new RelExpr(expr.getLHS(),toNegatedOperator(expr.getOp()), expr.getRHS());
			contextLiterals.add(expr);
		} else {
			LogExpr expr = (LogExpr) formula;
			switch (expr.getOp()) {
			case not:
				processTerm(expr.getRHS());
				break;
			case none:
				processNegatedTerm(expr.getRHS());
				break;
			//A little De Morgan here...
			case and:
				logger.warning("Disjunction resolution not implemented");
				break;
			case or:
				processNegatedTerm(expr.getLHS());
				processNegatedTerm(expr.getRHS());
				break;
			}
		}
	}
	
	public RelationalOp toNegatedOperator(RelationalOp op) {
		switch (op) {
		case unify:
		case eq:
			return RelationalOp.dif;
		case dif:
			return RelationalOp.eq;
		case gt:
			return RelationalOp.lte;
		case gte:
			return RelationalOp.lt;
		case literalBuilder:
			return RelationalOp.literalBuilder;
		case lt:
			return RelationalOp.gte;
		case lte:
			return RelationalOp.gt;
		case none:
		default:
			return RelationalOp.eq;
		}
	}
	
	public List<LogicalFormula> getContext() {
		return contextLiterals;
	}
	
	public static PlanContextExtractor getPlanContextExtractor() {
		return new PlanContextExtractor();
	}
}
