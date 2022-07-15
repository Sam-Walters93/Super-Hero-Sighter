/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosighter.dao;

import com.sg.superherosighter.dto.Location;
import java.util.List;

/**
 *
 * @author stwal
 */
public interface locationDao {
    Location addLocation(Location location);
    
    List<Location> getAllLocations();
    
    Location getById(int id);
    
    public boolean deleteById(int id);
    
    void updateLocation(Location location);    
}
