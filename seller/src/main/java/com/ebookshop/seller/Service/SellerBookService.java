package com.ebookshop.seller.Service;

import com.ebookshop.seller.Model.SellerBookModel;
import com.ebookshop.seller.Repo.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SellerBookService
{
    @Autowired
    private RestTemplate restApi;

    @Autowired
    SellerRepo sellerRepo;


    private boolean checkIfBookExistsInShop(long bId, long sId)
    {
        ResponseEntity<?> response = null;
        String url = "http://book-service/backend/books/protected/checkifbookexistsinshop/"+sId+"/"+bId;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
        response = restApi.exchange(url, HttpMethod.GET, entity, Boolean.class);
        Boolean returnVal = (Boolean) response.getBody();

        return Boolean.TRUE.equals(returnVal);

    }
    public ResponseEntity<?> addNewBook(SellerBookModel bookModel)
    {
        ResponseEntity<?> response = null;
        String url = "http://book-service/backend/books/protected/addbook";

        try
        {

            String ret = sellerRepo.findMyUserIdandRole(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
            long sId = Long.parseLong(ret.split(",")[0]);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("sid", sId);
            data.put("bookName", bookModel.getName());
            data.put("bookAuthor", bookModel.getAuthor());
            data.put("bookDescription", bookModel.getDescription());
            data.put("bookPrice", bookModel.getPrice());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
            response = restApi.exchange(url, HttpMethod.POST, entity, String.class);

            return response;
        }

        catch (Exception e)
        {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> getBookDetails(long bId) {
        ResponseEntity<?> response = null;

        String ret = sellerRepo.findMyUserIdandRole(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        long sId = Long.parseLong(ret.split(",")[0]);

        if (checkIfBookExistsInShop(bId, sId)) {
            String url = "http://book-service/backend/books/protected/getbookdetails/" + bId;
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
                response = restApi.exchange(url, HttpMethod.GET, entity, String.class);
                return response;

            } catch (Exception e) {
                response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }

        } else {
            response = new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
            return response;
        }
    }

    public ResponseEntity<?> getBooksOfShop() {
        ResponseEntity<?> response = null;

        String ret = sellerRepo.findMyUserIdandRole(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        long sId = Long.parseLong(ret.split(",")[0]);

        String url = "http://book-service/backend/books/protected/getbooksbyshop/" + sId;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
            response = restApi.exchange(url, HttpMethod.GET, entity, String.class);
            return response;

        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }

    }

    public ResponseEntity<?> getBooksOfShopByBuyer(long sId) {
        ResponseEntity<?> response = null;

        String url = "http://book-service/backend/books/protected/getbooksbyshop/" + sId;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
            response = restApi.exchange(url, HttpMethod.GET, entity, String.class);
            return response;

        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }

    }

    public ResponseEntity<?> deleteBook(long bId) {
        ResponseEntity<?> response = null;

        String ret = sellerRepo.findMyUserIdandRole(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        long sId = Long.parseLong(ret.split(",")[0]);

        if (checkIfBookExistsInShop(bId, sId)) {
            String url = "http://book-service/backend/books/protected/deletebook/" + bId;
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
                response = restApi.exchange(url, HttpMethod.DELETE, entity, String.class);
                return response;

            } catch (Exception e) {
                response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }

        } else {
            response = new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
            return response;
        }
    }

    public ResponseEntity<?> updateBook(long bId, SellerBookModel bookModel) {
        ResponseEntity<?> response = null;

        String ret = sellerRepo.findMyUserIdandRole(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        long sId = Long.parseLong(ret.split(",")[0]);

        if (checkIfBookExistsInShop(bId, sId)) {
            String url = "http://book-service/backend/books/protected/updatebook/" + bId;
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("bookName", bookModel.getName());
                data.put("bookAuthor", bookModel.getAuthor());
                data.put("bookDescription", bookModel.getDescription());
                data.put("bookPrice", bookModel.getPrice());

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
                response = restApi.exchange(url, HttpMethod.PUT, entity, String.class);

                return response;

            } catch (Exception e) {
                response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }

        } else {
            response = new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
            return response;
        }
    }
}
