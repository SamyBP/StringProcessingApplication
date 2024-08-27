package com.app.restserver.configuration;

import com.app.restserver.dtos.ModuleName;
import com.app.restserver.persistance.queue.QueueDispatcherStrategy;
import com.app.restserver.persistance.queue.PostgresQueueDispatcherStrategy;
import com.app.restserver.persistance.queue.RabbitQueueDispatcherStrategy;
import com.app.restserver.validations.module.ModuleValidator;
import com.app.restserver.validations.module.SubstringModuleValidator;
import com.app.restserver.validations.module.TrimModuleValidator;
import com.app.restserver.validations.module.UpperLowerModuleValidator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfiguration {
    private final Environment environment;

    @Value("${rabbitmq.work.queue}")
    private String workQueueName;

    @Autowired
    public BeanConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Map<Integer, QueueDispatcherStrategy> queueDispatcherStrategies() {
        Map<Integer, QueueDispatcherStrategy> strategies = new HashMap<>();

        strategies.put(1, new RabbitQueueDispatcherStrategy(messageQueue(), rabbitTemplate()));
        strategies.put(2, new PostgresQueueDispatcherStrategy(queueJdbcTemplate()));

        return strategies;
    }

    @Bean
    public Queue messageQueue() {
        return new Queue(workQueueName, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(rabbitConnectionFactory());
    }

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() {
        return new CachingConnectionFactory();
    }


    @Bean(name = "queueJdbcTemplate")
    public JdbcTemplate queueJdbcTemplate() {
        return new JdbcTemplate(queueDataSource());
    }

    @Bean(name = "queueDataSource")
    public DataSource queueDataSource() {
        HikariConfig connectionPoolConfiguration = new HikariConfig();
        connectionPoolConfiguration.setDriverClassName("org.postgresql.Driver");
        connectionPoolConfiguration.setJdbcUrl(environment.getProperty("spring.datasource.queue.url"));
        connectionPoolConfiguration.setPassword(environment.getProperty("spring.datasource.queue.password"));
        connectionPoolConfiguration.setUsername(environment.getProperty("spring.datasource.queue.username"));
        return new HikariDataSource(connectionPoolConfiguration);
    }

    @Bean
    public Map<ModuleName, ModuleValidator> moduleValidators() {
        Map<ModuleName, ModuleValidator> validators = new HashMap<>();

        validators.put(ModuleName.SUBSTRING, new SubstringModuleValidator());
        validators.put(ModuleName.LOWER, new UpperLowerModuleValidator());
        validators.put(ModuleName.UPPER, new UpperLowerModuleValidator());
        validators.put(ModuleName.TRIM, new TrimModuleValidator());

        return validators;
    }
}
