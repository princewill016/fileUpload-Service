package fileUpload.demo.FileService;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String addFile(MultipartFile file, String entityName, String entityId);

    byte[] getFile(String uri);

}
