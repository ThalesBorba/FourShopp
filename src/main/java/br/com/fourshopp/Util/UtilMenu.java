package br.com.fourshopp.Util;

import br.com.fourshopp.entities.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

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

    private static double valorTotalCompra;

    private static Scanner scanner;

    // SOU CLIENTE
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

    public static void gerarCupomFiscal(Cliente cliente) throws IOException {
        List<Produto> produtos = cliente.getProdutoList();
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
            Chunk nome = new Chunk("\n" + produto.getNome()+" ("+produto.getQuantidade()+") \nPreço unidade : R$"+df.format(produto.getPreco() / produto.getQuantidade()));
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

        Paragraph paragraph = new Paragraph("\n\nTOTAL: R$" + df.format(valorTotalCompra), total);
        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);


        document.add(item1);
        document.add(paragraph);


        document.close();
    }

    public static Funcionario cadastrarFuncionario(Scanner scanner) throws ParseException {

        System.out.println("Insxira seu nome: ");
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

        System.out.println("Data de contratação: ");
        String hireDate = scanner.next();

        System.out.println("Insira o salário CLT bruto: ");
        double salario = scanner.nextDouble();


        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(hireDate);

        Endereco endereco = new Endereco(rua, cidade, bairro, numero);
        return new Funcionario(nome, email, celular, password, cpf, endereco, data , Cargo.CHEFE_SECAO, Setor.MERCEARIA, salario, new ArrayList<>(), new ArrayList<>());


    }

    public static Operador menuCadastrarOperador(Scanner scanner) throws ParseException {

        System.out.println("Insxira seu nome: ");
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

        System.out.println("Data de contratação: ");
        String hireDate = scanner.next();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(hireDate);

        System.out.println("Insira o salário CLT bruto: ");
        double salario = scanner.nextDouble();

        return new Operador(nome,email,celular,password,cpf,new Endereco(), data,Cargo.OPERADOR, salario);
    }
}
