package com.zakaria.finalexam.repository;

import com.zakaria.finalexam.model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EtudiantDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Etudiant> rowMapper = new RowMapper<Etudiant>() {
        @Override
        public Etudiant mapRow(ResultSet rs, int rowNum) throws SQLException {
            Etudiant etudiant = new Etudiant();
            etudiant.setId(rs.getLong("id"));
            etudiant.setNom(rs.getString("nom"));
            etudiant.setPrenom(rs.getString("prenom"));
            etudiant.setEmail(rs.getString("email"));
            etudiant.setAdresse(rs.getString("adresse"));
            etudiant.setTelephone(rs.getString("telephone"));
            etudiant.setProcessusCorId(rs.getLong("processus_cor_id"));
            return etudiant;
        }
    };

    public List<Etudiant> findAll() {
        String sql = "SELECT * FROM etudiant";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Etudiant findById(Long id) {
        String sql = "SELECT * FROM etudiant WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    public int save(Etudiant etudiant) {
        String sql = "INSERT INTO etudiant (nom, prenom, email, adresse, telephone, processus_cor_id) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, etudiant.getNom(), etudiant.getPrenom(), etudiant.getEmail(), etudiant.getAdresse(), etudiant.getTelephone(), etudiant.getProcessusCorId());
    }

    public int update(Etudiant etudiant) {
        String sql = "UPDATE etudiant SET nom = ?, prenom = ?, email = ?, adresse = ?, telephone = ?, processus_cor_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, etudiant.getNom(), etudiant.getPrenom(), etudiant.getEmail(), etudiant.getAdresse(), etudiant.getTelephone(), etudiant.getProcessusCorId(), etudiant.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM etudiant WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
