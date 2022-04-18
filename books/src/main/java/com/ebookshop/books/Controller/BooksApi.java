package com.ebookshop.books.Controller;

import com.ebookshop.books.Model.BookModel;
import com.ebookshop.books.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/backend/books/protected")
public class BooksApi
{
    @Autowired
    BookService bookService;

    @PostMapping(path = "/addbook")
    public ResponseEntity<?> addBook(@RequestBody BookModel bModel) {
        return this.bookService.addBook(bModel);
    }

    @PutMapping(path = "/updatebook/{bId}")
    public ResponseEntity<?> updateBook(@PathVariable String bId, @RequestBody BookModel bModel) {
        return this.bookService.updateBook(Long.parseLong(bId), bModel);
    }

    @DeleteMapping(path = "/deletebook/{bId}")
    public ResponseEntity<?> removeBook(@PathVariable String bId){
        return this.bookService.removeBook(Long.parseLong(bId));
    }

    @GetMapping(path = "/getbookdetails/{bId}")
    public ResponseEntity<?> getBookDetails(@PathVariable String bId) {
        return this.bookService.getBookDetails(Long.parseLong(bId));
    }

    @GetMapping(path = "/getbooklist")
    public ResponseEntity<?> getBookList() {
        return this.bookService.getBookList();
    }

    @GetMapping(path = "/getbooksbyshop/{sId}")
    public ResponseEntity<?> getBookListForShop(@PathVariable String sId) {
        return this.bookService.getBookListForShop(Long.parseLong(sId));
    }

    @GetMapping(path = "/checkifbookexistsinshop/{sId}/{bId}")
    public ResponseEntity<?> checkIfBookExistsInShop(@PathVariable String sId, @PathVariable String bId) {
        return this.bookService.checkIfBookExistsInShop(Long.parseLong(bId), Long.parseLong(sId));
    }

    @GetMapping(path = "/getbookswithshopdata")
    public ResponseEntity<?> getBookListForShop() {
        return this.bookService.getAllBooksWithShopData();
    }


}
