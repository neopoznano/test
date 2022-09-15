package com.natlexquest.model;

import java.util.Map;
import javax.persistence.*;


public class GeologicalClass {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "geologicalClass")
    private Map<String, String> geologicalClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, String> getGeoClass() {
        return geologicalClass;
    }

    public void setGeoClass(Map<String, String> geologicalClass) {
        this.geologicalClass = geologicalClass;
    }
    
    public Map<String, String> getByCode(int code) {
        return geologicalClass;
    }
}