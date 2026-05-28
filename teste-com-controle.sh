#!/bin/bash
echo "Iniciando 50 depósitos simultâneos (Com @Version)..."
for i in {1..50}
do
   curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:8080/contas-versionadas/1/deposito \
   -H "Content-Type: application/json" \
   -d '{"valor": 10.00}' &
done | sort | uniq -c
wait
echo "Todos os depósitos finalizados."
echo "Saldo Final (Consistente com os 200 OK):"
curl -s http://localhost:8080/contas-versionadas/1
echo ""
