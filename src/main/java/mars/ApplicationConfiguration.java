package mars;

import java.sql.Connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import mars.common.MySqlDBUtil;

@Configuration
@EnableWebMvc
public class ApplicationConfiguration {

	@Bean(name="restTemplate")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}


	@Bean(name="dataSource")
	public Connection createDriverManagerDataSource() {
		DriverManagerDataSource dataSource = new  DriverManagerDataSource();
		dataSource.setDriverClassName(MySqlDBUtil.DRIVER_CLASS_NAME);
		dataSource.setUsername(MySqlDBUtil.USERNAME);
		dataSource.setPassword(MySqlDBUtil.PASSWORD);
		dataSource.setUrl(MySqlDBUtil.URL);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}

}



