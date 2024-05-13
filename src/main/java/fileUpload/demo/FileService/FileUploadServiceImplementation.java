package fileUpload.demo.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImplementation implements FileUploadService {

    String uploadLocation = "/Users/admin/Desktop/fileUpload Service/uploaded-files";
    private final List<String> supportedFileExtensions = Arrays.asList(".JPG", ".JPEG", ".PNG", ".TXT", ".pdf");

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    private boolean isSupportedFile(String fileName) {
        String extension = getFileExtension(fileName);
        return supportedFileExtensions.contains(extension);
    }

    @Override
    public String addFile(MultipartFile file) throws IOException {

        String entityNameInDirectory = "entity";
        Path entityFolderPath = Paths.get(uploadLocation, entityNameInDirectory);

        if (isSupportedFile(file.getOriginalFilename())) {
            if (!Files.exists(entityFolderPath) && !Files.isDirectory(entityFolderPath)) {
                // Construct the file path where the uploaded file will be saved
                Files.createDirectory(entityFolderPath);
            } else
                return ""; 
            Long timeStamp = Instant.now().toEpochMilli();
            String newFileName = timeStamp + getFileExtension(file.getOriginalFilename());

            try {
                byte[] fileBytes = file.getBytes();
                Path targetLocation = Paths.get(uploadLocation, newFileName);
                Files.write(targetLocation, fileBytes);
                return "File uploaded successfully";
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file", e);
            }
        } else {
            throw new IllegalArgumentException("Unsupported file type or file is empty");
        }
    }

    @Override
    public byte[] getFile(Long uuid, String entityName) throws IOException {
        String storageFolderPath = "/Users/admin/Desktop/fileUpload Service/uploaded-files";
        Path filePath = Paths.get(storageFolderPath, entityName, uuid.toString());
        return Files.readAllBytes(filePath);
    }

}
