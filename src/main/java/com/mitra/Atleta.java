package com.mitra;
import java.io.Serializable;
import java.time.LocalDate;

public class Atleta implements Serializable {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private double peso;
    private double altura;

    public Atleta(int id, String nome, LocalDate dataNascimento, double peso, double altura) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }
}
