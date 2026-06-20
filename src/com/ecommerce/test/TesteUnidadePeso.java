package com.ecommerce.test;

import com.ecommerce.model.UnidadePeso;
import com.ecommerce.model.produto.ProdutoFisico;

/**
 * Testes para validar a precisão dos cálculos de peso e frete
 * com suporte a múltiplas unidades (kg e g).
 */
public class TesteUnidadePeso {

    public static void main(String[] args) {
        System.out.println("=== TESTES DE PRECISAO DE PESO E FRETE ===\n");
        
        testeConversaoGramaParaKg();
        testeConversaoKgParaGrama();
        testeCriacaoProdutoComGramas();
        testeCriacaoProdutoComQuilogramas();
        testeCalculoFrete();
        testeValidacaoPesoInvalido();
        testeExtremo();
        
        System.out.println("\n✓ Todos os testes completados com sucesso!");
    }

    private static void testeConversaoGramaParaKg() {
        System.out.println("▶ Teste 1: Conversao de gramas para quilogramas");
        double pesoEmGramas = 500;
        double pesoEmKg = UnidadePeso.GRAMA.converterParaKg(pesoEmGramas);
        assert pesoEmKg == 0.5 : "Esperado 0.5 kg, obteve " + pesoEmKg;
        System.out.println("  500g = " + pesoEmKg + " kg ✓");
        
        pesoEmGramas = 2500;
        pesoEmKg = UnidadePeso.GRAMA.converterParaKg(pesoEmGramas);
        assert pesoEmKg == 2.5 : "Esperado 2.5 kg, obteve " + pesoEmKg;
        System.out.println("  2500g = " + pesoEmKg + " kg ✓");
    }

    private static void testeConversaoKgParaGrama() {
        System.out.println("\n▶ Teste 2: Conversao de quilogramas para gramas");
        double pesoEmKg = 1.5;
        double pesoEmGramas = UnidadePeso.QUILOGRAMA.converterDeKg(pesoEmKg);
        assert pesoEmGramas == 1500 : "Esperado 1500g, obteve " + pesoEmGramas;
        System.out.println("  1.5kg = " + pesoEmGramas + " g ✓");
        
        pesoEmKg = 0.75;
        pesoEmGramas = UnidadePeso.QUILOGRAMA.converterDeKg(pesoEmKg);
        assert pesoEmGramas == 750 : "Esperado 750g, obteve " + pesoEmGramas;
        System.out.println("  0.75kg = " + pesoEmGramas + " g ✓");
    }

    private static void testeCriacaoProdutoComGramas() {
        System.out.println("\n▶ Teste 3: Criacao de produto com peso em gramas");
        ProdutoFisico produto = new ProdutoFisico(
            "Smartphone", 
            "Telefone celular de 150 gramas", 
            999.99, 
            150.0,  // 150 gramas
            UnidadePeso.GRAMA, 
            50
        );
        
        double pesoKgArmazenado = produto.getPesoKg();
        assert pesoKgArmazenado == 0.15 : "Esperado 0.15 kg armazenado, obteve " + pesoKgArmazenado;
        System.out.println("  Produto criado com 150g");
        System.out.println("  Peso armazenado internamente: " + pesoKgArmazenado + " kg ✓");
        System.out.println("  Peso recuperado em gramas: " + produto.getPeso(UnidadePeso.GRAMA) + " g ✓");
    }

    private static void testeCriacaoProdutoComQuilogramas() {
        System.out.println("\n▶ Teste 4: Criacao de produto com peso em quilogramas");
        ProdutoFisico produto = new ProdutoFisico(
            "Caixa de Livros", 
            "10 livros pesando 2.5 kg", 
            89.99, 
            2.5,  // 2.5 kg
            UnidadePeso.QUILOGRAMA, 
            100
        );
        
        double pesoKgArmazenado = produto.getPesoKg();
        assert pesoKgArmazenado == 2.5 : "Esperado 2.5 kg armazenado, obteve " + pesoKgArmazenado;
        System.out.println("  Produto criado com 2.5kg");
        System.out.println("  Peso armazenado internamente: " + pesoKgArmazenado + " kg ✓");
        System.out.println("  Peso recuperado em gramas: " + produto.getPeso(UnidadePeso.GRAMA) + " g ✓");
    }

    private static void testeCalculoFrete() {
        System.out.println("\n▶ Teste 5: Calculo de frete deve ser identico independente da unidade de entrada");
        
        // Mesmo produto criado de duas formas diferentes
        ProdutoFisico produtoEmGramas = new ProdutoFisico(
            "Produto A", "Teste", 50.0, 500.0, UnidadePeso.GRAMA, 10
        );
        
        ProdutoFisico produtoEmKg = new ProdutoFisico(
            "Produto B", "Teste", 50.0, 0.5, UnidadePeso.QUILOGRAMA, 10
        );
        
        double freteA = produtoEmGramas.calcularFrete();
        double freteB = produtoEmKg.calcularFrete();
        
        assert freteA == freteB : "Frete diferente: " + freteA + " vs " + freteB;
        System.out.println("  Produto criado com 500g: frete = R$ " + String.format("%.2f", freteA));
        System.out.println("  Produto criado com 0.5kg: frete = R$ " + String.format("%.2f", freteB));
        System.out.println("  Fretes identicos ✓");
    }

    private static void testeValidacaoPesoInvalido() {
        System.out.println("\n▶ Teste 6: Validacao de pesos invalidos");
        
        try {
            new ProdutoFisico("Produto", "Teste", 50.0, -100.0, UnidadePeso.GRAMA, 10);
            assert false : "Deveria ter lancado excecao para peso negativo";
        } catch (IllegalArgumentException e) {
            System.out.println("  Peso negativo rejeitado ✓");
        }
        
        try {
            new ProdutoFisico("Produto", "Teste", 50.0, 0.0, UnidadePeso.QUILOGRAMA, 10);
            assert false : "Deveria ter lancado excecao para peso zero";
        } catch (IllegalArgumentException e) {
            System.out.println("  Peso zero rejeitado ✓");
        }
        
        try {
            new ProdutoFisico("Produto", "Teste", 50.0, 2_000_000.0, UnidadePeso.GRAMA, 10);
            assert false : "Deveria ter lancado excecao para peso acima do limite";
        } catch (IllegalArgumentException e) {
            System.out.println("  Peso acima do limite rejeitado ✓");
        }
    }

    private static void testeExtremo() {
        System.out.println("\n▶ Teste 7: Casos extremos de precisao");
        
        // Teste com valor muito pequeno em gramas
        ProdutoFisico produtoLeve = new ProdutoFisico(
            "Parafuso", "1 parafuso de 5 gramas", 0.50, 5.0, UnidadePeso.GRAMA, 1000
        );
        double pesoKg = produtoLeve.getPesoKg();
        assert pesoKg == 0.005 : "Esperado 0.005 kg, obteve " + pesoKg;
        System.out.println("  5g convertido para " + pesoKg + " kg ✓");
        System.out.println("  Frete calculado: R$ " + String.format("%.2f", produtoLeve.calcularFrete()));
        
        // Teste com precisao decimal
        ProdutoFisico produtoMedio = new ProdutoFisico(
            "Livro", "Um livro", 25.0, 350.5, UnidadePeso.GRAMA, 50
        );
        double pesoKgMedio = produtoMedio.getPesoKg();
        assert pesoKgMedio == 0.3505 : "Esperado 0.3505 kg, obteve " + pesoKgMedio;
        System.out.println("  350.5g convertido para " + pesoKgMedio + " kg ✓");
    }
}
