package com.mitra.model;

import java.io.Serializable;
import java.time.LocalDate;

public class AvaliacaoFisica implements Serializable {

    private int id;
    private int idAtleta;
    private double peso;
    private double altura;
    private double percentualGordura;
    private LocalDate dataAvaliacao;

    public AvaliacaoFisica(int id, int idAtleta, double peso, double altura, double percentualGordura, LocalDate dataAvaliacao) {
        this.id = id;
        this.idAtleta = idAtleta;
        this.peso = peso;
        this.altura = altura;
        this.percentualGordura = percentualGordura;
        this.dataAvaliacao = dataAvaliacao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdAtleta() { return idAtleta; }
    public void setIdAtleta(int idAtleta) { this.idAtleta = idAtleta; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }
    public double getPercentualGordura() { return percentualGordura; }
    public void setPercentualGordura(double percentualGordura) { this.percentualGordura = percentualGordura; }
    public LocalDate getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDate dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }
}
