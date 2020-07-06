package com.beth.infy.service;

import com.beth.infy.domain.TemplateMappingDto;
import com.beth.infy.model.TemplateMappingOrm;
import com.beth.infy.model.TemplateOrm;
import com.beth.infy.repository.ITemplateMappingRepository;
import com.beth.infy.repository.ITemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateMappingService {

    @Autowired
    private ITemplateMappingRepository templateMappingRepository;

    @Autowired
    private ITemplateRepository templateRepository;

    public TemplateMappingOrm getTemplateMappingUsing(TemplateOrm templateOrm) {
       return templateMappingRepository.findTemplateMappingOrmByTemplate(templateOrm);
    }

    public TemplateMappingOrm save(TemplateMappingDto mappingDto) {
        TemplateMappingOrm orm = new TemplateMappingOrm();
        TemplateOrm templateOrm = templateRepository.findTemplateOrmByTemplateId(mappingDto.getTemplateId());

        orm.setTemplate(templateOrm);
        orm.setMappingData(mappingDto.getMappingData());
        orm.setActive(mappingDto.getActive());
        orm.setCreatedBy(mappingDto.getCrtdBy());
        orm.setLastModifiedBy(mappingDto.getLastMdfdBy());
        orm.setCreatedTimestamp(mappingDto.getCrtdTs());
        orm.setLastModifiedDate(mappingDto.getLastMdfdTs());
        return templateMappingRepository.save(orm);
    }
}
