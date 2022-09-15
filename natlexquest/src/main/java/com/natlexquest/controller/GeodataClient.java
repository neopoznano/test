package com.natlexquest.controller;

import com.natlexquest.model.Geodata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeodataClient {
    
    @RequestMapping(value = "/sections/by-code?code={code}", method = RequestMethod.GET)
    public ModelAndView getGeodata (@PathVariable("code") int code,
                                    @ModelAttribute("geodata") Geodata geodata) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("geodata");

        modelAndView.addObject("geodata", geodata.getByCode(code)); //TODO - реализовать поиск в БД по коду, будет возвращаться в модель нужное значение
        return modelAndView;
    }
}
