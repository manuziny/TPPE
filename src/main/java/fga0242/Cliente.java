package fga0242;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Cliente {
    private String tipo;
    private String estado;
    private boolean capital;
    private double cashback;
    private List<Venda> compras;

    public Cliente(String tipo, String estado, boolean capital) {
        this.tipo = tipo;
        this.estado = estado;
        this.capital = capital;
        this.cashback = 0.0;
        this.compras = new ArrayList<>();
    }

    public void calcularComprasUltimoMes() {
        double totalCompras = 0;
        Calendar umMesAtras = Calendar.getInstance();
        umMesAtras.add(Calendar.MONTH, -1);

        for (Venda venda : compras) {
            if (venda.getData().after(umMesAtras.getTime())) {
                totalCompras += venda.getValorTotal();
            }
        }
        verificarElegibilidadeClienteEspecial(totalCompras>100.0);
    }

    private void verificarElegibilidadeClienteEspecial(boolean check) {
        if(this.tipo=="Prime"){
            return;
        }
        this.tipo = check ? "Especial" : "Padrão";
    }

    public void adicionarVenda(Venda venda) {
        compras.add(venda);
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isCapital() {
        return capital;
    }

    public double getCashback() {
        return cashback;
    }

    public void adicionarCashback(double valor) {
        this.cashback += valor;
    }
}