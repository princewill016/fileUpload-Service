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
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException();
        } else
            fileUploadService.addFile(file);
    }

    @GetMapping(path = "/files/{uuid}/{entityName}")
    public byte[] getfile(@PathVariable("uuid") Long uuid, @PathVariable("entityName") String entityName)
            throws IOException {
        return fileUploadService.getFile(uuid, entityName);
    }

}
