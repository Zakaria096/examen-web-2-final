package com.zakaria.finalexam.model;

import lombok.Data;

@Data
public class Etudiant {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String telephone;
    private Long processusCorId;
}
