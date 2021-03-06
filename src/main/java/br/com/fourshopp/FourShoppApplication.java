package br.com.fourshopp;

import br.com.fourshopp.Util.UtilMenu;
import br.com.fourshopp.Util.Validation;
import br.com.fourshopp.Util.ValidationEnum;
import br.com.fourshopp.entities.*;
import br.com.fourshopp.repository.OperadorRespository;
import br.com.fourshopp.repository.PessoaRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
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

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private OperadorRespository operadorRespository;


    private Cliente cliente;

    public static void main(String[] args) {
        SpringApplication.run(FourShoppApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {


/*
        Funcionario administrador = new Funcionario("Thales Borba", "thales@fourshopp.com", "2199887766",
                "Abc123**", "111.111.111-33", new Endereco("a", "b", "c", 1),
                new SimpleDateFormat("dd/MM/yyyy").parse("22/06/2022"), Cargo.ADMINISTRADOR, null,
                50000.00, new ArrayList<>(), new ArrayList<>());

        pessoaRepository.save(administrador);
*/


        System.out.println("====== BEM-VINDO AO FOURSHOPP ======");
        System.out.println("1- Sou cliente \n2- ??rea do ADM \n3- Seja um Cliente \n4- Login funcion??rio \n5- Encerrar ");
        int opcao = Validation.numberFormatValidation(scanner).intValue();
        menuInicial(opcao);
    }

    public void menuInicial(int opcao) throws CloneNotSupportedException, IOException, ParseException {
        switch (opcao) {
            case 1 -> {
                while (true) {
                    try {
                        System.out.println("Insira seu cpf: ");
                        String cpf = Validation.regexValidation(scanner, ValidationEnum.CPF);
                        System.out.println("Insira sua senha: ");
                        String password = scanner.next();
                        this.cliente = clienteService.loadByEmailAndPassword(cpf, password).orElseThrow(() -> new
                                ObjectNotFoundException(1L, "Cliente"));
                        break;
                    } catch (ObjectNotFoundException | ClassCastException e) {
                        System.err.println("Usuario n??o encontrado !");
                    }
                }

                int contador = 1;
                while (contador == 1) {
                    System.out.println("Escolha o setor: ");
                    int setor = menuSetor(scanner);


                    List<Produto> collect = produtoService.listaProdutosPorSetor(setor).stream().filter(x -> x.getSetor() == setor).collect(Collectors.toList());
                    collect.forEach(produto -> {
                        System.out.println(produto.getId() + "- " + produto.getNome() + " Pre??o: " + produto.getPreco() + " Estoque - " + produto.getQuantidade());
                    });

                    System.out.println("Informe o n??mero do produto desejado: ");
                    Long produto = Validation.validateProductToSell(scanner, produtoService);

                    Produto foundById = produtoService.findById(produto);

                    System.out.println("Escolha a quantidade");
                    int quantidade = Validation.validateQuantityToSell(scanner, foundById);

                    // Atualiza estoque

                    produtoService.diminuirEstoque(quantidade, foundById);

                    Produto clone = foundById.clone();
                    System.out.println(clone.toString());
                    clone.getCalculaValor(quantidade, clone);
                    cliente.getProdutoList().add(clone);
                    System.out.println("Deseja outro produto S/N ?");
                    String escolha = scanner.next();

                    if (!escolha.equalsIgnoreCase("S")) {
                        contador = 2;
                        gerarCupomFiscal(cliente);
                        System.out.println("Gerando nota fiscal...");
                        System.err.println("Fechando a aplica????o...");
                    }
                }
            }

            case 2 -> {
                System.out.println("INTRANET FOURSHOPP....");

                System.out.println("Insira as credenciais do usu??rio administrador: ");

                System.out.println("Insira seu cpf: ");
                String cpf = Validation.regexValidation(scanner, ValidationEnum.CPF);

                System.out.println("Insira sua password: ");
                String password = scanner.next();
                Optional<Funcionario> admnistrador = this.funcionarioService.loadByEmailAndPassword(cpf, password);


                try {
                    if (admnistrador.get().getCargo() != Cargo.ADMINISTRADOR) {
                        throw new NoSuchElementException();
                    }
                    System.out.println("1- Cadastrar funcion??rios \n2- Cadastrar Operador \n3- Demitir Operador");
                    int escolhaAdm = Validation.numberFormatValidation(scanner).intValue();
                    switch (escolhaAdm) {
                        case 1 -> {
                            Funcionario funcionario = cadastrarFuncionario(scanner, pessoaRepository);
                            this.funcionarioService.create(funcionario);
                            System.out.println("Funcion??rio cadastrado com sucesso");
                        }
                        case 2 -> {
                            Operador operador = UtilMenu.menuCadastrarOperador(scanner, pessoaRepository);
                            this.operadorService.create(operador);
                            System.out.println("Operador cadastrado com sucesso");
                        }
                        case 3 -> {
                            String cpfDoOperador = UtilMenu.menuDemitirOperador(scanner, operadorRespository);
                            funcionarioService.removeOperative(operadorRespository.findByCpf(cpfDoOperador));
                            operadorRespository.deleteByCpf(cpfDoOperador);
                            System.out.println("Operador demitido com sucesso");
                        }
                        default -> System.out.println("Op????o inv??lida");
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("Administrador nao encontrado");
                    menuInicial(2);
                }


            }
            case 3 -> {
                Cliente cliente = menuCadastroCliente(scanner, pessoaRepository);
                this.clienteService.create(cliente);
                System.out.println("Bem-vindo, " + cliente.getNome());
                menuInicial(1);
            }

            case 4 -> {
                System.out.println("??rea do funcion??rio");

                System.out.println("1- Chefe  \n2- Operador ");
                int escolhaCargo = Validation.numberFormatValidation(scanner).intValue();

                System.out.println("Insira seu cpf: ");
                String cpf = Validation.regexValidation(scanner, ValidationEnum.CPF);

                System.out.println("Insira sua password: ");
                String password = scanner.next();

                if (escolhaCargo == 1) {
                    try {
                        Funcionario funcionario = this.funcionarioService.loadByEmailAndPassword(cpf, password).
                                orElseThrow(NoSuchElementException::new);
                        if (funcionario.getCargo() == Cargo.ADMINISTRADOR || funcionario.getCargo() == Cargo.GERENTE) {
                            throw new IllegalAccessException();
                        }
                        System.out.println("1 - Cadastrar produto  \n2 - Cadastrar operadores \n3 - Remover Produto " +
                                "\n4 - Alterar produto");
                        int opcaoDoChefe = Validation.numberFormatValidation(scanner).intValue();
                        switch (opcaoDoChefe) {
                            case 1 -> {
                                Produto produto = UtilMenu.menuCadastrarProduto(scanner, funcionario);
                                this.produtoRepository.save(produto);
                                System.out.println("Produto salvo");
                            }
                            case 2 -> {
                                Operador operador = UtilMenu.menuCadastrarOperador(scanner, pessoaRepository);
                                operador.setSetor(funcionario.getSetor());
                                this.operadorService.create(operador);
                                this.funcionarioService.update(operador, funcionario.getCpf());
                                System.out.println("Operador cadastrado com sucesso");
                            }
                            case 3 -> {
                                System.out.println("Digite a id do produto a remover: ");
                                Long id = Validation.validateProductRemoval(scanner, produtoService, funcionario);
                                this.produtoService.remove(id);
                                System.out.println("Produto removido com sucesso!");
                            }
                            case 4 -> {
                                Validation.validateProductUpdate(scanner, funcionario, produtoService);
                            }
                            default -> {
                                System.out.println("Op????o inv??lida!");
                                menuInicial(4);
                            }
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Chefe de se????o n??o encontrado");
                        menuInicial(4);
                    } catch (IllegalAccessException | ClassCastException e) {
                        System.out.println("Somente chefe de se????o pode acessar essa ??rea!");
                        menuInicial(4);
                    }
                } else if (escolhaCargo == 2) {
                    try {
                        Operador operador = this.operadorService.loadByEmailAndPassword(cpf, password).
                                orElseThrow(NoSuchElementException::new);
                    } catch (NoSuchElementException | ClassCastException e) {
                        System.out.println("Operador n??o encontrado");
                        menuInicial(4);
                    }
                } else {
                    System.out.println("Op????o inv??lida!");
                    menuInicial(4);
                }
            }
        }
    }


}
