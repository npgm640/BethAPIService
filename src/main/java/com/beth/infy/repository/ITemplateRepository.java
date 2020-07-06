package com.beth.infy.repository;

import com.beth.infy.model.ClientOrm;
import com.beth.infy.model.TemplateOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateRepository extends JpaRepository<TemplateOrm, Long> {
    TemplateOrm findTemplateOrmByClientAndTemplateNameAndTemplateType(ClientOrm client, String templateName, String templateType);
    TemplateOrm findTemplateOrmByTemplateId(long templateId);
}
