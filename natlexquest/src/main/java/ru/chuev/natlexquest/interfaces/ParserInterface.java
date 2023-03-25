package ru.chuev.natlexquest.interfaces;

import ru.chuev.natlexquest.model.Section;

import java.io.InputStream;
import java.util.List;

public interface ParserInterface {
    void parseFileToDB(InputStream is, long fileId);
    void parseDBToFile(List<Section> sections, long fileId);

}
