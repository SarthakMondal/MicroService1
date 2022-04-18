package com.ebookshop.seller.Controller;

import com.ebookshop.seller.Model.SellerBookModel;
import com.ebookshop.seller.Service.SellerBookService;
import com.ebookshop.seller.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/backend/seller/protected")
public class SellerProtectedApi
{
    @Autowired
    SellerService sellerService;

    @Autowired
    SellerBookService sellerBookService;

    @GetMapping(path = "/sellerprofile")
    public ResponseEntity<?> viewShopProfile()
    {
        return this.sellerService.viewShopProfile();
    }

    @PostMapping(path = "/addbookfromstore")
    public ResponseEntity<?> addBookFromShop(@RequestBody SellerBookModel sellerBookModel)
    {
        return this.sellerBookService.addNewBook(sellerBookModel);
    }

    @PutMapping(path = "/updatebookfromstore/{bId}")
    public ResponseEntity<?> updateBookFromShop(@PathVariable String bId, @RequestBody SellerBookModel sellerBookModel)
    {
        return this.sellerBookService.updateBook(Long.parseLong(bId), sellerBookModel);
    }

    @GetMapping(path = "/getallbooksfromstore")
    public ResponseEntity<?> getBooksFromStore()
    {
        return this.sellerBookService.getBooksOfShop();
    }

    @GetMapping(path = "/getbookdetailsfromstore/{bId}")
    public ResponseEntity<?> getBookDetailsFromStore(@PathVariable String bId)
    {
        return this.sellerBookService.getBookDetails(Long.parseLong(bId));
    }

    @DeleteMapping(path = "/deletebookfromstore/{bId}")
    public ResponseEntity<?> deleteBookFromStore(@PathVariable String bId)
    {
        return this.sellerBookService.deleteBook(Long.parseLong(bId));
    }



}
