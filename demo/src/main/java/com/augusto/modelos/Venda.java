package com.augusto.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nomeComprador;
    private String emailComprador;
    private String telefoneComprador;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Venda() {
    }

    public Venda(String nomeComprador, String emailComprador, String telefoneComprador, Produto produto) {
        this.nomeComprador = nomeComprador;
        this.emailComprador = emailComprador;
        this.telefoneComprador = telefoneComprador;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    public String getEmailComprador() {
        return emailComprador;
    }

    public void setEmailComprador(String emailComprador) {
        this.emailComprador = emailComprador;
    }

    public String getTelefoneComprador() {
        return telefoneComprador;
    }

    public void setTelefoneComprador(String telefoneComprador) {
        this.telefoneComprador = telefoneComprador;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Venda [id=" + id + ", nomeComprador=" + nomeComprador + ", emailComprador=" + emailComprador
                + ", telefoneComprador=" + telefoneComprador + ", produto=" + produto + "]";
    }
}
