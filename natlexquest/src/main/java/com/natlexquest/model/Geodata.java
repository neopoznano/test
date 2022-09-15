package com.natlexquest.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "geodata")
public class Geodata {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "geoData")
    private List<GeologicalClass> geoData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeologicalClass> getGeoData() {
        return geoData;
    }

    public void setGeoClass(List<GeologicalClass> geoData) {
        this.geoData = geoData;
    }
}
