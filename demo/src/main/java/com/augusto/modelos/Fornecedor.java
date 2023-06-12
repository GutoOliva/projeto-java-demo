package com.augusto.modelos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String nome;
    private List<Produto> produtos;


    public Fornecedor() {
    }


    public Fornecedor(String nome, List<Produto> produtos) {
        this.nome = nome;
        this.produtos = produtos;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Fornecedor [id=" + id + ", nome=" + nome + ", produtos=" + produtos + "]";
    }
}
