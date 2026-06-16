package com.mitra.model;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {

    // Arquivos separados para cada tipo de objeto
    private static final String ARQ_ATLETAS = "atletas.dat";
    private static final String ARQ_TREINADORES = "treinadores.dat";
    private static final String ARQ_MODALIDADES = "modalidades.dat";
    private static final String ARQ_EQUIPES = "equipes.dat";
    private static final String ARQ_MEDICO = "medico.dat";
    private static final String ARQ_PRESIDENTE = "presidente.dat";
    private static final String ARQ_EXERCICIOS = "exercicio.dat";
    private static final String ARQ_METRICAS = "metricas.dat";


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

    // ---------------- MODALIDADES ----------------

    // Salva a lista de modalidades no arquivo binário
    public static void salvarModalidades(ArrayList<Modalidades> modalidades) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_MODALIDADES))) {
            oos.writeObject(modalidades);
            System.out.println("Lista de modalidades salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar modalidades: " + e.getMessage());
        }
    }

    // Lê a lista de modalidades do arquivo binário
    public static ArrayList<Modalidades> lerModalidades() {
        ArrayList<Modalidades> lista = new ArrayList<>();
        File arq = new File(ARQ_MODALIDADES);
        if (!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_MODALIDADES))) {
            lista = (ArrayList<Modalidades>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler modalidades: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona uma nova modalidade e regrava o arquivo
    public static void adicionarModalidade(Modalidades novo) {
        ArrayList<Modalidades> modalidades = lerModalidades();
        modalidades.add(novo);
        salvarModalidades(modalidades);
    }

    // ---------------- EQUIPES ----------------
    public static void salvarEquipes(ArrayList<Equipes> equipes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_EQUIPES))) {
            oos.writeObject(equipes);
            System.out.println("Lista de equipes salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar as equipes: " + e.getMessage());
        }
    }

    // Lê a lista de equipes do arquivo binário
    public static ArrayList<Equipes> lerEquipes() {
        ArrayList<Equipes> lista = new ArrayList<>();
        File arq = new File(ARQ_EQUIPES);
        if (!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_EQUIPES))) {
            lista = (ArrayList<Equipes>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler as equipes: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona uma nova equipe e regrava o arquivo
    public static void adicionarEquipe(Equipes novo) {
        ArrayList<Equipes> equipes = lerEquipes();
        equipes.add(novo);
        salvarEquipes(equipes);
    }

    // ---------------- MEDICO ----------------

    // Salva a lista de medico no arquivo binário
    public static void salvarMedico(ArrayList<Medico> medico) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_MEDICO))) {
            oos.writeObject(medico);
            System.out.println("Lista de medicos salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar medicos: " + e.getMessage());
        }
    }

    // Lê a lista de medico do arquivo binário
    public static ArrayList<Medico> lerMedico() {
        ArrayList<Medico> lista = new ArrayList<>();
        File arq = new File(ARQ_MEDICO);
        if (!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_MEDICO))) {
            lista = (ArrayList<Medico>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler medicos: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo medico e regrava o arquivo
    public static void adicionarMedico(Medico novo) {
        ArrayList<Medico> medico = lerMedico();
        medico.add(novo);
        salvarMedico(medico);
    }


    // ---------------- Presidente ----------------

    // Salva a lista de presidente no arquivo binário
    public static void salvarPresidente(ArrayList<Presidente> presidente) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_PRESIDENTE))) {
            oos.writeObject(presidente);
            System.out.println("Lista de presidente salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar presidente: " + e.getMessage());
        }
    }

    // Lê a lista de presidente do arquivo binário
    public static ArrayList<Presidente> lerPresidente() {
        ArrayList<Presidente> lista = new ArrayList<>();
        File arq = new File(ARQ_PRESIDENTE);
        if (!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_PRESIDENTE))) {
            lista = (ArrayList<Presidente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler presidente: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo presidente e regrava o arquivo
    public static void adicionarPresidente(Presidente novo) {
        ArrayList<Presidente> presidente = lerPresidente();
        presidente.add(novo);
        salvarPresidente(presidente);
    }


    //---------------- Exercícios ----------------

    //Salva a lista de exercício no arquivo binário

    public static void salvarExercicios(ArrayList<Exercicio> exercicios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_EXERCICIOS))) {
            oos.writeObject(exercicios);
            System.out.println("Lista de exercícios salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar exercícios: " + e.getMessage());
        }
    }

    //Lê a lista de exercícios no arquivo binário
    public static ArrayList<Exercicio> lerExercicios() {
        ArrayList<Exercicio> lista = new ArrayList<>();
        File arq = new File(ARQ_EXERCICIOS);
        if(!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_EXERCICIOS))) {
            lista = (ArrayList<Exercicio>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler exercícios: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo exercício e regrava o arquivo
    public static void adicionarExercicio(Exercicio novo) {
        ArrayList<Exercicio> exercicios = lerExercicios();
        exercicios.add(novo);
        salvarExercicios(exercicios);
    }


    // ---------------- METRICAS ----------------


    // Salva a lista de métricas no arquivo binário
    public static void salvarMetricas(ArrayList<Metricas> metricas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_METRICAS))) {
            oos.writeObject(metricas);
            System.out.println("Lista de métricas salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar métricas: " + e.getMessage());
        }
    }

    // Lê a lista de métricas do arquivo binário
    public static ArrayList<Metricas> lerMetricas() {
        ArrayList<Metricas> lista = new ArrayList<>();
        File arq = new File(ARQ_METRICAS);
        if (!arq.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ_METRICAS))) {
            lista = (ArrayList<Metricas>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler métricas: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona uma nova métrica e regrava o arquivo
    public static void adicionarMetrica(Metricas novo) {
        ArrayList<Metricas> metricas = lerMetricas();
        metricas.add(novo);
        salvarMetricas(metricas);
    }
}
