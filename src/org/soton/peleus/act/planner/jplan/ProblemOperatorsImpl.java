/**
 * 
 */
package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.BodyLiteral;
import jason.asSyntax.LogExprTerm;
import jason.asSyntax.Plan;
import jason.asSyntax.RelExprTerm;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;
import jason.asSyntax.LogExprTerm.LogicalOp;

import java.util.Iterator;
import java.util.List;

import org.soton.peleus.act.planner.PlanContextExtractor;
import org.soton.peleus.act.planner.ProblemOperators;

public class ProblemOperatorsImpl extends ProblemOperators{
	
	public ProblemOperatorsImpl() {
		super();
	}

	@Override
	public String toPlannerString() {
		StringBuffer sb = new StringBuffer();
		
		for (Plan plan : operators) {
			Trigger trigger = plan.getTriggerEvent();
			sb.append("operator: ");
			sb.append(trigger.getLiteral().getFunctor());
			
			//Append operator parameters
			sb.append("(");
			if(trigger.getLiteral().getTermsSize() > 0) {
				Term types[] = plan.getLabel().getTermsArray();
				Term terms[] = trigger.getLiteral().getTermsArray();
				for (int i = 0; i < terms.length; i++) {
					sb.append(types[i]);
					/*sb.append(" ?");
					sb.append(terms[i]);*/
					sb.append(" ");
					sb.append(toStripsString(terms[i]));
					sb.append(((i+1) == terms.length) ? ")" : ", ");
				}
			}
			sb.append(System.getProperty("line.separator"));
			//Append restrictions on objects
			//Changed to adapt to Jason 0.9 new context format
			//List literals = plan.getContext();
			PlanContextExtractor contextExtractor = PlanContextExtractor.getPlanContextExtractor();
			contextExtractor.extractContext(plan);
			List<Term> contextTerms = contextExtractor.getContext();
			
			StringBuffer sbOpContext = new StringBuffer();
			StringBuffer sbOpPreconds = new StringBuffer();
			for (Iterator<Term> iter = contextTerms.iterator(); iter.hasNext();) {
				Term term = iter.next();
				//Representation of things in Jason 0.9 changed, now when we deal with
				//Expressions, we are dealing with context information
				if(term instanceof LogExprTerm) {
					if(sbOpContext.length() != 0) {
						sbOpContext.append(" & ");
					}
					sbOpContext.append(toStripsString(term));
				} else { // Otherwise we are dealing with preconditions
					if(sbOpPreconds.length() != 0) {
						sbOpPreconds.append(" & ");
					}
					sbOpPreconds.append(toStripsString(term));
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
			
			List<BodyLiteral> body = plan.getBody();
			StringBuffer sbPositiveLiterals = new StringBuffer();
			StringBuffer sbNegativeLiterals = new StringBuffer();
			for (BodyLiteral literal : body) {
				if(literal.getType() == BodyLiteral.BodyType.delBel) {
					if(sbNegativeLiterals.length() != 0) {
						sbNegativeLiterals.append(" & ");
					}
					sbNegativeLiterals.append(toStripsString(literal.getTerm()));
				}else if(literal.getType() == BodyLiteral.BodyType.addBel) {
					if(sbPositiveLiterals.length() != 0) {
						sbPositiveLiterals.append(" & ");
					}
					sbPositiveLiterals.append(toStripsString(literal.getTerm()));
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
	
	protected String toStripsOperator(LogExprTerm.LogicalOp logicalOp) {
		switch (logicalOp) {
		case and:
			return "&";
		case not:
			return "!";
		case or:
			return "|";
		case none:
		default:
			return "";
		}
	}
	
	protected String toStripsOperator(RelExprTerm.RelationalOp op) {
		switch (op) {
		case dif:
			return "!=";
		case eq:
			return "=";
		case gt:
			return ">";
		case gte:
			return ">=";
		case literalBuilder:
			return "ARGH";
		case lt:
			return "<";
		case lte:
			return "<=";
		case unify:
			return "==";
		case none:
		default:
			return "";
		}
	}
	
	protected String toStripsNegatedOperator(RelExprTerm.RelationalOp op) {
		switch (op) {
		case unify:
		case eq:
			return toStripsOperator(RelExprTerm.RelationalOp.dif);
		case dif:
			return toStripsOperator(RelExprTerm.RelationalOp.eq);
		case gt:
			return toStripsOperator(RelExprTerm.RelationalOp.lte);
		case gte:
			return toStripsOperator(RelExprTerm.RelationalOp.lt);
		case literalBuilder:
			return toStripsOperator(RelExprTerm.RelationalOp.literalBuilder);
		case lt:
			return toStripsOperator(RelExprTerm.RelationalOp.gte);
		case lte:
			return toStripsOperator(RelExprTerm.RelationalOp.gt);
		case none:
		default:
			return "";
		}
	}
	
	/**
	 * This method stinks big time by overriding the one in 
	 * the outter class.
	 * 
	 * @param term A Term to be converted into a STRIPS String
	 * @return A STRIPS-compatible String representation
	 */
	public String toStripsString(Term term) {
		StringBuffer sb = new StringBuffer();
		
		if(term.isVar()) {
			sb.append("?");
		}
		
		if(term instanceof LogExprTerm) {
			LogExprTerm exprTerm = (LogExprTerm) term;
			if(exprTerm.isUnary()) {
				if( (exprTerm.getOp() == LogicalOp.not) && 
					(exprTerm.getRHS() instanceof RelExprTerm)) {
					RelExprTerm exprTerm2 = (RelExprTerm) exprTerm.getRHS();
					if(!exprTerm2.getLHS().isVar()) {
						sb.append("@");
					}
					sb.append(toStripsString(exprTerm2.getLHS()));
					sb.append(toStripsNegatedOperator(exprTerm2.getOp()));
					if(!exprTerm2.getRHS().isVar()) {
						sb.append("@");
					}
					sb.append(toStripsString(exprTerm2.getRHS()));
				} else {
					sb.append(toStripsOperator(exprTerm.getOp()));
					sb.append(toStripsString(exprTerm.getRHS()));
				}
			} else {
				//sb.append("(");
				sb.append(toStripsString(exprTerm.getLHS()));
				sb.append(toStripsOperator(exprTerm.getOp()));
				sb.append(toStripsString(exprTerm.getRHS()));
				//sb.append(")");
			}
		} else if (term instanceof RelExprTerm) {
			RelExprTerm exprTerm = (RelExprTerm) term;
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
	}
}