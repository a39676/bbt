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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
// multiple scan, 通配符的使用应放后边, 否则会被"覆盖?重写?"后失效
@MapperScan(
	basePackages = {
		"demo.base.*.mapper", 
		"demo.autoTestBase.*.mapper", 
		"demo.clawing.*.mapper", 
		"demo.interaction.*.mapper", 
		"demo.*.mapper"
	},
	sqlSessionTemplateRef = "bbtSqlSessionTemplate"
	)
public class DatabaseBBTConfig {
	
	@Bean(name="bbtDataSourceProperties")
	@ConfigurationProperties(prefix = "spring.datasource.bbt")
	@Primary
    public DataSourceProperties bbtDataSourceProperties() {
		return new DataSourceProperties();
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
		sqlSessionFactoryBean.setTypeAliasesPackage(""
				+ "demo.interaction.movieInteraction.pojo, "
				);
		
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
