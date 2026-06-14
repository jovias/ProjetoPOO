package com.example.atleta;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {

    // Arquivos separados para cada tipo de objeto
    private static final String ARQ_ATLETAS = "atletas.dat";
    private static final String ARQ_TREINADORES = "treinadores.dat";

    // ---------------- ATLETAS ----------------

    // Salva a lista de atletas no arquivo binário
    public static void salvarAtletas(ArrayList<Atleta> atletas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_ATLETAS))) {
            oos.writeObject(atletas);
            System.out.println("Lista de atletas salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar atletas: " + e.getMessage());
        }
    }

    // Lê a lista de atletas do arquivo binário
    public static ArrayList<Atleta> lerAtletas() {
        ArrayList<Atleta> lista = new ArrayList<>();
        File arq = new File(ARQ_ATLETAS);
        if (!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_ATLETAS))) {
            lista = (ArrayList<Atleta>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler atletas: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo atleta e regrava o arquivo
    public static void adicionarAtleta(Atleta novo) {
        ArrayList<Atleta> atletas = lerAtletas();
        atletas.add(novo);
        salvarAtletas(atletas);
    }

    // ---------------- TREINADORES ----------------

    // Salva a lista de treinadores no arquivo binário
    public static void salvarTreinadores(ArrayList<Treinador> treinadores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_TREINADORES))) {
            oos.writeObject(treinadores);
            System.out.println("Lista de treinadores salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar treinadores: " + e.getMessage());
        }
    }

    // Lê a lista de treinadores do arquivo binário
    public static ArrayList<Treinador> lerTreinadores() {
        ArrayList<Treinador> lista = new ArrayList<>();
        File arq = new File(ARQ_TREINADORES);
        if (!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_TREINADORES))) {
            lista = (ArrayList<Treinador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler treinadores: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo treinador e regrava o arquivo
    public static void adicionarTreinador(Treinador novo) {
        ArrayList<Treinador> treinadores = lerTreinadores();
        treinadores.add(novo);
        salvarTreinadores(treinadores);
    }
}
