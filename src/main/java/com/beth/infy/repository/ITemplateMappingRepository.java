package com.beth.infy.repository;

import com.beth.infy.model.ClientOrm;
import com.beth.infy.model.TemplateMappingOrm;
import com.beth.infy.model.TemplateOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateMappingRepository extends JpaRepository<TemplateMappingOrm, Long> {
    TemplateMappingOrm findTemplateMappingOrmByTemplate(TemplateOrm template);

}
