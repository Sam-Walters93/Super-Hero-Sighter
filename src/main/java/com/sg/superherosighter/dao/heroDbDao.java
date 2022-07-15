/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosighter.dao;

import com.sg.superherosighter.dto.Hero;
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
public class heroDbDao implements heroDao {
    
    private final JdbcTemplate jdbcTemplate;
     
    @Autowired
    public heroDbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String sql = "INSERT INTO hero(power, name, description) VALUES (?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {

          PreparedStatement statement = conn.prepareStatement(
            sql, 
            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, hero.getPower());
            statement.setString(2, hero.getName());
            statement.setString(3, hero.getDescription());
            return statement;

        }, keyHolder);

        hero.setSupeId(keyHolder.getKey().intValue());
        
        return hero;    
    }
    
    @Override
    public List<Hero> getAllHeroes() {
        final String sql = "SELECT * FROM supe";
        return jdbcTemplate.query(sql, new HeroMapper());
    }
    
    @Override
    public Hero getById(int id) {
        final String sql = "SELECT * FROM supe WHERE supeId = ?;";
        return jdbcTemplate.queryForObject(sql, new HeroMapper(), id);
    }
    
    @Override
    @Transactional 
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM supe WHERE supeId = ?;";
        final String sql2 = "DELETE FROM sighting WHERE supeId = ?;";
        
        jdbcTemplate.update(sql2, id);
        return jdbcTemplate.update(sql, id) == 1;
    }

    @Override
    public void updateHero(Hero hero) {
        final String sql = "UPDATE supe SET power = ?, name = ?, description = ?, good = ? WHERE supeId = ?;";
        jdbcTemplate.update(sql, hero.getPower(), hero.getName(), hero.getDescription(), hero.getGood(), hero.getSupeId());
           
    }
    
    private final class HeroMapper implements RowMapper<Hero> {
        
        @Override 
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setSupeId(rs.getInt("supeId"));
            hero.setPower(rs.getString("power"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setGood(rs.getBoolean("good"));
            return hero;
        }
    }
}
