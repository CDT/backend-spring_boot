package backend;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import oracle.jdbc.pool.OracleDataSource;

@SpringBootApplication
public class Application {
	
	private Properties databaseProperties = new Properties();
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    DataSource dataSource() throws SQLException, FileNotFoundException, IOException {
    	
    	databaseProperties.load(Application.class.getResourceAsStream("database.properties"));
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(databaseProperties.getProperty("oracle_user"));
        dataSource.setPassword(databaseProperties.getProperty("oracle_password"));
        dataSource.setURL(databaseProperties.getProperty("oracle_URL"));
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
        
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
    
    
}