package fileUpload.demo.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fileUpload.demo.FileRepository.FileUploadRepository;

@Service
public class FileUploadServiceImplementation implements FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

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
    public String addFile(MultipartFile file) {

        if (isSupportedFile(file.getOriginalFilename())) {
            String uuid = UUID.randomUUID().toString();
            String newFileName = getFileExtension(file.getOriginalFilename()) + uuid;
            // String uri = "/api/files/" + newFileName;

            try {
                byte[] fileBytes = file.getBytes();
                Path filePath = Paths.get(newFileName);
                Files.write(filePath, fileBytes);
                // Save file details to repository
                fileUploadRepository.save(file);

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
