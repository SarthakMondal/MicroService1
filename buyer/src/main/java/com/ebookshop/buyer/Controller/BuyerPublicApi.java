package com.ebookshop.buyer.Controller;

import com.ebookshop.buyer.Model.BuyerLoginModel;
import com.ebookshop.buyer.Model.BuyerModel;
import com.ebookshop.buyer.Service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/backend/buyer/public")
public class BuyerPublicApi
{
    @Autowired
    BuyerService buyerService;

    @PostMapping(path = "/registerbuyer")
    public ResponseEntity<?> registerShop(@RequestBody BuyerModel buyerModel)
    {
        return this.buyerService.signupBuyer(buyerModel);
    }

    @PostMapping(path = "/loginbuyer")
    public ResponseEntity<?> loginShop(@RequestBody BuyerLoginModel buyerLoginModel)
    {
        return this.buyerService.loginBuyer(buyerLoginModel);
    }
}
