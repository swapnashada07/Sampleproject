package vendorconfiguration.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vendorconfiguration.documents.ImageModel;


@Repository("imageRepository")
public interface ImageRepository extends MongoRepository<ImageModel, UUID> {

}
