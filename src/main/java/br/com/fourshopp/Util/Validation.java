package br.com.fourshopp.Util;

import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.entities.Produto;
import br.com.fourshopp.entities.Setor;
import br.com.fourshopp.repository.OperadorRespository;
import br.com.fourshopp.repository.PessoaRepository;
import br.com.fourshopp.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
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
            if (!validationEnum.getKey().matcher(inputToverify).matches() || !isCPF(inputToverify)) {
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

    public static Long validateProductRemoval(Scanner scanner, ProdutoService produtoService, Funcionario funcionario) {
        while (true) {
            String inputToverify = scanner.next();
            try {
                Long id = Long.parseLong(inputToverify);
                Produto produto = produtoService.findById(id);
                if(funcionario.getSetor().getCd() != produto.getSetor()) {
                    throw new IllegalAccessException();
                }
                return id;
            } catch (NumberFormatException e) {
                System.err.println("Digite um número!");
            } catch (ResourceNotFoundException e) {
                System.err.println("Produto não encontrado!");
            } catch (IllegalAccessException e) {
                //todo testar
                System.out.println("Produto pertence a outro setor, permissão negada!");
            }
        }
    }

    public static void validateProductUpdate(Scanner scanner, Funcionario funcionario, ProdutoService produtoService) {
        Boolean isInvalid = true;
        while (isInvalid) {
            System.out.println("Digite a id do produto a ser alterado: ");
            try {
                Long id = longNumberFormatValidation(scanner);
                Produto produto = produtoService.findById(id);
                if (funcionario.getSetor().getCd() != produto.getSetor()) {
                    throw new IllegalAccessException();}
                    System.out.println("Digite 1 para diminuir estoque, 2 para alterar preço: ");
                    String opcao = scanner.next();
                    switch (opcao) {
                        case "1" -> {
                            System.out.println("Quanto deseja remover? ");
                            Integer quantidade = numberFormatValidation(scanner).intValue();
                            produtoService.diminuirEstoque(quantidade, produto);
                            System.out.println("Removido com sucesso!");
                            isInvalid = false;
                        }
                        case "2" -> {
                            System.out.println("Qual é o novo preço? ");
                            Double preco = numberFormatValidation(scanner);
                            produtoService.alteraPreco(preco, produto);
                            System.out.println("Alterado com sucesso!");
                            isInvalid = false;
                        }
                        default -> System.err.println("Opção inválida!");
                    }
                } catch(ResourceNotFoundException e){
                    System.err.println("Produto não encontrado!");
                } catch(ArithmeticException e){
                    System.err.println("Estoque insuficiente para realizar a operação!");
                } catch(IllegalArgumentException e){
                    System.err.println("Preço não pode ser igual ou menor que 0!");
                } catch(IllegalAccessException e){
                    System.out.println("Produto pertence a outro setor, permissão negada!");
                }
            }
        }

    public static Long validateProductToSell(Scanner scanner, ProdutoService produtoService) {
        while (true) {
            String inputToverify = scanner.next();
            try {
                Long id = Long.parseLong(inputToverify);
                produtoService.findById(id);
                return id;
            } catch (NumberFormatException e) {
                System.err.println("Digite um número!");
            } catch (ResourceNotFoundException e) {
                System.err.println("Produto não encontrado!");
            }
        }
    }

    public static int validateQuantityToSell(Scanner scanner, Produto produto) {
        Integer quantidade;
        while (true) {
            System.out.println("Quantos deseja comprar? ");
            try {
                quantidade = numberFormatValidation(scanner).intValue();
                if (quantidade > produto.getQuantidade()) {
                    throw new ArithmeticException();
                }
                break;
            } catch (ArithmeticException e) {
                System.err.println("Estoque insuficiente!");
                scanner.nextLine();
            }
        }
        return quantidade;
    }

    public static boolean isCPF(String CPF) {

        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {

                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);


            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }
}

