package com.honorato.minhasfinancas.model.repositories;

import java.math.BigDecimal;

import com.honorato.minhasfinancas.model.entities.Lancamento;
import com.honorato.minhasfinancas.model.enums.StatusLancamento;
import com.honorato.minhasfinancas.model.enums.TipoLancamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "select sum(l.valor) from Lancamento l join l.usuario u "
                + "where u.id = :idUsuario and l.tipo =:tipo and l.status =:status group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuarioEStatus(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo, @Param("status") StatusLancamento status);
    
}
