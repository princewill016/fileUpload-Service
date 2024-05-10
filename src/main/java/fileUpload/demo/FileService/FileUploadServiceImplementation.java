package fileUpload.demo.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fileUpload.demo.FileRepository.FileUploadRepository;

@Service
public class FileUploadServiceImplementation implements FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    private static final List<String> supportedFileExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".txt", ".pdf");

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public String addFile(MultipartFile file, String entityName, String entityId) {
        if (!file.isEmpty() && isSupportedFile(file.getOriginalFilename())) {
            String fileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
            String uri = "/api/files/" + fileName;

            try {
                byte[] fileBytes = file.getBytes();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, fileBytes);

                // Save file details to repository
                fileUploadRepository.save(file, entityId, entityName);

                return "File uploaded successfully";
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file", e);
            }
        } else {
            throw new IllegalArgumentException("Unsupported file type or file is empty");
        }
    }

    @Override
    public byte[] getFile(String uri) {
        try {
            Path filePath = Paths.get(uploadDir + uri);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve file", e);
        }
    }

    private static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    private static boolean isSupportedFile(String fileName) {
        String extension = getFileExtension(fileName);
        return supportedFileExtensions.contains(extension);
    }
}
