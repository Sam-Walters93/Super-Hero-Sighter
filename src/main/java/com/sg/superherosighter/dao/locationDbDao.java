/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosighter.dao;
import com.sg.superherosighter.dto.Location;
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
public class locationDbDao implements locationDao {

    private final JdbcTemplate jdbcTemplate;
     
    @Autowired
    public locationDbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Location addLocation(Location location) {
        final String sql = "INSERT INTO location(address, city, state) VALUES (?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {

          PreparedStatement statement = conn.prepareStatement(
            sql, 
            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, location.getAddress());
            statement.setString(2, location.getCity());
            statement.setString(3, location.getState());
            return statement;

        }, keyHolder);

        location.setLocationId(keyHolder.getKey().intValue());
        
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
       final String sql = "SELECT * FROM location";
         return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Override
    public Location getById(int id) {
        final String sql = "SELECT * FROM location WHERE locationId = ?;";
        return jdbcTemplate.queryForObject(sql, new LocationMapper(), id);
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM location WHERE fk_sighting_Id = ?;";
      
        return jdbcTemplate.update(sql, id) == 1;
    }

    @Override
    public void updateLocation(Location location) {
        final String sql = "UPDATE location SET address = ?, city = ?, state = ?, country = ? WHERE locationId = ?;";
        jdbcTemplate.update(sql, location.getAddress(), location.getCity(), location.getState(), location.getCountry());
    }
    
    private final class LocationMapper implements RowMapper<Location> {
        
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setAddress(rs.getString("address"));
            location.setCity(rs.getString("city"));
            location.setState(rs.getString("state"));
            location.setCountry(rs.getString("country"));
         
            return location;
        }
    }  
}
