package com.mitra;
import java.io.Serializable;

public class Modalidades implements Serializable {
    private int id;
    private String nome;
    private String status;
    private String treinadorResponsavel;

    public Modalidades(int id, String nome, String status, String treinadorResponsavel) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.treinadorResponsavel = treinadorResponsavel;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getTreinadorResponsavel() {
        return treinadorResponsavel;
    }
    public void setTreinadorResponsavel(String treinadorResponsavel) {
        this.treinadorResponsavel = treinadorResponsavel;
    }
}
