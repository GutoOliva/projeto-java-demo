package com.augusto;

import java.util.Scanner;

import com.augusto.modelos.*;
import com.augusto.service.*;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EstoqueService estoqueService = new EstoqueService();
        ProdutoService produtoService = new ProdutoService();
        CategoriaService categoriaService = new CategoriaService();
        FornecedorService fornecedorService = new FornecedorService();

        while (true) {
            System.out.println("==== Controle de Estoque ====");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Cadastrar Categoria");
            System.out.println("4. Listar Categorias");
            System.out.println("5. Cadastrar Fornecedor");
            System.out.println("6. Listar Fornecedores");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:   
                System.out.println("==== Cadastro de Produto ====");
                System.out.print("Digite o nome do produto: ");
                String nomeProduto = scanner.nextLine();

                System.out.print("Digite o preço do produto: ");
                double precoProduto = scanner.nextDouble();

                System.out.print("Digite a quantidade do produto: ");
                int quantidadeProduto = scanner.nextInt();

                scanner.nextLine(); 

                System.out.print("Digite o fornecedor do produto: ");
                String fornecedorProduto = scanner.nextLine();

                Produto produto = new Produto(nomeProduto, precoProduto, quantidadeProduto, fornecedorProduto);

                produtoService.cadastrarProduto(produto);

                System.out.println("Produto cadastrado com sucesso!");

                break;             
                case 2:
                    
                    break;
                case 3:
                    // Cadastrar Categoria
                    // Implemente a lógica de interação com o usuário para cadastrar uma categoria
                    break;
                case 4:
                    // Listar Categorias
                    // Implemente a lógica para exibir a lista de categorias cadastradas
                    break;
                case 5:
                    // Cadastrar Fornecedor
                    // Implemente a lógica de interação com o usuário para cadastrar um fornecedor
                    break;
                case 6:
                    // Listar Fornecedores
                    // Implemente a lógica para exibir a lista de fornecedores cadastrados
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
