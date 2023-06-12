package com.augusto.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.augusto.modelos.Produto;

import java.util.List;

public class ProdutoDAO{
    private EntityManagerFactory emf;

    public ProdutoDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void CadastrarProduto(Produto produto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(produto);
        em.getTransaction().commit();
        em.close();
    }

    public Produto buscarProdutoPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Produto produto = em.find(Produto.class, id);
        em.close();
        return produto;
    }

    public List<Produto> buscarTodosProdutos() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Pessoa p");
        List<Produto> produtos = query.getResultList();
        em.close();
        return produtos;
    }

    public void atualizarProduto(Produto produto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(produto);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirProduto(Produto produto) {
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        System.out.println(produto.getId());
        produto = em.merge(produto);
        em.remove(produto);
        em.getTransaction().commit();
        em.close();
    }
}

