package br.com.fourshopp.Util;

import br.com.fourshopp.entities.Setor;
import br.com.fourshopp.repository.OperadorRespository;
import br.com.fourshopp.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
                    case CPF -> System.err.println("Cpf inválido!");
                    case EMAIL -> System.err.println("Email deve receber somente letras minúsculas!");
                    case CELLPHONE -> System.err.println("Somente número com DDD!");
                    case DATE -> System.err.println("Data deve seguir o formato: 01/01/2022");
                    case PASSWORD -> System.err.println("Senha deve ter pelo menos 8 caracteres, uma letra maiúscula" +
                            ", uma minúscula, um número e um caracter especial");
                }

            }
        }
        return inputToverify;
    }

    public static String regexValidationCpf(Scanner scanner, PessoaRepository pessoaRepository, ValidationEnum validationEnum) {
        String inputToverify;
        while (true) {
            inputToverify = scanner.next();
            if (!validationEnum.getKey().matcher(inputToverify).matches()) {
                System.err.println("Cpf inválido!");
            }else if (!assertCpfIsNew(inputToverify, pessoaRepository)) {
                System.err.println("Cpf já cadastrado!");
            } else {
                break;
            }
        }
        return inputToverify;
    }

    public static String validationCpfToFire(Scanner scanner, OperadorRespository operadorRespository,
                                             ValidationEnum validationEnum) {
        String inputToverify;
        while (true) {
            inputToverify = scanner.next();
            if (!validationEnum.getKey().matcher(inputToverify).matches()) {
                System.err.println("Cpf inválido!");
            }else if (operadorRespository.findByCpf(inputToverify) == null) {
                System.err.println("Não há registro de operador com esse cpf!");
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
                System.err.println("Digite um número!");
            }
            }
    }

    public static Long longNumberFormatValidation(Scanner scanner) {
        while (true) {
            String inputToverify = scanner.next();
            try {
                return Long.parseLong(inputToverify);
            } catch (NumberFormatException e) {
                System.err.println("Digite um número!");
            }
        }
    }

    public static Boolean assertCpfIsNew(String cpf, PessoaRepository pessoaRepository) {
        return pessoaRepository.findByCpf(cpf) == null;
    }

    public static Date assertExpiringDateIsValid(Scanner scanner) throws ParseException {
        Date data = null;
        while (true) {
            String dataDeVencimento = scanner.next();
            if (ValidationEnum.DATE.getKey().matcher(dataDeVencimento).matches()) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data = formato.parse(dataDeVencimento);
                if (data.before(new Date())) {
                    System.err.println("Data de validade expirada!");
                } else {
                    break;
                }
            } else {
                System.err.println("Data deve seguir o formato: 01/01/2022");
            }
        }
        return data;
    }

    public static Setor convertDepartment(Scanner scanner) {
        Setor setor;
        while (true) {
            String department = scanner.next();
            try {
                Integer departmentNumber = Integer.parseInt(department);
                switch (departmentNumber) {
                    case 1 -> setor = Setor.MERCEARIA;
                    case 2 -> setor = Setor.BAZAR;
                    case 3 -> setor = Setor.ELETRONICOS;
                    default -> throw new IllegalArgumentException();
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Digite um número");
            } catch (IllegalArgumentException e) {
                System.err.println("Opção inválida!");
            }
        }

    return setor;
    }
}

