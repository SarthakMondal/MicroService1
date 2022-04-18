package com.ebookshop.buyer.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name = "buyer_data")
@Getter
@Setter
public class BuyerModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id", nullable = false)
    private Long id;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_email")
    private String emailId;

    @Column(name = "buyer_address")
    private String buyerAddress;

    @Column(name = "buyer_age")
    private int buyerAge;

    @Column(name = "buyer_password")
    private String password;

    @Column(name = "buyer_role")
    private String role = "ROLE_BUYER";


}
