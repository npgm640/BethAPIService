package com.beth.infy.repository;

import com.beth.infy.model.ClientOrm;
import com.beth.infy.model.UserOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<ClientOrm, Long> {
    ClientOrm findByClientId(long clientId);
}
