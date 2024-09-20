package com.zakaria.finalexam.repository;

import com.zakaria.finalexam.model.Absence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AbsenceDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Absence> rowMapper = new RowMapper<Absence>() {
        @Override
        public Absence mapRow(ResultSet rs, int rowNum) throws SQLException {
            Absence absence = new Absence();
            absence.setId(rs.getLong("id"));
            absence.setEtudiantId(rs.getLong("etudiant_id"));
            absence.setCoursId(rs.getLong("cours_id"));
            absence.setDate(rs.getDate("date"));
            absence.setMotif(rs.getString("motif"));
            absence.setJustifiee(rs.getBoolean("justifiee"));
            return absence;
        }
    };

    public List<Absence> findAll() {
        String sql = "SELECT * FROM absence";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Absence findById(Long id) {
        String sql = "SELECT * FROM absence WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    public int save(Absence absence) {
        String sql = "INSERT INTO absence (etudiant_id, cours_id, date, motif, justifiee) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, absence.getEtudiantId(), absence.getCoursId(), absence.getDate(), absence.getMotif(), absence.isJustifiee());
    }

    public int update(Absence absence) {
        String sql = "UPDATE absence SET etudiant_id = ?, cours_id = ?, date = ?, motif = ?, justifiee = ? WHERE id = ?";
        return jdbcTemplate.update(sql, absence.getEtudiantId(), absence.getCoursId(), absence.getDate(), absence.getMotif(), absence.isJustifiee(), absence.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM absence WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int countNonJustifiedAbsences(Long etudiantId) {
        String sql = "SELECT COUNT(*) FROM absence WHERE etudiant_id = ? AND justifiee = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{etudiantId}, Integer.class);
    }
}
