package com.exemplo.concorrencia.controller;

import com.exemplo.concorrencia.dto.ValorRequestDTO;
import com.exemplo.concorrencia.entity.ContaBancariaVersionada;
import com.exemplo.concorrencia.service.ContaBancariaVersionadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas-versionadas")
@RequiredArgsConstructor
public class ContaBancariaControllerVersionada {

    private final ContaBancariaVersionadaService service;

    @GetMapping("/{id}")
    public ContaBancariaVersionada buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/{id}/deposito")
    public ContaBancariaVersionada deposito(@PathVariable Long id, @RequestBody ValorRequestDTO request) {
        return service.deposito(id, request.getValor());
    }

    @PostMapping("/{id}/saque")
    public ContaBancariaVersionada saque(@PathVariable Long id, @RequestBody ValorRequestDTO request) {
        return service.saque(id, request.getValor());
    }
}
