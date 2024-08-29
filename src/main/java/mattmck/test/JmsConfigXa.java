package mattmck.test;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfigXa {
	
	@Bean(name = "xaJmsProperties")
    @ConfigurationProperties("spring.activemq")
    public ActiveMQProperties activeMQProperties() {
        return new ActiveMQProperties();
    }

    @Bean(name = "xaJmsConnectionFactory")
    public ConnectionFactory jmsConnectionFactory(@Qualifier("xaJmsProperties") ActiveMQProperties properties) {

        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory(properties.getBrokerUrl());
        connectionFactory.setUserName(properties.getUser());
        connectionFactory.setPassword(properties.getPassword());

        AtomikosConnectionFactoryBean xaConnectionFactory = new AtomikosConnectionFactoryBean();
        xaConnectionFactory.setUniqueResourceName("xa_JmsConnectionFactory");
        xaConnectionFactory.setXaConnectionFactory(connectionFactory);
        xaConnectionFactory.setPoolSize(5);

        return xaConnectionFactory;
    }
    
    @Bean(name = "xaJmsTemplate")
    public JmsTemplate jmsTemplate(@Qualifier("xaJmsConnectionFactory") ConnectionFactory connectionFactory) {

        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }
}
