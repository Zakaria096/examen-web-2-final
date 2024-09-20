package com.zakaria.finalexam.model;

import lombok.Data;

@Data
public class Enseignant {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matiere;
}
