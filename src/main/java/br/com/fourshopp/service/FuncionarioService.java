package br.com.fourshopp.service;

import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private Funcionario create(Funcionario operador){
        return funcionarioRepository.save(operador);
    }

    private Funcionario findById(Long id){
        return funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    private List<Funcionario> listAll(){
        return funcionarioRepository.findAll();
    }

    private void remove(Long id){
        funcionarioRepository.deleteById(id);
    }

    private Funcionario update(Funcionario operador, Long id){
        Funcionario found = findById(id);
        found.setCargo(operador.getCargo());
        found.setSalario(operador.getSalario());
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return funcionarioRepository.save(found);
    }
}
