/**
 * 
 */
package org.soton.peleus.mot;

import jason.asSemantics.Agent;
import jason.asSemantics.Event;
import jason.asSemantics.Intention;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.soton.peleus.mot.parser.MotivationParser;

/** 
 * This class represents a motivated agent, capable of generating goals autonomously
 * while responding to a model of motivational intensity.
 * 
 * @author Felipe Rech Meneguzzi
 */
public class MotivatedAgent extends Agent {
	
	protected static final Logger logger = Logger.getLogger(MotivatedAgent.class.getName());
	
	/**
	 * 
	 */
	protected List<Motivation> motivations;
	
	/**
	 * 
	 */
	protected Hashtable<Term, Motivation> motivatedPendingGoals;

	protected List<Motivation> triggeredMotivations;
	
	protected Intention motivatedIntention;
	
	public MotivatedAgent() {
		motivations = new ArrayList<Motivation>();
		triggeredMotivations = new ArrayList<Motivation>();
		motivatedPendingGoals = new Hashtable<Term, Motivation>();
		this.motivatedIntention = new Intention();
		
		//XXX Review this
		try {
			readMotivations("motivations.mot");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readMotivations(String file) throws Exception {
		FileInputStream inputStream = new FileInputStream(file);
		MotivationParser parser = new MotivationParser(inputStream);
		List<Motivation> mots = parser.parse();
		//logger.info(parser.toString());
		this.motivations.addAll(mots);
	}

	@Override
	public void buf(List<Literal> percepts) {
		super.buf(percepts);
		logger.fine("Updating Motivations");
		//First mitigate any triggered motivations
		for (Iterator<Motivation> iter = triggeredMotivations.iterator(); iter.hasNext();) {
			Motivation motivation = iter.next();
			if(motivation.getMotivationIntensity() < motivation.getMotivationThreshold()) {
				logger.info("Motivation "+motivation.getMotivationName()+" mitigated");
				iter.remove();
			}
		}
		List<Motivation> newTriggeredMotivations = new ArrayList<Motivation>();
		for (Motivation	motivation : motivations) {
			logger.fine("Updating Motivation: '"+motivation.getMotivationName()+"'");
			boolean thresholdReached = motivation.updateIntensity(this.fBB);
			if(thresholdReached && !triggeredMotivations.contains(motivation)) {
				logger.info(motivation.getMotivationName()+" threshold reached.");
				//triggeredMotivations.add(motivation);
				newTriggeredMotivations.add(motivation);
			}
		}
		//Then for each triggered motivation, generate goals
		for (Motivation motivation : newTriggeredMotivations) {
			List<Trigger> goals = motivation.generateGoals(this.fBB);
			triggeredMotivations.add(motivation);
			//For each generated "goal", post a new event for the agent
			for (Trigger trigger : goals) {
				logger.info("Adding goal "+trigger.toString());
				//I'm currently using the Empty Intention to create the event
				//But perhaps it would be interesting to have an intention
				//structure specifically for motivated goals
				//this.fTS.updateEvents(new Event(trigger, Intention.EmptyInt));
				this.addMotivatedGoal(trigger);
			}
		}
	}
	
	public void addMotivatedGoal(Trigger trigger) {
		this.fTS.updateEvents(new Event(trigger, motivatedIntention));
	}
}
