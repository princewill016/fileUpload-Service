package fileUpload.demo.FileRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import fileUpload.demo.FileModel.FileUploadEntity;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {

    void save(MultipartFile file, String entityId, String entityName);

}
