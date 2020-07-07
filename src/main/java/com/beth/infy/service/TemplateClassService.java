package com.beth.infy.service;

import com.beth.infy.model.ClientOrm;
import com.beth.infy.model.TemplateClassOrm;
import com.beth.infy.model.TemplateOrm;
import com.beth.infy.repository.ITemplateClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateClassService {

    @Autowired
    private ITemplateClassRepository templateClassRepository;

    public TemplateClassOrm getTemplateClassUsing(TemplateOrm template, ClientOrm client) {
       return templateClassRepository.findTemplateClassOrmByTemplateAndClient(template, client);
    }
}
