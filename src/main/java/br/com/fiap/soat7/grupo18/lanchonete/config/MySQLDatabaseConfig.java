package br.com.fiap.soat7.grupo18.lanchonete.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Profile("mysql")
public class MySQLDatabaseConfig implements DatabaseConfig {

    private final static String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String USER = "user";
    private final static String PASSWD = "password";

    @Value("${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/lanchonete_db?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false}")
    private String urlDatabase;

    @Value("${MYSQL_USER:"+ USER + "}")
    private String mySqlUser;

    @Value("${MYSQL_PASSWORD:" + PASSWD + "}")
    private String mySqlPasswd;


    @Override
    @Bean
    public DataSource dataSource() {
        System.out.println(String.format("Conectando ao banco de dados em: %s", urlDatabase));
        return DataSourceBuilder
                .create()
                .driverClassName(CLASS_NAME)
                .url(urlDatabase)
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
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
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
        properties.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("spring.jpa.properties.hibernate.format_sql", "true");
        properties.setProperty("spring.jpa.show-sql", "true");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }

}
