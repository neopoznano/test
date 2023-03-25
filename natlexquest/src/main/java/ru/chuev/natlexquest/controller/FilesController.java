package ru.chuev.natlexquest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.chuev.natlexquest.interfaces.FilesInterface;
import ru.chuev.natlexquest.model.Files;

@RestController
@RequestMapping("")
public class FilesController {
    @Autowired
    private FilesInterface filesService;

    @PostMapping(value = "import")
    public long importFile(@RequestParam("file") MultipartFile multipartFile) {
        return filesService.importFile(multipartFile);
    }

    @GetMapping(value = "export")
    public long exportFile() {
        return filesService.exportFile();
    }

    @GetMapping(value = "import/{id}", produces = "application/json")
    public Files getImportStatusById(@PathVariable long id) {
        return filesService.getImportFileStatusById(id);
    }

    @GetMapping("export/{id}")
    public Files getExportStatusById(@PathVariable long id) {
        return filesService.getExportFileStatusById(id);
    }

    @GetMapping(value = "export/{id}/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Resource getExportedFile(@PathVariable long id) {
        return filesService.getExportedFileById(id);
    }
}
