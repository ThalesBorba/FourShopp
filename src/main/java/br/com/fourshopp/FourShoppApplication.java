package br.com.fourshopp;

import br.com.fourshopp.Util.UtilMenu;
import br.com.fourshopp.Util.Validation;
import br.com.fourshopp.entities.*;
import br.com.fourshopp.repository.ProdutoRepository;
import br.com.fourshopp.service.ClienteService;
import br.com.fourshopp.service.FuncionarioService;
import br.com.fourshopp.service.OperadorService;
import br.com.fourshopp.service.ProdutoService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.fourshopp.Util.UtilMenu.*;

@SpringBootApplication
public class FourShoppApplication implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private OperadorService operadorService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FuncionarioService funcionarioService;


    private Cliente cliente;

    public static void main(String[] args) {
        SpringApplication.run(FourShoppApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {

        System.out.println("====== BEM-VINDO AO FOURSHOPP ======");
        System.out.println("1- Sou cliente \n2- Área do ADM \n3- Seja um Cliente \n4- Login funcionário \n5- Encerrar ");
        int opcao = Validation.numberFormatValidation(scanner).intValue();
        menuInicial(opcao);
    }

    public void menuInicial(int opcao) throws CloneNotSupportedException, IOException, ParseException {
        if(opcao == 1){
            System.out.println("Insira seu cpf: ");
            String cpf = scanner.next();
            System.out.println("Insira sua senha: ");
            String password = scanner.next();
            //aceita senha errada
            this.cliente = clienteService.loadByEmailAndPassword(cpf, password).orElseThrow(() -> new ObjectNotFoundException(1L,"Cliente"));
            if(cliente == null){
                System.err.println("Usuario não encontrado !");
                menuInicial(4);
            }

            int contador = 1;
            while (contador == 1) {
                System.out.println("Escolha o setor: ");
                int setor = menuSetor(scanner);


                List<Produto> collect = produtoService.listaProdutosPorSetor(setor).stream().filter(x -> x.getSetor() == setor).collect(Collectors.toList());
                collect.forEach(produto -> {
                    System.out.println(produto.getId()+"- "+produto.getNome()+" Preço: "+produto.getPreco()+" Estoque - "+produto.getQuantidade());
                });

                System.out.println("Informe o número do produto desejado: ");
                Long produto = Validation.longNumberFormatValidation(scanner);

                System.out.println("Escolha a quantidade");
                int quantidade = Validation.numberFormatValidation(scanner).intValue();

                // Atualiza estoque
                Produto foundById = produtoService.findById(produto);
                produtoService.diminuirEstoque(quantidade, foundById);

                Produto clone = foundById.clone();
                System.out.println(clone.toString());
                clone.getCalculaValor(quantidade, clone);
                cliente.getProdutoList().add(clone);
                System.out.println("Deseja outro produto S/N ?");
                String escolha  = scanner.next();

                if(!escolha.equalsIgnoreCase("S")) {
                    contador = 2;
                    gerarCupomFiscal(cliente);
                    System.out.println("Gerando nota fiscal...");
                    System.err.println("Fechando a aplicação...");
                }
            }
        }

        if(opcao == 2){
            System.out.println("INTRANET FOURSHOPP....");

            System.out.println("Insira as credenciais do usuário administrador: ");

            System.out.println("Insira seu cpf: ");
            String cpf = scanner.next();

            System.out.println("Insira sua password: ");
            String password = scanner.next();

            Optional<Funcionario> admnistrador = this.funcionarioService.loadByEmailAndPassword(cpf, password);

            if(admnistrador.get().getCargo() != Cargo.ADMINISTRADOR){
                System.out.println("Administrador nao encontrado");
                menuInicial(2);
            }else {
                System.out.println("1- Cadastrar funcionários \n2- Cadastrar Operador");
                int escolhaAdm = Validation.numberFormatValidation(scanner).intValue();
                if(escolhaAdm == 1){
                    cadastrarFuncionario(scanner);
                    System.out.println("Funcionário cadastrado com sucesso");
                }else if (escolhaAdm == 2){
                    UtilMenu.menuCadastrarOperador(scanner);
                    System.out.println("Operador cadastrado com sucesso");


                }else
                    System.out.println("Opção inválida");

            }

        }

        if(opcao == 3) {
            Cliente cliente = menuCadastroCliente(scanner);
            this.clienteService.create(cliente);
            System.out.println("Bem-vindo, " + cliente.getNome());
            menuInicial(1);
        }

        if(opcao == 4){
            System.out.println("Área do funcionário");

            System.out.println("1- Chefe  \n2- Operador ");
            int escolhaCargo = Validation.numberFormatValidation(scanner).intValue();

            System.out.println("Insira seu cpf: ");
            String cpf = scanner.next();

            System.out.println("Insira sua password: ");
            String password = scanner.next();

            if (escolhaCargo == 1){
                this.funcionarioService.loadByEmailAndPassword(cpf,password);
                System.out.println("Cadastrar produto");
                System.out.println("Cadastrar operadores");
            }else{
                Optional<Operador> operador = this.operadorService.loadByEmailAndPassword(cpf, password);
            }
        }
    }


}
