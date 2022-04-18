package com.ebookshop.seller.Service;

import com.ebookshop.seller.Model.BookShopModel;
import com.ebookshop.seller.Model.SellerLoginModel;
import com.ebookshop.seller.Model.SellerModel;
import com.ebookshop.seller.Repo.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SellerService
{
    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    public ResponseEntity<?> loginShop(SellerLoginModel sellerLoginModel)
    {
        ResponseEntity<?> response = null;
        ResponseEntity<?> loginRes = null;

        RestTemplate rest = new RestTemplate();
        String url = "http://localhost:8082/oauth/token";
        String authHeader = "Basic " + "RUJvb2tTaG9wX1NlbGxlcl9DbGllbnRJZDplYm9va3Nob3Bfc2VsbGVyQHNlY3JldA==";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type","password");
        formData.add("username", sellerLoginModel.getEmailId());
        formData.add("password", sellerLoginModel.getPassword());

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
            String error = temp.get("error") + ", " + temp.get("error_description");
            response = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    public ResponseEntity<?> registerShop(SellerModel sellerModel)
    {
        ResponseEntity<?> response = null;

        try
        {
            sellerModel.setPassword(new BCryptPasswordEncoder().encode(sellerModel.getPassword()));
            sellerRepo.save(sellerModel);
            response = new ResponseEntity<>("REGISTERED", HttpStatus.OK);
            return response;
        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> viewShopProfile()
    {
        ResponseEntity<?> response = null;

        try
        {
            String ret = sellerRepo.findMyUserIdandRole(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
            long sId = Long.parseLong(ret.split(",")[0]);

            SellerModel sellerModel = sellerRepo.findById(sId).get();
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

    public ResponseEntity<?> getShopList()
    {
        ResponseEntity<?> response = null;

        try
        {
           List<SellerModel> list = sellerRepo.findAll();
           List<BookShopModel> ret = new ArrayList<BookShopModel>();

           for(SellerModel sellerModel: list)
           {
               BookShopModel bookShopModel = new BookShopModel();
               bookShopModel.setSid(sellerModel.getId());
               bookShopModel.setShopName(sellerModel.getShopName());
               bookShopModel.setRegistrationNo(sellerModel.getRegistrationNo());
               bookShopModel.setShopAddress(sellerModel.getShopAddress());

               ret.add(bookShopModel);
           }

            response = new ResponseEntity<>(ret, HttpStatus.OK);
            return response;
        }

        catch (Exception e)
        {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }
}
