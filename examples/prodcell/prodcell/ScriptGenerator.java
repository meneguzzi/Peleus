package prodcell;

import jason.asSyntax.Literal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class ScriptGenerator {
	protected Random random;
	protected int steps;
	protected int timeBetweenSteps;
	protected OutputStream outputStream;
	protected int firstStep;
	protected List<Literal> initialBeliefs;
	protected List<Literal> finalEvents;
	
	protected List<Block> blocks;
	protected int maxBlockType;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScriptGenerator generator = new ScriptGenerator(args);
		Document script = generator.createScript();
		try {
			generator.writeDocument(script);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ScriptGenerator(String args[]) {
		this.random = new Random();
		this.outputStream = System.out;
		this.steps = 10;
		this.timeBetweenSteps = 1;
		this.blocks = new ArrayList<Block>();
		this.maxBlockType = 3; 
		parseArgs(args);
	}
	
	protected void parseArgs(String args[]) {
		for (int i = 0; i < args.length; i++) {
			if(args[i].equals("-o")) {
				if(++i < args.length) {
					File file = new File(args[i]);
					try {
						this.outputStream = new FileOutputStream(file);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.err.println("-o parameter requires a filename.");
				}
			} else if(args[i].equals("-maxtype")) {
				if(++i < args.length) {
					try {
						int maxType = Integer.parseInt(args[i]);
						this.maxBlockType = maxType;
					}catch (NumberFormatException e) {
						System.err.println("-maxtype parameter requires an integer");
					}
				} else {
					System.err.println("-maxtype parameter requires an integer");
				}
			} else if(args[i].equals("-steps")) {
				if(++i < args.length) {
					try {
						int steps = Integer.parseInt(args[i]);
						this.steps = steps;
					}catch (NumberFormatException e) {
						System.err.println("-steps parameter requires an integer");
					}
				} else {
					System.err.println("-steps parameter requires an integer");
				}
			} else if (args[i].equals("-stepsize")) {
				if(++i < args.length) {
					try {
						int stepsize = Integer.parseInt(args[i]);
						this.timeBetweenSteps = stepsize;
					}catch (NumberFormatException e) {
						System.err.println("-stepsize parameter requires an integer");
					}
				} else {
					System.err.println("-stepsize parameter requires an integer");
				}
			} else if (args[i].equals("-firststep")) {
				if(++i < args.length) {
					try {
						int firstStep = Integer.parseInt(args[i]);
						this.firstStep = firstStep;
					}catch (NumberFormatException e) {
						System.err.println("-stepsize parameter requires an integer");
					}
				} else {
					System.err.println("-stepsize parameter requires an integer");
				}
			} else if (args[i].equals("-initbeliefs")) {
				if(++i < args.length) {
					this.initialBeliefs = new ArrayList<Literal>();
					while(i < args.length && !args[i].startsWith("-")) {
						Literal l = Literal.parseLiteral(args[i]);
						this.initialBeliefs.add(l);
						i++;
					}
					i--;
				} else {
					System.err.println("-initbeliefs parameter requires a list of beliefs");
				}
			} else if (args[i].equals("-finalevents")) {
				if(++i < args.length) {
					this.finalEvents = new ArrayList<Literal>();
					while(i < args.length && !args[i].startsWith("-")) {
						Literal l = Literal.parseLiteral(args[i]);
						this.finalEvents.add(l);
						i++;
					}
					i--;
				} else {
					System.err.println("-finalevents parameter requires a list of beliefs");
				}
			} else {
				System.err.println("Unrecognized parameter: "+args[i]);
			}
		}
	}
	
	public void writeDocument(Document document) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StreamResult result = new StreamResult(outputStream);
		DOMSource source = new DOMSource(document);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
	}
	
	public Document createScript() {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		Document document = builder.newDocument();
		
		Element scriptElement = document.createElement("script");
		document.appendChild(scriptElement);
		
		if(this.initialBeliefs != null) {
			Element step = createScriptStep(document, 0, this.initialBeliefs);
			step.setAttribute("time", "0");
			scriptElement.appendChild(step);
		}
		
		this.addSteps(document, scriptElement, steps);
		
		if(this.finalEvents != null) {
			Element finalStep = createScriptStep(document, steps, this.finalEvents);
			scriptElement.appendChild(finalStep);
		}
		
		return document;
	}
	
	protected void addSteps(Document document, Element scriptElement, int steps) {
		for(int i=0; i < steps; i++) {
			List<Literal> percepts = createPercepts(i);
			Element step = createScriptStep(document, i, percepts);
			scriptElement.appendChild(step);
		}
	}
	
	protected List<Literal> createPercepts(int time) {
		List<Literal> percepts = new ArrayList<Literal>();
		
		Block block = new Block(0,0);
				
		do {
			block.blockNumber = time;
			block.blockType = random.nextInt(maxBlockType+1);
		} while (blocks.contains(block));
		
		blocks.add(block);
		percepts.add(Literal.parseLiteral("object(block, block"+block.blockNumber+")"));
		percepts.add(Literal.parseLiteral("type(block"+block.blockNumber+", type"+block.blockNumber+")"));
		percepts.add(Literal.parseLiteral("over(block"+block.blockNumber+", feedBelt)"));
		
		return percepts;
	}
	
	protected Element createScriptStep(Document document, int time, List<Literal> percepts) {
		Element step = document.createElement("step");
		step.setAttribute("time", ""+(firstStep + (time*timeBetweenSteps)));
		StringBuffer buffer = new StringBuffer();
		
		for (Literal literal : percepts) {
			buffer.append(literal.toString());
			//buffer.append(System.getProperty("line.separator"));
			buffer.append(".\n");
		}
		Text text = document.createTextNode(buffer.toString());
		
		step.appendChild(text);
		
		return step;
	}
	
	class Block {
		public int blockNumber;
		public int blockType;
		
		public Block(int blockType, int blockNumber) {
			this.blockNumber = blockNumber;
			this.blockType = blockType;
		}
	}
}
