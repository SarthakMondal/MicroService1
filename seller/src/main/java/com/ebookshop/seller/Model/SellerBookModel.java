package com.ebookshop.seller.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SellerBookModel
{
    @Transient
    private String name;

    @Transient
    private String author;

    @Transient
    private int price;

    @Transient
    private String description;
}
