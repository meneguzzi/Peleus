package org.soton.peleus.script;

import jason.asSyntax.Literal;
import jason.asSyntax.Rule;

import java.util.List;

/**
 * An interface to a script used to post sensor information to JasonAgents
 * at regular intervals.
 * 
 * @author Felipe Meneguzzi
 *
 */
public interface JasonScript {
	
	/**
	 * Returns a list with all the events for the specified time.
	 * @param time The point in time for which the events are to be returned.
	 * @return A list of <code>jason.asSyntax.Rule</code>.
	 */
	public List<Rule> getEvents(int time);
	
	/**
	 * Conveniency method to allow the list of events to be expressed as literals
	 * @param time The point in time for which the events are to be returned.
	 * @return A list of <code>jason.asSyntax.Literal</code>.
	 */
	public List<Literal> getPercepts(int time);
	
	/**
	 * Adds a list of events to be posted at the specified point in time.
	 * @param time The point in time for which the events are to be added.
	 * @param events A list of <code>jason.asSyntax.Rule</code>.
	 */
	public void addEvents(int time, List<Rule> events);
	
	/**
	 * Adds a single event to the specified point in time.
	 * @param time The point in time for which the event is to be added.
	 * @param rule An instance of <code>jason.asSyntax.Rule</code>
	 */
	public void addEvent(int time, Rule rule);
}
