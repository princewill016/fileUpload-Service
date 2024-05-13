package fileUpload.demo.FileService;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String addFile(MultipartFile file);

    byte[] getFile(Long uuid, String entityName) throws IOException;

}
