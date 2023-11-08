package edu.kalum.kalummanagement.core.broker;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);
        return connectionFactory;
    }

    @Bean
    public Queue testQueue(){
        return new Queue("queue-candidateprocess", true);
    }
    @Bean
    DirectExchange exchange(){
        return new DirectExchange("exchange-candidate-process");
    }
    @Bean
    Binding testBinding(Queue testQueue, DirectExchange exchange){
        return BindingBuilder.bind(testQueue).to(exchange).with("expediente");
    }
}
