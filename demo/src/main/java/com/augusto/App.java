package com.augusto;

import com.augusto.dao.EstoqueDAO;
import com.augusto.dao.ProdutoDAO;
import com.augusto.dao.VendaDAO;
import com.augusto.modelos.Produto;
import com.augusto.modelos.Estoque;
import com.augusto.modelos.Venda;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Id;

public class App {
    private static final String LOGIN = "admin";
    private static final String SENHA = "123";

    private static ProdutoDAO produtoDAO;
    private static EstoqueDAO estoqueDAO;
    private static VendaDAO vendaDAO;

    private static Scanner scanner;

    public static void main(String[] args) {
        produtoDAO = new ProdutoDAO();
        estoqueDAO = new EstoqueDAO();
        vendaDAO = new VendaDAO();

        scanner = new Scanner(System.in);

        realizarLogin();
    }

    private static void realizarLogin() {
        System.out.println("===== Sistema de Estoque =====");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (login.equals(LOGIN) && senha.equals(SENHA)) {
            exibirMenu();
        } else {
            System.out.println("Login e/ou senha inválidos. Tente novamente.");
            realizarLogin();
        }
    }

    private static void exibirMenu() {
        System.out.println("===== Menu =====");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("3. Excluir Produto");
        System.out.println("4. Consultar Quantidade Total no Estoque");
        System.out.println("5. Realizar Venda");
        System.out.println("6. Listar Vendas");
        System.out.println("0. Sair");

        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcao) {
            case 1:
                cadastrarProduto();
                break;
            case 2:
                listarProdutos();
                break;
            case 3:
                excluirProduto();
                break;
            case 4:
                consultarQuantidadeTotalEstoque();
                break;
            case 5:
                realizarVenda();
                break;
            case 6:
                listarVendas();
                break;
            case 0:
                System.out.println("Saindo do sistema...");
                return;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }

        exibirMenu();
    }

    private static void cadastrarProduto() {
        System.out.println("===== Cadastro de Produto =====");
        System.out.print("Nome do Produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço do Produto: ");
        double preco = scanner.nextDouble();
        System.out.print("Quantidade em Estoque: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Fornecedor do Produto: ");
        String fornecedor = scanner.nextLine();

        Produto produto = new Produto(nome, preco, quantidade, fornecedor);
        produtoDAO.CadastrarProduto(produto);
        estoqueDAO.salvar(new Estoque(produto, quantidade));

        System.out.println("Produto cadastrado com sucesso!");
    }
    
    private static void excluirProduto() {
        System.out.println("===== Exclusão de Produto =====");
        System.out.print("ID do Produto: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Produto produto = produtoDAO.buscarProdutoPorId(id);

        if (produto != null) {
            estoqueDAO.excluir(id);
            produtoDAO.excluirProduto(produto);
            System.out.println("Produto excluído com sucesso!");
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

private static void listarProdutos() {
    System.out.println("===== Lista de Produtos =====");
    List<Produto> produtos = produtoDAO.buscarTodosProdutos();

    if (produtos.isEmpty()) {
        System.out.println("Nenhum produto cadastrado.");
    } else {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (Produto produto : produtos) {
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Preço: " + produto.getPreco());
            System.out.println("Quantidade em estoque: " + produto.getQuantidade());
            System.out.println("Fornecedor: " + produto.getFornecedor());

            
            if (estoque != null) {
                System.out.println("Quantidade em estoque: " + estoque.getQuantidade());
                System.out.println("Status: " + (estoque.getQuantidade() > 0 ? "Disponível" : "Indisponível"));
            }
            System.out.println();
        }
    }
}
    private static void consultarQuantidadeTotalEstoque() {
        int quantidadeTotal = 0;

        List<Estoque> estoques = estoqueDAO.buscarTodos();
        for (Estoque estoque : estoques) {
            quantidadeTotal += estoque.getQuantidade();
        }

        System.out.println("Quantidade total de todos os produtos no estoque: " + quantidadeTotal);
    }

    private static void realizarVenda() {
        System.out.println("===== Realização de Venda =====");
        System.out.print("Nome do comprador: ");
        String nomeComprador = scanner.nextLine();
        System.out.print("Email do comprador: ");
        String emailComprador = scanner.nextLine();
        System.out.print("Telefone do comprador: ");
        String telefoneComprador = scanner.nextLine();

        System.out.println("Selecione o produto:");

        List<Produto> produtos = produtoDAO.buscarTodosProdutos();
        for (Produto produto : produtos) {
            Estoque estoque = estoqueDAO.buscarPorId(produto);
            String status = (estoque != null && estoque.getQuantidade() > 0) ? "Disponível" : "Indisponível";
            System.out.println(produto.getId() + " - " + produto.getNome() + " - " + status);
        }

        System.out.print("ID do produto: ");
        Long idProduto = scanner.nextLong();
        scanner.nextLine(); 

        Produto produtoSelecionado = produtoDAO.buscarProdutoPorId(idProduto);
        if (produtoSelecionado != null) {
            Estoque estoque = estoqueDAO.buscarPorId(produtoSelecionado);
            if (estoque != null && estoque.getQuantidade() > 0) {
                Venda venda = new Venda(nomeComprador, emailComprador, telefoneComprador, produtoSelecionado);
                vendaDAO.cadastrarVenda(venda);
                estoque.setQuantidade(estoque.getQuantidade() - 1);
                estoqueDAO.atualizar(estoque);
                System.out.println("Venda realizada com sucesso!");
            } else {
                System.out.println("Produto indisponível no estoque.");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void listarVendas() {
        System.out.println("===== Lista de Vendas =====");
        List<Venda> vendas = vendaDAO.buscarTodasVendas();

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda realizada.");
        } else {
            for (Venda venda : vendas) {
                System.out.println(venda);
                System.out.println();
            }
        }
    }
}

