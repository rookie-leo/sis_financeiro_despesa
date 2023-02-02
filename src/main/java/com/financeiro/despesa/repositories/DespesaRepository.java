package com.financeiro.despesa.repositories;

import com.financeiro.despesa.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByDescricao(String descricao);

    @Query("select d from Despesa d where date_format(d.dataEntrada, '%Y-%m') = :dataFmt")
    List<Despesa> findByDataEntrada(@Param("dataFmt")String dataFmt);
}
