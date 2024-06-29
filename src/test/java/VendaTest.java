import fga0242.*;
import fga0242.Cliente;
import fga0242.Produto;
import fga0242.Venda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class VendaTest {
    private Date data;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private String metodoPagamento;
    private boolean usarCashback;
    private double expectedValorTotal;
    private double expectedValorFrete;
    private double expectedValorDesconto;
    private double expectedIcms;
    private double expectedImpostoMunicipal;

    public VendaTest(Date data, Cliente cliente, List<ItemVenda> itens, String metodoPagamento, boolean usarCashback, double expectedValorTotal, double expectedValorFrete, double expectedValorDesconto, double expectedIcms, double expectedImpostoMunicipal) {
        this.data = data;
        this.cliente = cliente;
        this.itens = itens;
        this.metodoPagamento = metodoPagamento;
        this.usarCashback = usarCashback;
        this.expectedValorTotal = expectedValorTotal;
        this.expectedValorFrete = expectedValorFrete;
        this.expectedValorDesconto = expectedValorDesconto;
        this.expectedIcms = expectedIcms;
        this.expectedImpostoMunicipal = expectedImpostoMunicipal;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("001", "Produto 1", 100.0, "unidade");
        Produto produto2 = new Produto("002", "Produto 2", 50.0, "unidade");
        ItemVenda item1 = new ItemVenda(produto1, 2);
        ItemVenda item2 = new ItemVenda(produto2, 1);
        List<ItemVenda> itens = Arrays.asList(item1, item2);

        Cliente clienteEspecialDF = new Cliente("Especial", "DF", true);
        Cliente clientePrimeSudesteCapital = new Cliente("Prime", "Sudeste", true);
        Cliente clientePadraoSudesteInterior = new Cliente("Padrão", "Sudeste", false);
        Cliente clientePrimeSudesteInterior = new Cliente("Prime", "Sudeste", false);

        return Arrays.asList(new Object[][]{
            {new Date(), clienteEspecialDF, itens, "4296130000000000", false, 248.5, 3.5, 50.0, 0.18, 0.0},
            {new Date(), clientePrimeSudesteCapital, itens, "1234560000000000", false, 290.0, 0.0, 0.0, 0.12, 0.04},
            {new Date(), clientePadraoSudesteInterior, itens, "7894560000000000", false, 300.0, 10.0, 0.0, 0.12, 0.04},
            {new Date(), clientePrimeSudesteInterior, itens, "4296131111111111", true, 275.5, 0.0, 14.5, 0.12, 0.04}
        });
    }

    @Test
    public void testCalcularValores() {
        Venda venda = new Venda(data, cliente, itens, metodoPagamento, usarCashback);
        assertEquals(expectedValorTotal, venda.getValorTotal(), 0.01);
        assertEquals(expectedValorFrete, venda.getValorFrete(), 0.01);
        assertEquals(expectedValorDesconto, venda.getValorDesconto(), 0.01);
        assertEquals(expectedIcms, venda.getIcms(), 0.01);
        assertEquals(expectedImpostoMunicipal, venda.getImpostoMunicipal(), 0.01);
    }
}
