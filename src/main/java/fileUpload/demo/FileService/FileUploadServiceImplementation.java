package fileUpload.demo.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImplementation implements FileUploadService {

    private String uploadLocation = "/Users/admin/Desktop/fileUpload Service/uploaded-files";
    private final List<String> supportedFileExtensions = Arrays.asList(".JPG", ".JPEG", ".PNG", ".TXT", ".PDF");

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")).toUpperCase();
        } else {
            return "";
        }
    }

    private boolean isSupportedFile(String fileName) {
        String extension = getFileExtension(fileName);
        return supportedFileExtensions.contains(extension);
    }

    @Override
    public String addFile(MultipartFile file, String entityName) throws IOException {
        String entityNameInDirectory = entityName;
        Path entityFolderPath = Paths.get(uploadLocation, entityNameInDirectory);
        if (isSupportedFile(file.getOriginalFilename())) {
            if (!Files.exists(entityFolderPath) && !Files.isDirectory(entityFolderPath)) {
                Files.createDirectory(entityFolderPath);
            }
            String newFileName = Instant.now().toEpochMilli() + getFileExtension(file.getOriginalFilename());

            try {
                String newFileLocation = uploadLocation + "/" + entityNameInDirectory;

                byte[] fileBytes = file.getBytes();
                Path targetLocation = Paths.get(newFileLocation, newFileName);
                Files.write(targetLocation, fileBytes);
                return "";
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file", e);
            }
        } else {
            throw new IllegalArgumentException("file too large, upload a file less than 10mb");
        }
    }

    @Override
    public byte[] getFile(String entityName, Long uuid) throws IOException {
        String storedFilePath = "/Users/admin/Desktop/fileUpload Service/uploaded-files/" + entityName;

        String fileEx = null;
        for (Path filePath : Files.newDirectoryStream(Paths.get(storedFilePath))) {
            String fileName = filePath.getFileName().toString();

            if (fileName.toLowerCase().contains(uuid.toString().toLowerCase())) {
                String regex = "\\.(\\w+)$";
                Matcher matcher = Pattern.compile(regex).matcher(fileName);
                if (matcher.find()) {
                    fileEx = matcher.group(1);
                    break;
                }
            }
        }
        Path filePath = Paths.get(storedFilePath, uuid.toString() + "." + fileEx);
        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new FileNotFoundException("File not found, check your entity-name or ID");
        }
    }

}