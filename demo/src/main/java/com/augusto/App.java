package com.augusto;

import com.augusto.dao.FornecedorDAO;
import com.augusto.modelos.Fornecedor;
import com.augusto.dao.EstoqueDAO;
import com.augusto.dao.ProdutoDAO;
import com.augusto.dao.VendaDAO;
import com.augusto.modelos.Produto;
import com.augusto.modelos.Estoque;
import com.augusto.modelos.Venda;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final String LOGIN = "admin";
    private static final String SENHA = "123";
    private static final String SENHA_HASH = BCrypt.hashpw(SENHA, BCrypt.gensalt());

    private static ProdutoDAO produtoDAO;
    private static EstoqueDAO estoqueDAO;
    private static VendaDAO vendaDAO;

    private static Scanner scanner;
    private static FornecedorDAO fornecedorDAO;

    public static void main(String[] args) {
        produtoDAO = new ProdutoDAO();
        estoqueDAO = new EstoqueDAO();
        vendaDAO = new VendaDAO();
        fornecedorDAO = new FornecedorDAO();


        scanner = new Scanner(System.in);

        realizarLogin();
    }

    private static void realizarLogin() {
        System.out.println("===== Sistema de Estoque =====");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (login.equals(LOGIN) && BCrypt.checkpw(senha, SENHA_HASH)) {
        exibirMenu();
        } else {
        System.out.println("Login e/ou senha inválidos. Tente novamente.");
        realizarLogin();
        }
    }

    private static void exibirMenu() {
        System.out.println("===== Menu =====");
        System.out.println("1. Gerenciar Produtos");
        System.out.println("2. Consultar Quantidade Total no Estoque");
        System.out.println("3. Realizar Venda");
        System.out.println("4. Listar Vendas");
        System.out.println("5. Gerenciar Fornecedores");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                MenuProdutos();
                break;
            case 2:
                consultarQuantidadeTotalEstoque();
                break;    
            case 3:
                realizarVenda();
                break;
            case 4:
                listarVendas();
                break;
            case 5:
                exibirMenuFornecedores();
                break;
            case 0:
                System.out.println("Saindo do sistema...");
                return;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
            exibirMenu();
    }

    public static void MenuProdutos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("===== Menu de Produtos =====");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Buscar produto por ID");
            System.out.println("3. Listar todos os produtos");
            System.out.println("4. Excluir produto");
            System.out.println("0. Voltar ao menu principal");
            System.out.println("============================");

            System.out.print("Digite a opção desejada:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    buscarProdutoPorId();
                    break;
                case 3:
                    listarProdutos();
                    break;              
                case 4:
                    excluirProduto();
                    break;
                case 0:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Digite novamente.");
                    break;
            }
        }
    }


    private static void cadastrarProduto() {
        System.out.println("===== Cadastro de Produto =====");

        try {
            String nome = null;
            while (nome == null || nome.isEmpty()) {
                try {
                    System.out.print("Nome do Produto: ");
                    nome = scanner.nextLine();

                    if (nome.isEmpty()) {
                        throw new Exception("O nome do produto é obrigatório.");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }

            double preco = 0;
                boolean inputValido = false;
                while (!inputValido) {
                    try {
                        System.out.print("Preço do Produto: ");
                        String precoStr = scanner.nextLine();

                        preco = Double.parseDouble(precoStr);

                        if (preco <= 0) {
                            throw new Exception("O preço do produto deve ser maior que zero.");
                        }

                        inputValido = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Erro: O preço do produto deve ser um número válido.");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }

            int quantidade = 0;
                inputValido = false;
                while (!inputValido) {
                    try {
                        System.out.print("Quantidade em Estoque: ");
                        String quantidadeStr = scanner.nextLine();

                        quantidade = Integer.parseInt(quantidadeStr);

                        if (quantidade <= 0) {
                            throw new Exception("A quantidade em estoque deve ser maior que zero.");
                        }

                        inputValido = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Erro: A quantidade em estoque deve ser um número válido.");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }

            String fornecedor = null;
            while (fornecedor == null || fornecedor.length() <= 2) {
                try {
                    System.out.print("Fornecedor do Produto: ");
                    fornecedor = scanner.nextLine();

                    if (fornecedor.length() <= 2) {
                        throw new Exception("O nome do fornecedor deve conter mais de dois caracteres.");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }

        Produto produto = new Produto(nome, preco, quantidade, fornecedor);
        produtoDAO.CadastrarProduto(produto);
        estoqueDAO.salvar(new Estoque(produto, quantidade));

        System.out.println("Produto cadastrado com sucesso!");
    } catch (Exception e) {
        System.out.println("Ocorreu um erro ao cadastrar o produto: " + e.getMessage());
    }
}

    private static void buscarProdutoPorId() {
    System.out.println("===== Buscar Produto por ID =====");
    System.out.print("ID do Produto: ");
    Long id = scanner.nextLong();
    scanner.nextLine();

    Produto produto = produtoDAO.buscarProdutoPorId(id);

    if (produto != null) {
        System.out.println("Produto encontrado:");
        System.out.println("ID: " + produto.getId());
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Preço: " + produto.getPreco());
        System.out.println("Quantidade em Estoque: " + produto.getQuantidade());
    } else {
        System.out.println("Produto não encontrado!");
    }
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
    } 
         for (Produto produto : produtos) {
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Preço: " + produto.getPreco());
            System.out.println("Quantidade em estoque: " + produto.getQuantidade());

            Estoque estoque = estoqueDAO.buscarEstoquePorProduto(produto);
            if (estoque != null) {
                System.out.println("Quantidade em estoque: " + estoque.getQuantidade());
                System.out.println("Status: " + (estoque.getQuantidade() > 0 ? "Disponível" : "Indisponível"));
            }
            System.out.println();
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
        Estoque estoque = estoqueDAO.buscarPorId(produto.getId());
        String status = (estoque != null && estoque.getQuantidade() > 0) ? "Disponível" : "Indisponível";
        System.out.println(produto.getId() + " - " + produto.getNome() + " - " + status);
    }

    System.out.print("ID do produto: ");
    Long idProduto = scanner.nextLong();
    scanner.nextLine(); 

    Produto produtoSelecionado = produtoDAO.buscarProdutoPorId(idProduto);
    if (produtoSelecionado != null) {
        Estoque estoque = estoqueDAO.buscarPorId(idProduto);
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

    private static void exibirMenuFornecedores() {
    System.out.println("===== Gerenciar Fornecedores =====");
    System.out.println("1. Cadastrar Fornecedor");
    System.out.println("2. Listar Fornecedores");
    System.out.println("3. Buscar Fornecedor por ID");
    System.out.println("4. Atualizar Fornecedor");
    System.out.println("5. Excluir Fornecedor");
    System.out.println("0. Voltar");

    System.out.print("Opção: ");
    int opcao = scanner.nextInt();
    scanner.nextLine();

    switch (opcao) {
        case 1:
            cadastrarFornecedor();
            break;
        case 2:
            listarFornecedores();
            break;
        case 3:
            buscarFornecedorPorId();
            break;
        case 4:
            atualizarFornecedor();
            break;
        case 5:
            excluirFornecedor();
            break;
        case 0:
            return;
        default:
            System.out.println("Opção inválida. Tente novamente.");
    }
    exibirMenuFornecedores();
}

    private static void cadastrarFornecedor() {
    System.out.println("===== Cadastro de Fornecedor =====");
    
    System.out.print("Nome do Fornecedor: ");
    String nome = scanner.nextLine();
    
    System.out.print("Telefone do Fornecedor: ");
    String telefone = scanner.nextLine();
    
    Fornecedor fornecedor = new Fornecedor(nome, telefone);
    fornecedorDAO.SalvarFornecedor(fornecedor);
    
    System.out.println("Fornecedor cadastrado com sucesso!");
}

private static void listarFornecedores() {
    System.out.println("===== Lista de Fornecedores =====");
    List<Fornecedor> fornecedores = fornecedorDAO.listarFornecedores();

    if (fornecedores.isEmpty()) {
        System.out.println("Nenhum fornecedor cadastrado.");
    } else {
        for (Fornecedor fornecedor : fornecedores) {
            System.out.println("ID: " + fornecedor.getId());
            System.out.println("Nome: " + fornecedor.getNome());
            System.out.println("Telefone: " + fornecedor.getTelefone());
            System.out.println();
        }
    }
}

private static void buscarFornecedorPorId() {
    System.out.println("===== Buscar Fornecedor por ID =====");
    System.out.print("ID do Fornecedor: ");
    Long id = scanner.nextLong();
    scanner.nextLine();
    
    Fornecedor fornecedor = fornecedorDAO.buscarFornecedorPorId(id);

    if (fornecedor != null) {
        System.out.println("ID: " + fornecedor.getId());
        System.out.println("Nome: " + fornecedor.getNome());
        System.out.println("Telefone: " + fornecedor.getTelefone());
    } else {
        System.out.println("Fornecedor não encontrado!");
    }
}

private static void atualizarFornecedor() {
    System.out.println("===== Atualizar Fornecedor =====");
    System.out.print("ID do Fornecedor: ");
    Long id = scanner.nextLong();
    scanner.nextLine();
    
    Fornecedor fornecedor = fornecedorDAO.buscarFornecedorPorId(id);

    if (fornecedor != null) {
        System.out.print("Novo Nome do Fornecedor: ");
        String novoNome = scanner.nextLine();
        fornecedor.setNome(novoNome);
        
        System.out.print("Novo Telefone do Fornecedor: ");
        String novoTelefone = scanner.nextLine();
        fornecedor.setTelefone(novoTelefone);
        
        fornecedorDAO.atualizarFornecedor(fornecedor);
        System.out.println("Fornecedor atualizado com sucesso!");
    } else {
        System.out.println("Fornecedor não encontrado!");
    }
}

private static void excluirFornecedor() {
    System.out.println("===== Excluir Fornecedor =====");
    System.out.print("ID do Fornecedor: ");
    Long id = scanner.nextLong();
    scanner.nextLine();
    
    Fornecedor fornecedor = fornecedorDAO.buscarFornecedorPorId(id);

    if (fornecedor != null) {
        fornecedorDAO.excluirFornecedor(fornecedor);
        System.out.println("Fornecedor excluído com sucesso!");
    } else {
        System.out.println("Fornecedor não encontrado!");
    }
  }
}


