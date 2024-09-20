package com.zakaria.finalexam.controller;

import com.zakaria.finalexam.model.Enseignant;
import com.zakaria.finalexam.repository.EnseignantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantDAO enseignantDAO;

    @GetMapping
    public List<Enseignant> getAllEnseignants() {
        return enseignantDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> getEnseignantById(@PathVariable Long id) {
        Enseignant enseignant = enseignantDAO.findById(id);
        if (enseignant != null) {
            return ResponseEntity.ok().body(enseignant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Enseignant> createEnseignant(@RequestBody Enseignant enseignant) {
        enseignantDAO.save(enseignant);
        return ResponseEntity.ok().body(enseignant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@PathVariable Long id, @RequestBody Enseignant enseignantDetails) {
        Enseignant enseignant = enseignantDAO.findById(id);
        if (enseignant != null) {
            enseignant.setNom(enseignantDetails.getNom());
            enseignant.setPrenom(enseignantDetails.getPrenom());
            enseignant.setEmail(enseignantDetails.getEmail());
            enseignant.setTelephone(enseignantDetails.getTelephone());
            enseignant.setMatiere(enseignantDetails.getMatiere()); // Ajout de la colonne "matière"
            enseignantDAO.update(enseignant);
            return ResponseEntity.ok().body(enseignant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        enseignantDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
