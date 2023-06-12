package com.augusto.dao;

import com.augusto.modelos.Categoria;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class CategoriaDAO {

    private EntityManagerFactory emf;

    public CategoriaDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvar(Categoria categoria) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(categoria);
        em.getTransaction().commit();
        em.close();
    }

    public void atualizar(Categoria categoria) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(categoria);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(Categoria categoria2) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Categoria categoria = em.find(Categoria.class, categoria2);
        em.remove(categoria);
        em.getTransaction().commit();
        em.close();
    }

    public Categoria buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Categoria categoria = em.find(Categoria.class, id);
        em.close();
        return categoria;
    }

    public List<Categoria> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT c FROM Categoria c");
        List<Categoria> categorias = query.getResultList();
        em.close();
        return categorias;
    }
}
