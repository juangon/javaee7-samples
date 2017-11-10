package org.javaee7.jms.temp.destination;

import javax.ejb.Singleton;
import javax.jms.JMSDestinationDefinition;

/**
 * Application scoped JMS resources for the samples.
 * 
 * @author Patrik Dudits
 */
@JMSDestinationDefinition(
	name = Resources.REQUEST_QUEUE, 
	interfaceName = "javax.jms.Queue", 
	destinationName = "requestQueue", 
	description = "Queue for service requests")
@Singleton
public class Resources {
	public static final String REQUEST_QUEUE = "java:global/jms/requestQueue";
}
