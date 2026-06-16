package com.mitra.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Jogo implements Serializable {
    private int id;
    private String equipe;
    private LocalDate data;
    private String adversario;
    private String modalidade;

    public Jogo(int id, String equipe, LocalDate data, String adversario, String modalidade) {
        this.id = id;
        this.equipe = equipe;
        this.data = data;
        this.adversario = adversario;
        this.modalidade = modalidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) { this.equipe = equipe; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getAdversario() { return adversario; }
    public void setAdversario(String adversario) { this.adversario = adversario; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
}
