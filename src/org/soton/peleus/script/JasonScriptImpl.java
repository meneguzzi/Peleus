package org.soton.peleus.script;

import jason.asSyntax.Literal;
import jason.asSyntax.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JasonScriptImpl implements JasonScript {
	protected HashMap<Integer, List<Rule>> eventsMap;
	
	public JasonScriptImpl() {
		this.eventsMap = new HashMap<Integer, List<Rule>>();
	}

	public void addEvent(int time, Rule rule) {
		List<Rule> list = null;
		if (eventsMap.containsKey(time) && eventsMap.get(time) != null) {
			list = eventsMap.get(time);
		} else {
			list = new ArrayList<Rule>();
		}
		
		list.add(rule);
	}

	public void addEvents(int time, List<Rule> events) {
		if (eventsMap.containsKey(time) && eventsMap.get(time) != null) {
			eventsMap.get(time).addAll(events);
		} else {
			eventsMap.put(time, events);
		}
	}

	public List<Rule> getEvents(int time) {
		if (eventsMap.containsKey(time) && eventsMap.get(time) != null) {
			return eventsMap.get(time);
		}
		return null;
	}

	public List<Literal> getPercepts(int time) {
		if (eventsMap.containsKey(time) && eventsMap.get(time) != null) {
			List<Literal> list = new ArrayList<Literal>(eventsMap.get(time));
			return list;
		}
		return null;
	}

}
