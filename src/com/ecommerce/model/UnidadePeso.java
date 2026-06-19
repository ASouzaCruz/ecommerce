package com.ecommerce.model;

/**
 * Enumeração para gerenciar unidades de peso com conversão automática para quilogramas.
 * Garante precisão nos cálculos de frete independente da unidade de entrada.
 */
public enum UnidadePeso {
    GRAMA("g", 0.001),
    QUILOGRAMA("kg", 1.0);

    private final String simbolo;
    private final double fatorConversaoParaKg;

    UnidadePeso(String simbolo, double fatorConversaoParaKg) {
        this.simbolo = simbolo;
        this.fatorConversaoParaKg = fatorConversaoParaKg;
    }

    /**
     * Converte um valor da unidade atual para quilogramas.
     * @param valor o valor em unidade
     * @return o valor convertido em kg
     */
    public double converterParaKg(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O peso nao pode ser negativo.");
        }
        return valor * fatorConversaoParaKg;
    }

    /**
     * Converte um valor em quilogramas para a unidade atual.
     * @param pesoEmKg o peso em kg
     * @return o valor convertido para esta unidade
     */
    public double converterDeKg(double pesoEmKg) {
        if (pesoEmKg < 0) {
            throw new IllegalArgumentException("O peso nao pode ser negativo.");
        }
        return pesoEmKg / fatorConversaoParaKg;
    }

    /**
     * Obtém a unidade a partir do símbolo (case-insensitive).
     * @param simbolo o símbolo da unidade ("g" ou "kg")
     * @return a UnidadePeso correspondente
     * @throws IllegalArgumentException se o símbolo for inválido
     */
    public static UnidadePeso porSimbolo(String simbolo) {
        if (simbolo == null || simbolo.isBlank()) {
            throw new IllegalArgumentException("Simbolo de unidade nao pode estar vazio.");
        }
        String normalized = simbolo.toLowerCase().trim();
        for (UnidadePeso unidade : values()) {
            if (unidade.simbolo.equals(normalized)) {
                return unidade;
            }
        }
        throw new IllegalArgumentException("Unidade de peso invalida: '" + simbolo + "'. Use 'g' ou 'kg'.");
    }

    public String getSimbolo() {
        return simbolo;
    }

    public double getFatorConversao() {
        return fatorConversaoParaKg;
    }

    @Override
    public String toString() {
        return simbolo;
    }
}
