package com.augusto.dao;

import com.augusto.modelos.Estoque;
import com.augusto.modelos.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class EstoqueDAO {

    private EntityManagerFactory emf;

    public EstoqueDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvar(Estoque estoque) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(estoque);
        em.getTransaction().commit();
        em.close();
    }

    public void atualizar(Estoque estoque) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(estoque);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Estoque estoque = em.find(Estoque.class, id);
        em.remove(estoque);
        em.getTransaction().commit();
        em.close();
    }

    public Estoque buscarPorId(Produto produto) {
        EntityManager em = emf.createEntityManager();
        Estoque estoque = em.find(Estoque.class, produto);
        em.close();
        return estoque;
    }

    public List<Estoque> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Estoque p");
        List<Estoque> estoques = query.getResultList();
        em.close();
        return estoques;
    }
}
