package com.ebookshop.buyer.Service;

import com.ebookshop.buyer.Model.BuyerLoginModel;
import com.ebookshop.buyer.Model.BuyerModel;
import com.ebookshop.buyer.Repo.BuyerRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class BuyerService
{
    @Autowired
    BuyerRepo buyerRepo;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    public ResponseEntity<?> loginBuyer(BuyerLoginModel buyerLoginModel)
    {
        ResponseEntity<?> response = null;
        ResponseEntity<?> loginRes = null;

        RestTemplate rest = new RestTemplate();
        String url = "http://localhost:8083/oauth/token";
        String authHeader = "Basic " + "RUJvb2tTaG9wX0J1eWVyX0NsaWVudElkOmVib29rc2hvcF9idXllckBzZWNyZXQ=";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type","password");
        formData.add("username", buyerLoginModel.getEmailId());
        formData.add("password", buyerLoginModel.getPassword());

        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        header.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        header.set("Authorization", authHeader);
        JSONObject temp = new JSONObject();
        HttpEntity<MultiValueMap<String, String>> data = new HttpEntity<>(formData, header);

        try
        {
            loginRes = rest.exchange(url, HttpMethod.POST, data, String.class);
            temp = new JSONObject(loginRes.getBody().toString());
            response = new ResponseEntity<>(temp.get("access_token"), HttpStatus.OK);
        }

        catch(Exception e)
        {
            temp = new JSONObject(e.getMessage().substring(7, e.getMessage().length()));
            System.out.println(temp);
            String error = temp.get("error") + ", " + temp.get("error_description");
            response = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    public ResponseEntity<?> signupBuyer(BuyerModel buyerModel)
    {
        ResponseEntity<?> response = null;

        try
        {
            buyerModel.setPassword(new BCryptPasswordEncoder().encode(buyerModel.getPassword()));
            buyerRepo.save(buyerModel);
            response = new ResponseEntity<>("SIGNEDUP", HttpStatus.OK);
            return response;
        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> viewBuyerProfile()
    {
        ResponseEntity<?> response = null;

        try
        {
            String ret = buyerRepo.findMyUserIdandRole(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
            long sId = Long.parseLong(ret.split(",")[0]);

            BuyerModel sellerModel = buyerRepo.findById(sId).get();
            sellerModel.setPassword("**************");
            response = new ResponseEntity<>(sellerModel, HttpStatus.OK);
            return response;
        }

        catch (Exception e)
        {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }
}
