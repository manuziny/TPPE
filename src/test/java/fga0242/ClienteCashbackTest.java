package test.java.fga0242;

import fga0242.*;
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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ClienteCashbackTest {
    private Cliente cliente;
    private String metodoPagamento;
    private double expectedCashback;
    private List<Venda> compras;

    public ClienteCashbackTest(Cliente cliente, String metodoPagamento, double expectedCashback, List<Venda> compras) {
        this.cliente = cliente;
        this.metodoPagamento = metodoPagamento;
        this.expectedCashback = expectedCashback;
        this.compras = compras;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("001", "Produto 1", 100.0, "unidade");
        ItemVenda item1 = new ItemVenda(produto1, 2);
        List<ItemVenda> itens = Arrays.asList(item1);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);
        Date recentDate1 = cal.getTime();

        Cliente clientePadrao = new Cliente("Padrão", "DF", false);
        Cliente clientePrime = new Cliente("Prime", "DF", false);
        Cliente clientePrimeCartao = new Cliente("Prime", "DF", false);
        Cliente clienteEspecial = new Cliente("Especial", "DF", false);

        Venda vendaRecente1 = new Venda(recentDate1, clientePrime, itens, "1234567890123456", false);
        Venda vendaRecente2 = new Venda(recentDate1, clientePrimeCartao, itens, "4296137890123456", false);

        return Arrays.asList(new Object[][] {
            {clientePadrao, "1234567890123456", 0.0, Arrays.asList(vendaRecente1)},
            {clientePrime, "1234567890123456", 7.08, Arrays.asList(vendaRecente1)},
            {clientePrimeCartao, "4296137890123456", 11.8, Arrays.asList(vendaRecente2)},
            {clienteEspecial, "1234567890123456", 0.0, Arrays.asList(vendaRecente1)}
        });
    }
    
    @Test
    public void testGetCashback() {
        double cashback = cliente.getCashback();
        assertEquals(expectedCashback, cashback, 0.01);
    }
}