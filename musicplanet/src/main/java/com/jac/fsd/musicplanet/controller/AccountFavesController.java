//package com.jac.fsd.musicplanet.controller;
//
//import com.jac.fsd.musicplanet.model.Album;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Controller
//public class AccountFavesController {
//
//    // TODO This is hardcoded and needs to be elaborated on, just a proof of concept
//    @GetMapping("/user/favorites")
//    public String favorites(Model model) {
//        List<Album> hardCoded = new ArrayList<>(Arrays.asList(
//                new Album("Dark Side of the Moon", 1973),
//                new Album("Meteora", 2003),
//                new Album("The Memory of Trees", 1995)
//        ));
//        model.addAttribute("albumList", hardCoded);
//        model.addAttribute("customVar", "testMessage");
//        return "favorites";
//    }
//}
