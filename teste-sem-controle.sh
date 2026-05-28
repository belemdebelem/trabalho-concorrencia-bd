#!/bin/bash
echo "Iniciando 50 depósitos simultâneos (Sem Controle)..."
for i in {1..50}
do
   curl -s -X POST http://localhost:8080/contas/1/deposito \
   -H "Content-Type: application/json" \
   -d '{"valor": 10.00}' > /dev/null &
done
wait
echo "Todos os depósitos finalizados."
echo "Saldo Final (Esperado 1500.00):"
curl -s http://localhost:8080/contas/1
echo ""
