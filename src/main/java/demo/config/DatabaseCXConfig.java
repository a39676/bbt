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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = { "demo.toyParts.multipleDB.mapper" }, sqlSessionTemplateRef = "cxSqlSessionTemplate")
public class DatabaseCXConfig {
	
	@Value("${databaseCX.driverClassName}")
  	private String driverClassName;
  	@Value("${databaseCX.url}")
  	private String url;
  	@Value("${databaseCX.username}")
  	private String username;
  	@Value("${databaseCX.password}")
  	private String password;

	@Bean(name = "cxDataSourceProperties")
	@ConfigurationProperties(prefix = "spring.datasource.cx")
	public DataSourceProperties cxDataSourceProperties() {
		DataSourceProperties d = new DataSourceProperties();
		d.setDriverClassName(driverClassName);
		d.setUrl(url);
		d.setUsername(username);
		d.setPassword(password);
		return d;
	}

	@Bean(name = "cxDataSource")
	public HikariDataSource cxDataSource(@Qualifier("cxDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

//	@Bean(name="cxDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource-cx")
//    public DataSource cxDataSource() {
//        return DataSourceBuilder.create().build();
//    }

	@Bean(name = "cxSqlSessionFactory")
	public SqlSessionFactoryBean cxSqlSessionFactory(@Qualifier("cxDataSource") DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		Properties mybatisProperties = new Properties();
		mybatisProperties.setProperty("cacheEnabled", "true");
		sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties);
		sqlSessionFactoryBean.setDataSource(dataSource);

		return sqlSessionFactoryBean;
	}

	@Bean(name = "cxTransactionManager")
	public DataSourceTransactionManager cxTransactionManager(@Qualifier("cxDataSource") DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		return transactionManager;
	}

	@Bean(name = "cxSqlSessionTemplate")
	public SqlSessionTemplate cxSqlSessionTemplate(
			@Qualifier("cxSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
