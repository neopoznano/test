package ru.chuev.natlexquest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chuev.natlexquest.interfaces.GeodataInterface;
import ru.chuev.natlexquest.model.Geodata;

import java.util.List;

@RestController
@RequestMapping("/geodata")

public class GeodataController {
    @Autowired
    private GeodataInterface geodataService;

    @GetMapping
    public List<Geodata> getAllGeoClass(){
        return geodataService.getAllGeodata();
    }

    @GetMapping("/{id}")
    public Geodata getGeoClassById(@PathVariable long id) {
        return geodataService.getGeodataById(id);
    }

    @PostMapping
    public Geodata addGeodata(@RequestBody @Valid Geodata geodata
    ) {
        return geodataService.saveGeodata(geodata);
    }

    @PutMapping("/{id}")
    public void updateGeodata(@PathVariable("id") long id, @RequestBody @Valid Geodata geodata) {
        geodataService.updateGeodata(id, geodata);
    }

    @DeleteMapping("/{id}")
    public void deleteGeodataById(@PathVariable long id) {
        geodataService.deleteGeodataById(id);
    }
}
