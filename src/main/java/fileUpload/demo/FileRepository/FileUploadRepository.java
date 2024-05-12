package fileUpload.demo.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileUploadRepository {

    private String uploadLocation = "/Users/admin/Desktop/fileUpload Service/uploaded-files";

    public String save(MultipartFile file, String entityId, String entityName) throws IOException {
        // Construct the file path where the uploaded file will be saved
        Path targetLocation = Paths.get(uploadLocation, file.getOriginalFilename());

        // Copy the contents of the uploaded file to the target location
        Files.copy(file.getInputStream(), targetLocation);

        // Return the saved file path or any other relevant information
        return targetLocation.toString();
    }
}
