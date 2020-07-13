package com.beth.infy.repository;

import com.beth.infy.model.Mt202Orm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMt202epository extends JpaRepository<Mt202Orm, Long> {
    List<Mt202Orm> findAllByActive(int i);
}
