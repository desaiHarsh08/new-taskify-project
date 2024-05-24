package com.taskify.repositories;

import com.taskify.models.RefreshTokenModel;
import com.taskify.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Integer> {

    RefreshTokenModel findByUserModel(UserModel userModel);
    RefreshTokenModel findByRefreshToken(String refreshToken);

}
