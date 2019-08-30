package demo.config;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@PropertySources({ 
	@PropertySource(value = "classpath:properties/database/mySqlFinancer.properties"),
//	@PropertySource(value = "classpath:properties/database/vpsFinancer.properties"),
	@PropertySource(value = "classpath:none.properties", ignoreResourceNotFound = true) 
})
@EnableTransactionManagement // <tx:annotation-driven />
// multiple scan, 通配符的使用应放后边, 否则会被"覆盖?重写?"后失效
@MapperScan({"demo.mapper", "demo.base.*.mapper", "demo.*.mapper"})
public class MybatisConfig implements TransactionManagementConfigurer {
	
	
	// 直接写properties文件内的属性名
	@Value("${DB_DRIVER_CLASS}")
	private String DB_DRIVER_CLASS;
	
	@Value("${DB_URL}")
	private String DB_URL;
	
	@Value("${DB_USERNAME}")
	private String DB_USERNAME;
	
	@Value("${DB_PASSWORD}")
	private String DB_PASSWORD;
	
	
	@Bean(name="dataSource")
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DB_DRIVER_CLASS);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);
		return dataSource;
	}
	
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		Properties mybatisProperties = new Properties();
		mybatisProperties.setProperty("cacheEnabled", "true");
		sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties);
		sqlSessionFactoryBean.setDataSource(dataSource());
		
		return sqlSessionFactoryBean;
	}
	
	@Bean
    public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}
	

}
