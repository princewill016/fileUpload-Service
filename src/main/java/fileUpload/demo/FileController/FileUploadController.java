package fileUpload.demo.FileController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import fileUpload.demo.FileService.FileUploadService;

@RestController
@RequestMapping("/api/v2")
public class FileUploadController {
    @Autowired
    private  FileUploadService fileUploadService;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, String entityName) throws IOException {
        long time;
        if (file.isEmpty()) {
            throw new IllegalStateException("Select a file to upload");
        }
         else
            time = Instant.now().toEpochMilli();
        fileUploadService.addFile(file, entityName);
        return String.format("File uploaded successfully, unique ID: %d, entity-name: %s", time, entityName + ".");
    }

    @GetMapping(path = "/files/{entityName}/{uuid}")
    public ResponseEntity<byte[]> getfile(@PathVariable("entityName") String entityName,
            @PathVariable("uuid") Long uuid)
          {
        try {
            byte[] fileBytes = fileUploadService.getFile(entityName, uuid);
            return ResponseEntity.ok().body(fileBytes);
        } catch (IOException e) {
            String errorMessage = "File not found, check your entity-name or ID";
            byte[] errorMessageBytes = errorMessage.getBytes(StandardCharsets.UTF_8);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageBytes);
        }
    }

    //todo::: get should return an image
}
