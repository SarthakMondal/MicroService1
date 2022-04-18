package com.ebookshop.seller.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name = "shop_data")
@Getter
@Setter
public class SellerModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id", nullable = false)
    private Long id;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shop_email")
    private String emailId;

    @Column(name = "shop_address")
    private String shopAddress;

    @Column(name = "shop_resno")
    private String registrationNo;

    @Column(name = "shop_password")
    private String password;

    @Column(name = "shop_role")
    private String role = "ROLE_SELLER";

}
