package com.mitra.model;

import java.io.Serializable;

public class Metricas implements Serializable {

    private int id;
    private String nome;
    private String descricao;
    private String unidadeMedida;
    private String status;

    public Metricas(int id, String nome, String descricao, String unidadeMedida, String status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.status = status;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}