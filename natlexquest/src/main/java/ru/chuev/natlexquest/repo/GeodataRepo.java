package ru.chuev.natlexquest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chuev.natlexquest.model.Geodata;

@Repository
public interface GeodataRepo extends JpaRepository<Geodata, Long> {
}
