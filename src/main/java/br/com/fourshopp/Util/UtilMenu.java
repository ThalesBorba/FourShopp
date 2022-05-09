package br.com.fourshopp.Util;

import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.entities.Endereco;
import br.com.fourshopp.service.ClienteService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class UtilMenu {



    public static Cliente menuCadastroCliente(Scanner scanner){

        System.out.println("Insira seu nome: ");
        String nome = scanner.next();

        System.out.println("Insira seu email: ");
        String email = scanner.next();

        System.out.println("Insira seu celular: ");
        String celular = scanner.next();

        System.out.println("Insira sua password: ");
        String password = scanner.next();

        System.out.println("Insira seu cpf: ");
        String cpf = scanner.next();

        System.out.println("Insira sua rua: ");
        String rua = scanner.next();

        System.out.println("Insira seu cidade: ");
        String cidade = scanner.next();

        System.out.println("Insira seu bairro: ");
        String bairro = scanner.next();

        System.out.println("Insira seu numero: ");
        int numero = scanner.nextInt();

        System.out.println("Insira sua data de nascimento (dd/MM/yyyy): ");
        String dataNascimento = scanner.next();

        Endereco endereco = new Endereco(rua, cidade, bairro, numero);
        Cliente cliente = new Cliente(nome, email, celular, password, cpf, endereco, new Date());

        return cliente;

    }

    public static int menuSetor(Scanner scanner) {
        System.out.println("Digite a opção desejada: " +
                "\n1- MERCEARIA \n2- BAZAR \n3- ELETRÔNICOS");
        int opcao = scanner.nextInt();
        return opcao;
    }
}
