package com.mitra.model;
import java.io.Serializable;

public class Equipes implements Serializable {
    private int id;
    private String nome;
    private String categoria;
    private String modalidade;
    private String genero;

    public Equipes(int id, String nome, String categoria, String modalidade, String genero) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.modalidade = modalidade;
        this.genero = genero;
    }

    //getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getModalidade() {
        return modalidade;
    }
    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
}
