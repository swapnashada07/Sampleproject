package vendorconfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import vendorconfiguration.controlers.DeployProductController;
import vendorconfiguration.property.FileStorageProperties;



@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class VendorconfigurationEapServicesApplication {
	private static final Logger logger = LoggerFactory.getLogger(VendorconfigurationEapServicesApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(VendorconfigurationEapServicesApplication.class, args);
		
		logger.info("--Vendorconfiguration_EAP_Services Started--");
	}
}
