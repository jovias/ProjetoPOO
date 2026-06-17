package com.mitra.model;
import java.io.Serializable;
import java.time.LocalDate;

// Model de Massagista
public class Massagista implements Serializable {

    // Atributos
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private String crefito;
    private String especialidade;

    // Construtor
    public Massagista(int id, String nome, LocalDate dataNascimento, String crefito, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.crefito = crefito;
        this.especialidade = especialidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getCrefito() { return crefito; }
    public void setCrefito(String crefito) { this.crefito = crefito; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
