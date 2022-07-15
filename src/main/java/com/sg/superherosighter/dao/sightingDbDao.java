/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosighter.dao;

import com.sg.superherosighter.dto.Sighting;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author stwal
 */
public class sightingDbDao implements sightingDao{
    
    private final JdbcTemplate jdbcTemplate;
     
    @Autowired
    public sightingDbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
       final String sql = "INSERT INTO sighting(description, address) VALUES (?,?);"; 
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
         jdbcTemplate.update((Connection conn) -> {

          PreparedStatement statement = conn.prepareStatement(
            sql, 
            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, sighting.getDescription());
            statement.setString(2, sighting.getAddress());
        
            return statement;

        }, keyHolder);

        sighting.setSightingId(keyHolder.getKey().intValue());
        
        return sighting; 
    }

    @Override
    public List<Sighting> getAll() {
         final String sql = "SELECT * FROM sighting";
        return jdbcTemplate.query(sql, new SightingMapper());
    }

    @Override
    public Sighting getById(int id) {
        final String sql = "SELECT * FROM sighting WHERE sightId = ?;";
        return jdbcTemplate.queryForObject(sql, new SightingMapper(), id);
    }

    @Override
    public boolean deleteById(int id) {
       final String sql = "DELETE FROM sighting WHERE sightId = ?;";
       return jdbcTemplate.update(sql, id) == 1;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String sql = "UPDATE sighting SET description = ?, address = ? WHERE sightingId = ?;";
        jdbcTemplate.update(sql, sighting.getDescription(), sighting.getAddress(), sighting.getSightingId());
    }
    
    
     private final class SightingMapper implements RowMapper<Sighting> {
        
        @Override 
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightId"));
            sighting.setDescription(rs.getString("description"));
            sighting.setAddress(rs.getString("address"));
            
            return sighting;
        }
    }
    
}
