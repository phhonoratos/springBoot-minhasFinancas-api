package com.honorato.minhasfinancas.services;

import java.util.List;

import com.honorato.minhasfinancas.model.entities.Lancamento;
import com.honorato.minhasfinancas.model.enums.StatusLancamento;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamentoFiltro);

    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    
    void validar(Lancamento lancamento);
}
