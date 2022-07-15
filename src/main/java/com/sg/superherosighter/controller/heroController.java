/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosighter.controller;

import com.sg.superherosighter.dao.heroDao;
import com.sg.superherosighter.dao.orgDao;
import com.sg.superherosighter.dao.sightingDao;
import com.sg.superherosighter.dto.Hero;
import com.sg.superherosighter.service.superHeroService;
import static java.lang.Boolean.parseBoolean;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author stwal
 */
@Controller
public class heroController {
    private superHeroService service;
    
    @Autowired
    heroDao heroDao;
    
    @Autowired
    sightingDao sightingDao;
    
    @Autowired
    orgDao orgDao;
    
    
    public heroController(superHeroService service) {
        this.service = service;
    }
    
    public heroController(){}
    
    @GetMapping("/heroes")
    public String displayAll(Model model){
        List<Hero> heroes = heroDao.getAllHeroes();
        model.addAttribute("heroes", heroes);
        return "heroes";    
    }
    
    @GetMapping("/addHero")
    public String addHeroRedirect() {
        return "redirect:/heroes";
    }
    
    @PostMapping("/addHero")
    public String addHero(Hero hero, BindingResult result, HttpServletRequest request, Model model){
        if (result.hasErrors()) {
            List<Hero> heroes = heroDao.getAllHeroes();
            model.addAttribute("heroes", heroes);
            return "/addHero";
        }
        
        hero.setName(request.getParameter("name"));
        hero.setPower(request.getParameter("power"));
        hero.setDescription(request.getParameter("description"));
        hero.setGood(parseBoolean(request.getParameter("good")));
        
        heroDao.addHero(hero);
        
        return "redirect:/heroes";   
    }
    
    @PostMapping("/deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("supeId"));
        heroDao.deleteById(id);

        return "redirect:/heroes";
    }
    
    @GetMapping("/editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("supeId"));
        Hero hero = heroDao.getById(id);

        model.addAttribute("hero", hero);
        return "editHero";
    }
}
