package com.augusto.service;

import com.augusto.dao.EstoqueDAO;
import com.augusto.modelos.Estoque;
import com.augusto.modelos.Produto;

public class EstoqueService {
    private EstoqueDAO estoqueDAO;

    public EstoqueService() {
        this.estoqueDAO = new EstoqueDAO();
    }

    public void atualizarEstoque(Produto produto, int quantidade) {
        Estoque estoque = estoqueDAO.buscarPorId(produto);

        if (estoque != null) {
            estoque.setQuantidade(estoque.getQuantidade() + quantidade);
            estoqueDAO.atualizar(estoque);
        } else {
            estoque = new Estoque(produto, quantidade);
            estoqueDAO.salvar(estoque);
        }
    }

    public void removerEstoque(Produto produto, int quantidade) {
        Estoque estoque = estoqueDAO.buscarPorId(produto);

        if (estoque != null) {
            int novaQuantidade = estoque.getQuantidade() - quantidade;

            if (novaQuantidade >= 0) {
                estoque.setQuantidade(novaQuantidade);
                estoqueDAO.atualizar(estoque);
            } else {
                System.out.println("Quantidade insuficiente em!");
            }
        } 
        else{
        }
    }

    public int verificarEstoque(Produto produto) {
        Estoque estoque = estoqueDAO.buscarPorId(produto);

        if (estoque != null) {
            return estoque.getQuantidade();
        } else {
            return 0;
        }
    }
}
