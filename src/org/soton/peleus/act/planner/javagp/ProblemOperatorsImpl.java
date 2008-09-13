package org.soton.peleus.act.planner.javagp;

import graphplan.domain.DomainDescription;
import graphplan.domain.Operator;
import graphplan.domain.Proposition;
import graphplan.domain.jason.OperatorImpl;
import graphplan.domain.jason.PropositionImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanBody;
import jason.asSyntax.PlanBodyImpl;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;
import jason.asSyntax.PlanBody.BodyType;

import java.util.ArrayList;
import java.util.List;

import org.soton.peleus.act.planner.PlanContextExtractor;
import org.soton.peleus.act.planner.ProblemOperators;

public class ProblemOperatorsImpl extends ProblemOperators {
	
	protected final List<Operator> operators;
	
	public ProblemOperatorsImpl() {
		super();
		operators = new ArrayList<Operator>();
	}
	
	/**
	 * Creates problem operators given a set of AgentSpeak plans.
	 * @param plans
	 */
	public ProblemOperatorsImpl(List<Plan> plans) {
		this();
		this.plans = plans;
		this.generateOperators();
	}
	
	/**
	 * Generates the problem operators using the information stored in 
	 * another instance of <code>problemOperators</code>.
	 * @param problemOperators
	 */
	public ProblemOperatorsImpl(ProblemOperators problemOperators) {
		this(problemOperators.getPlans());
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Operator> getOperators() {
		return this.operators;
	}
	
	/**
	 * Generates the planner operators from the list of plans from the
	 * Jason plan library.
	 */
	protected void generateOperators() {
		this.operators.clear();
		for (Plan plan : plans) {
			Trigger trigger = plan.getTriggerEvent();
			
			PlanContextExtractor contextExtractor = PlanContextExtractor.getPlanContextExtractor();
			contextExtractor.extractContext(plan);
			List<LogicalFormula> contextTerms = contextExtractor.getContext();
			
			//We create the preconditions based on the AgentSpeak context
			List<Proposition> preconds = new ArrayList<Proposition>();
			
			//The constraints to the types are represented as preconditions
			if(trigger.getLiteral().getArity() > 0) {
				Term types[] = plan.getLabel().getTermsArray();
				Term terms[] = trigger.getLiteral().getTermsArray();
				for (int i = 0; i < terms.length; i++) {
					PropositionImpl proposition = new PropositionImpl(true,types[i].toString());
					proposition.addTerm(terms[i]);
					
					preconds.add(proposition);
				}
			}
			
			//We then add the AgentSpeak plan context to the preconditions
			for (LogicalFormula formula : contextTerms) {
				//Right now we only deal with literals in the context
				//So for each literal, we create a proposition for JavaGP
				if(formula instanceof Literal) {
					preconds.add(new PropositionImpl((Literal)formula));
				}
			}
			
			//Then we try to extract belief the effects from the plan body
			List<Proposition> effects = new ArrayList<Proposition>();
			for (PlanBody bodyLiteral : (PlanBodyImpl)plan.getBody()) {
				PropositionImpl proposition = null;
				if(bodyLiteral.getBodyType() == BodyType.delBel) {
					// XXX We were having problems with the variables using this method of instantiation
					//proposition = new PropositionImpl(false, literal.getLiteralFormula().toString());
					// XXX So we changed it to this mode
					proposition = new PropositionImpl((Literal) bodyLiteral.getBodyTerm());
					proposition.setNegated(Literal.LNeg);
				} else if(bodyLiteral.getBodyType() == BodyType.addBel) {
					// XXX We were having problems with the variables using this method of instantiation
					//proposition = new PropositionImpl(true, literal.getLiteralFormula().toString());
					// XXX So we changed it to this mode
					proposition = new PropositionImpl((Literal) bodyLiteral.getBodyTerm());
				}
				
				//If proposition is null, this is a part of the plan we can't cope, 
				//so we ignore it. Adding only valid effects
				if(proposition != null) {
					effects.add(proposition);
				}
			}
			
			Structure operatorSignature = new Structure(trigger.getLiteral());
			OperatorImpl operator = new OperatorImpl(operatorSignature, preconds, effects);
			operators.add(operator);
		}
	}
	
	/**
	 * Generates an instance of <code>DomainDescription</code> containing operators
	 * derived from the list of Jason <code>Plan</code>s.
	 * 
	 * @param plans
	 * @return
	 */
	protected DomainDescription generateDomainDescription(List<Operator> operators) {
		DomainDescription description = null;		
		List<Proposition> empty = new ArrayList<Proposition>();
		description = new DomainDescription(operators, empty, empty);
		return description;
	}
	
	/**
	 * Returns a JavaGP domain description
	 * @return
	 */
	public DomainDescription getDomainDescription() {
		return this.generateDomainDescription(operators);
	}

	@Override
	public String toPlannerString() {
		return generateDomainDescription(operators).toString();
	}

}
