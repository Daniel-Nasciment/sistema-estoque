package br.com.estoque.sistema_estoque.repository;

import br.com.estoque.sistema_estoque.model.Historico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    Page<Historico> findByEstoqueIdOrderByCriadoEmDesc(Pageable pageable, Long estoqueId);
}
