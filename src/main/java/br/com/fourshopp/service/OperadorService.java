package br.com.fourshopp.service;

import br.com.fourshopp.entities.Operador;
import br.com.fourshopp.repository.OperadorRespository;
import br.com.fourshopp.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperadorService {

    @Autowired
    private OperadorRespository operadorRespository;

    public Operador create(Operador operador){
        return operadorRespository.save(operador);
    }

    public Operador findById(Long id){
        return operadorRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<Operador> listAll(){
        return operadorRespository.findAll();
    }

    public void remove(Long id){
        operadorRespository.deleteById(id);
    }

    public Operador update(Operador operador, Long id){
        Operador found = findById(id);
        found.setCargo(operador.getCargo());
        found.setSalario(operador.getSalario());
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return operadorRespository.save(found);
    }

    public Optional<Operador> loadByEmailAndPassword(String cpf, String password) {
        return operadorRespository.findByCpfAndPassword(cpf,password);
    }
}
