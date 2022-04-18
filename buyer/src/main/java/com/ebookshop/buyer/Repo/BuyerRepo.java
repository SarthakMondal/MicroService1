package com.ebookshop.buyer.Repo;

import com.ebookshop.buyer.Model.BuyerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepo extends JpaRepository<BuyerModel, Long>
{
    Optional<BuyerModel> findByEmailId(String emailId);

    @Query(value="SELECT `buyer_id`, `buyer_role` FROM `buyer_data` WHERE `buyer_email` = :emailId", nativeQuery=true)
    String findMyUserIdandRole(String emailId);
}
