package br.com.fourshopp.Util;

import java.util.Scanner;

public class Validation {

    public static String regexValidation(Scanner scanner, ValidationEnum validationEnum) {
        String inputToverify;
        while (true) {
            inputToverify = scanner.next();
            if (validationEnum.getKey().matcher(inputToverify).matches())
                break;
            else {
                switch (validationEnum) {
                    case CPF -> System.out.println("Cpf inválido!");
                    case EMAIL -> System.out.println("Email em formato inválido!");
                    case CELLPHONE -> System.out.println("Somente número com DDD!");
                    case DATE -> System.out.println("Data deve seguir o formato: 01/01/2022");
                }

            }
        }
        return inputToverify;
    }
}
