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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EligibilidadeClienteTest {

    private Cliente cliente;
    private Venda venda1;
    private Venda venda2;
    private String tipoEsperado;

    public EligibilidadeClienteTest(String tipo, String estado, boolean capital, double valorVenda1, double valorVenda2, String tipoEsperado) {
        this.cliente = new Cliente(tipo, estado, capital);
        this.venda1 = criarVenda(valorVenda1);
        this.venda2 = criarVenda(valorVenda2);
        this.tipoEsperado = tipoEsperado;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {"Padrão", "DF", true, 50.0, 60.0, "Especial"},
            {"Padrão", "SP", false, 30.0, 20.0, "Padrão"},
            {"Prime", "DF", true, 150.0, 200.0, "Prime"},
            {"Especial", "RJ", true, 50.0, 20.0, "Padrão"}
        });
    }

    @Test
    public void testCalcularComprasUltimoMes() {
        cliente.calcularComprasUltimoMes();
        assertEquals(tipoEsperado, cliente.getTipo());
    }

    private Venda criarVenda(double valorTotal) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);

        Produto produto = new Produto("001", "Produto Teste", valorTotal, "unidade");
        ItemVenda itemVenda = new ItemVenda(produto, 1);
        return new Venda(cal.getTime(), cliente, Arrays.asList(itemVenda), "429613", false);
    }
}