package fga0242;

/* 
Impactos da refatoração 'extrair classe':
* Isolamento de Responsabilidades - Cada método agora tem uma responsabilidade clara e específica, 
o que está em linha com o princípio da responsabilidade única (Single Responsibility Principle). Isso melhora a modularidade do código.
* Facilidade de Manutenção - Com a separação, qualquer alteração necessária 
na lógica de identificação de um produto pode ser realizada diretamente na classe 
'IdentificacaoProduto', minimizando impactos na classe 'Produto'.
*/

public class Produto {
    // Atributo 'identificacao' é agora uma instância da classe 'IdentificacaoProduto'
    private IdentificacaoProduto identificacao;
    private double valor;
    private String unidade;

    // Construtor de 'Produto' agora inicializa 'identificacao' usando a nova classe
    public Produto(String codigo, String descricao, double valor, String unidade) {
        this.identificacao = new IdentificacaoProduto(codigo, descricao); // Instancia a nova classe
        this.valor = valor;
        this.unidade = unidade;
    }

    // Métodos 'getCodigo' e 'getDescricao' agora delegam para a instância de 'IdentificacaoProduto'
    public String getCodigo() {
        return identificacao.getCodigo(); // Delegação
    }

    public String getDescricao() {
        return identificacao.getDescricao(); // Delegação
    }

    public double getValor() {
        return valor;
    }

    public String getUnidade() {
        return unidade;
    }
}
