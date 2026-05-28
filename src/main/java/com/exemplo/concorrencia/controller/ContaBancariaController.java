package com.exemplo.concorrencia.controller;

import com.exemplo.concorrencia.dto.ValorRequestDTO;
import com.exemplo.concorrencia.entity.ContaBancaria;
import com.exemplo.concorrencia.service.ContaBancariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaBancariaController {

    private final ContaBancariaService service;

    @GetMapping("/{id}")
    public ContaBancaria buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/{id}/deposito")
    public ContaBancaria deposito(@PathVariable Long id, @RequestBody ValorRequestDTO request) {
        return service.deposito(id, request.getValor());
    }

    @PostMapping("/{id}/saque")
    public ContaBancaria saque(@PathVariable Long id, @RequestBody ValorRequestDTO request) {
        return service.saque(id, request.getValor());
    }
}
