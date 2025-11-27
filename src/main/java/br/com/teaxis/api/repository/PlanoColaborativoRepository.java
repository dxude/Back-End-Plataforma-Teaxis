package br.com.teaxis.api.repository;

import br.com.teaxis.api.model.PlanoColaborativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoColaborativoRepository extends JpaRepository<PlanoColaborativo, Long> {
   
}