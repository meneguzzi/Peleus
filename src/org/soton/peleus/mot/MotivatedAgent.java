/**
 * 
 */
package org.soton.peleus.mot;

import jason.asSemantics.Agent;
import jason.asSemantics.Event;
import jason.asSemantics.IntendedMeans;
import jason.asSemantics.Intention;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.asSyntax.TermImpl;
import jason.asSyntax.Trigger;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
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
		//first remove from the triggered motivations, any motivation whose plan failed
		
		
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
				trigger.getLiteral().addAnnot(TermImpl.parse(motivation.getMotivationName()));
				logger.info("Adding goal "+trigger.toString());
				//I'm currently using the Empty Intention to create the event
				//But perhaps it would be interesting to have an intention
				//structure specifically for motivated goals
				//this.fTS.updateEvents(new Event(trigger, Intention.EmptyInt));
				this.addMotivatedGoal(trigger);
			}
		}
		//Then mitigate any triggered motivations that might have been satisfied
		for (Iterator<Motivation> iter = triggeredMotivations.iterator(); iter.hasNext();) {
			Motivation motivation = iter.next();
			
			if(motivation.mitigate(this.fBB)) {
				logger.info("Motivation "+motivation.getMotivationName()+" mitigated");
				iter.remove();
			} /*else {
				logger.info("Motivation "+motivation.getMotivationName()+" not mitigated yet");
			}*/
			//Old way of mitigating the motivation, in which the agent would only
			//believe that the motivation was mitigated if the intensity of the motivation
			//had dropped below the threshold
			/*if(motivation.getMotivationIntensity() < motivation.getMotivationThreshold()) {
				logger.info("Motivation "+motivation.getMotivationName()+" mitigated");
				iter.remove();
			}*/
		}
	}
	
	@Override
	public Intention selectIntention(Queue<Intention> intentions) {
		// TODO Use motivations to select the intention
		//return super.selectIntention(intentions);
		//logger.info("Selecting intention...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intention mostMotivatedIntention = null;
		int higherIntensity = 0;
		for (Iterator iter = intentions.iterator(); iter.hasNext();) {
			Intention intention = (Intention) iter.next();
			IntendedMeans intendedMeans = intention.get(0);
			ListTerm annots = intendedMeans.getTrigger().getLiteral().getAnnots();
			for (Iterator iterator = annots.iterator(); iterator.hasNext();) {
				Term term = (Term) iterator.next();
				Motivation motivation = getMotivation(term.toString());
				if(motivation != null && motivation.getMotivationIntensity() > higherIntensity) {
					logger.info("Most motivated intention is "+motivation.getMotivationName());
					mostMotivatedIntention = intention;
					higherIntensity = motivation.getMotivationIntensity();
				}
			}
		}
		if(mostMotivatedIntention != null) {
			return mostMotivatedIntention;
		} else {
			logger.info("No motivated intention found.");
			return super.selectIntention(intentions);
		}
	}
	
	public void addMotivatedGoal(Trigger trigger) {
		this.fTS.updateEvents(new Event(trigger, motivatedIntention));
	}
	
	public Motivation getMotivation(String motivationName) {
		for (Iterator iter = motivations.iterator(); iter.hasNext();) {
			Motivation motivation = (Motivation) iter.next();
			if(motivation.getMotivationName().equals(motivationName)) {
				return motivation;
			}
		}
		return null;
	}
	
	public List<Motivation> getMotivations() {
		return motivations;
	}
}
