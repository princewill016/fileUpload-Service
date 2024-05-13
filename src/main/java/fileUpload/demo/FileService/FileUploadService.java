package fileUpload.demo.FileService;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String addFile(MultipartFile file, String entityName) throws IOException;

    byte[] getFile(String entityName,Long uuid) throws IOException;

}
