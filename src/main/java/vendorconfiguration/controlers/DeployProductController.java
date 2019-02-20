package vendorconfiguration.controlers;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import vendorconfiguration.documents.DeployProducts;
import vendorconfiguration.documents.ImageModel;
import vendorconfiguration.exception.FileStorageException;
import vendorconfiguration.payload.UploadFileResponse;
import vendorconfiguration.property.FileStorageProperties;
import vendorconfiguration.services.DeployProductService;
import vendorconfiguration.services.FileStorageService;
import vendorconfiguration.services.ImageService;



@RestController
@RequestMapping("/productapi")
@CrossOrigin(origins="http://localhost:4200")

public class DeployProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeployProductController.class);

	@Autowired
	private DeployProductService deployproductservice;
	@Autowired
	private ImageService imgservice;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	

	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		
		String Fileid = UUID.randomUUID().toString();
		logger.info(Fileid);
		String fileName=null;
		UploadFileResponse uprespond=null;
		try {
		fileName = fileStorageService.storeFile(file, Fileid);
		

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();
		uprespond = new UploadFileResponse(UUID.randomUUID(), fileName,Fileid, fileDownloadUri,
				file.getContentType(), file.getSize());
		String imagePath = fileStorageProperties.getUploadDir() + File.separator + fileName;
		
		ImageModel imageModel = new ImageModel();
		imageModel.setImagePath(imagePath);
		imageModel.setImageId(uprespond.getImageId());
		imageModel.setImageName(uprespond.getFileName());
		imageModel.setImageUrl(uprespond.getFileDownloadUri());
		imageModel.setFileId(uprespond.getFileId());
		
		imgservice.insert(imageModel);
		
		}
		catch(FileStorageException e) {
			logger.error("File Not uploaded "+e);
			
			return null;
		}
		catch(NullPointerException e) {
			System.out.println("Null pointer issue ");
			
			return null;
		}
		catch(ArithmeticException e) {
			System.out.println("Problem in arithemetic issue ");
			
			return null;
		}
		catch(Exception e) {
			System.out.println("Exception "+e);
			
			return null;
		}
		
		return uprespond;
		
		
	} 
	
	@GetMapping("/downloadOneFileId/{fileId}")
	public ResponseEntity<org.springframework.core.io.Resource> downloadOneFileId(@PathVariable String fileId, HttpServletRequest request) {

		org.springframework.core.io.Resource resource = fileStorageService.loadFileAsFileId(fileId);

		String contentType = null;

		if (contentType == null) 
		{
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/products")
	public DeployProducts insertProduct(DeployProducts product) { 
		return deployproductservice.insertProduct(product);
	}
	


	
	}


