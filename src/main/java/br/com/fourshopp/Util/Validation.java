package br.com.fourshopp.Util;

import br.com.fourshopp.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class Validation {

    @Autowired
    PessoaRepository pessoaRepository;

    public static String regexValidation(Scanner scanner, ValidationEnum validationEnum) {
        String inputToverify;
        while (true) {
            inputToverify = scanner.next();
            if (validationEnum.getKey().matcher(inputToverify).matches()) {
                break;
            } else {
                switch (validationEnum) {
                    case CPF -> System.out.println("Cpf inválido!");
                    case EMAIL -> System.out.println("Email deve receber somente letras minúsculas!");
                    case CELLPHONE -> System.out.println("Somente número com DDD!");
                    case DATE -> System.out.println("Data deve seguir o formato: 01/01/2022");
                    case PASSWORD -> System.out.println("Senha deve ter pelo menos 8 caracteres, uma letra maiúscula" +
                            ", uma minúscula, um número e um caracter especial");
                }

            }
        }
        return inputToverify;
    }

    public static String regexValidation(Scanner scanner, PessoaRepository pessoaRepository, ValidationEnum validationEnum) {
        String inputToverify;
        while (true) {
            inputToverify = scanner.next();
            if (!validationEnum.getKey().matcher(inputToverify).matches()) {
                switch (validationEnum) {
                    case CPF -> System.out.println("Cpf inválido!");
                    case EMAIL -> System.out.println("Email deve receber somente letras minúsculas!");
                    case CELLPHONE -> System.out.println("Somente número com DDD!");
                    case DATE -> System.out.println("Data deve seguir o formato: 01/01/2022");
                    case PASSWORD -> System.out.println("Senha deve ter pelo menos 8 caracteres, uma letra maiúscula" +
                            ", uma minúscula, um número e um caracter especial");
                }
            }else if (!assertCpfIsNew(inputToverify, pessoaRepository)) {
                System.out.println("Cpf já cadastrado!");
            } else {
                break;
            }
        }
        return inputToverify;

    }

    public static Double numberFormatValidation(Scanner scanner) {
        while (true) {
            String inputToverify = scanner.next();
            try {
                return Double.parseDouble(inputToverify);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número!");
            }
            }
    }

    public static Long longNumberFormatValidation(Scanner scanner) {
        while (true) {
            String inputToverify = scanner.next();
            try {
                return Long.parseLong(inputToverify);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número!");
            }
        }
    }

    public static Boolean assertCpfIsNew(String cpf, PessoaRepository pessoaRepository) {
        return pessoaRepository.findByCpf(cpf) == null;
    }
    }

