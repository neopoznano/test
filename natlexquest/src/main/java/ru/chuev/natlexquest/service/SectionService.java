package ru.chuev.natlexquest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chuev.natlexquest.exceptions.SectionNotFoundException;
import ru.chuev.natlexquest.interfaces.SectionInterface;
import ru.chuev.natlexquest.model.Section;
import ru.chuev.natlexquest.repo.SectionRepo;

import java.util.List;

@Service
public class SectionService implements SectionInterface {
    @Autowired
    private SectionRepo sectionRepo;

    @Override
    public List<Section> getAllSections() {
        return sectionRepo.findAll();
    }

    @Override
    public Section saveSection(Section section) {
        sectionRepo.save(section);
        return section;
    }

    @Override
    public Section getSectionById(long id) {
        return sectionRepo.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(id));
    }

    @Override
    public void deleteSectionById(long id) throws SectionNotFoundException {
        Section section = sectionRepo.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(id));

        sectionRepo.delete(section);
    }

    @Override
    public void updateSection(long id, Section newSection) {
        Section oldSection = sectionRepo.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(id));

        oldSection.setName(newSection.getName());
        sectionRepo.save(oldSection);
    }

    @Override
    public List<Section> getSectionsByGeodataCode(String code) {

        return sectionRepo.findByGeodataCode(code);
    }
}
