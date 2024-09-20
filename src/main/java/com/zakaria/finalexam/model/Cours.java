package com.zakaria.finalexam.model;

import lombok.Data;

@Data
public class Cours {
    private Long id;
    private String titre;
    private String description;
    private Long enseignantId;
}
