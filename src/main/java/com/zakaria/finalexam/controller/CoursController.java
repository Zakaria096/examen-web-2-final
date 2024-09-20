package com.zakaria.finalexam.controller;

import com.zakaria.finalexam.model.Cours;
import com.zakaria.finalexam.repository.CoursDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cours")
public class CoursController {

    @Autowired
    private CoursDAO coursDAO;

    @GetMapping
    public List<Cours> getAllCours() {
        return coursDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id) {
        Cours cours = coursDAO.findById(id);
        if (cours != null) {
            return ResponseEntity.ok().body(cours);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cours> createCours(@RequestBody Cours cours) {
        coursDAO.save(cours);
        return ResponseEntity.ok().body(cours);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cours> updateCours(@PathVariable Long id, @RequestBody Cours coursDetails) {
        Cours cours = coursDAO.findById(id);
        if (cours != null) {
            cours.setTitre(coursDetails.getTitre());
            cours.setDescription(coursDetails.getDescription());
            cours.setEnseignantId(coursDetails.getEnseignantId());
            coursDAO.update(cours);
            return ResponseEntity.ok().body(cours);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        coursDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
