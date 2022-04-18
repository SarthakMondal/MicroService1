package com.ebookshop.seller.Controller;

import com.ebookshop.seller.Model.SellerLoginModel;
import com.ebookshop.seller.Model.SellerModel;
import com.ebookshop.seller.Service.SellerBookService;
import com.ebookshop.seller.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/backend/seller/public")
public class SellerPublicApi
{
    @Autowired
    SellerService sellerService;

    @Autowired
    SellerBookService sellerBookService;

    @PostMapping(path = "/registershop")
    public ResponseEntity<?> registerShop(@RequestBody SellerModel sellerModel)
    {
        return this.sellerService.registerShop(sellerModel);
    }

    @PostMapping(path = "/loginshop")
    public ResponseEntity<?> loginShop(@RequestBody SellerLoginModel sellerLoginModel)
    {
        return this.sellerService.loginShop(sellerLoginModel);
    }

    @GetMapping(path = "/getallshops")
    public ResponseEntity<?> getAllShops()
    {
        return this.sellerService.getShopList();
    }

    @GetMapping(path = "/getbooksofshop/{sId}")
    public ResponseEntity<?> getBooksOfShop(@PathVariable String sId)
    {
        return this.sellerBookService.getBooksOfShopByBuyer(Long.parseLong(sId));
    }

}
