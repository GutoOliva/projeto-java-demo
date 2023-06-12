package com.augusto.service;

import java.util.List;

import com.augusto.dao.ProdutoDAO;
import com.augusto.modelos.Produto;

public class ProdutoService {
    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public void cadastrarProduto(Produto produto) {
        produtoDAO.CadastrarProduto(produto);
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoDAO.buscarProdutoPorId(id);
    }

    public List<Produto> buscarTodosProdutos() {
        return produtoDAO.buscarTodosProdutos();
    }

    public void atualizarProduto(Produto produto) {
        produtoDAO.atualizarProduto(produto);
    }

    public void excluirProduto(Produto produto) {
        produtoDAO.excluirProduto(produto);
    }
}
