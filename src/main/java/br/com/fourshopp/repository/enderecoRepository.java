package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface enderecoRepository extends JpaRepository<Endereco, Long> {
}
