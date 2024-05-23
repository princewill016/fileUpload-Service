package fileUpload.demo.FileService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

   void addFile(MultipartFile file, String entityName) throws IOException;

   byte[] getFile(String entityName, Long uuid) throws IOException;

   String getFileExtension(byte[] fileBytes);

   long getTime();
}