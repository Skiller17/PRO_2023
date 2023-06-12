package com.example.BackEnd.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "artists")
@Access(AccessType.FIELD)
public class Artist {
    public Artist() {
    }

    public Artist(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @ManyToOne()
    @JoinColumn(name = "countryid")
    public Country country;

    @Column(name = "age", nullable = false)
    public String age;

    @JsonIgnore
    @OneToMany(mappedBy = "artist")
    public List<Painting>
            paintings = new ArrayList<>();


}
