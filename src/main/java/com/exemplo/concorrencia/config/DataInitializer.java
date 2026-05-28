package com.exemplo.concorrencia.config;

import com.exemplo.concorrencia.entity.ContaBancaria;
import com.exemplo.concorrencia.entity.ContaBancariaVersionada;
import com.exemplo.concorrencia.repository.ContaBancariaRepository;
import com.exemplo.concorrencia.repository.ContaBancariaVersionadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ContaBancariaRepository contaRepository;
    private final ContaBancariaVersionadaRepository contaVersionadaRepository;

    @Override
    public void run(String... args) {
        // Criar uma conta comum (sem controle de concorrência)
        if (contaRepository.count() == 0) {
            ContaBancaria c1 = new ContaBancaria(null, "João Silva (Sem Controle)", new BigDecimal("1000.00"));
            contaRepository.save(c1);
            System.out.println("Conta comum criada com ID: " + c1.getId());
        }

        // Criar uma conta versionada (com controle otimista)
        if (contaVersionadaRepository.count() == 0) {
            ContaBancariaVersionada cv1 = new ContaBancariaVersionada(null, "Maria Oliveira (Com @Version)", new BigDecimal("1000.00"), 0);
            contaVersionadaRepository.save(cv1);
            System.out.println("Conta versionada criada com ID: " + cv1.getId());
        }
    }
}
