package br.com.fourshopp.Util;

import br.com.fourshopp.entities.*;
import br.com.fourshopp.repository.OperadorRespository;
import br.com.fourshopp.repository.PessoaRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class UtilMenu {

    @Autowired
    private PessoaRepository pessoaRepository;
    private static double valorTotalCompra;

    private static Scanner scanner;

    // SOU CLIENTE
    public static Cliente menuCadastroCliente(Scanner scanner, PessoaRepository pessoaRepository) throws ParseException {

        System.out.println("Insira seu nome: ");
        scanner.nextLine();
        String nome = scanner.nextLine();

        System.out.println("Insira seu email: ");
        String email = Validation.regexValidation(scanner, ValidationEnum.EMAIL);

        System.out.println("Insira seu celular: ");
        String celular = Validation.regexValidation(scanner, ValidationEnum.CELLPHONE);

        System.out.println("Insira sua password: ");
        String password = Validation.regexValidation(scanner, ValidationEnum.PASSWORD);

        System.out.println("Insira seu cpf: ");
        String cpf = Validation.regexValidationCpf(scanner, pessoaRepository, ValidationEnum.CPF);

        System.out.println("Insira sua rua: ");
        String rua = scanner.next();

        System.out.println("Insira seu cidade: ");
        scanner.nextLine();
        String cidade = scanner.next();

        System.out.println("Insira seu bairro: ");
        scanner.nextLine();
        String bairro = scanner.next();

        System.out.println("Insira seu numero: ");
        scanner.nextLine();
        int numero = Validation.numberFormatValidation(scanner).intValue();

        System.out.println("Insira sua data de nascimento (dd/MM/yyyy): ");
        String dataNascimento = Validation.regexValidation(scanner, ValidationEnum.DATE);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada = formato.parse(dataNascimento);

        Endereco endereco = new Endereco(rua, cidade, bairro, numero);
        Cliente cliente = new Cliente(nome, email, celular, password, cpf, endereco, dataFormatada);

        return cliente;

    }


    public static int menuSetor(Scanner scanner) {
        System.out.println("Digite a opção desejada: " +
                "\n1- MERCEARIA \n2- BAZAR \n3- ELETRÔNICOS");
        int opcao = 0;
        while (true) {
            opcao = Validation.numberFormatValidation(scanner).intValue();
            if (opcao > 0 && opcao < 4) {
                break;
            }
            System.err.println("Opção inválida!");
        }
        return opcao;
    }

    public static void gerarCupomFiscal(Cliente cliente) throws IOException {
        List<Produto> produtos = cliente.getProdutoList();
        Double desconto = verificaMercearia(produtos);

        Document document = new Document(PageSize.A4);
        File file = new File("CupomFiscal_" + new Random().nextInt() + ".pdf");
        String absolutePath = file.getAbsolutePath();
        PdfWriter.getInstance(document, new FileOutputStream(absolutePath));
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Image image1 = Image.getInstance("src/main/java/br/com/fourshopp/service/fourshopp.png");
        image1.scaleAbsolute(140f, 140f);
        image1.setAlignment(Element.ALIGN_CENTER);


        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Font total = FontFactory.getFont(FontFactory.HELVETICA);
        total.setSize(12);
        total.setColor(Color.blue);

        Font header = FontFactory.getFont(FontFactory.HELVETICA);
        header.setSize(12);
        header.setFamily("bold");

        document.add(image1);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        ListItem item1 = new ListItem();
        produtos.forEach(produto -> {

            System.out.println(produto.toString());
            Chunk nome = new Chunk("\n" + produto.getNome()+" ("+produto.getQuantidade()+") \nPreço unidade : R$"
                    +df.format(produto.getPreco() / produto.getQuantidade()));
            Phrase frase = new Phrase();
            frase.add(nome);

            Paragraph x = new Paragraph(frase);

            String preco = "............................................................................................................................R$ " + df.format(produto.getPreco());
            Paragraph y = new Paragraph(preco);
            y.setAlignment(Paragraph.ALIGN_RIGHT);
            item1.add(x);
            item1.add(y);

            valorTotalCompra =  valorTotalCompra + produto.getPreco();
        });

        //todo test
        Paragraph paragraph = new Paragraph("\n\nTOTAL: R$" + df.format(valorTotalCompra - desconto), total);
        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);


        document.add(item1);
        document.add(paragraph);


        document.close();
    }

    private static Double verificaMercearia(List<Produto> produtos) {
        Double totalMercearia = 0.0;
        Integer desconto = 0;
        for (Produto produto: produtos) {
            if (produto.getSetor() == 1) {
                totalMercearia += produto.getPreco();
                }
            }
        if (totalMercearia >= 500) {
        desconto = ((totalMercearia).intValue() / 500) * 50;
        }
        return desconto.doubleValue();

    }

    public static Funcionario cadastrarFuncionario(Scanner scanner, PessoaRepository pessoaRepository) throws ParseException {

        System.out.println("Insira seu nome: ");
        scanner.nextLine();
        String nome = scanner.nextLine();

        System.out.println("Insira seu email: ");
        String email = Validation.regexValidation(scanner, ValidationEnum.EMAIL);

        System.out.println("Insira seu celular: ");
        String celular = Validation.regexValidation(scanner, ValidationEnum.CELLPHONE);

        System.out.println("Insira sua password: ");
        String password = Validation.regexValidation(scanner, ValidationEnum.PASSWORD);

        System.out.println("Insira seu cpf: ");
        String cpf = Validation.regexValidationCpf(scanner, pessoaRepository, ValidationEnum.CPF);

        System.out.println("Insira sua rua: ");
        scanner.nextLine();
        String rua = scanner.next();

        System.out.println("Insira sua cidade: ");
        scanner.nextLine();
        String cidade = scanner.next();

        System.out.println("Insira seu bairro: ");
        scanner.nextLine();
        String bairro = scanner.next();

        System.out.println("Insira seu numero: ");
        scanner.nextLine();
        int numero = Validation.numberFormatValidation(scanner).intValue();

        System.out.println("Data de contratação: ");
        String hireDate = Validation.regexValidation(scanner, ValidationEnum.DATE);

        System.out.println("Insira o salário CLT bruto: ");
        double salario = Validation.numberFormatValidation(scanner);

        System.out.println("Escolha o setor: \n1 - MERCEARIA\n2 - BAZAR\n3 - ELETRONICOS");
        Setor setor = Validation.convertDepartment(scanner);



        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(hireDate);

        Endereco endereco = new Endereco(rua, cidade, bairro, numero);
        return new Funcionario(nome, email, celular, password, cpf, endereco, data , Cargo.CHEFE_SECAO, setor,
                salario, new ArrayList<>(), new ArrayList<>());


    }

    public static Operador menuCadastrarOperador(Scanner scanner, PessoaRepository pessoaRepository) throws ParseException {

        System.out.println("Insira seu nome: ");
        scanner.nextLine();
        String nome = scanner.nextLine();

        System.out.println("Insira seu email: ");
        String email = Validation.regexValidation(scanner, ValidationEnum.EMAIL);

        System.out.println("Insira seu celular: ");
        String celular = Validation.regexValidation(scanner, ValidationEnum.CELLPHONE);

        System.out.println("Insira sua password: ");
        String password = Validation.regexValidation(scanner, ValidationEnum.PASSWORD);

        System.out.println("Insira seu cpf: ");
        String cpf = Validation.regexValidationCpf(scanner, pessoaRepository, ValidationEnum.CPF);

        System.out.println("Insira sua rua: ");
        scanner.nextLine();
        String rua = scanner.next();

        System.out.println("Insira seu cidade: ");
        scanner.nextLine();
        String cidade = scanner.next();

        System.out.println("Insira seu bairro: ");
        scanner.nextLine();
        String bairro = scanner.next();

        System.out.println("Insira seu numero: ");
        scanner.nextLine();
        int numero = Validation.numberFormatValidation(scanner).intValue();

        System.out.println("Data de contratação: ");
        String hireDate = Validation.regexValidation(scanner, ValidationEnum.DATE);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(hireDate);

        System.out.println("Insira o salário CLT bruto: ");
        double salario = Validation.numberFormatValidation(scanner);

        System.out.println("Insira a carga horária mensal: ");
        Integer cargaHoraria = Validation.numberFormatValidation(scanner).intValue();

        return new Operador(nome,email,celular,password,cpf,new Endereco(), data,Cargo.OPERADOR, salario, cargaHoraria);
    }

    public static String menuDemitirOperador(Scanner scanner, OperadorRespository operadorRespository) {
        String opcao = "N";
        String cpf = null;
        while (!opcao.equalsIgnoreCase("S")) {
            System.out.println("Digite o cpf do operador a ser demitido");
            cpf = Validation.validationCpfToFire(scanner, operadorRespository, ValidationEnum.CPF);
            System.out.println("Tem certeza? [S/N]");
            opcao = scanner.next();
        }

        return cpf;

    }

    public static Produto menuCadastrarProduto(Scanner scanner, Funcionario funcionario) throws ParseException {
        System.out.println("Insira o nome do produto: ");
        scanner.nextLine();
        String nome = scanner.next();

        System.out.println("Insira a quantidade: ");
        scanner.nextLine();
        Integer quantidade = Validation.numberFormatValidation(scanner).intValue();

        System.out.println("Insira o preco: ");
        Double preco = Validation.numberFormatValidation(scanner);

        System.out.println("Insira a data de vencimento: ");
        Date dataVencimento = Validation.assertExpiringDateIsValid(scanner);

        return new Produto(nome, quantidade, preco, funcionario.getSetor(), dataVencimento);
    }

}
