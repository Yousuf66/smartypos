package ksa.so;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Configuration
@EnableJpaRepositories(basePackages = "ksa.so.repositories",repositoryBaseClass=DataTablesRepositoryImpl.class)

public class DatatableConfig {
	
}
//@Configuration
//@EnableJpaRepositories(basePackages = "ksa.so.repositories")
//public class DatatableConfig {}

//@Configuration
//@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class, basePackages = "ksa.so.repositories")
//public class DatatableConfig {}
