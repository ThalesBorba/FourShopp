package br.com.fourshopp.service;

import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    private Cliente findById(Long id){
        return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    private List<Cliente> listAll(){
        return clienteRepository.findAll();
    }

    private void remove(Long id){
        clienteRepository.deleteById(id);
    }

    private Cliente update(Cliente operador, Long id){
        Cliente found = findById(id);
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return clienteRepository.save(found);
    }

    public Optional<Cliente> loadByEmailAndPassword(String cpf, String password){
        return clienteRepository.findByCpfAndPassword(cpf,password);
    }
}
