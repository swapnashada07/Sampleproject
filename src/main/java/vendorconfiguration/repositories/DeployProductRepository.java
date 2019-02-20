package vendorconfiguration.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import vendorconfiguration.documents.DeployProducts;





@Repository("productRepo")
@CrossOrigin(origins="http://localhost:4200")
public interface DeployProductRepository extends MongoRepository<DeployProducts, UUID> {

	DeployProducts findByProductId(UUID Id);

}
