package com.example.BackEnd.controllers;

import com.example.BackEnd.models.Artist;
import com.example.BackEnd.models.Country;
import com.example.BackEnd.models.Museum;
import com.example.BackEnd.models.Painting;
import com.example.BackEnd.repositories.ArtistRepository;
import com.example.BackEnd.repositories.MuseumRepository;
import com.example.BackEnd.repositories.PaintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;


import java.util.*;

@RestController
@CrossOrigin("")
@RequestMapping("/api/v1")

public class PaintingController {

    public long findArtistIdByName(String cName){
        long id = 101;
        for (long index = 0; index < 100; index++){
            Optional<Artist> cc = artistRepository.findById(index);
            if (cc.isPresent()) {
                Artist art = cc.get();
                if (cName.equalsIgnoreCase(art.name)){
                    id = index;
                    break;
                }
            }
        }
        return id;
    }
    public long findMuseumIdByName(String cName){
        long id = 1001;
        for (long index = 0; index < 1000; index++){
            Optional <Museum> mus = museumRepository.findById(index);
            if (mus.isPresent()) {
                Museum museum = mus.get();
                if (cName.equalsIgnoreCase(museum.name)){
                    id = index;
                    break;
                }
            }
        }
        return id;
    }

    @Autowired
    PaintingRepository paintingRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    MuseumRepository museumRepository;


    @GetMapping("/paintings")
    public Page<Painting> getAllArtists(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return paintingRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "name")));
    }
    @PostMapping("/paintings")
    public ResponseEntity<Object> createPainting(@RequestBody Painting painting) throws Exception {
        try {
            long ind_art = findArtistIdByName(painting.artist.name);

            if (ind_art > 100 ){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого художника нет в базе");
            }
            long ind_mus = findMuseumIdByName(painting.museum.name);

            if (ind_mus > 1000 ){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого музея нет в базе");
            }
            Optional<Artist>
                    art = artistRepository.findById(ind_art);
            Optional<Museum>
                    mus = museumRepository.findById(ind_mus);
            art.ifPresent(artist -> painting.artist = artist);
            mus.ifPresent(museum -> painting.museum = museum);
            Painting nc = paintingRepository.save(painting);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);
        }

        catch(Exception ex) {

            String error = "undefinederror";
            Map<String, String> map =  new HashMap<>();
            map.put("error", error);
            System.out.println(error);
            return new ResponseEntity<Object> (map, HttpStatus.OK);
        }
    }

    @PutMapping("/paintings/{id}")
    public ResponseEntity<Painting> updatePainting(@PathVariable(value = "id") Long paintingId,
                                                   @RequestBody Painting paintingDetails) {
        Painting painting = null;
        Optional<Painting>
                cc = paintingRepository.findById(paintingId);
        if (cc.isPresent()) {

            long ind_art = findArtistIdByName(paintingDetails.artist.name);
            long ind_mus = findMuseumIdByName(paintingDetails.museum.name);
            if (ind_art > 100 ){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого художника нет в базе");
            }
            if (ind_mus > 1000 ){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого музея нет в базе");
            }
            Optional<Artist>
                    art = artistRepository.findById(ind_art);
            Optional<Museum>
                    mus = museumRepository.findById(ind_mus);

            art.ifPresent(artist -> paintingDetails.artist = artist);
            mus.ifPresent(museum -> paintingDetails.museum = museum);
            painting= cc.get();
            painting.name = paintingDetails.name;
            painting.artist = paintingDetails.artist;
            painting.museum = paintingDetails.museum;
            painting.year = paintingDetails.year;
            paintingRepository.save(painting);
            return ResponseEntity.ok(painting);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "painting not found");
        }
    }


/*    @DeleteMapping("/paintings/{id}")
    public ResponseEntity<Object> deletePainting(@PathVariable(value = "id") Long paintingId) {
        Optional<Painting>
                painting = paintingRepository.findById(paintingId);
        Map<String, Boolean>
                resp = new HashMap<>();
        if (painting.isPresent()) {
            paintingRepository.delete(painting.get());
            resp.put("deleted", Boolean.TRUE);
        }
        else
            resp.put("deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }*/
    @PostMapping("/deletepaintings")
    public ResponseEntity<HttpStatus> deletePaintings(@RequestBody List<Painting> paintings) {
        System.out.println(paintings.get(0).toString());
        List<Long> listOfIds = new ArrayList<>();
        for (Painting painting : paintings) {
            listOfIds.add(painting.id);
        }
        paintingRepository.deleteAllById(listOfIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
