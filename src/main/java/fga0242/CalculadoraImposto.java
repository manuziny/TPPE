package fga0242;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*
Impactos da refatoração 'Substituir método por objeto-método':
* Encapsulamento de Lógica de Negócio - A lógica de cálculo de impostos foi movida para uma nova classe (`CalculadoraImpostos`), que agora encapsula essa responsabilidade. Isso facilita futuras alterações na lógica de cálculo sem afetar outras partes do código.
* Melhoria da Manutenção - A manutenção do código fica mais simples, pois as alterações no cálculo de impostos podem ser feitas diretamente na classe `CalculadoraImpostos`. Isso diminui o risco de introduzir erros ao modificar o método principal da classe `Venda`.
* Reutilização - A classe `CalculadoraImpostos` pode ser reutilizada em outras partes do sistema, onde seja necessário realizar cálculos de impostos semelhantes. Isso evita duplicação de código e melhora a consistência.
* Clareza e Organização - O código da classe `Venda` fica mais claro e organizado, com cada classe tendo uma responsabilidade específica. Isso melhora a legibilidade e a compreensão do código.
* Extensibilidade - A refatoração facilita a adição de novas regras de impostos ou a adaptação das existentes para diferentes clientes ou estados. Alterações podem ser feitas na classe `CalculadoraImpostos` sem a necessidade de modificar diretamente a classe `Venda`.
*/

class CalculadoraImpostos {
    private Cliente cliente;
    private double valorTotal;
    private double icms;
    private double impostoMunicipal;

    public CalculadoraImpostos(Cliente cliente, double valorTotal) {
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

    public double[] calcular() {
        if (cliente.getEstado().equals("DF")) {
            this.icms = 0.18;
            this.impostoMunicipal = 0.00;
        } else {
            this.icms = 0.12;
            this.impostoMunicipal = 0.04;
        }
        double valorIcms = valorTotal * this.icms;
        double valorImpostoMunicipal = valorTotal * this.impostoMunicipal;
        return new double[]{valorIcms, valorImpostoMunicipal};
    }

    public double getIcms() {
        return icms;
    }

    public double getImpostoMunicipal() {
        return impostoMunicipal;
    }
}