package fileUpload.demo.FileController;

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
    public void uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("entityName") String entityName,
            @RequestParam("entityId") String entityId) {
        if (file.isEmpty()) {
            throw new IllegalStateException();
        } else
            fileUploadService.addFile(file, entityName, entityId);
    }

    @GetMapping("/file/{uri}")
    public byte[] getfile() {
        return fileUploadService.getFile(null);
    }

}
