package br.com.fourshopp.service;

import br.com.fourshopp.entities.Produto;
import br.com.fourshopp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto create(Produto operador){
        return produtoRepository.save(operador);
    }

    private Produto findById(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    private List<Produto> listAll(){
        return produtoRepository.findAll();
    }

    private void remove(Long id){
        produtoRepository.deleteById(id);
    }

    private Produto update(Produto produto, Long id){
        Produto found = findById(id);
        found.setDataVencimento(produto.getDataVencimento());
        found.setNome(produto.getNome());
        found.setQuantidade(produto.getQuantidade());
        found.setPreco(produto.getPreco());
        return produtoRepository.save(found);
    }
}
