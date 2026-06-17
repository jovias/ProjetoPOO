package com.mitra.model;
import java.io.Serializable;
import java.time.LocalDate;

// Model de Imprensa
public class Imprensa implements Serializable {

    // Atributos
    private int id;
    private String nome;
    private String veiculo;
    private String credencial;
    private LocalDate dataCredencial;

    // Construtor
    public Imprensa(int id, String nome, String veiculo, String credencial, LocalDate dataCredencial) {
        this.id = id;
        this.nome = nome;
        this.veiculo = veiculo;
        this.credencial = credencial;
        this.dataCredencial = dataCredencial;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }

    public String getCredencial() { return credencial; }
    public void setCredencial(String credencial) { this.credencial = credencial; }

    public LocalDate getDataCredencial() { return dataCredencial; }
    public void setDataCredencial(LocalDate dataCredencial) { this.dataCredencial = dataCredencial; }
}
