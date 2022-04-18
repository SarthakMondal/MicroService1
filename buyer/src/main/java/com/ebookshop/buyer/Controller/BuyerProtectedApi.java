package com.ebookshop.buyer.Controller;

import com.ebookshop.buyer.Service.BuyerBookService;
import com.ebookshop.buyer.Service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/backend/buyer/protected")
public class BuyerProtectedApi
{
    @Autowired
    BuyerService buyerService;

    @Autowired
    BuyerBookService buyerBookService;

    @GetMapping(path = "/buyerprofile")
    public ResponseEntity<?> viewShopProfile()
    {
        return this.buyerService.viewBuyerProfile();
    }


    @GetMapping(path = "/buyergetbookswithshopdata")
    public ResponseEntity<?> getAllBooksWithShopDataFromBuyer()
    {
        return this.buyerBookService.getAllBooksWithShopDataFromBuyer();
    }

    @GetMapping(path = "/buyergetallshops")
    public ResponseEntity<?> getAllshops()
    {
        return this.buyerBookService.getAllShops();
    }

    @GetMapping(path = "/buyergetbookbyshop/{sId}")
    public ResponseEntity<?> getAllshops(@PathVariable String sId)
    {
        return this.buyerBookService.getBooksbyShop(sId);
    }
}
