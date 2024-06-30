package fga0242;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VendaService {
    private List<Venda> vendas;

    public VendaService() {
        this.vendas = new ArrayList<>();
    }

    public void adicionarVenda(Venda venda) {
        vendas.add(venda);
    }
    
    public double calcularVendasUltimoMes(Cliente cliente) {
        double totalVendas = 0;
        Calendar umMesAtras = Calendar.getInstance();
        umMesAtras.add(Calendar.MONTH, -1);

        for (Venda venda : vendas) {
            if (venda.getCliente().equals(cliente) && venda.getData().after(umMesAtras.getTime())) {
                totalVendas += venda.getValorTotal();
            }
        }
        return totalVendas;
    }

    public boolean verificarElegibilidadeClienteEspecial(Cliente cliente) {
        double totalVendasUltimoMes = calcularVendasUltimoMes(cliente);
        return totalVendasUltimoMes > 100.0;
    }
}