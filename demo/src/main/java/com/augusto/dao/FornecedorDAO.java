package com.augusto.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.augusto.modelos.Fornecedor;
import com.augusto.modelos.Produto;

import java.util.List;


public class FornecedorDAO {
    private EntityManagerFactory emf;

    public FornecedorDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvarFornecedor(Fornecedor fornecedor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(fornecedor);
        em.getTransaction().commit();
        em.close();
    }

    public Fornecedor buscarFornecedorPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Fornecedor fornecedor = em.find(Fornecedor.class, id);
        em.close();
        return fornecedor;
    }

    public List<Produto> listarProdutosPorFornecedor(Fornecedor fornecedor) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Produto p WHERE p.fornecedor = :fornecedor");
        query.setParameter("fornecedor: ", fornecedor);
        List<Produto> produtos = query.getResultList();
        em.close();
        return produtos;
    }
}