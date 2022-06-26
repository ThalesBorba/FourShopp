package br.com.fourshopp.entities;

import br.com.fourshopp.entities.Cargo;
import br.com.fourshopp.entities.Endereco;
import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.entities.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@PrimaryKeyJoinColumn(name = "cd_operador")
@Entity
@Setter
@Getter
@Table(name = "tb_operador")
public class Operador  extends Pessoa {

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_hireDate")
    private Date dataContratacao;

    @Column(name = "ds_cargo")
    private Cargo cargo;

    @Column(name = "vl_salario")
    private double salario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cd_pessoa")
    private Funcionario funcionario;

    @Column(name = "ns_cargaHoraria")
    private Integer cargaHoraria;

    @Column(name = "ns_departamento")
    private Setor setor;

    public Operador() {}

    public Operador(String nome, String email, String celular, String password, String cpf, Endereco endereco,
                    Date dataContratacao, Cargo cargo, double salario, Integer cargaHoraria) {
        super(nome, email, celular, password, cpf, endereco);
        this.dataContratacao = dataContratacao;
        this.cargo = cargo;
        this.salario = salario;
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operador operador = (Operador) o;
        return Double.compare(operador.salario, salario) == 0 && Objects.equals(dataContratacao, operador.dataContratacao) && cargo == operador.cargo && Objects.equals(funcionario, operador.funcionario) && Objects.equals(cargaHoraria, operador.cargaHoraria) && setor == operador.setor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataContratacao, cargo, salario, funcionario, cargaHoraria, setor);
    }
}