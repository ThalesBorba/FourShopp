package br.com.fourshopp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@PrimaryKeyJoinColumn(name = "cd_cliente")
@Entity
@Setter
@Getter
@Table(name = "tb_operador")
public class Cliente extends Pessoa{

    @Column(name = "dt_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    private Carrinho carrinhoDeCompras;

    public Cliente(String nome, String email, String celular, String password, String cpf, Endereco endereco, Date dataNascimento) {
        super(nome, email, celular, password, cpf, endereco);
        this.dataNascimento = dataNascimento;
    }

    public Cliente() {}
}
