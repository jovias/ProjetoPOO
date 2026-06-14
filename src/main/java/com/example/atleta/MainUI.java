package com.example.atleta;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainUI extends Application {

    @Override
    public void start(Stage stage) {
        // TabPane é o container que permite criar várias abas
        TabPane tabPane = new TabPane();

        // Criamos a aba de Atleta
        Tab tabAtleta = criarAbaAtleta();

        // Criamos a aba de Treinador (vai vir na próxima parte)
        Tab tabTreinador = criarAbaTreinador();

        // Adiciona as abas ao TabPane
        tabPane.getTabs().addAll(tabAtleta, tabTreinador);

        // Cena principal
        Scene scene = new Scene(tabPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Sistema MITRA - CRUD Atleta & Treinador");
        stage.show();
    }

    // ------------------- CRUD ATLETA -------------------
    private Tab criarAbaAtleta() {
        // Layout vertical para organizar os campos e botões
        VBox atletaBox = new VBox(10);

        // Campos de entrada de dados
        TextField idAtleta = new TextField(); idAtleta.setPromptText("ID");
        TextField nomeAtleta = new TextField(); nomeAtleta.setPromptText("Nome");
        DatePicker nascimentoAtleta = new DatePicker(); // campo de data
        TextField pesoAtleta = new TextField(); pesoAtleta.setPromptText("Peso");
        TextField alturaAtleta = new TextField(); alturaAtleta.setPromptText("Altura");

        // Tabela para mostrar os atletas cadastrados
        TableView<Atleta> tabelaAtleta = new TableView<>();

        // Coluna ID (precisa converter int para String)
        TableColumn<Atleta, String> colIdA = new TableColumn<>("ID");
        colIdA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        // Coluna Nome
        TableColumn<Atleta, String> colNomeA = new TableColumn<>("Nome");
        colNomeA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        // Coluna Data de Nascimento
        TableColumn<Atleta, String> colNascimentoA = new TableColumn<>("Nascimento");
        colNascimentoA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataNascimento())));

        // Coluna Peso
        TableColumn<Atleta, String> colPesoA = new TableColumn<>("Peso");
        colPesoA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPeso())));

        // Coluna Altura
        TableColumn<Atleta, String> colAlturaA = new TableColumn<>("Altura");
        colAlturaA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAltura())));

        // Adiciona todas as colunas na tabela
        tabelaAtleta.getColumns().addAll(colIdA, colNomeA, colNascimentoA, colPesoA, colAlturaA);

        // Botões CRUD
        Button salvarAtleta = new Button("Inserir");
        Button listarAtleta = new Button("Listar");
        Button atualizarAtleta = new Button("Atualizar");
        Button excluirAtleta = new Button("Excluir");

        // ---------------- INSERIR ----------------
        salvarAtleta.setOnAction(e -> {
            try {
                // Cria um novo objeto Atleta com os dados dos campos
                Atleta atleta = new Atleta(
                        Integer.parseInt(idAtleta.getText()),
                        nomeAtleta.getText(),
                        nascimentoAtleta.getValue(),
                        Double.parseDouble(pesoAtleta.getText()),
                        Double.parseDouble(alturaAtleta.getText())
                );
                // Adiciona o atleta na lista e salva no arquivo binário
                Persistencia.adicionarAtleta(atleta);
                new Alert(Alert.AlertType.INFORMATION, "Atleta salvo!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
            }
        });

        // ---------------- LISTAR ----------------
        listarAtleta.setOnAction(e -> {
            // Limpa a tabela e carrega todos os atletas do arquivo
            tabelaAtleta.getItems().clear();
            tabelaAtleta.getItems().addAll(Persistencia.lerAtletas());
        });

        // ---------------- ATUALIZAR ----------------
        atualizarAtleta.setOnAction(e -> {
            Atleta selecionado = tabelaAtleta.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    // Atualiza os dados do objeto selecionado
                    selecionado.setNome(nomeAtleta.getText());
                    selecionado.setPeso(Double.parseDouble(pesoAtleta.getText()));
                    selecionado.setAltura(Double.parseDouble(alturaAtleta.getText()));

                    // Regrava a lista inteira no arquivo
                    ArrayList<Atleta> lista = new ArrayList<>(tabelaAtleta.getItems());
                    Persistencia.salvarAtletas(lista);

                    // Atualiza a tabela
                    listarAtleta.fire();
                    new Alert(Alert.AlertType.INFORMATION, "Atleta atualizado!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
                }
            }
        });

        // ---------------- EXCLUIR ----------------
        excluirAtleta.setOnAction(e -> {
            Atleta selecionado = tabelaAtleta.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                // Remove da tabela
                tabelaAtleta.getItems().remove(selecionado);

                // Regrava a lista sem o atleta excluído
                ArrayList<Atleta> lista = new ArrayList<>(tabelaAtleta.getItems());
                Persistencia.salvarAtletas(lista);

                new Alert(Alert.AlertType.INFORMATION, "Atleta excluído!").show();
            }
        });

        // Adiciona todos os componentes na aba
        atletaBox.getChildren().addAll(idAtleta, nomeAtleta, nascimentoAtleta, pesoAtleta, alturaAtleta,
                salvarAtleta, listarAtleta, atualizarAtleta, excluirAtleta, tabelaAtleta);

        return new Tab("Atleta", atletaBox);
    }

    // ------------------- CRUD TREINADOR -------------------
    private Tab criarAbaTreinador() {
        // Layout vertical para organizar os campos e botões
        VBox treinadorBox = new VBox(10);

        // Campos de entrada de dados
        TextField idTreinador = new TextField(); idTreinador.setPromptText("ID");
        TextField nomeTreinador = new TextField(); nomeTreinador.setPromptText("Nome");
        TextField telefoneTreinador = new TextField(); telefoneTreinador.setPromptText("Telefone");
        TextField emailTreinador = new TextField(); emailTreinador.setPromptText("Email");
        TextField especialidadeTreinador = new TextField(); especialidadeTreinador.setPromptText("Especialidade");

        // Tabela para mostrar os treinadores cadastrados
        TableView<Treinador> tabelaTreinador = new TableView<>();

        // Coluna ID (precisa converter int para String)
        TableColumn<Treinador, String> colIdT = new TableColumn<>("ID");
        colIdT.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        // Coluna Nome
        TableColumn<Treinador, String> colNomeT = new TableColumn<>("Nome");
        colNomeT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        // Coluna Telefone
        TableColumn<Treinador, String> colTelefoneT = new TableColumn<>("Telefone");
        colTelefoneT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefone()));

        // Coluna Email
        TableColumn<Treinador, String> colEmailT = new TableColumn<>("Email");
        colEmailT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        // Coluna Especialidade
        TableColumn<Treinador, String> colEspT = new TableColumn<>("Especialidade");
        colEspT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEspecialidade()));

        // Adiciona todas as colunas na tabela
        tabelaTreinador.getColumns().addAll(colIdT, colNomeT, colTelefoneT, colEmailT, colEspT);

        // Botões CRUD
        Button salvarTreinador = new Button("Inserir");
        Button listarTreinador = new Button("Listar");
        Button atualizarTreinador = new Button("Atualizar");
        Button excluirTreinador = new Button("Excluir");

        // ---------------- INSERIR ----------------
        salvarTreinador.setOnAction(e -> {
            try {
                // Cria um novo objeto Treinador com os dados dos campos
                Treinador treinador = new Treinador(
                        Integer.parseInt(idTreinador.getText()),
                        nomeTreinador.getText(),
                        telefoneTreinador.getText(),
                        emailTreinador.getText(),
                        especialidadeTreinador.getText()
                );
                // Adiciona o treinador na lista e salva no arquivo binário
                Persistencia.adicionarTreinador(treinador);
                new Alert(Alert.AlertType.INFORMATION, "Treinador salvo!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
            }
        });

        // ---------------- LISTAR ----------------
        listarTreinador.setOnAction(e -> {
            // Limpa a tabela e carrega todos os treinadores do arquivo
            tabelaTreinador.getItems().clear();
            tabelaTreinador.getItems().addAll(Persistencia.lerTreinadores());
        });

        // ---------------- ATUALIZAR ----------------
        atualizarTreinador.setOnAction(e -> {
            Treinador selecionado = tabelaTreinador.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    // Atualiza os dados do objeto selecionado
                    selecionado.setNome(nomeTreinador.getText());
                    selecionado.setTelefone(telefoneTreinador.getText());
                    selecionado.setEmail(emailTreinador.getText());
                    selecionado.setEspecialidade(especialidadeTreinador.getText());

                    // Regrava a lista inteira no arquivo
                    ArrayList<Treinador> lista = new ArrayList<>(tabelaTreinador.getItems());
                    Persistencia.salvarTreinadores(lista);

                    // Atualiza a tabela
                    listarTreinador.fire();
                    new Alert(Alert.AlertType.INFORMATION, "Treinador atualizado!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
                }
            }
        });

        // ---------------- EXCLUIR ----------------
        excluirTreinador.setOnAction(e -> {
            Treinador selecionado = tabelaTreinador.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                // Remove da tabela
                tabelaTreinador.getItems().remove(selecionado);

                // Regrava a lista sem o treinador excluído
                ArrayList<Treinador> lista = new ArrayList<>(tabelaTreinador.getItems());
                Persistencia.salvarTreinadores(lista);

                new Alert(Alert.AlertType.INFORMATION, "Treinador excluído!").show();
            }
        });

        // Adiciona todos os componentes na aba
        treinadorBox.getChildren().addAll(idTreinador, nomeTreinador, telefoneTreinador, emailTreinador, especialidadeTreinador,
                salvarTreinador, listarTreinador, atualizarTreinador, excluirTreinador, tabelaTreinador);

        return new Tab("Treinador", treinadorBox);
    }
}

