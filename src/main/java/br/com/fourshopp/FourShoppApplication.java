package br.com.fourshopp;

import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.service.ClienteService;
import br.com.fourshopp.service.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;
import static br.com.fourshopp.Util.UtilMenu.menuCadastroCliente;

@SpringBootApplication
public class FourShoppApplication implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private OperadorService operadorService;



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
            Cliente cliente = clienteService.loadByEmailAndPassword(cpf, password);
            System.out.println(cliente.toString());
            // chamar o fluxo que o cliente realiza os add dos itens no carrinho

        }else if(opcao == 2){
            System.out.println("Insira seu cpf: ");
            String cpf = scanner.next();
            System.out.println("Insira sua senha: ");
            String password = scanner.next();
            //chamar o fluxo que lista as ações do funcionario com base no cargo

        }else if(opcao == 3){
            Cliente cliente = menuCadastroCliente();

        }else{
            System.err.println("Opcão inválida");
        }
    }
}
