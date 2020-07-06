package com.beth.infy.repository;

import com.beth.infy.model.UserOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserOrm, Long> {
    UserOrm findByUserName(String userName);
    UserOrm findByUserId(long userId);
}
