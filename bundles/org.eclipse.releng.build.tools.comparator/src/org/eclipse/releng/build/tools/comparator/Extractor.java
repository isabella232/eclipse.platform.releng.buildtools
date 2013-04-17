package org.eclipse.releng.build.tools.comparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for extracting the relevent "Debug" messages from
 * the huge maven debug log.
 * 
 * @author davidw
 * 
 */
public class Extractor {

	private final String BUILD_DIRECTORY_PROPERTY = "builddirectory";
	private final String debugFilename = "mb060_run-maven-build_output.txt";
	private final String fullOutputFilename = "buildtimeComparatorFull.log";
	private final String buildlogsDirectory = "buildlogs";
	private String buildDirectory;
	private String inputFilename;
	private String outputFilenameFull;
	private String regexPattern = "^\\[WARNING\\].*eclipse.platform.releng.aggregator/(.*)/pom.xml: baseline and build artifacts have same version but different contents";
	private Pattern mainPattern = Pattern.compile(regexPattern);
    private static final String EOL                = System.getProperty("line.separator", "\n");


	public Extractor() {

	}

	public static void main(String[] args) {
		Extractor extractor = new Extractor();
		if (args.length > 0) {
			extractor.setBuildDirectory(args[0]);
		}
		// test only
		extractor.setBuildDirectory("/home/davidw/temp/I20130416-1514");
		try {
			extractor.readInputfile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getInputFilename() {
		if (inputFilename == null) {
			inputFilename = getBuildDirectory() + "/" + buildlogsDirectory + "/" + debugFilename;
		}
		return inputFilename;
	}

	private String getOutputFilenameFull() {
		if (outputFilenameFull == null) {
			outputFilenameFull = getBuildDirectory() + "/" + buildlogsDirectory + "/" + fullOutputFilename;
		}
		return outputFilenameFull;
	}

	public String getBuildDirectory() {
		// if not set explicitly, see if its a system property
		if (buildDirectory == null) {
			buildDirectory = System.getProperty(BUILD_DIRECTORY_PROPERTY);
		}
		return buildDirectory;
	}

	public void setBuildDirectory(String buildDirectory) {
		this.buildDirectory = buildDirectory;
	}

	void readInputfile() throws IOException {
		File infile = new File(getInputFilename());
		Reader in = new FileReader(infile);
		BufferedReader input = null;
		input = new BufferedReader(in);
		File outfile = new File(getOutputFilenameFull());
		Writer out = new FileWriter(outfile);
		BufferedWriter output = new BufferedWriter(out);
		output.write("Comparator differences from current build" + EOL);
		output.write("\t" + getBuildDirectory() + EOL);
		output.write("\t\t" + "compared to reference repo at .../eclipse/updates/4.3-I-builds" + EOL + EOL);
		try {
			String inputLine = "";
			int count = 0;
			while (inputLine != null) {
				inputLine = input.readLine();
				if (inputLine != null) {
					Matcher matcher = mainPattern.matcher(inputLine);
					if (matcher.matches()) {
						count++;
						output.write(count  + ".  " + matcher.group(1) + EOL);
						// read and write differences, until next blank line
						do {
							inputLine = input.readLine();
							if (inputLine != null && inputLine.length() > 0) {
								output.write(inputLine + EOL);
							}
						} while (inputLine != null && inputLine.length() > 0);
						output.write(EOL);
					}
				}
			}
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}
}