package dev.isadiape.tests.multidatasourcejpa.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "firstDbEm",
        transactionManagerRef = "firstDbTransactionManager",
        basePackages = {"dev.isadiape.tests.multidatasourcejpa.repository.first"}
)
public class FirstDbConfig {

    private final Environment env;

    public FirstDbConfig(Environment env) {
        this.env = env;
    }

    @Primary
    @Bean(name = "firstDbDatasource")
    @ConfigurationProperties("spring.datasource.first-db")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jpaProperties")
    @ConfigurationProperties("spring.jpa")
    public Map<String, String> jpaProperties() {
        return new HashMap<>();
    }

    @Primary
    @Bean(name = "firstDbEm")
    LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder,
                                                         @Qualifier("firstDbDatasource") DataSource dataSource,
                                                         @Qualifier("jpaProperties") Map<String, String> properties) {
        var datasourceProperties = new HashMap<String, String>();
        datasourceProperties.put("hibernate.default_schema",
                env.getRequiredProperty("spring.datasource.first-db.schema"));
        datasourceProperties.put("spring.datasource.driver-class-name",
                env.getRequiredProperty("spring.datasource.first-db.driver-class-name"));
        datasourceProperties.putAll(properties);

        return builder
                .dataSource(dataSource)
                .packages("dev.isadiape.tests.multidatasourcejpa.entity.first")
                .properties(datasourceProperties)
                .persistenceUnit("firstDbPersistenUnit")
                .build();
    }

    @Primary
    @Bean(name = "firstDbTransactionManager")
    PlatformTransactionManager invoiceContextManager(@Qualifier("firstDbEm") EntityManagerFactory emFactory) {
        return new JpaTransactionManager(emFactory);
    }
}
