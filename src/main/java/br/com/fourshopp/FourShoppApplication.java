package br.com.fourshopp;

import br.com.fourshopp.Util.UtilMenu;
import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.entities.Produto;
import br.com.fourshopp.service.ClienteService;
import br.com.fourshopp.service.OperadorService;
import br.com.fourshopp.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static br.com.fourshopp.Util.UtilMenu.menuCadastroCliente;
import static br.com.fourshopp.Util.UtilMenu.menuSetor;

@SpringBootApplication
public class FourShoppApplication implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private OperadorService operadorService;

    @Autowired
    private ProdutoService produtoService;



    public static void main(String[] args) {
        SpringApplication.run(FourShoppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        System.out.println("====== BEM-VINDO AO FOURSHOPP ======");
        System.out.println("1- Sou cliente \n2- Sou funcionário \n3- Seja um Cliente \n4- Encerrar");
        int opcao = scanner.nextInt();
        menuInicial(opcao);
    }

    private void menuInicial(int opcao) {
        if(opcao == 1){
            System.out.println("Insira seu cpf: ");
            String cpf = scanner.next();
            System.out.println("Insira sua senha: ");
            String password = scanner.next();
            Optional<Cliente> cliente = clienteService.loadByEmailAndPassword(cpf, password);
            System.out.println(cliente.toString());
            System.out.println("Logado com Sucesso!!");
            int setor = menuSetor(scanner);
            List<Produto> collect = produtoService.listaProdutosPorSetor(setor).stream().filter(x -> x.getSetor() == setor).collect(Collectors.toList());
            collect.forEach(produto -> {
                System.out.println(produto.getId()+"- "+produto.getNome());
            });

            



        }else if(opcao == 2){
            System.out.println("Insira seu cpf: ");
            String cpf = scanner.next();
            System.out.println("Insira sua senha: ");
            String password = scanner.next();
            //chamar o fluxo que lista as ações do funcionario com base no cargo

        }else if(opcao == 3){
            Cliente cliente = menuCadastroCliente(scanner);
            if(cliente != null){
                System.out.println("Usuario cadastrado com sucesso");
                menuInicial(1);
            }

        }else{
            System.err.println("Opcão inválida");
        }
    }
}
