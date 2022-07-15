/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosighter.dao;

import com.sg.superherosighter.dto.Org;
import java.util.List;

/**
 *
 * @author stwal
 */
public interface orgDao {
    Org addOrg(Org org);
    
    List<Org> getAllOrg();
    
    Org getById(int id);
    
    public boolean deleteById(int id);
    
    public void updateOrg(Org org);
    
}
