package com.exemplo.concorrencia.repository;

import com.exemplo.concorrencia.entity.ContaBancariaVersionada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaVersionadaRepository extends JpaRepository<ContaBancariaVersionada, Long> {
}
