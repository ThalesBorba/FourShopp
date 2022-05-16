package br.com.fourshopp.service;

import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.repository.FuncionarioRepository;
import br.com.fourshopp.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario create(Funcionario operador){
        return funcionarioRepository.save(operador);
    }

    public Funcionario findById(Long id){
        return (Funcionario) funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<Funcionario> listAll(){
        return funcionarioRepository.findAll();
    }

    public void remove(Long id){
        funcionarioRepository.deleteById(id);
    }

    public Funcionario update(Funcionario operador, Long id){
        Funcionario found = findById(id);
        found.setCargo(operador.getCargo());
        found.setSalario(operador.getSalario());
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return funcionarioRepository.save(found);
    }

    public Optional<Funcionario> loadByEmailAndPassword(String cpf, String password){
        return funcionarioRepository.findByCpfAndPassword(cpf,password);
    }
}
