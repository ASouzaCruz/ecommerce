package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.model.produto.*;
import com.ecommerce.model.usuario.*;

import java.io.*;
import java.util.*;

public class PersistenciaService {
    private static final String DIR = "dados/";
    private static final String ARQ_PRODUTOS  = DIR + "produtos.csv";
    private static final String ARQ_USUARIOS  = DIR + "usuarios.csv";
    private static final String ARQ_PEDIDOS   = DIR + "pedidos.csv";
    private static final String ARQ_ITENS     = DIR + "itens_pedido.csv";
    private static final String ARQ_ENDERECOS = DIR + "enderecos.csv";
    private static final String SEP = ";";

    public PersistenciaService() {
        new File(DIR).mkdirs();
    }

    // ── PRODUTOS ─────────────────────────────────────────────────────────────
    public void salvarProdutos(List<Produto> produtos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_PRODUTOS))) {
            pw.println("id;tipo;nome;descricao;preco;ativo;extra1;extra2");
            for (Produto p : produtos) {
                if (p instanceof ProdutoFisico f) {
                    pw.printf("%s;FISICO;%s;%s;%.2f;%b;%.2f;%d%n",
                            f.getId(), f.getNome(), f.getDescricao(), f.getPreco(),
                            f.isAtivo(), f.getPesoKg(), f.getEstoque());
                } else if (p instanceof ProdutoDigital d) {
                    pw.printf("%s;DIGITAL;%s;%s;%.2f;%b;%s;%s%n",
                            d.getId(), d.getNome(), d.getDescricao(), d.getPreco(),
                            d.isAtivo(), d.getLinkDownload(), d.getFormato());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public List<Produto> carregarProdutos() {
        List<Produto> lista = new ArrayList<>();
        File f = new File(ARQ_PRODUTOS);
        if (!f.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine(); // header
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] c = linha.split(SEP, -1);
                if (c.length < 8) continue;
                Produto p;
                if ("FISICO".equals(c[1])) {
                    p = new ProdutoFisico(c[2], c[3], Double.parseDouble(c[4].replace(",",".")),
                            Double.parseDouble(c[6].replace(",",".")), Integer.parseInt(c[7]));
                } else {
                    p = new ProdutoDigital(c[2], c[3], Double.parseDouble(c[4].replace(",",".")), c[6], c[7]);
                }
                p.setId(c[0]);
                p.setAtivo(Boolean.parseBoolean(c[5]));
                lista.add(p);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }
        return lista;
    }

    // ── USUARIOS ─────────────────────────────────────────────────────────────
    public void salvarUsuarios(List<com.ecommerce.model.usuario.Usuario> usuarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_USUARIOS));PrintWriter pwEnd = new PrintWriter(new FileWriter(ARQ_ENDERECOS))) {
            pw.println("id;tipo;nome;email;senha;extra1;extra2");
            pwEnd.println("usuarioId;rua;numero;bairro;cidade;estado;cep");
            for (var u : usuarios) {
                if (u instanceof Cliente cl) {
                    pw.printf("%s;CLIENTE;%s;%s;%s;%s;%n",
                            cl.getId(), cl.getNome(), cl.getEmail(), cl.getSenha(), cl.getCpf());
                    for (Endereco e : cl.getEnderecos()) {
                        pwEnd.printf("%s;%s;%s;%s;%s;%s;%s%n",
                                cl.getId(),
                                e.getRua(),
                                e.getNumero(),
                                e.getBairro(),
                                e.getCidade(),
                                e.getEstado(),
                                e.getCep());
                    }
                } else if (u instanceof Administrador adm) {
                    pw.printf("%s;ADMIN;%s;%s;%s;%s;%b%n",
                            adm.getId(), adm.getNome(), adm.getEmail(), adm.getSenha(),
                            adm.getCargo(), adm.isSuperAdmin());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuarios: " + e.getMessage());
        }
    }

    public List<com.ecommerce.model.usuario.Usuario> carregarUsuarios() {
        List<com.ecommerce.model.usuario.Usuario> lista = new ArrayList<>();
        File f = new File(ARQ_USUARIOS);
        File endFile = new File(ARQ_ENDERECOS);
        if (!f.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] c = linha.split(SEP, -1);
                if (c.length < 5) continue;
                com.ecommerce.model.usuario.Usuario u;
                if ("CLIENTE".equals(c[1])) {
                    u = new Cliente(c[2], c[3], c[4], c[5].isBlank() ? "00000000000" : c[5]);
                } else {
                    u = new Administrador(c[2], c[3], c[4],
                            c[5].isBlank() ? "Cargo" : c[5],
                            c.length > 6 && Boolean.parseBoolean(c[6]));
                }
                u.setId(c[0]);
                lista.add(u);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuarios: " + e.getMessage());
        }
        if (endFile.exists()) {

            try (BufferedReader br = new BufferedReader(new FileReader(endFile))) {

                br.readLine();

                String linha;

                while ((linha = br.readLine()) != null) {

                    String[] c = linha.split(SEP, -1);

                    if (c.length < 7) continue;

                    String usuarioId = c[0];

                    for (Usuario u : lista) {

                        if (u.getId().equals(usuarioId) && u instanceof Cliente cliente) {

                            cliente.adicionarEndereco(
                                new Endereco(
                                    c[1], // rua 
                                    c[2], // numero
                                    c[3], // bairro
                                    c[4], // cidade
                                    c[5], // estado
                                    c[6]  // cep
                                )
                            );

                            break;
                        }
                    }
                }

            } catch (IOException e) {
                System.err.println("Erro ao carregar enderecos: " + e.getMessage());
            }
        }
        return lista;
    }

    // ── PEDIDOS (simplificado) ────────────────────────────────────────────────
    public void salvarPedidos(List<Pedido> pedidos, List<Produto> produtos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_PEDIDOS));
            PrintWriter pwI = new PrintWriter(new FileWriter(ARQ_ITENS))) {
            pw.println("id;clienteId;estado;dataCriacao;dataAtualizacao");
            pwI.println("pedidoId;produtoId;quantidade;precoUnitario");
            for (Pedido p : pedidos) {
                pw.printf("%s;%s;%s;%s;%s%n",
                        p.getId(), p.getCliente().getId(), p.getEstado(),
                        p.getDataCriacao(), p.getDataAtualizacao());
                for (ItemPedido item : p.getItens()) {
                    pwI.printf("%s;%s;%d;%.2f%n",
                            p.getId(), item.getProduto().getId(),
                            item.getQuantidade(), item.getPrecoUnitario());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pedidos: " + e.getMessage());
        }
    }
}
