package com.augusto.service;

import java.util.List;

import com.augusto.dao.FornecedorDAO;
import com.augusto.modelos.Fornecedor;
import com.augusto.modelos.Produto;

public class FornecedorService {
    private FornecedorDAO fornecedorDAO;

    public FornecedorService() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    public void cadastrarFornecedor(Fornecedor fornecedor) {
        fornecedorDAO.salvarFornecedor(fornecedor);
    }

    public Fornecedor buscarFornecedorPorId(Long id) {
        return fornecedorDAO.buscarFornecedorPorId(id);
    }

    public List<Produto> listarProdutosPorFornecedor(Fornecedor fornecedor) {
        return fornecedorDAO.listarProdutosPorFornecedor(fornecedor);
    }
}
