package org.soton.peleus.act.planner.javagp;

import graphplan.PlanResult;
import graphplan.domain.Operator;
import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;

import org.soton.peleus.act.planner.StripsPlan;

public class StripsPlanImpl extends StripsPlan {

	protected final PlanResult planResult;
	
	public StripsPlanImpl(PlanResult planResult) {
		this.planResult = planResult;
	}
	
	public StripsPlanImpl(byte[] stripsPlan) {
		super(stripsPlan);
		planResult = new PlanResult(false);
	}

	@Override
	public Plan toAgentSpeakPlan(int planSequence) {
		StringBuffer sbNewPlan = new StringBuffer();
		//sbNewPlan.append("@p"+planSequence+"[atomic]");
		sbNewPlan.append(System.getProperty("line.separator"));
		sbNewPlan.append("+!executePlan(plan"+(planSequence)+") : true");
		sbNewPlan.append(System.getProperty("line.separator"));
		sbNewPlan.append("  <- ");
		
		sbNewPlan.append(this.convertPlanBody());
		
		return Plan.parse(sbNewPlan.toString());
	}

	@Override
	public Plan toGenericAgentSpeakPlan(Literal triggerCondition,
			LogicalFormula contextCondition) {
		StringBuffer sbNewPlan = new StringBuffer();
		sbNewPlan.append("+!"+triggerCondition.toString()+" : "+contextCondition.toString());
		sbNewPlan.append(System.getProperty("line.separator"));
		sbNewPlan.append("  <- ");
		
		//sbNewPlan.append(".print(\"Shite!!\");");
		sbNewPlan.append(".print(\"Executing generated plan\");");
		
		sbNewPlan.append(this.convertPlanBody());
		
		return Plan.parse(sbNewPlan.toString());
	}
	
	protected String convertPlanBody() {
		StringBuilder sb = new StringBuilder();
		
		for(Operator operator: planResult) {
			sb.append("!");
			sb.append(operator.getSignature());
			sb.append(";");
			sb.append(System.getProperty("line.separator"));
		}
		
		sb.append("true.");
		
		return sb.toString();
	}

}
