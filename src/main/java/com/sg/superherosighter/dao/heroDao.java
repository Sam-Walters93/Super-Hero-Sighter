/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosighter.dao;

import com.sg.superherosighter.dto.Hero;
import java.util.List;

/**
 *
 * @author stwal
 */
public interface heroDao {
    Hero addHero(Hero hero);
    
    List<Hero> getAllHeroes();
    
    Hero getById(int id);
    
    public boolean deleteById(int id);
    
    void updateHero(Hero hero);
}
