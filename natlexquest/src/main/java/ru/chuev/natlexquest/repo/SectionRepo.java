package ru.chuev.natlexquest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.chuev.natlexquest.model.Section;
import java.util.List;

@Repository
public interface SectionRepo extends JpaRepository<Section, Long> {
    @Query(value = "SELECT * FROM sections section " +
            "JOIN geodata gdata ON section.id = gdata.section_id " +
            "WHERE gdata.code = :code", nativeQuery = true)
    List<Section> findByGeodataCode(@Param(value = "code") String code);

}
