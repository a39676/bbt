package demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(
	basePackages = {
		"demo.*.*.*.*.mapper",
		"demo.*.*.*.mapper",
		"demo.*.*.mapper",
		"demo.*.mapper",
	},
	sqlSessionTemplateRef = "bbtSqlSessionTemplate"
	)
public class DatabaseBBTConfig {
	
	@Value("${databaseBBT.driverClassName}")
  	private String driverClassName;
  	@Value("${databaseBBT.url}")
  	private String url;
  	@Value("${databaseBBT.username}")
  	private String username;
  	@Value("${databaseBBT.password}")
  	private String password;
	
	@Bean(name="bbtDataSourceProperties")
	@Primary
    public DataSourceProperties bbtDataSourceProperties() {
		DataSourceProperties d = new DataSourceProperties();
		d.setDriverClassName(driverClassName);
		d.setUrl(url);
		d.setUsername(username);
		d.setPassword(password);
		return d;
    }

    @Bean(name="bbtDataSource")
    @Primary
    public HikariDataSource bbtDataSource(@Qualifier("bbtDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }
    
//    @Bean(name="bbtDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.bbt")
//    @Primary
//    public DataSource bbtDataSource() {
//        return DataSourceBuilder.create().build();
//    }
	
	@Bean(name="bbtSqlSessionFactory")
	@Primary
	public SqlSessionFactoryBean bbtSqlSessionFactory(@Qualifier("bbtDataSource") DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		Properties mybatisProperties = new Properties();
		mybatisProperties.setProperty("cacheEnabled", "true");
		sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties);
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		return sqlSessionFactoryBean;
	}
	
	@Bean(name="bbtTransactionManager")
	@Primary
    public DataSourceTransactionManager bbtTransactionManager(@Qualifier("bbtDataSource") DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }
	
	@Bean(name = "bbtSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate bbtSqlSessionTemplate(@Qualifier("bbtSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

//	@Bean(name = "bbtTransactionManager")
//    @Primary
//	public PlatformTransactionManager bbtAnnotationDrivenTransactionManager(@Qualifier("bbtDataSource") DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource);
//	}
	

}
