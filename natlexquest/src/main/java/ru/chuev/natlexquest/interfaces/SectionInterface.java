package ru.chuev.natlexquest.interfaces;

import ru.chuev.natlexquest.model.Section;
import java.util.List;

public interface SectionInterface {
    List<Section> getAllSections();
    Section getSectionById(long id);
    Section saveSection(Section section);
    void updateSection(long id, Section newSection);
    void deleteSectionById(long id);
    List<Section> getSectionsByGeodataCode(String code);
}
