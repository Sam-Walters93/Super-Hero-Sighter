/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosighter.controller;

import com.sg.superherosighter.dao.locationDao;
import com.sg.superherosighter.dto.Location;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author stwal
 */
@Controller
public class locationController {
    @Autowired
    locationDao locationDao;
    
    @GetMapping("/locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }
    
    @PostMapping("/addLocation")
    public String addLocation(Location location, BindingResult result, HttpServletRequest request, Model model) {
    
        if (result.hasErrors()) {
            List<Location> locations = locationDao.getAllLocations();
            model.addAttribute("locations", locations);
            return "addLocation";
        }
        
        location.setAddress(request.getParameter("address"));
        location.setCity(request.getParameter("city"));
        location.setState(request.getParameter("state"));
        
    }
}
