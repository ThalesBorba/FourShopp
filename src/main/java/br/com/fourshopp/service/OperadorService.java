package br.com.fourshopp.service;

import br.com.fourshopp.entities.Operador;
import br.com.fourshopp.repository.OperadorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorService {

    @Autowired
    private OperadorRespository operadorRespository;


    private Operador create(Operador operador){
        return operadorRespository.save(operador);
    }

    private Operador findById(Long id){
        return operadorRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    private List<Operador> listAll(){
        return operadorRespository.findAll();
    }

    private void remove(Long id){
        operadorRespository.deleteById(id);
    }

    private Operador update(Operador operador, Long id){
        Operador found = findById(id);
        found.setCargo(operador.getCargo());
        found.setSalario(operador.getSalario());
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return operadorRespository.save(found);
    }
}
