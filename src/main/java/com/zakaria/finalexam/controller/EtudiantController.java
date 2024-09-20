package com.zakaria.finalexam.controller;

import com.zakaria.finalexam.model.Etudiant;
import com.zakaria.finalexam.repository.EtudiantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantDAO etudiantDAO;

    @GetMapping
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantDAO.findAll();
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable Long id) {
        Etudiant etudiant = etudiantDAO.findById(id);
        if (etudiant != null) {
            return new ResponseEntity<>(etudiant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Etudiant> createEtudiant(@RequestBody Etudiant etudiant) {
        try {
            etudiantDAO.save(etudiant);
            return new ResponseEntity<>(etudiant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiantDetails) {
        Etudiant etudiant = etudiantDAO.findById(id);
        if (etudiant != null) {
            etudiant.setNom(etudiantDetails.getNom());
            etudiant.setPrenom(etudiantDetails.getPrenom());
            etudiant.setEmail(etudiantDetails.getEmail());
            etudiant.setAdresse(etudiantDetails.getAdresse());
            etudiant.setTelephone(etudiantDetails.getTelephone());
            etudiant.setProcessusCorId(etudiantDetails.getProcessusCorId());
            etudiantDAO.update(etudiant);
            return new ResponseEntity<>(etudiant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Etudiant> partialUpdateEtudiant(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Etudiant etudiant = etudiantDAO.findById(id);
        if (etudiant != null) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Etudiant.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, etudiant, value);
            });
            etudiantDAO.update(etudiant);
            return new ResponseEntity<>(etudiant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        try {
            etudiantDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
