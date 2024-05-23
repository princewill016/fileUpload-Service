package fileUpload.demo.FileController;

import fileUpload.demo.FileService.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v2")
public class FileUploadController {
   @Autowired
   private FileUploadService fileUploadService;

   @PostMapping("/upload")
   public String uploadFile(@RequestParam("file") MultipartFile file, String entityName) throws IOException {
      long time;
      if(file.isEmpty()) {
         throw new IllegalStateException("Select a file to upload");
      } else
         time = fileUploadService.getTime();
      fileUploadService.addFile(file, entityName);
      return String.format("File uploaded successfully, unique ID: %d, entity-name: %s", time, entityName + ".");
   }

   @GetMapping(path = "/files/{entityName}/{uuid}")
   public ResponseEntity<ByteArrayResource> getFile(@PathVariable("entityName") String entityName, @PathVariable("uuid") Long uuid) {
      try {
         byte[] fileBytes = fileUploadService.getFile(entityName, uuid);
         Path filePath = Paths.get("/Users/admin/Desktop/fileUpload Service/uploaded-files", entityName, uuid + "." + fileUploadService.getFileExtension(fileBytes));
         String mimeType = Files.probeContentType(filePath);
         if(mimeType == null) {
            mimeType = "application/octet-stream";
         }
         ByteArrayResource resource = new ByteArrayResource(fileBytes);

         return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"").body(resource);

      } catch(IOException e) {
         String errorMessage = "File not found, check your entity-name or ID";
         byte[] errorMessageBytes = errorMessage.getBytes(StandardCharsets.UTF_8);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(errorMessageBytes));
      }
   }
}
//ID now use uniform time