package com.example.demo.services;

import com.example.demo.files_upload.FileUploadUtil;
import com.example.demo.models.Auction;
import com.example.demo.models.User;
import com.example.demo.repositories.AuctionsRepository;
import com.example.demo.web_config.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuctionService{

    private final AuctionsRepository auctionsRepo;

    AuctionService(final AuctionsRepository auctionsRepo){
        this.auctionsRepo=auctionsRepo;
    }

    public void saveAuction(CustomUserDetails loggedUser, Auction auction, MultipartFile multipartFile){

        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Auction createdAuction=new Auction(
                auction.getTitle(),
                auction.getDescription(),
                auction.getPrice(),
                loggedUser.getUser()
        );

        createdAuction.setPhotos(fileName);
        auctionsRepo.save(createdAuction);
        String uploadDir="auction-photos/" + createdAuction.getId();
        try{
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);}
        catch(IOException ioe){
            ioe.printStackTrace();}
    }

    public List<Auction> showAllAuctions(){
        List<Auction> allAuctions=auctionsRepo.findAll(Sort.by("time").descending());
        return allAuctions;
    }
    public Page<Auction> findPage(int PageNumber){
        Pageable pageable= PageRequest.of(PageNumber-1,5);
        return auctionsRepo.findAll(pageable);
    }
    public Auction findAuction(long id){
        Optional<Auction> auction =auctionsRepo.findById(id);
        return auction.get();
    }
    public List<Auction> showUserAuctions(User user){
        List<Auction> userAuctions=auctionsRepo.findByUser(user);
        return userAuctions;
    }
}
