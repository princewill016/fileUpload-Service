package fileUpload.demo.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileUploadRepository {

    String uploadLocation = "/Users/admin/Desktop/fileUpload Service/uploaded-files";

    public String save(MultipartFile file) throws IOException {

        // to check the storage directory if the file already exists otherwise it
        // creates the file.
        String entityNameInDirectory = "entity";
        Path entityFolderPath = Paths.get(uploadLocation, entityNameInDirectory);

        if (Files.exists(entityFolderPath) && Files.isDirectory(entityFolderPath)) {
            // Construct the file path where the uploaded file will be saved
            Path targetLocation = Paths.get(uploadLocation, file.getOriginalFilename());
            // Copy the contents of the uploaded file to the target location
            Files.write(targetLocation, file.getBytes());
        } else {
            try {
                Files.createDirectory(entityFolderPath);
                System.out.println("directory has been creted");
            } catch (RuntimeException e) {
                System.out.println("error creating file " + e);
            }
        }
        ;

        // Return the saved file path or any other relevant information
        return "file saved successfully";
    }

}
