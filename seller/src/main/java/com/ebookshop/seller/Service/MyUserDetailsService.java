package com.ebookshop.seller.Service;

import com.ebookshop.seller.Model.SellerModel;
import com.ebookshop.seller.Repo.SellerRepo;
import com.ebookshop.seller.Security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Autowired
    SellerRepo sellerRepo;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException
    {
        Optional<SellerModel> user = sellerRepo.findByEmailId(emailId);
        user.orElseThrow(() -> new UsernameNotFoundException("Sorry User is Not Registered"));

        return (UserDetails) user.map(MyUserDetails::new).get();
    }

    public String findMySellerIdandRole(String emailId)
    {
        return sellerRepo.findMyUserIdandRole(emailId);
    }
}
