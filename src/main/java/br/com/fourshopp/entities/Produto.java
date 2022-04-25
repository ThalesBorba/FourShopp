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
public class Produto implements Serializable {

    // PRODUTO FORA DA DATA DE VENCIMENTO NÃO PODE SER ADICIONADO NO CARRINHO

    private static final long serialVersionUID = -642046748542200454L;

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


    @Temporal(TemporalType.DATE)
    @Column(name = "dt_vencimento")
    private Date dataVencimento;
}
