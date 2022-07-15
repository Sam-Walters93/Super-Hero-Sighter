/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosighter.dao;

import com.sg.superherosighter.dto.Sighting;
import java.util.List;

/**
 *
 * @author stwal
 */
public interface sightingDao {
    Sighting addSighting(Sighting sighting);
    
    List<Sighting> getAll();
    
    Sighting getById(int id);
    
    public boolean deleteById(int id);   
    
    void updateSighting(Sighting sighting);
}
