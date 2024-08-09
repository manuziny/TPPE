package fga0242;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*
Impactos da refatoração 'extrair método':
* Melhoria da Legibilidade - O método `calcularValores()` ficou mais limpo e fácil de entender, facilitando a leitura e compreensão do código.
* Facilidade de Manutenção - Alterações em regras de negócio, como o cálculo de descontos ou impostos, podem ser realizadas de forma isolada nos métodos específicos, sem a necessidade de modificar o método principal. Isso reduz a chance de introdução de erros.
* Isolamento de Responsabilidades - Cada método agora tem uma responsabilidade clara e específica, o que está em linha com o princípio da responsabilidade única (Single Responsibility Principle). Isso melhora a modularidade do código.
*/

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

    private List<double[]> impostosItens;
    private boolean usarCashback;

    public Venda(Date data, Cliente cliente, List<ItemVenda> itens, String metodoPagamento, boolean usarCashback) {
        this.data = data;
        this.cliente = cliente;
        this.itens = itens;
        this.metodoPagamento = metodoPagamento;
        this.impostosItens = new LinkedList<double[]>();
        this.usarCashback = usarCashback;
        calcularValores();
    }

    private void calcularValores() {
        valorTotal = 0;
        valorDesconto = 0;
        double valorImposto = 0;

        cliente.calcularComprasUltimoMes();

        // Extrai o cálculo do valor de cada item
        for (ItemVenda item : itens) {
            processarItemVenda(item);
        }

        valorFrete = calcularFrete(cliente);
        valorTotal += valorFrete + valorImposto;

        if (cliente.getTipo().equals("Prime")) {
            aplicarCashback(metodoPagamento);
            aplicarDescontoCashback();
        }

        cliente.adicionarVenda(this);
    }

    // Novo método para processar cada item da venda
    private void processarItemVenda(ItemVenda item) {
        double valorItem = item.getQuantidade() * item.getProduto().getValor();
        double descontoItem = calcularDescontoItem(cliente, metodoPagamento);

        // Substituíção da lógica de calcular impostos por uma instância da nova classe 'CalculadoraImpostos'
        CalculadoraImpostos calculadoraImpostos = new CalculadoraImpostos(cliente, valorItem);
        double[] impostos = calculadoraImpostos.calcular();

        // Atribui a taxa de imposto ao invés do valor monetário
        this.icms = calculadoraImpostos.getIcms();
        this.impostoMunicipal = calculadoraImpostos.getImpostoMunicipal();

        impostosItens.add(new double[]{impostos[0], impostos[1]});

        valorTotal += aplicarDescontoEImpostos(valorItem, descontoItem, impostos[0], impostos[1]);
    }

    // Novo método para calcular o desconto de um item
    private double calcularDescontoItem(Cliente cliente, String metodoPagamento) {
        double descontoItem = 0;
        if (cliente.getTipo().equals("Especial")) {
            descontoItem += 0.10;
            if (metodoPagamento.startsWith("429613")) {
                descontoItem += 0.10;
            }
        }
        return descontoItem;
    }

    // Novo método para aplicar descontos e impostos ao valor de um item
    private double aplicarDescontoEImpostos(double valorItem, double descontoItem, double icms, double impostoMunicipal) {
        valorDesconto += valorItem * descontoItem;
        valorItem -= valorItem * descontoItem;
        return valorItem + icms + impostoMunicipal;
    }

    // Método para aplicar o desconto do cashback no valor total
    private void aplicarDescontoCashback() {
        double cb = cliente.getCashback();
        if (cb > 0.0 && usarCashback) {
            valorDesconto += cb;
            valorTotal -= valorDesconto;
        }
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