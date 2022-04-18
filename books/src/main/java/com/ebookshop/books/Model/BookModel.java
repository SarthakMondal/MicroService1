package com.ebookshop.books.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "books_data")
@Getter
@Setter
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Long id;

    @Column(name = "shop_id")
    private long sid;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_price")
    private int bookPrice;

    @Column(name = "book_description")
    private String bookDescription;

}
