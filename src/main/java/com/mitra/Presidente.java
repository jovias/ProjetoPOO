package com.mitra;
import java.io.Serializable;
import java.time.LocalDate;

public class Presidente implements Serializable {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private long CPF; // agora é long

    public Presidente(int id, String nome, LocalDate dataNascimento, long CPF) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.CPF = CPF;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public long getCPF() { return CPF; }
    public void setCPF(long cpf) { this.CPF = cpf; }
}
