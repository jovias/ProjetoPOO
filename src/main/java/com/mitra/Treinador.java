package com.mitra;

import java.io.Serializable;

public class Treinador implements Serializable {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String especialidade;

    public Treinador(int id, String nome, String telefone, String email, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.especialidade = especialidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
