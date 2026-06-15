package com.mitra;
import java.io.Serializable;
import java.time.LocalDate;

public class Medico implements Serializable {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private int CRM;

    public Medico(int id, String nome, LocalDate dataNascimento, int CRM) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.CRM = CRM;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public int getCRM() { return CRM; }
    public void setCRM(int CRM) { this.CRM = CRM; }
}
