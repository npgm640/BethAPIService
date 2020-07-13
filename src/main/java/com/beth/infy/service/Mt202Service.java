package com.beth.infy.service;

import com.beth.infy.model.Mt202Orm;
import com.beth.infy.repository.IMt202epository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mt202Service {

    @Autowired
    private IMt202epository mt202Repository;

    public List<Mt202Orm> getAllActiveMt202Items(int i) {
       return mt202Repository.findAllByActive(i);
    }
}
