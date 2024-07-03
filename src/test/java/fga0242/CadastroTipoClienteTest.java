package test.java.fga0242;

import fga0242.Cliente;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CadastroTipoClienteTest {
    
    private String tipo;
    private String estado;
    private boolean capital;
    private Cliente cliente;

    public CadastroTipoClienteTest(String tipo, String estado, boolean capital) {
        this.tipo = tipo;
        this.estado = estado;
        this.capital = capital;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"Padrão", "DF", true},
            {"Especial", "GO", false},
            {"Prime", "SP", true}
        });
    }

    @Test
    public void testCadastraCliente() {
        cliente = new Cliente(tipo, estado, capital);

        assertEquals(tipo, cliente.getTipo());
        assertEquals(estado, cliente.getEstado());
        assertEquals(capital, cliente.isCapital());
    }

}
