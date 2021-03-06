package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service("storageService")
@Profile("production")
public class AWSImageService implements ImageService {

    @Autowired
    S3Service s3service;

    @Value("${amazon.s3.bucket-name}")
    private String BUCKET_NAME;

    @Override
    public String createImage(MultipartFile multiPartFile) throws IOException {
        s3service.createBucketIfNotExists(BUCKET_NAME);
        s3service.uploadFile(BUCKET_NAME, multiPartFile);
        return BUCKET_NAME+"/"+multiPartFile.getOriginalFilename();
    }

    @Override
    public void deleteImage(String image) {
        s3service.deleteObject(BUCKET_NAME, image);
    }
    
}
