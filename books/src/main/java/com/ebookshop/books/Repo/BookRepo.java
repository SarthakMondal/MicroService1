package com.ebookshop.books.Repo;

import com.ebookshop.books.Model.BookModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<BookModel, Long>
{
    @Query(value = "SELECT * FROM `books_data` WHERE `shop_id` = :sId ", nativeQuery = true)
    List<BookModel> getBooksByShop(long sId);
}
