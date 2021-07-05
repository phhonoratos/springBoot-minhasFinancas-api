package com.honorato.minhasfinancas.model.repositories;

import com.honorato.minhasfinancas.model.entities.Lancamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    
}
