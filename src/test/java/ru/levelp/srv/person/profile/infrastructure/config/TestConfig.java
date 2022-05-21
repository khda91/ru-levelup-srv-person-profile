package ru.levelp.srv.person.profile.infrastructure.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.boot.actuate.autoconfigure.metrics.orm.jpa.HibernateMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@TestConfiguration
@ComponentScan(basePackages = "ru.levelp.srv.person.profile")
@EnableAutoConfiguration(
        exclude = {
                HibernateJpaAutoConfiguration.class,
                JpaRepositoriesAutoConfiguration.class,
                HibernateMetricsAutoConfiguration.class,
                KafkaAutoConfiguration.class,
                TaskSchedulingAutoConfiguration.class,
                TaskExecutionAutoConfiguration.class,
                GroovyTemplateAutoConfiguration.class
        }
)
@DependsOn("liquibase")
public class TestConfig {

    /**
     * Allows schema, table and column names escaping.
     * The property value is an escape pattern where the ? is replaced by the name.
     * For example, the pattern "`?`" is expanded as "`MY_TABLE`" for a table named "MY_TABLE"
     */
    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setDatatypeFactory(new PostgresqlDataTypeFactory());
        return bean;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource) {
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean(dataSource);
        bean.setDatabaseConfig(dbUnitDatabaseConfig());
        return bean;
    }
}
