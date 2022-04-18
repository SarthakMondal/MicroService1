package com.ebookshop.seller.Repo;

import com.ebookshop.seller.Model.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepo extends JpaRepository<SellerModel, Long>
{
    Optional<SellerModel> findByEmailId(String emailId);

    @Query(value="SELECT `shop_id`, `shop_role` FROM `shop_data` WHERE `shop_email` = :emailId", nativeQuery=true)
    String findMyUserIdandRole(String emailId);
}
