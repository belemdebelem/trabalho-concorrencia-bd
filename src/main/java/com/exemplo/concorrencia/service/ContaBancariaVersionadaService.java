package com.exemplo.concorrencia.service;

import com.exemplo.concorrencia.entity.ContaBancariaVersionada;
import com.exemplo.concorrencia.exception.ResourceNotFoundException;
import com.exemplo.concorrencia.exception.SaldoInsuficienteException;
import com.exemplo.concorrencia.repository.ContaBancariaVersionadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContaBancariaVersionadaService {

    private final ContaBancariaVersionadaRepository repository;

    @Transactional
    public ContaBancariaVersionada deposito(Long id, BigDecimal valor) {
        ContaBancariaVersionada conta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        // Simulação de um pequeno atraso para aumentar a chance de concorrência
        simularAtraso();

        conta.setSaldo(conta.getSaldo().add(valor));
        
        // Aqui o Hibernate verificará a versão no momento do commit.
        // Se a versão no banco for diferente da carregada, lançará ObjectOptimisticLockingFailureException.
        return repository.save(conta);
    }

    @Transactional
    public ContaBancariaVersionada saque(Long id, BigDecimal valor) {
        ContaBancariaVersionada conta = repository.findById(id)
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

    public ContaBancariaVersionada buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
    }
}
