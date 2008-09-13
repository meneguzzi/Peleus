/**
 * 
 */
package org.soton.peleus.act.planner.jemplan;

import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanBody;
import jason.asSyntax.PlanBodyImpl;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;
import jason.asSyntax.PlanBody.BodyType;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.soton.peleus.act.planner.PlanContextExtractor;
import org.soton.peleus.act.planner.ProblemOperators;

/**
 * @author  Felipe Meneguzzi
 */
public class ProblemOperatorsImpl extends ProblemOperators {
	
	protected static final Logger logger = Logger.getLogger(ProblemOperators.class.getName());	
	private final EMPlanPlannerConverter converter;
	
	public ProblemOperatorsImpl(EMPlanPlannerConverter converter) {
		this.converter = converter;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.ProblemOperators#toPlannerString()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String toPlannerString() {
		StringBuffer sb = new StringBuffer();
		
		for (Plan plan : plans) {
			Trigger trigger = plan.getTrigger();
			sb.append("operator ");
			sb.append(trigger.getLiteral());
			
			/*sb.append("(");
			if(trigger.getLiteral().getTermsSize() > 0) {
				Term types[] = plan.getLabel().getTermsArray();
				Term terms[] = trigger.getLiteral().getTermsArray();
				for (int i = 0; i < terms.length; i++) {
					sb.append(types[i]);
					//sb.append(" ?");
					//sb.append(terms[i]);
					sb.append(" ");
					sb.append(toStripsString(terms[i]));
					sb.append(((i+1) == terms.length) ? ")" : ", ");
				}
			}*/
			sb.append(System.getProperty("line.separator"));
			//Append preconditions
			sb.append("preconds (");
			
			//Changed to adapt to Jason 0.9 new context format
			//List<Literal> contextLiterals = plan.getContext();
			PlanContextExtractor contextExtractor = PlanContextExtractor.getPlanContextExtractor();
			contextExtractor.extractContext(plan);
			List<LogicalFormula> contextTerms = contextExtractor.getContext();
			
			StringBuffer sbOpPreconds = new StringBuffer();
			
			//The constraints to the types are represented as preconditions
			if(trigger.getLiteral().getArity() > 0) {
				Term types[] = plan.getLabel().getTermsArray();
				Term terms[] = trigger.getLiteral().getTermsArray();
				for (int i = 0; i < terms.length; i++) {
					sb.append(types[i]);
					sb.append("(");
					sb.append(converter.toStripsString(terms[i]));
					sb.append(")");
					if((i+1) != terms.length) {
						sb.append(", ");
					}
				}
				if(contextTerms.size() > 0)
					sb.append(", ");
			}
			
			for (Iterator<LogicalFormula> iter = contextTerms.iterator(); iter.hasNext();) {
				LogicalFormula formula = iter.next();
				if(sbOpPreconds.length() != 0) {
					sbOpPreconds.append(", ");
				}
				if(formula instanceof Literal) {
					Literal literal = (Literal) formula;
					sbOpPreconds.append(converter.toStripsString(literal));
				} else {
					logger.fine("Ignored formula: "+formula);
				}
			}
			if(sbOpPreconds.length() == 0) {
				sb.append("true");
			}else {
				sb.append(sbOpPreconds);
			}
			sb.append(")");
			sb.append(System.getProperty("line.separator"));
			
			PlanBody body = plan.getBody();
			StringBuffer sbEffects = new StringBuffer();
			for (PlanBody literal : (PlanBodyImpl)body) {
				if(literal.getBodyType() == BodyType.delBel) {
					if(sbEffects.length() != 0) {
						sbEffects.append(", ");
					}
					sbEffects.append("-");
					sbEffects.append(converter.toStripsString(literal.getBodyTerm()));
				}else if(literal.getBodyType() == BodyType.addBel) {
					if(sbEffects.length() != 0) {
						sbEffects.append(", ");
					}
					sbEffects.append(converter.toStripsString(literal.getBodyTerm()));
				}
			}
			
			sb.append("effects (");
			if(sbEffects.length() > 0) {
				sb.append(sbEffects);
			} else {
				sb.append("true");
			}
			sb.append(")");
			
			sb.append(System.getProperty("line.separator"));
			sb.append(System.getProperty("line.separator"));
		}
		
		sb.append(System.getProperty("line.separator"));
		
		return sb.toString();
	}

}
