package org.javaee7.jms.xa;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSConnectionFactoryDefinitions;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSProducer;
import javax.jms.Queue;

@JMSDestinationDefinition(
    name = "java:/app/jms/queue", 
    interfaceName = "javax.jms.Queue"
)
@JMSConnectionFactoryDefinitions(
    value = {
        // Will be selected via the NonXAConnectionFactoryProducer
        @JMSConnectionFactoryDefinition(
            name = "java:app/jms/nonXAconnectionFactory",
            transactional = false,
            resourceAdapter="activemq-ra",
            properties = {
                            "connectors=in-vm",}
        ),
        
        // Will be selected via the XAConnectionFactoryProducer
        @JMSConnectionFactoryDefinition(
            name = "java:app/jms/xaConnectionFactory",
            resourceAdapter="activemq-ra",
            properties = {
                            "connectors=in-vm",}
        )
    }        
)
@Singleton
public class JMSSender {

    @Inject
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/app/jms/queue")
    private Queue queue;

    public void sendMessage(String payload, boolean transacted) {
        try (JMSContext context = connectionFactory.createContext()) {
            System.out.println("---------Sending...");
            context.createProducer().send(queue, payload);            
            System.out.println("---------JMSContext is transacted:" + context.getTransacted());
            if (transacted && context.getTransacted()) {
                 context.commit();
            }
            System.out.println("---------Sent!");
        }
    }
}
