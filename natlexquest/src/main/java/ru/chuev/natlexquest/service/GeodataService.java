package ru.chuev.natlexquest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chuev.natlexquest.interfaces.GeodataInterface;
import ru.chuev.natlexquest.model.Geodata;
import ru.chuev.natlexquest.repo.GeodataRepo;
import ru.chuev.natlexquest.exceptions.GeodataNotFoundException;

import java.util.List;
@Service
public class GeodataService implements GeodataInterface {
    @Autowired
    private GeodataRepo geodataRepo;

    @Override
    public List<Geodata> getAllGeodata() {
        return geodataRepo.findAll();
    }

    @Override
    public Geodata getGeodataById(long id) {
        return geodataRepo.findById(id)
                .orElseThrow(() -> new GeodataNotFoundException(id));
    }

    @Override
    public Geodata saveGeodata(Geodata geodata) {
        geodataRepo.save(geodata);
        return geodata;
    }

    @Override
    public void updateGeodata(long id, Geodata newGeodata) {
        Geodata oldGeodata = geodataRepo.findById(id)
                .orElseThrow(() -> new GeodataNotFoundException(id));

        oldGeodata.setName(newGeodata.getName());
        oldGeodata.setCode(newGeodata.getCode());
        geodataRepo.save(oldGeodata);
    }

    @Override
    public void deleteGeodataById(long id) {
        Geodata geodata = geodataRepo.findById(id)
                .orElseThrow(() -> new GeodataNotFoundException(id));

        geodataRepo.delete(geodata);
    }
}
