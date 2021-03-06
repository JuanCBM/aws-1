package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String createImage(MultipartFile multiPartFile) throws IOException;

    public void deleteImage(String image);
}
