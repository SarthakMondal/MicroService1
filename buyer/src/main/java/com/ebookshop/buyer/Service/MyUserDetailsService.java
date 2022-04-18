package com.ebookshop.buyer.Service;
import com.ebookshop.buyer.Model.BuyerModel;
import com.ebookshop.buyer.Repo.BuyerRepo;
import com.ebookshop.buyer.Security.MyUserDetails;
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
    BuyerRepo buyerRepo;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException
    {
        Optional<BuyerModel> user = buyerRepo.findByEmailId(emailId);
        user.orElseThrow(() -> new UsernameNotFoundException("Sorry User is Not Registered"));

        return (UserDetails) user.map(MyUserDetails::new).get();
    }

    public String findMySellerIdandRole(String emailId)
    {
        return buyerRepo.findMyUserIdandRole(emailId);
    }
}
