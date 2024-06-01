package br.com.estoque.sistema_estoque.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORICO")
@Getter
@Builder
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Estoque estoque;
    @Column(nullable = false)
    private Long antigaQuantidade;
    @Column(nullable = false)
    private Long quantidade;
    @Column(nullable = false)
    private String tipoMovimentacao;
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist(){
        this.criadoEm = LocalDateTime.now();
    }

}
