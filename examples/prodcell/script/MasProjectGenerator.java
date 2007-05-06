package script;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class to generate a number of Jason project files for testing
 * @author meneguzz
 *
 */
public class MasProjectGenerator {
	protected List<JasonAgent> agents;
	protected String agentClassName;
	protected String environmentClassName;
	protected String targetDirectory;
	protected String projectName;
	protected int numberOfProjects;
	
	
	public static void main(String[] args) {
		MasProjectGenerator generator = new MasProjectGenerator(args);
		generator.generateProjects();
	}
	
	public MasProjectGenerator(String args[]) {
		this.agents = new ArrayList<JasonAgent>();
		this.agentClassName = null;
		this.environmentClassName = null;
		this.targetDirectory = "./";
		this.numberOfProjects = 10;
		this.projectName = null;
		this.parseArguments(args);
	}
	
	protected void parseArguments(String args[]) {
		this.agents.add(new JasonAgent("prodcell"));
		//this.projectName = "prodcell";
	}
	
	public void generateProjects() {
		for (int i = 0; i < numberOfProjects; i++) {
			generateProject(i);
		}
	}
	
	protected void generateProject(int number) {
		
	}
	
	protected class JasonAgent {
		public String agentName;
		
		public JasonAgent(String agentName) {
			
		}
	}
}
