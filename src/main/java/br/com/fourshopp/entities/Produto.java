package br.com.fourshopp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "produto", sequenceName = "sq_produto", allocationSize = 1)
@Table(name = "tb_produto")
public class Produto implements Serializable, Cloneable {

    // PRODUTO FORA DA DATA DE VENCIMENTO NÃO PODE SER ADICIONADO NO CARRINHO

    private static final long serialVersionUID = 54L;

    @Id
    @GeneratedValue(generator = "produto", strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @Column(name = "nm_nome")
    private String nome;

    @Column(name = "qtd_quantidade")
    private int quantidade;

    @Column(name = "vl_preco")
    private double preco;

    @Column(name = "ds_setor")
    private int setor;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_vencimento")
    private Date dataVencimento;

    public Produto(String nome, int quantidade, double preco, Setor setor, Date dataVencimento) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.setor = setor.getCd();
        this.dataVencimento = dataVencimento;
    }

    @Override
    public Produto clone() throws CloneNotSupportedException {
        return (Produto) super.clone();
    }

    public void getCalculaValor(int quantidade, Produto produto){
        produto.setQuantidade(quantidade);
        produto.setPreco(getPreco()*quantidade);
    }
}
