package fileUpload.demo.FileRepository;


import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileUploadRepository {

    String uploadLocation = "/Users/admin/Desktop/fileUpload Service/uploaded-files";

    public String save(MultipartFile file) {
 
        ;

        // Return the saved file path or any other relevant information
        return "file saved successfully";
    }

}
