package demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = { "demo.scriptCore.localClawing.hades.mapper" }, sqlSessionTemplateRef = "hadesSqlSessionTemplate")
public class DatabaseHadesConfig {

	@Bean(name = "hadesDataSourceProperties")
	@ConfigurationProperties(prefix = "spring.datasource.hades")
	public DataSourceProperties hadesDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "hadesDataSource")
	public HikariDataSource hadesDataSource(@Qualifier("hadesDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

//	@Bean(name="hadesDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource-hades")
//    public DataSource hadesDataSource() {
//        return DataSourceBuilder.create().build();
//    }

	@Bean(name = "hadesSqlSessionFactory")
	public SqlSessionFactoryBean hadesSqlSessionFactory(@Qualifier("hadesDataSource") DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		Properties mybatisProperties = new Properties();
		mybatisProperties.setProperty("cacheEnabled", "true");
		sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties);
		sqlSessionFactoryBean.setDataSource(dataSource);

		return sqlSessionFactoryBean;
	}

	@Bean(name = "hadesTransactionManager")
	public DataSourceTransactionManager hadesTransactionManager(@Qualifier("hadesDataSource") DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		return transactionManager;
	}

	@Bean(name = "hadesSqlSessionTemplate")
	public SqlSessionTemplate hadesSqlSessionTemplate(
			@Qualifier("hadesSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
