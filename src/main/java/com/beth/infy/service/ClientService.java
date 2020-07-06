package com.beth.infy.service;

import com.beth.infy.model.ClientOrm;
import com.beth.infy.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private IClientRepository clientRepository;

    public ClientOrm getClient(long clientId) {
        return clientRepository.findByClientId(clientId);
    }

}
