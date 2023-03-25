package ru.chuev.natlexquest.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.chuev.natlexquest.interfaces.ParserInterface;
import ru.chuev.natlexquest.model.Files;
import ru.chuev.natlexquest.model.Geodata;
import ru.chuev.natlexquest.model.Section;
import ru.chuev.natlexquest.model.Status;
import ru.chuev.natlexquest.repo.FilesRepo;
import ru.chuev.natlexquest.repo.SectionRepo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class Parser implements ParserInterface {

    @Autowired
    private SectionRepo sectionRepo;
    @Autowired
    private FilesRepo filesRepo;

    @Async
    @Override
    public void parseFileToDB(InputStream inputStream, long fileId) {
        Files importFile = new Files();
        importFile.setStatus(Status.IN_PROGRESS.name());
        importFile.setId(fileId);

        importFile = filesRepo.save(importFile);

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            List<Section> sections = getSectionsContentFromSheet(sheet);

            sectionRepo.saveAll(sections);
            importFile.setStatus(Status.DONE.name());
            filesRepo.save(importFile);

        } catch (IOException e) {
            importFile.setStatus(Status.ERROR.name());
            filesRepo.save(importFile);
            throw new RuntimeException(e);
        }
    }

    public List<Section> getSectionsContentFromSheet(Sheet sheet) {
        List<Section> sections = new ArrayList<>();
        Iterator<Row> rows = sheet.iterator();

        boolean skipHeader = true;
        while (rows.hasNext()) {
            Row row = rows.next();

            if (skipHeader) {
                skipHeader = false;
                row = rows.next();
            }

            Iterator<Cell> cellsInRow = row.iterator();
            sections.add(getSectionContentFromCells(cellsInRow));
        }

        return sections;
    }

    public Section getSectionContentFromCells(Iterator<Cell> cellsInRow) {
        List<Geodata> geoClasses = new ArrayList<>();
        Section section = new Section();

        int cellIdx = 0;
        boolean cellIsEmpty = false;
        while (cellsInRow.hasNext()) {
            Cell currentCell = cellsInRow.next();

            cellIsEmpty = currentCell.getStringCellValue().isEmpty();

            if (cellIdx == 0 && !cellIsEmpty) {
                section.setName(currentCell.getStringCellValue());
                cellIdx++;
                continue;
            }

            if (cellIdx % 2 == 1) {
                Geodata geodata = new Geodata();
                geodata.setName(currentCell.getStringCellValue());
                geodata.setSection(section);
                geoClasses.add(geodata);
            }

            if (cellIdx % 2 == 0) {
                geoClasses.get(geoClasses.size() - 1).setCode(currentCell.getStringCellValue());
            }

            cellIdx++;
        }

        section.setGeologicalClasses(geoClasses);

        return section;
    }

    @Async
    @Override
    public void parseDBToFile(List<Section> sections, long fileId) {
        String path = "src/main/resources/files/" + fileId;

        Files exportFile = new Files();
        exportFile.setStatus(Status.IN_PROGRESS.name());
        exportFile.setId(fileId);

        exportFile = filesRepo.save(exportFile);

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(path + ".xlsx")) {

            Sheet sheet = workbook.createSheet("Sections");
            fillSheetWithContent(sheet, sections);
            workbook.write(fos);

            exportFile.setStatus(Status.DONE.name());
            filesRepo.save(exportFile);

        } catch (IOException e) {
            exportFile.setStatus(Status.ERROR.name());
            filesRepo.save(exportFile);
            throw new RuntimeException("Fail to export data to Excel file!");
        }
    }

    public void fillSheetWithContent(Sheet sheet, List<Section> sections) {
        sheet.setDefaultColumnWidth(30);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Section name");

        int maxGeoClassesCount = Section.getMaxGeoClassesCount(sections);
        int classNumber = 1;
        for (int i = 1; i < maxGeoClassesCount * 2; i += 2) {
            header.createCell(i).setCellValue("Class " + classNumber + " name");
            header.createCell(i + 1).setCellValue("Class " + classNumber + " code");
            classNumber++;
        }

        int rowIdx = 1;
        for (Section section : sections) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(section.getName());

            int cellIdx = 1;
            for (Geodata geodata : section.getGeologicalData()) {
                row.createCell(cellIdx++).setCellValue(geodata.getName());
                row.createCell(cellIdx++).setCellValue(geodata.getCode());
            }
        }
    }
}
