/**
 * 
 */
package org.soton.peleus.script;

import jason.asSyntax.Rule;
import jason.asSyntax.parser.ParseException;
import jason.asSyntax.parser.as2j;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * An XML event handler for a JasonScript
 * @author Felipe Meneguzzi
 */
public class JasonScriptContentHandler extends DefaultHandler {
	
	public static final String SCRIPT="script";
	public static final String STEP="step";
	
	public static final String ATTR_TIME="time";
	
	protected JasonScript jasonScript;
	protected List<Rule> currentEventList;
	protected int currentTime;
	protected as2j jasonParser;
	protected CharArrayReader charArrayReader;

	public JasonScriptContentHandler() {
		this.jasonScript = null;
		currentEventList = null;
		currentTime = 0;
		charArrayReader = null;
		jasonParser = new as2j(charArrayReader);
	}

	public JasonScript getJasonScript() {
		return jasonScript;
	}

	@Override
	public void startDocument() throws SAXException {
		this.jasonScript = new JasonScriptImpl();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(qName.equals(STEP)) {
			//System.out.println("Processing "+STEP);
			if(attributes.getValue(ATTR_TIME) == null)
				throw new SAXException("Element "+STEP+" must have a "+ATTR_TIME+" attribute");
			try{
				currentTime = Integer.parseInt(attributes.getValue(ATTR_TIME));
			} catch (Exception e) {
				throw new SAXException("Invalid "+ATTR_TIME+" attribute", e);
			}
			currentEventList = new ArrayList<Rule>();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(currentEventList != null) {
			//System.out.println("Processing events at time "+currentTime);
			charArrayReader = new CharArrayReader(ch, start, length);
			jasonParser.ReInit(charArrayReader);
			try {
				jasonParser.belief_base(currentEventList);
			} catch (ParseException e) {
				throw new SAXException("Invalid events at time "+currentTime, e);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals(STEP)) {
			if(currentEventList != null) {
				this.jasonScript.addEvents(currentTime, currentEventList);
				currentEventList = null;
			} else {
				throw new SAXException("Problems processing events");
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
}
