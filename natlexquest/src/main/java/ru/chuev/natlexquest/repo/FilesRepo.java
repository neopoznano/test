package ru.chuev.natlexquest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chuev.natlexquest.model.Files;

@Repository
public interface FilesRepo extends JpaRepository<Files, Long> {
}
