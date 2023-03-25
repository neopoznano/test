package ru.chuev.natlexquest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.chuev.natlexquest.exceptions.FileNotFoundException;
import ru.chuev.natlexquest.interfaces.FilesInterface;
import ru.chuev.natlexquest.model.Files;
import ru.chuev.natlexquest.model.Status;
import ru.chuev.natlexquest.repo.FilesRepo;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FilesService implements FilesInterface {

    @Autowired
    private FilesRepo filesRepo;
    @Autowired
    private Parser parser;
    @Autowired
    private SectionService sectionService;

    AtomicLong counter = new AtomicLong();
    @Override
    public long importFile(MultipartFile multipartFile) {
        long fileId = counter.incrementAndGet();

        try {
            parser.parseFileToDB(multipartFile.getInputStream(), fileId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileId;
    }

    @Override
    public long exportFile() {
        long fileId = counter.incrementAndGet();
        parser.parseDBToFile(sectionService.getAllSections(), fileId);

        return fileId;
    }

    @Override
    public Files getImportFileStatusById(long id) {
        Files importFile = filesRepo.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));

        return importFile;
    }

    @Override
    public Files getExportFileStatusById(long id) {
        Files exportFile = filesRepo.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));

        return exportFile;
    }

    @Override
    public Resource getExportedFileById(long id) {

        Files exportedFile = filesRepo.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));

        String pathToFile = "src/main/resources/files/" + id + ".xlsx";
        if(exportedFile.getStatus().equals(Status.DONE.name())) {
            return new FileSystemResource(pathToFile);
        } else {
            throw new RuntimeException("File is being processing!");
        }
    }
}
