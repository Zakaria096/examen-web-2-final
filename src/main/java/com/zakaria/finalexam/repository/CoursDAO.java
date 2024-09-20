package com.zakaria.finalexam.repository;

import com.zakaria.finalexam.model.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CoursDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Cours> rowMapper = new RowMapper<Cours>() {
        @Override
        public Cours mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cours cours = new Cours();
            cours.setId(rs.getLong("id"));
            cours.setTitre(rs.getString("titre"));
            cours.setDescription(rs.getString("description"));
            cours.setEnseignantId(rs.getLong("enseignant_id"));
            return cours;
        }
    };

    public List<Cours> findAll() {
        String sql = "SELECT * FROM cours";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Cours findById(Long id) {
        String sql = "SELECT * FROM cours WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    public int save(Cours cours) {
        String sql = "INSERT INTO cours (titre, description, enseignant_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, cours.getTitre(), cours.getDescription(), cours.getEnseignantId());
    }

    public int update(Cours cours) {
        String sql = "UPDATE cours SET titre = ?, description = ?, enseignant_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, cours.getTitre(), cours.getDescription(), cours.getEnseignantId(), cours.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM cours WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
