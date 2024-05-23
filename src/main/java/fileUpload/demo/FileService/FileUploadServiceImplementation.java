package fileUpload.demo.FileService;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileUploadServiceImplementation implements FileUploadService {

   private final List<String> supportedFileExtensions = Arrays.asList(".JPG", ".JPEG", ".PNG", ".TXT", ".PDF");

   private String getFileExtension(String fileName) {
      if(fileName.lastIndexOf(".") != - 1 && fileName.lastIndexOf(".") != 0) {
         return fileName.substring(fileName.lastIndexOf(".")).toUpperCase();
      } else {
         return "No file extension";
      }
   }

   private boolean isSupportedFile(String fileName) {
      String extension = getFileExtension(fileName);
      return supportedFileExtensions.contains(extension);
   }

   @Override
   public void addFile(MultipartFile file, String entityName) throws IOException {
      String entityNameInDirectory;
      entityNameInDirectory = entityName;
      String uploadLocation = "/Users/admin/Desktop/fileUpload Service/uploaded-files";
      Path entityFolderPath = Paths.get(uploadLocation, entityNameInDirectory);
      if(isSupportedFile(file.getOriginalFilename())) {
         if(! Files.exists(entityFolderPath) && ! Files.isDirectory(entityFolderPath))
            Files.createDirectory(entityFolderPath);
         String newFileName = Instant.now().toEpochMilli() + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

         try {
            String newFileLocation = uploadLocation + "/" + entityNameInDirectory;

            byte[] fileBytes = file.getBytes();
            Path targetLocation = Paths.get(newFileLocation, newFileName);
            Files.write(targetLocation, fileBytes);
         } catch(IOException e) {
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
      for(Path filePath : Files.newDirectoryStream(Paths.get(storedFilePath))) {
         String fileName = filePath.getFileName().toString();

         if(fileName.toLowerCase().contains(uuid.toString().toLowerCase())) {
            String regex = "\\.(\\w+)$";
            Matcher matcher = Pattern.compile(regex).matcher(fileName);
            if(matcher.find()) {
               fileEx = matcher.group(1);
               break;
            }
         }
      }
      Path filePath = Paths.get(storedFilePath, uuid.toString() + "." + fileEx);
      if(Files.exists(filePath)) {
         return Files.readAllBytes(filePath);
      } else {
         throw new FileNotFoundException("File not found, check your entity-name or ID");
      }
   }

   public String getFileExtension(byte[] fileBytes) {
      Tika tika = new Tika();
      String mimeType = tika.detect(fileBytes);
      return mimeTypeToExtension(mimeType);
   }

   private String mimeTypeToExtension(String mimeType) {
      return switch(mimeType) {
         case "image/jpeg" -> "jpg";
         case "image/png" -> "png";
         case "application/pdf" -> "pdf";
         case "text/plain" -> "txt";
         // Add more MIME types and their corresponding extensions as needed
         default -> null;
      };
   }

}