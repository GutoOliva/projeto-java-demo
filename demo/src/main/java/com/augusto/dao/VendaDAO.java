package com.augusto.dao;

import com.augusto.modelos.Venda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class VendaDAO{
    private EntityManagerFactory emf;

    public VendaDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void cadastrarVenda(Venda venda) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(venda);
        em.getTransaction().commit();
        em.close();
    }

    public Venda buscarVendaPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Venda venda = em.find(Venda.class, id);
        em.close();
        return venda;
    }

    public List<Venda> buscarTodasVendas() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT v FROM Venda v");
        List<Venda> vendas = query.getResultList();
        em.close();
        return vendas;
    }

    public void atualizarVenda(Venda venda) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(venda);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirVenda(Venda venda) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        venda = em.merge(venda);
        em.remove(venda);
        em.getTransaction().commit();
        em.close();
    }
}
