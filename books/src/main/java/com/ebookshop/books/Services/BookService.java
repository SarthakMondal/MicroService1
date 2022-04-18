package com.ebookshop.books.Services;

import java.util.*;

import com.ebookshop.books.Exception.BookNotFoundException;
import com.ebookshop.books.Model.BookModel;
import com.ebookshop.books.Model.BookShopModel;
import com.ebookshop.books.Repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    RestTemplate restApi;

    public ResponseEntity<?> addBook(BookModel bModel) {

        ResponseEntity<?> response = null;

        try {
            bookRepo.save(bModel);
            response = new ResponseEntity<>("OK", HttpStatus.OK);
            return response;
        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }

    }

    public ResponseEntity<?> updateBook(long bId, BookModel obModel) {

        ResponseEntity<?> response = null;

        try {
            Optional<BookModel> nbModelOp = bookRepo.findById(bId);

            if (nbModelOp.isEmpty()) {
                throw new BookNotFoundException("Book Not Found");
            } else {
                BookModel nbModel = nbModelOp.get();
                nbModel.setBookName(obModel.getBookName());
                nbModel.setBookAuthor(obModel.getBookAuthor());
                nbModel.setBookPrice(obModel.getBookPrice());
                nbModel.setBookDescription(obModel.getBookDescription());

                bookRepo.save(nbModel);
                response = new ResponseEntity<>("OK", HttpStatus.OK);
                return response;
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> removeBook(long bId) {
        ResponseEntity<?> response = null;

        try {

            Optional<BookModel> nbModelOp = bookRepo.findById(bId);

            if(nbModelOp.isEmpty())
            {
                throw new BookNotFoundException("Book Not Found");
            }

            else {
                bookRepo.deleteById(bId);
                response = new ResponseEntity<>("OK", HttpStatus.OK);
                return response;
            }
        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> getBookDetails(long bId) {
        ResponseEntity<?> response = null;
        try {

            Optional<BookModel> nbModelOp = bookRepo.findById(bId);

            if(nbModelOp.isEmpty())
            {
                throw new BookNotFoundException("Book Not Found");
            }

            else {
                BookModel book = nbModelOp.get();
                response = new ResponseEntity<>(book, HttpStatus.OK);
                return response;
            }

        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> getBookList() {
        ResponseEntity<?> response = null;
        try {

            List<BookModel> bookModelList = bookRepo.findAll();
            response = new ResponseEntity<>(bookModelList, HttpStatus.OK);
            return response;
        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> getBookListForShop(long sId) {
        ResponseEntity<?> response = null;
        try {

            List<BookModel> bookModelList = bookRepo.getBooksByShop(sId);
            response = new ResponseEntity<>(bookModelList, HttpStatus.OK);
            return response;
        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> checkIfBookExistsInShop(long bId, long sId) {
        ResponseEntity<?> response = null;
        try {

            Optional<BookModel> nbModelOp = bookRepo.findById(bId);

            if (nbModelOp.isEmpty()) {
                throw new BookNotFoundException("Book Not Found");

            }
            else {
                if( nbModelOp.get().getSid() == sId)
                {
                    response = new ResponseEntity<>(true, HttpStatus.OK);
                }

                else
                {
                    response = new ResponseEntity<>(false, HttpStatus.OK);
                }

                return response;
            }
        }

        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    public ResponseEntity<?> getAllBooksWithShopData()
    {
        ResponseEntity<?> response = null;
        ResponseEntity<?> shopresponse = null;
        String url = "http://seller-service/backend/seller/public/getallshops";

        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
            shopresponse = restApi.exchange(url, HttpMethod.GET, entity, Object.class);
            List<LinkedHashMap<String, ?>> shoplist = (List<LinkedHashMap<String, ?>>) shopresponse.getBody();
            List<BookModel> booklist = bookRepo.findAll();
            List<BookShopModel> bookShopModelList = new ArrayList<BookShopModel>();

            for(BookModel bookModel: booklist)
            {
                long id1 = bookModel.getSid();

                for(LinkedHashMap<String, ?> shopModel: shoplist)
                {
                    long id2 = ((Number)shopModel.get("sid")).longValue();

                    if(id2==id1)
                    {
                        BookShopModel bookShopModel = new BookShopModel();
                        bookShopModel.setBookName(bookModel.getBookName());
                        bookShopModel.setBookAuthor(bookModel.getBookAuthor());
                        bookShopModel.setBookPrice(bookModel.getBookPrice());
                        bookShopModel.setBookDescription(bookModel.getBookDescription());
                        bookShopModel.setShopName((String) shopModel.get("shopName"));
                        bookShopModel.setShopRegNo((String) shopModel.get("registrationNo"));
                        bookShopModel.setShopAddress((String) shopModel.get("shopAddress"));

                        bookShopModelList.add(bookShopModel);
                        break;
                    }
                }
            }

            response = new ResponseEntity<>(bookShopModelList, HttpStatus.OK);
            return response;
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }

    }

}
