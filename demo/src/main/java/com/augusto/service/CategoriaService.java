package com.augusto.service;

import java.util.List;

import com.augusto.dao.CategoriaDAO;
import com.augusto.modelos.Categoria;

public class CategoriaService {
    private CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public void cadastrarCategoria(Categoria categoria) {
        categoriaDAO.salvar(categoria);
    }

    public void atualizarCategoria(Categoria categoria) {
        categoriaDAO.atualizar(categoria);
    }

    public void excluirCategoria(Categoria categoria) {
        categoriaDAO.excluir(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.buscarTodos();
    }

    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaDAO.buscarPorId(id);
    }
}
