import fga0242.Produto;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CadastroTipoProdutoTest {

    private String codigo;
    private String descricao;
    private double valor;
    private String unidade;
    private Produto produto;

    public CadastroTipoProdutoTest(String codigo, String descricao, double valor, String unidade) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
        this.unidade = unidade;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"1", "Livro de histórias infantis", 10.0, "unidade"},
            {"2", "Porcelanato Azul", 20.0, "cm2"},
            {"3", "Parafuso", 30.0, "peça"}
        });
    }

    @Test
    public void testCadastraProdutoValorPositivo() {
        produto = new Produto(codigo, descricao, valor, unidade);

        assertEquals(codigo, produto.getCodigo());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(valor, produto.getValor(), 1e-6);
        assertEquals(unidade, produto.getUnidade());
    }
}
