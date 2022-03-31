package com.example.demo.controller;

import com.example.demo.models.Auction;
import com.example.demo.models.User;
import com.example.demo.projections.ChangePasswordProjection;
import com.example.demo.services.AuctionService;
import com.example.demo.services.DetailsService;
import com.example.demo.web_config.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuctionController {
    AuctionService auctionService;
    DetailsService detailsService;

    AuctionController(AuctionService auctionService, DetailsService detailsService) {
        this.auctionService = auctionService;
        this.detailsService = detailsService;
    }

    @GetMapping("/")
    String mainPage(Model model) {
        return getSinglePage(model, 1);
    }

    @GetMapping("/{pageNumber}")
    String getSinglePage(Model model, @PathVariable("pageNumber") int currentPage){

        Page<Auction> page = auctionService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Auction> auctionList = page.getContent();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("Auctions",auctionList);
        return "main";
}
    @GetMapping("/register")
    String registerPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }
    @PostMapping("/register")
    String registerPage(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "register";
        }
        detailsService.register(user);
        return "register_success";
    }

    @GetMapping("/add_auction")
    String addAuction(Model model){
        model.addAttribute("AuctionPattern", new Auction());
        return "add_auction";
    }
    @PostMapping("/add_auction")
    String addAuction(@AuthenticationPrincipal CustomUserDetails loggedUser,
        @Valid Auction auction, @RequestParam("image") MultipartFile multipartFile,
        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "add_auction";
        }
        auctionService.saveAuction(loggedUser,auction, multipartFile);
        return "add_auction_success";
    }

    @GetMapping("/auctions/{id}")
    String auctionPage(@PathVariable Long id, Model model){
        Auction auction=auctionService.findAuction(id);
        model.addAttribute("auction", auction);
        model.addAttribute("user", auction.getUser());
        return "inside_auction";
    }
    @GetMapping("/user_panel")
    String userPanel(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model){
        model.addAttribute("user",loggedUser.getUser());
        model.addAttribute("auctions", auctionService.showUserAuctions(loggedUser.getUser()));
        return "user_panel";
    }
    @GetMapping("/user_panel/change_password")
    String changePasswordForm(Model model){
        model.addAttribute("passwordProjection", new ChangePasswordProjection());
        return "change_password_form";
    }
    @PostMapping("/user_panel/change_password")
    String changePasswordForm(@AuthenticationPrincipal CustomUserDetails loggedUser,
    ChangePasswordProjection passwordProjection){
        detailsService.changePassword(loggedUser, passwordProjection);
        return ("/change_password_form_success");
    }



}
