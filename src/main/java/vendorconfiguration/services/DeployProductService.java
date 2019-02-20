package vendorconfiguration.services;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import vendorconfiguration.documents.DeployProducts;





public interface DeployProductService {
	
	
	DeployProducts insertProduct(DeployProducts product);

	void store(MultipartFile file);

	Resource loadFile(String filename);
	

	void init();
		
}
