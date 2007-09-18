package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.soton.peleus.act.planner.StripsPlan;

public class StripsPlanImpl extends StripsPlan {
	
	public StripsPlanImpl(byte stripsPlan[]) {
		super(stripsPlan);
	}

	@Override
	public Plan toAgentSpeakPlan(int planSequence) {
		StringBuffer sbNewPlan = new StringBuffer();
		sbNewPlan.append("+!executePlan(plan"+(planSequence)+") : true");
		sbNewPlan.append(System.getProperty("line.separator"));
		sbNewPlan.append("  <- ");
		
		sbNewPlan.append(this.convertPlanBody());
		
		return Plan.parse(sbNewPlan.toString());
	}

	@Override
	public Plan toGenericAgentSpeakPlan(Literal triggerCondition, LogicalFormula contextCondition) {
		StringBuffer sbNewPlan = new StringBuffer();
		sbNewPlan.append("+"+triggerCondition.toString()+" : true [atomic]");
		sbNewPlan.append(System.getProperty("line.separator"));
		sbNewPlan.append("  <- ");
		
		sbNewPlan.append(this.convertPlanBody());
		
		return Plan.parse(sbNewPlan.toString());
	}
	
	/**
	 * Helper method to conver the plan body into a string representation of an
	 * AgentSpeak plan, as this is shared between the <code>toAgentSpeakPlan</code> and 
	 * <code>toGenericAgentSpeakPlan</code> methods.
	 * @return A string representation of the plan body.
	 */
	protected String convertPlanBody() {
		StringBuffer sbPlanBody = new StringBuffer();
		
		ByteArrayInputStream inStream = new ByteArrayInputStream(stripsPlan);
		InputStreamReader inReader = new InputStreamReader(inStream);
		BufferedReader reader = new BufferedReader(inReader);
		
		boolean firstStep = true;
		try {
			while(reader.ready()) {
				String planStep = reader.readLine();
				planStep = planStep.trim();
				//logger.info("Adding goal: "+planStep);
				//ts.getC().addGoal(Trigger.TEAchvG,Literal.parseLiteral(planStep), null);
				if(firstStep) {
					firstStep = false;
				} else {
					sbPlanBody.append(";");
					sbPlanBody.append(System.getProperty("line.separator"));
				}
				sbPlanBody.append("!");
				sbPlanBody.append(planStep);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sbPlanBody.append(".");
		
		return sbPlanBody.toString();
	}
}
