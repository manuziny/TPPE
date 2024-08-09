package fga0242;

// Nova classe 'IdentificacaoProduto'
public class IdentificacaoProduto {
    private String codigo;
    private String descricao;

    // Construtor para inicializar 'codigo' e 'descricao'
    public IdentificacaoProduto(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}