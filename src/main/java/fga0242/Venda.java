package fga0242;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Venda {
    private Date data;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private String metodoPagamento;
    private double valorTotal;
    private double valorFrete;
    private double valorDesconto;
    private double icms;
    private double impostoMunicipal;

    private List<double []> impostosItens;
    private boolean usarCashback;
    

    public Venda(Date data, Cliente cliente, List<ItemVenda> itens, String metodoPagamento, boolean usarCashback) {
        this.data = data;
        this.cliente = cliente;
        this.itens = itens;
        this.metodoPagamento = metodoPagamento;
        this.impostosItens = new LinkedList<double []>();
        this.usarCashback = usarCashback;
        calcularValores();
    }

    private void calcularValores() {
        valorTotal = 0;
        valorDesconto = 0;
        double valorImposto = 0;

        for (ItemVenda item : itens) {
            double valorItem = item.getQuantidade() * item.getProduto().getValor();
            double descontoItem = 0;
            if (cliente.getTipo().equals("Especial")) {
                descontoItem += 0.10;
                if (metodoPagamento.startsWith("429613")) {
                    descontoItem += 0.10;
                }
            }
            double[] impostos = calcularImpostos(cliente, valorItem);

            double icms = impostos[0];
            double impostoMunicipal = impostos[1];

            impostosItens.add(new double[]{icms, impostoMunicipal});
            valorImposto += icms + impostoMunicipal;
            
            valorDesconto += valorItem * descontoItem;
            valorItem -= valorItem * descontoItem;
            valorTotal += valorItem;
        }
        valorFrete = calcularFrete(cliente);
        valorTotal += valorFrete;
        valorTotal += valorImposto;

        if (cliente.getTipo().equals("Prime")) {
            aplicarCashback(metodoPagamento);

            double cb = cliente.getCashback();

            if (cb > 0.0) {       
                if (usarCashback) {
                    valorDesconto += cb;
                    valorTotal -= valorDesconto;
                }
            }
        }
    }

    private double[] calcularImpostos(Cliente cliente, double valorTotal) {
        if (cliente.getEstado().equals("DF")) {
            this.icms = 0.18;
            this.impostoMunicipal = 0.00;
        } else {
            this.icms = 0.12;
            this.impostoMunicipal = 0.04;
        }
        double valorIcms = valorTotal * this.icms;
        double valorImpostoMunicipal = valorTotal * this.impostoMunicipal;
        return new double[]{valorIcms, valorImpostoMunicipal};
    }

    private double calcularFrete(Cliente cliente) {
        double frete;
        switch (cliente.getEstado()) {
            case "DF":
                frete = 5.00;
                break;
            case "Centro-oeste":
                frete = cliente.isCapital() ? 10.00 : 13.00;
                break;
            case "Nordeste":
                frete = cliente.isCapital() ? 15.00 : 18.00;
                break;
            case "Norte":
                frete = cliente.isCapital() ? 20.00 : 25.00;
                break;
            case "Sudeste":
                frete = cliente.isCapital() ? 7.00 : 10.00;
                break;
            case "Sul":
                frete = cliente.isCapital() ? 10.00 : 13.00;
                break;
            default:
                frete = 0;
        }
        if (cliente.getTipo().equals("Prime")) {
            return 0;
        } else if (cliente.getTipo().equals("Especial")) {
            return frete * 0.70;
        }
        return frete;
    }

    private void aplicarCashback(String metodoPagamento) {
        double cashbackRate = metodoPagamento.startsWith("429613") ? 0.05 : 0.03;
        valorTotal = Math.floor(valorTotal);
        double cashback = valorTotal * cashbackRate;
        cliente.adicionarCashback(cashback);
    }

    public Date getData() {
        return data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public double getIcms() {
        return icms;
    }

    public double getImpostoMunicipal() {
        return impostoMunicipal;
    }

}