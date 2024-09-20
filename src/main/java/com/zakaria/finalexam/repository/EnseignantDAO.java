package com.zakaria.finalexam.repository;

import com.zakaria.finalexam.model.Enseignant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnseignantDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Enseignant> rowMapper = new RowMapper<Enseignant>() {
        @Override
        public Enseignant mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enseignant enseignant = new Enseignant();
            enseignant.setId(rs.getLong("id"));
        ;    enseignant.setNom(rs.getString("nom"));
            enseignant.setPrenom(rs.getString("prenom"));
            enseignant.setEmail(rs.getString("email"));
            enseignant.setTelephone(rs.getString("telephone"));
            enseignant.setMatiere(rs.getString("matiere")); // Ajout de la colonne "matière"
            return enseignant;
        }
    };

    public List<Enseignant> findAll() {
        String sql = "SELECT * FROM enseignant";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Enseignant findById(Long id) {
        String sql = "SELECT * FROM enseignant WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    public int save(Enseignant enseignant) {
        String sql = "INSERT INTO enseignant (nom, prenom, email, telephone, matiere) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, enseignant.getNom(), enseignant.getPrenom(), enseignant.getEmail(), enseignant.getTelephone(), enseignant.getMatiere());
    }

    public int update(Enseignant enseignant) {
        String sql = "UPDATE enseignant SET nom = ?, prenom = ?, email = ?, telephone = ?, matiere = ? WHERE id = ?";
        return jdbcTemplate.update(sql, enseignant.getNom(), enseignant.getPrenom(), enseignant.getEmail(), enseignant.getTelephone(), enseignant.getMatiere(), enseignant.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM enseignant WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
