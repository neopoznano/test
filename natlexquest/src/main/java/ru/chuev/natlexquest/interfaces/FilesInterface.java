package ru.chuev.natlexquest.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.chuev.natlexquest.model.Files;
public interface FilesInterface {
    long importFile(MultipartFile multipartFile);
    long exportFile();
    Files getImportFileStatusById(long id);
    Files getExportFileStatusById(long id);
    Resource getExportedFileById(long id);
}
