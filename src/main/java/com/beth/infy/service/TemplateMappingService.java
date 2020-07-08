package com.beth.infy.service;

import com.beth.infy.domain.TemplateMappingDto;
import com.beth.infy.model.TemplateMappingOrm;
import com.beth.infy.model.TemplateOrm;
import com.beth.infy.repository.ITemplateMappingRepository;
import com.beth.infy.repository.ITemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        TemplateMappingOrm templateMappingOrm = null;

        TemplateOrm templateOrm = templateRepository.findTemplateOrmByTemplateId(mappingDto.getTemplateId());

        templateMappingOrm = templateMappingRepository.findTemplateMappingOrmByTemplate(templateOrm);

        if (StringUtils.isEmpty(templateMappingOrm)) {
            templateMappingOrm.setTemplate(templateOrm);
            templateMappingOrm.setMappingData(mappingDto.getMappingData());
            templateMappingOrm.setActive(mappingDto.getActive());
            templateMappingOrm.setCreatedBy(mappingDto.getCrtdBy());
            templateMappingOrm.setLastModifiedBy(mappingDto.getLastMdfdBy());
            templateMappingOrm.setCreatedTimestamp(mappingDto.getCrtdTs());
            templateMappingOrm.setLastModifiedDate(mappingDto.getLastMdfdTs());
        } else {
            templateMappingOrm.setMappingData(mappingDto.getMappingData());
            templateMappingOrm.setCreatedBy(mappingDto.getCrtdBy());
            templateMappingOrm.setLastModifiedBy(mappingDto.getLastMdfdBy());
            templateMappingOrm.setCreatedTimestamp(mappingDto.getCrtdTs());
            templateMappingOrm.setLastModifiedDate(mappingDto.getLastMdfdTs());
        }

        return templateMappingRepository.save(templateMappingOrm);
    }
}
