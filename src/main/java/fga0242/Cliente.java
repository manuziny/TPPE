package fga0242;

public class Cliente {
    private String tipo;
    private String estado;
    private boolean capital;
    private double cashback;

    public Cliente(String tipo, String estado, boolean capital) {
        this.tipo = tipo;
        this.estado = estado;
        this.capital = capital;
        this.cashback = 0.0;
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