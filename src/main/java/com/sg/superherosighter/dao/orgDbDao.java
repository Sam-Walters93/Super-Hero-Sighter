/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosighter.dao;

import com.sg.superherosighter.dto.Org;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author stwal
 */
@Repository
public class orgDbDao implements orgDao {

    private final JdbcTemplate jdbcTemplate;
     
    @Autowired
    public orgDbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional
    public Org addOrg(Org org) {
        final String sql = "INSERT INTO org(name, description, address) VALUES (?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
         jdbcTemplate.update((Connection conn) -> {

          PreparedStatement statement = conn.prepareStatement(
            sql, 
            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, org.getName());
            statement.setString(2, org.getDescription());
            statement.setString(3, org.getAddress());
            return statement;

        }, keyHolder);

        org.setOrgId(keyHolder.getKey().intValue());
        
        return org;  
    }

    @Override
    public List<Org> getAllOrg() {
        final String sql = "SELECT * FROM org";
        return jdbcTemplate.query(sql, new OrgMapper());
    }

    @Override
    public Org getById(int id) {
        final String sql = "SELECT * FROM org WHERE orgId = ?;";
        return jdbcTemplate.queryForObject(sql, new OrgMapper(), id);
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM org WHERE orgId = ?;";
        return jdbcTemplate.update(sql, id) == 1;
    }

    @Override
    public void updateOrg(Org org) {
         final String sql = "UPDATE org SET name = ?, description = ?, address = ? WHERE orgId = ?;";
        jdbcTemplate.update(sql, org.getName(), org.getDescription(), org.getAddress(), org.getOrgId());
    }
    
       private final class OrgMapper implements RowMapper<Org> {
        
        @Override 
        public Org mapRow(ResultSet rs, int index) throws SQLException {
            Org org = new Org();
            
            org.setOrgId(rs.getInt("orgId"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            return org;
        }
    }            
}
