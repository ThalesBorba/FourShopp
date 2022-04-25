package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperadorRespository extends JpaRepository<Operador, Long> {
}
