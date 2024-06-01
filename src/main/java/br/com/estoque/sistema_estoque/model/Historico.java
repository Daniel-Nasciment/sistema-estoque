package br.com.estoque.sistema_estoque.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORICO")
@Getter
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Estoque estoque;
    @Column(nullable = false)
    private Long antigaQuantidade;
    @Column(nullable = false)
    private Long novaQuantidade;
    @Column(nullable = false)
    private String tipoMovimentacao;
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Deprecated
    public Historico(){

    }

    public Historico(Estoque estoque, Long quantidade, String tipoMovimentacao) {
        this.estoque = estoque;
        this.antigaQuantidade = estoque.getQuantidadeDisponivel();
        this.novaQuantidade = quantidade;
        this.tipoMovimentacao = tipoMovimentacao;
    }

    @PrePersist
    public void prePersist(){
        this.criadoEm = LocalDateTime.now();
    }

}
