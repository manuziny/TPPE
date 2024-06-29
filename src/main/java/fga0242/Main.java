package fga0242;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Produto produto1 = new Produto("001", "Produto 1", 100.0, "unidade");
        Produto produto2 = new Produto("002", "Produto 2", 50.0, "unidade");
        ItemVenda item1 = new ItemVenda(produto1, 2);
        ItemVenda item2 = new ItemVenda(produto2, 1);
        List<ItemVenda> itens = Arrays.asList(item1, item2);

        Cliente clienteEspecialDF = new Cliente("Especial", "DF", true);
        Cliente clientePrimeSudesteCapital = new Cliente("Prime", "Sudeste", true);
        Cliente clientePadraoSudesteInterior = new Cliente("Padrão", "Sudeste", false);
        Cliente clientePrimeSudesteInterior = new Cliente("Prime", "Sudeste", false);

        Date data = new Date();
        Cliente cliente = clientePrimeSudesteInterior;
        String metodoPagamento = "4296131111111111";
        boolean usarCashback = true;
        double expectedValorTotal = 290.0;
        double expectedValorFrete = 0.0;
        double expectedValorDesconto = 0.0;
        double expectedIcms = 0.12;
        double expectedImpostoMunicipal = 0.04;

        Venda venda = new Venda(data, cliente, itens, metodoPagamento, usarCashback);

    }
}