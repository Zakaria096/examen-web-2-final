package com.zakaria.finalexam.model;

import lombok.Data;
import java.util.Date;

@Data
public class Absence {
    private Long id;
    private Long etudiantId;
    private Long coursId;
    private Date date;
    private String motif;
    private boolean justifiee;
}
