package com.beth.infy.repository;

import com.beth.infy.model.ClientOrm;
import com.beth.infy.model.TemplateClassOrm;
import com.beth.infy.model.TemplateOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateClassRepository extends JpaRepository<TemplateClassOrm, Long> {
    TemplateClassOrm findTemplateClassOrmByTemplateAndClient(TemplateOrm template, ClientOrm client);
}
