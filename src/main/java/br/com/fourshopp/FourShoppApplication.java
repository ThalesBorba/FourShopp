package br.com.fourshopp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class FourShoppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FourShoppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("====== BEM-VINDO AO FOURSHOPP ======");
        System.out.println("1- Logar \n2- Cadastre-se \n3- Encerrar");
        int opcao = scanner.nextInt();
        menuInicial(opcao);
    }

    private void menuInicial(int opcao) {
        if(opcao == 1){
            //fluxo para login
        }else if(opcao == 2){
            //fluxo para cadastramento
        }else if(opcao == 3){
            System.out.println("Obrigado, volte sempre :)");

        }else{
            System.err.println("Opcão inválida");
            menuInicial(3);

        }
    }
}
