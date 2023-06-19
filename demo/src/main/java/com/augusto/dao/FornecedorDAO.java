package com.augusto.dao;

import com.augusto.modelos.Fornecedor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class FornecedorDAO extends ProdutoDAO{

    private EntityManagerFactory emf;

    public FornecedorDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void SalvarFornecedor(Fornecedor fornecedor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(fornecedor);
        em.getTransaction().commit();
        em.close();
    }

    public List<Fornecedor> listarFornecedores() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT f FROM Fornecedor f");
        List<Fornecedor> fornecedores = query.getResultList();
        em.close();
        return fornecedores;
    }

    public Fornecedor buscarFornecedorPorId(long id) {
        EntityManager em = emf.createEntityManager();
        Fornecedor fornecedor = em.find(Fornecedor.class, id);
        em.close();
        return fornecedor;
    }

    public void atualizarFornecedor(Fornecedor fornecedor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(fornecedor);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirFornecedor(Fornecedor fornecedor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        fornecedor = em.merge(fornecedor);
        em.remove(fornecedor);
        em.getTransaction().commit();
        em.close();
    }
}
