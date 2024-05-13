package fileUpload.demo.FileController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import fileUpload.demo.FileService.FileUploadService;

@RestController
@RequestMapping("/api/v2")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, String entityName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalStateException();
        } else
            fileUploadService.addFile(file, entityName);
        return "File uploaded successfully";
    }

    @GetMapping(path = "/files/{entityName}/{uuid}")
    public byte[] getfile(@PathVariable("entityName") String entityName, @PathVariable("uuid") Long uuid)
            throws IOException {
        return fileUploadService.getFile(entityName, uuid);
    }

    //todo....  post method should return the UUID and entity name to user.
}

