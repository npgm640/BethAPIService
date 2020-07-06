package com.beth.infy.service;

import com.beth.infy.model.ClientOrm;
import com.beth.infy.model.TemplateOrm;
import com.beth.infy.repository.ITemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    private ITemplateRepository templateRespository;

    public TemplateOrm getTemplateUsing(ClientOrm client, String tempName, String tempType) {
       return templateRespository.findTemplateOrmByClientAndTemplateNameAndTemplateType(client, tempName, tempType);
    }
}
