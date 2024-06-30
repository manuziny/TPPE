import fga0242.Cliente;
import fga0242.ItemVenda;
import fga0242.Produto;
import fga0242.Venda;
import fga0242.VendaService;

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
public class VendaServiceTest {
    private VendaService vendaService;
    private Cliente cliente;
    private List<Venda> vendas;
    private double expectedTotal;
    private boolean expectedElegibilidade;

    public VendaServiceTest(List<Venda> vendas, Cliente cliente, double expectedTotal, boolean expectedElegibilidade) {
        this.vendas = vendas;
        this.cliente = cliente;
        this.expectedTotal = expectedTotal;
        this.expectedElegibilidade = expectedElegibilidade;
    }

    @Before
    public void setUp() {
        vendaService = new VendaService();
        for (Venda venda : vendas) {
            vendaService.adicionarVenda(venda);
        }
    }

    @Parameters
    public static Collection<Object[]> data() {
        Cliente clienteEspecialDF = new Cliente("Especial", "DF", true);
        Cliente clientePrimeSudesteCapital = new Cliente("Prime", "Sudeste", true);
        Cliente clientePadraoSudesteInterior = new Cliente("Padrão", "Sudeste", false);
    
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        Produto produto1 = new Produto("001", "Produto 1", 100.0, "unidade");
        Venda venda1 = new Venda(calendar.getTime(), clienteEspecialDF, Arrays.asList(new ItemVenda(produto1, 1)), "4296130000000000", false);
    
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -20);
        Produto produto2 = new Produto("002", "Produto 2", 50.0, "unidade");
        Venda venda2 = new Venda(calendar.getTime(), clienteEspecialDF, Arrays.asList(new ItemVenda(produto2, 1)), "1234560000000000", false);
    
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        Venda venda3 = new Venda(calendar.getTime(), clientePrimeSudesteCapital, Arrays.asList(new ItemVenda(produto1, 2)), "1234560000000000", false);
    
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -40);
        Venda venda4 = new Venda(calendar.getTime(), clienteEspecialDF, Arrays.asList(new ItemVenda(produto1, 1)), "1234560000000000", false);
    
        return Arrays.asList(new Object[][]{
            {Arrays.asList(venda1, venda2), clienteEspecialDF, 159.0, true}, 
            {Arrays.asList(venda3), clientePrimeSudesteCapital, 232.0, true}, 
            {Arrays.asList(venda4), clienteEspecialDF, 0.0, false}, // Nenhuma venda no último mês
            {Arrays.asList(), clientePadraoSudesteInterior, 0.0, false} // Nenhuma venda registrada
        });
    }

    @Test
    public void testCalcularVendasUltimoMes() {
        double totalVendas = vendaService.calcularVendasUltimoMes(cliente);
        assertEquals(expectedTotal, totalVendas, 1e-2);
    }

    @Test
    public void testVerificarElegibilidadeClienteEspecial() {
        boolean isEligible = vendaService.verificarElegibilidadeClienteEspecial(cliente);
        assertEquals(expectedElegibilidade, isEligible);
    }
}