package com.exemplo.concorrencia.service;

import com.exemplo.concorrencia.entity.ContaBancaria;
import com.exemplo.concorrencia.exception.ResourceNotFoundException;
import com.exemplo.concorrencia.exception.SaldoInsuficienteException;
import com.exemplo.concorrencia.repository.ContaBancariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContaBancariaService {

    private final ContaBancariaRepository repository;

    @Transactional
    public ContaBancaria deposito(Long id, BigDecimal valor) {
        ContaBancaria conta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        // Simulação de um pequeno atraso para aumentar a chance de concorrência
        simularAtraso();

        conta.setSaldo(conta.getSaldo().add(valor));
        return repository.save(conta);
    }

    @Transactional
    public ContaBancaria saque(Long id, BigDecimal valor) {
        ContaBancaria conta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        // Simulação de um pequeno atraso para aumentar a chance de concorrência
        simularAtraso();

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        return repository.save(conta);
    }

    private void simularAtraso() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public ContaBancaria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
    }
}
