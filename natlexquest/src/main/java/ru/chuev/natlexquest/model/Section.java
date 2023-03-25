package ru.chuev.natlexquest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
@Entity
@Table(name = "sections")
public class Section {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "name")
        @NotBlank
        private String name;

        @Column
        @OneToMany(cascade = CascadeType.ALL,
                mappedBy = "section",
                fetch = FetchType.EAGER)
        private List<Geodata> geologicalClasses;
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public List<Geodata> getGeologicalData() {
                return geologicalClasses;
        }

        public void setGeologicalClasses(List<Geodata> geologicalClasses) {
                this.geologicalClasses = geologicalClasses;
        }

        public static int getMaxGeoClassesCount(List<Section> sections) {
            return sections.stream().map(section -> section.getGeologicalData().size())
                    .max(Integer::compareTo).get();
        }

}
