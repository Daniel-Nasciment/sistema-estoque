package br.com.estoque.sistema_estoque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ESTOQUE")
@Getter
@NoArgsConstructor
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String codigoProduto;
    @Column(nullable = false)
    private Long quantidadeDisponivel;
    @Column(nullable = false)
    private Long codigoBarras;
    @OneToMany(mappedBy = "estoque")
    private List<Historico> historico;
    @Column(nullable = false)
    private LocalDateTime criadoEm;
    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    public Estoque(String codigoProduto, Long quantidadeDisponivel, Long codigoBarras) {
        this.codigoProduto = codigoProduto;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.codigoBarras = codigoBarras;
    }

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        this.criadoEm = now;
        this.atualizadoEm = now;
    }

    @PreUpdate
    public void preUpdate(){
        this.atualizadoEm = LocalDateTime.now();
    }


    public void atualizarQuantidade(Long quantidadeASerAtualizada) {
        this.quantidadeDisponivel = quantidadeASerAtualizada;
    }

    public void addHistorico(Historico historico){
        if (this.historico == null || this.historico.isEmpty()){
            this.historico = new ArrayList<>();
        }
        this.historico.add(historico);
    }

}
