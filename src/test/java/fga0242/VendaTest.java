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
import java.util.Calendar;
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
    private String tipoEsperadoCliente;

    public VendaTest(Date data, Cliente cliente, List<ItemVenda> itens, String metodoPagamento, boolean usarCashback, double expectedValorTotal, double expectedValorFrete, double expectedValorDesconto, double expectedIcms, double expectedImpostoMunicipal, String tipoEsperadoCliente) {
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
        this.tipoEsperadoCliente = tipoEsperadoCliente;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("001", "Produto 1", 100.0, "unidade");
        Produto produto2 = new Produto("002", "Produto 2", 50.0, "unidade");
        ItemVenda item1 = new ItemVenda(produto1, 2);
        ItemVenda item2 = new ItemVenda(produto2, 1);
        List<ItemVenda> itens = Arrays.asList(item1, item2);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);
        Date recentDate1 = cal.getTime();

        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -10);
        Date recentDate2 = cal.getTime();

        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -40);
        Date oldDate = cal.getTime();

        Cliente clienteEspecialDF = new Cliente("Padrão", "DF", true);
        Venda vendaRecente1 = new Venda(recentDate1, clienteEspecialDF, itens, "1234567890123456", false);
        Venda vendaRecente2 = new Venda(recentDate2, clienteEspecialDF, itens, "1234567890123456", false);
        Venda vendaAntiga = new Venda(oldDate, clienteEspecialDF, itens, "1234567890123456", false);

        Cliente clientePrimeSudesteCapital = new Cliente("Prime", "Sudeste", true);

        Cliente clientePadraoSudesteInterior = new Cliente("Padrão", "Sudeste", false);

        Cliente clientePrimeSudesteInterior = new Cliente("Prime", "Sudeste", false);

        return Arrays.asList(new Object[][] {
            {new Date(), clienteEspecialDF, itens, "4296130000000000", false, 248.5, 3.5, 50.0, 0.18, 0.0, "Especial"},
            {new Date(), clientePrimeSudesteCapital, itens, "1234560000000000", false, 290.0, 0.0, 0.0, 0.12, 0.04, "Prime"},
            {new Date(), clientePadraoSudesteInterior, itens, "7894560000000000", false, 300.0, 10.0, 0.0, 0.12, 0.04, "Padrão"},
            {new Date(), clientePrimeSudesteInterior, itens, "4296131111111111", true, 275.5, 0.0, 14.5, 0.12, 0.04, "Prime"}
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
        assertEquals(tipoEsperadoCliente, cliente.getTipo());
    }
}