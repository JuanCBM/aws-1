package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("production")
public class S3Service {

    @Value("${amazon.s3.region}")
    private String REGION="us-east-1";

    public static AmazonS3 s3;

    public S3Service() {
        s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(REGION)).build();
    }

    public void createBucketIfNotExists(String bucketName) {
        if(!s3.doesBucketExistV2(bucketName)) {
            s3.createBucket(bucketName);
        }
    }

    public void uploadFile(String bucketName, MultipartFile multiPartFile) throws IllegalStateException, IOException {
        String fileName = multiPartFile.getOriginalFilename();
        File file = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multiPartFile.transferTo(file);
        s3.putObject(bucketName, fileName, file);
    }

    public void deleteObject(String bucketName, String objectName){
        s3.deleteObject(bucketName, objectName);
    }

}