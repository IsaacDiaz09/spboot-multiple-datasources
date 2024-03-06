package dev.isadiape.tests.multidatasourcejpa.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "secondDbEm",
        transactionManagerRef = "secondDbTransactionManager",
        basePackages = {"dev.isadiape.tests.multidatasourcejpa.repository.second"}
)
public class SecondDbConfig {

    private final Environment env;

    public SecondDbConfig(Environment env) {
        this.env = env;
    }

    @Bean(name = "secondDbDatasource")
    @ConfigurationProperties("spring.datasource.second-db")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondDbEm")
    LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder,
                                                         @Qualifier("secondDbDatasource") DataSource dataSource,
                                                         @Qualifier("jpaProperties") Map<String, String> properties) {
        var datasourceProperties = new HashMap<String, String>();
        datasourceProperties.put("hibernate.default_schema",
                env.getRequiredProperty("spring.datasource.second-db.schema"));
        datasourceProperties.put("spring.datasource.driver-class-name",
                env.getRequiredProperty("spring.datasource.second-db.driver-class-name"));
        datasourceProperties.putAll(properties);

        return builder
                .dataSource(dataSource)
                .packages("dev.isadiape.tests.multidatasourcejpa.entity.second")
                .properties(datasourceProperties)
                .persistenceUnit("secondDbPersistenUnit")
                .build();
    }

    @Bean(name = "secondDbTransactionManager")
    PlatformTransactionManager invoiceContextManager(@Qualifier("secondDbEm") EntityManagerFactory emFactory) {
        return new JpaTransactionManager(emFactory);
    }
}
