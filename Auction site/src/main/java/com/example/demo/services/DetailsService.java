package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.projections.ChangePasswordProjection;
import com.example.demo.repositories.UserRepository;
import com.example.demo.web_config.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class DetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    public DetailsService(){};

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> foundUser=userRepo.findByEmail(email);
        if(foundUser.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(foundUser.get());
    }

    String encodedPassword(User user){
        BCryptPasswordEncoder encoding=new BCryptPasswordEncoder();
        String encodedPassword=encoding.encode(user.getPassword());
        return encodedPassword;
    }
    public void register(User user){
        user.setPassword(encodedPassword(user));
        userRepo.save(user);
    }

//    public void changePassword(CustomUserDetails loggedUser, ChangePasswordProjection passwordProjection){
//        User user=loggedUser.getUser();
////        if(!user.getPassword().equals(passwordProjection.getOldPassword())){
//        user.setPassword(encodedPassword(user));
//        userRepo.save(loggedUser.getUser());
//    }

}
