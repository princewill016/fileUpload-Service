package fileUpload.demo.FileService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileUploadService {

    String addFile(MultipartFile file, String entityName, String entityId);

    byte[] getFile(String uri);

}
