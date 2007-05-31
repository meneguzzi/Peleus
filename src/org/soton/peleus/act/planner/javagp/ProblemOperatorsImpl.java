package org.soton.peleus.act.planner.javagp;

import graphplan.domain.DomainDescription;
import graphplan.domain.Operator;
import graphplan.domain.Proposition;
import graphplan.domain.jason.OperatorImpl;
import graphplan.domain.jason.PropositionImpl;
import jason.asSyntax.BodyLiteral;
import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;
import jason.asSyntax.Structure;
import jason.asSyntax.Trigger;

import java.util.ArrayList;
import java.util.List;

import org.soton.peleus.act.planner.PlanContextExtractor;
import org.soton.peleus.act.planner.ProblemOperators;

public class ProblemOperatorsImpl extends ProblemOperators {
	
	protected DomainDescription domainDescription = null;
	
	public ProblemOperatorsImpl() {
		super();
	}
	
	public ProblemOperatorsImpl(List<Plan> plans) {
		this();
		this.domainDescription = this.generateDomainDescription(plans);
	}
	
	/**
	 * Generates the problem operators using the information stored in 
	 * another instance of <code>problemOperators</code>.
	 * @param problemOperators
	 */
	public ProblemOperatorsImpl(ProblemOperators problemOperators) {
		this(problemOperators.getOperators());
	}
	
	/**
	 * Generates an instance of <code>DomainDescription</code> containing operators
	 * derived from the list of Jason <code>Plan</code>s.
	 * 
	 * @param plans
	 * @return
	 */
	protected DomainDescription generateDomainDescription(List<Plan> plans) {
		DomainDescription description = null;
		List<Operator> operators = new ArrayList<Operator>();
		for (Plan plan : plans) {
			Trigger trigger = plan.getTriggerEvent();
			
			PlanContextExtractor contextExtractor = PlanContextExtractor.getPlanContextExtractor();
			contextExtractor.extractContext(plan);
			List<LogicalFormula> contextTerms = contextExtractor.getContext();
			
			//We ignore type constraints here, so annotations are ignored
			
			//We then create the preconditions based on the AgentSpeak context
			List<PropositionImpl> preconds = new ArrayList<PropositionImpl>();
			for (LogicalFormula formula : contextTerms) {
				//Right now we only deal with literals in the context
				//So for each literal, we create a proposition for JavaGP
				if(formula instanceof Literal) {
					preconds.add(new PropositionImpl((Literal)formula));
				}
			}
			
			//Then we try to extract belief the effects from the plan body
			List<BodyLiteral> body = plan.getBody();
			List<PropositionImpl> effects = new ArrayList<PropositionImpl>();
			for (BodyLiteral literal : body) {
				PropositionImpl proposition = null;
				if(literal.getType() == BodyLiteral.BodyType.delBel) {
					proposition = new PropositionImpl(false, literal.getLiteralFormula().toString());
				} else if(literal.getType() == BodyLiteral.BodyType.addBel) {
					proposition = new PropositionImpl(true, literal.getLiteralFormula().toString());
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
		List<Proposition> empty = new ArrayList<Proposition>();
		description = new DomainDescription(operators, empty, empty);
		return description;
	}
	
	/**
	 * Returns a JavaGP domain description
	 * @return
	 */
	public DomainDescription getDomainDescription() {
		return this.domainDescription;
	}

	@Override
	public String toPlannerString() {
		if(domainDescription != null) {
			return domainDescription.toString();
		} else {
			return null;
		}
	}

}
