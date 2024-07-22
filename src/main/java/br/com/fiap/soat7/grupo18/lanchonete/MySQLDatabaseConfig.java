package br.com.fiap.soat7.grupo18.lanchonete;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Profile("mysql")
public class MySQLDatabaseConfig implements DatabaseConfig {

    private final static String URL = "jdbc:mysql://localhost:3306/lanchonete_db?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false";
    private final static String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String USER = "user";
    private final static String PASSWD = "password";
    private final static String ORM_ENTITY_PACKAGE = "br.com.fiap.soat7.grupo18.lanchonete.infrastructure.persistence.entity";


    @Override
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(CLASS_NAME)
                .url(URL)
                .username(USER)
                .password(PASSWD)
                .build();
    }

    @Override
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(ORM_ENTITY_PACKAGE);
        em.setJpaProperties(additionalProperties());
        em.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        return em;
    }

    @Override
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Override
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");
        properties.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("spring.jpa.properties.hibernate.format_sql", "true");
        return properties;
    }

}
