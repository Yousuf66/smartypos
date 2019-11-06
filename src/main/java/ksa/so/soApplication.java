package ksa.so;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import ksa.so.property.FileStorageProperties;

@SpringBootApplication
@EnableScheduling
//@EnableCaching
/* @EnableSwagger2 */
/* @Import(SwaggerConfiguration.class) */
@EnableAsync
@EnableConfigurationProperties({ FileStorageProperties.class })
public class soApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(soApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(soApplication.class, args);
	}
}
