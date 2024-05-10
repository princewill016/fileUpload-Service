package fileUpload.demo.FileModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file")
    @SequenceGenerator(name = "file", sequenceName = "file", allocationSize = 1)
    private Long Id;
    private String fileName;
    private String entityName;
    private byte file;

    public FileUploadEntity(String fileName, String entityName, byte file) {
        this.fileName = fileName;
        this.entityName = entityName;
        this.file = file;
    }

}
