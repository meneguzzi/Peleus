/**
 * 
 */
package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanBody;
import jason.asSyntax.PlanBodyImpl;
import jason.asSyntax.RelExpr;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;
import jason.asSyntax.PlanBody.BodyType;

import java.util.Iterator;
import java.util.List;

import org.soton.peleus.act.planner.PlanContextExtractor;
import org.soton.peleus.act.planner.ProblemOperators;

public class ProblemOperatorsImpl extends ProblemOperators {
	protected JPlanPlannerConverter converter;
	
	public ProblemOperatorsImpl(JPlanPlannerConverter converter) {
		super();
		this.converter = converter;
	}

	@Override
	public String toPlannerString() {
		StringBuffer sb = new StringBuffer();
		
		for (Plan plan : plans) {
			Trigger trigger = plan.getTrigger();
			sb.append("operator: ");
			sb.append(trigger.getLiteral().getFunctor());
			
			//Append operator parameters
			sb.append("(");
			if(trigger.getLiteral().getArity() > 0) {
				Term types[] = plan.getLabel().getTermsArray();
				Term terms[] = trigger.getLiteral().getTermsArray();
				for (int i = 0; i < terms.length; i++) {
					sb.append(types[i]);
					/*sb.append(" ?");
					sb.append(terms[i]);*/
					sb.append(" ");
					sb.append(converter.toStripsString(terms[i]));
					sb.append(((i+1) == terms.length) ? ")" : ", ");
				}
			}
			sb.append(System.getProperty("line.separator"));
			//Append restrictions on objects
			//Changed to adapt to Jason 0.9 new context format
			//List literals = plan.getContext();
			PlanContextExtractor contextExtractor = PlanContextExtractor.getPlanContextExtractor();
			contextExtractor.extractContext(plan);
			List<LogicalFormula> context = contextExtractor.getContext();
			
			StringBuffer sbOpContext = new StringBuffer();
			StringBuffer sbOpPreconds = new StringBuffer();
			for (Iterator<LogicalFormula> iter = context.iterator(); iter.hasNext();) {
				LogicalFormula formula = iter.next();
				//Representation of things in Jason 0.9 changed, now when we deal with
				//Expressions, we are dealing with context information
				if(formula instanceof RelExpr) {
					if(sbOpContext.length() != 0) {
						sbOpContext.append(" & ");
					}
					sbOpContext.append(converter.toStripsString((RelExpr)formula));
				} else { // Otherwise we are dealing with preconditions
					if(sbOpPreconds.length() != 0) {
						sbOpPreconds.append(" & ");
					}
					Literal literal = (Literal) formula;
					sbOpPreconds.append(converter.toStripsString2(literal));
				}
				
			}
			if(sbOpContext.length() == 0) {
				sb.append("true");
			}else {
				sb.append(sbOpContext);
			}
			sb.append(System.getProperty("line.separator"));
			sb.append("[");
			sb.append(sbOpPreconds);
			sb.append("]");
			sb.append(System.getProperty("line.separator"));
			
			StringBuffer sbPositiveLiterals = new StringBuffer();
			StringBuffer sbNegativeLiterals = new StringBuffer();
			for (PlanBody literal : (PlanBodyImpl)plan.getBody()) {
				if(literal.getBodyType() == BodyType.delBel) {
					if(sbNegativeLiterals.length() != 0) {
						sbNegativeLiterals.append(" & ");
					}
					sbNegativeLiterals.append(converter.toStripsString(literal));
				}else if(literal.getBodyType() == BodyType.addBel) {
					if(sbPositiveLiterals.length() != 0) {
						sbPositiveLiterals.append(" & ");
					}
					sbPositiveLiterals.append(converter.toStripsString(literal));
				}
			}
			
			if(sbPositiveLiterals.length() > 0) {
				sb.append("[");
				sb.append(sbPositiveLiterals);
				sb.append("]");
			} else {
				sb.append("true");
			}
			sb.append(System.getProperty("line.separator"));
			
			if(sbNegativeLiterals.length() > 0) {
				sb.append("[");
				sb.append(sbNegativeLiterals);
				sb.append("]");
			} else {
				sb.append("true");
			}
			sb.append(System.getProperty("line.separator"));
			sb.append(System.getProperty("line.separator"));
		}
		
		sb.append(System.getProperty("line.separator"));
		
		return sb.toString();
	}
	
	/**
	 * This method stinks big time by overriding the one in 
	 * the outter class.
	 * 
	 * @param term A Term to be converted into a STRIPS String
	 * @return A STRIPS-compatible String representation
	 */
	/*public String toStripsString(Term term) {
		StringBuffer sb = new StringBuffer();
		
		if(term.isVar()) {
			sb.append("?");
		}
		
		if(term instanceof LogExpr) {
			LogExpr exprTerm = (LogExpr) term;
			if(exprTerm.isUnary()) {
				if( (exprTerm.getOp() == LogicalOp.not) && 
					(exprTerm.getRHS() instanceof RelExpr)) {
					RelExpr exprTerm2 = (RelExpr) exprTerm.getRHS();
					if(!exprTerm2.getLHS().isVar()) {
						sb.append("@");
					}
					sb.append(toStripsString(exprTerm2.getLHS()));
					sb.append(converter.toStripsNegatedOperator(exprTerm2.getOp()));
					if(!exprTerm2.getRHS().isVar()) {
						sb.append("@");
					}
					sb.append(toStripsString(exprTerm2.getRHS()));
				} else {
					sb.append(converter.toStripsOperator(exprTerm.getOp()));
					sb.append(toStripsString((Literal)exprTerm.getRHS()));
				}
			} else {
				//sb.append("(");
				sb.append(toStripsString((Literal)exprTerm.getLHS()));
				sb.append(converter.toStripsOperator(exprTerm.getOp()));
				sb.append(toStripsString((Literal)exprTerm.getRHS()));
				//sb.append(")");
			}
		} else if (term instanceof RelExpr) {
			RelExpr exprTerm = (RelExpr) term;
			//sb.append("(");
			if(!exprTerm.getLHS().isVar()) {
				sb.append("@");
			}
			sb.append(toStripsString(exprTerm.getLHS()));
			sb.append(exprTerm.getOp());
			if(!exprTerm.getRHS().isVar()) {
				sb.append("@");
			}
			sb.append(toStripsString(exprTerm.getRHS()));
			//sb.append(")");
		} else {
			sb.append(term.getFunctor());
			if(term.getTermsSize() > 0) {
				sb.append("(");
				for (Term termPar : term.getTermsArray()) {
					if(sb.charAt(sb.length()-1) != '(')
						sb.append(", ");
					if(!termPar.isVar())
						sb.append("@");
					sb.append(toStripsString(termPar));
				}
				sb.append(")");
			}
		}
		
		return sb.toString();
	}*/
}