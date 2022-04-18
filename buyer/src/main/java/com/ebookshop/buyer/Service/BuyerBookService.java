package com.ebookshop.buyer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class BuyerBookService
{
    @Autowired
    RestTemplate restApi;

    public ResponseEntity<?> getAllBooksWithShopDataFromBuyer()
    {
        ResponseEntity<?> response = null;
        String url = "http://book-service/backend/books/protected/getbookswithshopdata";

        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
            response = restApi.exchange(url, HttpMethod.GET, entity, List.class);
            return response;
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> getAllShops()
    {
        ResponseEntity<?> response = null;
        String url = "http://seller-service/backend/seller/public/getallshops";

        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
            response = restApi.exchange(url, HttpMethod.GET, entity, List.class);
            return response;
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> getBooksbyShop(String sId)
    {
        ResponseEntity<?> response = null;
        String url = "http://seller-service/backend/seller/public/getbooksofshop/"+sId;

        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
            response = restApi.exchange(url, HttpMethod.GET, entity, List.class);
            return response;
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }
}
