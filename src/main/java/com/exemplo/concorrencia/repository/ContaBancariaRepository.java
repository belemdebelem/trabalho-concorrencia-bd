package com.exemplo.concorrencia.repository;

import com.exemplo.concorrencia.entity.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
}
