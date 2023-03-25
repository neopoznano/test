package ru.chuev.natlexquest.interfaces;

import ru.chuev.natlexquest.model.Geodata;

import java.util.List;

public interface GeodataInterface {
    List<Geodata> getAllGeodata();
    Geodata getGeodataById(long id);
    Geodata saveGeodata(Geodata geodata);
    void updateGeodata(long id, Geodata newGeodata);
    void deleteGeodataById(long id);
}
