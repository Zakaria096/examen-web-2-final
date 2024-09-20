package com.zakaria.finalexam.controller;

import com.zakaria.finalexam.model.Absence;
import com.zakaria.finalexam.model.Etudiant;
import com.zakaria.finalexam.repository.AbsenceDAO;
import com.zakaria.finalexam.repository.EtudiantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/absences")
public class AbsenceController {

    @Autowired
    private AbsenceDAO absenceDAO;

    @Autowired
    private EtudiantDAO etudiantDAO;

    @GetMapping
    public List<Absence> getAllAbsences() {
        return absenceDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable Long id) {
        Absence absence = absenceDAO.findById(id);
        if (absence != null) {
            return ResponseEntity.ok().body(absence);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Absence> createAbsence(@RequestBody Absence absence) {
        absenceDAO.save(absence);
        checkAndUpdateProcessusCor(absence.getEtudiantId());
        return ResponseEntity.ok().body(absence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Absence> updateAbsence(@PathVariable Long id, @RequestBody Absence absenceDetails) {
        Absence absence = absenceDAO.findById(id);
        if (absence != null) {
            absence.setEtudiantId(absenceDetails.getEtudiantId());
            absence.setCoursId(absenceDetails.getCoursId());
            absence.setDate(absenceDetails.getDate());
            absence.setMotif(absenceDetails.getMotif());
            absence.setJustifiee(absenceDetails.isJustifiee());
            absenceDAO.update(absence);
            checkAndUpdateProcessusCor(absence.getEtudiantId());
            return ResponseEntity.ok().body(absence);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        Absence absence = absenceDAO.findById(id);
        if (absence != null) {
            absenceDAO.deleteById(id);
            checkAndUpdateProcessusCor(absence.getEtudiantId());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void checkAndUpdateProcessusCor(Long etudiantId) {
        int nonJustifiedAbsences = absenceDAO.countNonJustifiedAbsences(etudiantId);
        if (nonJustifiedAbsences >= 3) {
            Etudiant etudiant = etudiantDAO.findById(etudiantId);
            if (etudiant != null) {

                etudiant.setProcessusCorId(etudiant.getProcessusCorId() + 1);
                etudiantDAO.update(etudiant);
            }
        }
    }
}
