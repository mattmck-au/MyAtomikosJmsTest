package mattmck.test;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfigNonXa {

	@Bean
    @Primary
	@ConfigurationProperties("spring.activemq")
    public ActiveMQProperties activeMQProperties() {
        return new ActiveMQProperties();
    }
	
    @Bean
    @Primary
    public ConnectionFactory jmsConnectionFactory(ActiveMQProperties properties) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getBrokerUrl());
        connectionFactory.setUserName(properties.getUser());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }
    
    @Bean
    @Primary
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
    	JmsTemplate jmsTemplate = new JmsTemplate();
    	jmsTemplate.setConnectionFactory(connectionFactory);
    	return jmsTemplate;
    }
}
