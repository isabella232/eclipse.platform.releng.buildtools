package org.eclipse.releng;

import java.io.*;
import java.util.Properties;

/**
 * Class that stores build identification information taken from monitor.
 * properties as String objects
 */
public class BuildProperties {
	// recipients key value setting.  Comma separated list of email addresses of those who should
	// receive build information
	private String recipientList = "";

	// email address of the sender
	private String sender = "";
	// mail server name
	private String host = "";

	// default name of the build log file used with listener
	private String logFile = "index.php";

	// the prefix prepended to the subject of build related emails
	private String buildSubjectPrefix="[build]";

	// the build id,  typically <buildType><build date>	
	private String buildid;
	// the date and time of the build
	private String timestamp;
	// the name of the directory containing the builds, typically <buildType>-<buildType><build date>-<timestamp>
	private String buildLabel;

	// the Object that holds the key value pairs in monitor.properties
	private Properties buildProperties;

	public BuildProperties() {
		buildProperties = new Properties();
		// retrieve information from monitor.properties file.
		//  This file should reside in the same directory as the startup.jar at build time.
		try {
			buildProperties.load(
				new FileInputStream(new File("monitor.properties")));

			try {
					buildSubjectPrefix = buildProperties.get("buildSubjectPrefix").toString();
				} catch (NullPointerException e) {
					System.out.println(
						"Value for buildSubjectPrefix not found in monitor.properties");
					System.out.println(
							"Default value, buildSubjectPrefix=[build] will be used.");

				}

			try {
				buildid = buildProperties.get("buildid").toString();
			} catch (NullPointerException e) {
				System.out.println(
					"Value for buildid not found in monitor.properties");
			}

			try {
				buildLabel = buildProperties.get("buildLabel").toString();
			} catch (NullPointerException e) {
				System.out.println(
					"Value for buildLabel not found in monitor.properties");
			}
			try {
				timestamp = buildProperties.get("timestamp").toString();
			} catch (NullPointerException e) {
				System.out.println(
					"Value for timestamp not found in monitor.properties");
			}

			try {
				recipientList = buildProperties.get("recipients").toString();
			} catch (NullPointerException e) {
				System.out.println(
					"Value for recipients not found in monitor.properties");

			}

			try {
				sender = buildProperties.get("sender").toString();
			} catch (NullPointerException e) {
				System.out.println(
					"Value for sender not found in monitor.properties");
			}

			try {
				host = buildProperties.get("host").toString();
			} catch (NullPointerException e) {
				System.out.println(
					"Value for host not found in monitor.properties");
			}

			try {
				logFile = buildProperties.get("log").toString();
			} catch (NullPointerException e) {
				System.out.println(
					"Value for log not found in monitor.properties");
				System.out.println(
					"Default value, log=index.php will be used.");
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void main(String args[]) {
		BuildProperties buildProperties = new BuildProperties();
	}


	/**
	 * Returns the buildLabel.
	 * @return String
	 */
	public String getBuildLabel() {
		return buildLabel;
	}

	/**
	 * Sets the buildLabel.
	 * @param buildLabel The buildLabel to set
	 */
	public void setBuildLabel(String buildLabel) {
		this.buildLabel = buildLabel;
	}

	/**
	 * Returns the logFile.
	 * @return String
	 */
	public String getLogFile() {
		return logFile;
	}

	/**
	 * Sets the logFile.
	 * @param logFile The logFile to set
	 */
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	/**
	 * Returns the buildid.
	 * @return String
	 */
	public String getBuildid() {
		return buildid;
	}

	/**
	 * Returns the timestamp.
	 * @return String
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the buildid.
	 * @param buildid The buildid to set
	 */
	public void setBuildid(String buildid) {
		this.buildid = buildid;
	}

	/**
	 * Sets the timestamp.
	 * @param timestamp The timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns the host.
	 * @return String
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Returns the recipientList.
	 * @return String
	 */
	public String getRecipientList() {
		return recipientList;
	}

	/**
	 * Returns the sender.
	 * @return String
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the host.
	 * @param host The host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Sets the recipientList.
	 * @param recipientList The recipientList to set
	 */
	public void setRecipientList(String recipientList) {
		this.recipientList = recipientList;
	}

	/**
	 * Sets the sender.
	 * @param sender The sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Returns the buildSubjectPrefix.
	 * @return String
	 */
	public String getBuildSubjectPrefix() {
		return buildSubjectPrefix;
	}

	/**
	 * Sets the buildSubjectPrefix.
	 * @param buildSubjectPrefix The buildSubjectPrefix to set
	 */
	public void setBuildSubjectPrefix(String buildSubjectPrefix) {
		this.buildSubjectPrefix = buildSubjectPrefix;
	}

}