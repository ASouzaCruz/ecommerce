package com.ecommerce.model.produto;

import com.ecommerce.exception.EstoqueInsuficienteException;
import com.ecommerce.model.UnidadePeso;

public class ProdutoFisico extends Produto {
    private double pesoKg; // Sempre armazenado em kg para garantir precisão
    private int estoque;
    private static final double TARIFA_KG = 5.0;

    /**
     * Construtor legado mantido para compatibilidade.
     * Assume que o peso é fornecido em quilogramas.
     */
    public ProdutoFisico(String nome, String descricao, double preco, double pesoKg, int estoque) {
        this(nome, descricao, preco, pesoKg, UnidadePeso.QUILOGRAMA, estoque);
    }

    /**
     * Construtor com suporte a diferentes unidades de peso.
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param preco preço em reais
     * @param pesoValor valor do peso na unidade especificada
     * @param unidade unidade do peso (kg ou g)
     * @param estoque quantidade em estoque
     */
    public ProdutoFisico(String nome, String descricao, double preco, double pesoValor, UnidadePeso unidade, int estoque) {
        super(nome, descricao, preco);
        validarPeso(pesoValor);
        if (estoque < 0) throw new IllegalArgumentException("Estoque nao pode ser negativo.");
        
        // Converte e armazena sempre em kg
        this.pesoKg = unidade.converterParaKg(pesoValor);
        this.estoque = estoque;
    }

    /**
     * Valida se o peso é válido (positivo e dentro de limites razoáveis).
     */
    private void validarPeso(double pesoValor) {
        if (pesoValor <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero.");
        }
        // Limite superior: 1000 kg (1 tonelada) é um limite razoável para produtos de e-commerce
        if (pesoValor > 1_000_000) {
            throw new IllegalArgumentException("Peso nao pode exceder 1000 kg (limite maximo para shipping).");
        }
    }

    @Override
    public double calcularFrete() { return pesoKg * TARIFA_KG; }

    @Override
    public boolean estaDisponivel() { return isAtivo() && estoque > 0; }

    @Override
    public String getTipo() { return "FISICO"; }

    public void reduzirEstoque(int qtd) {
        if (qtd > estoque) throw new EstoqueInsuficienteException(getNome(), estoque, qtd);
        this.estoque -= qtd;
    }

    public void aumentarEstoque(int qtd) {
        if (qtd < 0) throw new IllegalArgumentException("Quantidade invalida.");
        this.estoque += qtd;
    }

    /**
     * Obtém o peso em quilogramas (unidade interna).
     */
    public double getPesoKg() { return pesoKg; }

    /**
     * Obtém o peso convertido para a unidade especificada.
     * @param unidade a unidade desejada
     * @return o peso na unidade especificada
     */
    public double getPeso(UnidadePeso unidade) {
        return unidade.converterDeKg(pesoKg);
    }

    /**
     * Define o peso em quilogramas (unidade interna).
     * Mantido para compatibilidade.
     */
    public void setPesoKg(double p) {
        validarPeso(p);
        this.pesoKg = p;
    }

    /**
     * Define o peso com unidade especificada.
     * Converte automaticamente para kg.
     * @param pesoValor valor do peso
     * @param unidade unidade do peso
     */
    public void setPeso(double pesoValor, UnidadePeso unidade) {
        validarPeso(pesoValor);
        this.pesoKg = unidade.converterParaKg(pesoValor);
    }

    public int getEstoque() { return estoque; }
    public void setEstoque(int e) { this.estoque = e; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Estoque: %d | Peso: %.2f kg | Frete: R$ %.2f", estoque, pesoKg, calcularFrete());
    }
}
