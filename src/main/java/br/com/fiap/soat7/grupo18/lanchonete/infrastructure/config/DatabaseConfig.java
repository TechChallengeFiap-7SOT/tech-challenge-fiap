package br.com.fiap.soat7.grupo18.lanchonete.infrastructure.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public interface DatabaseConfig {

    public final static String PERSISTENCE_UNIT_NAME = "lanchonetePU";

    public DataSource dataSource();
    public LocalContainerEntityManagerFactoryBean entityManagerFactory();
    public PlatformTransactionManager transactionManager();
    Properties additionalProperties();
}
