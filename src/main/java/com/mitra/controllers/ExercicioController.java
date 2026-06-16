package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

import com.mitra.model.Exercicio;
import com.mitra.model.Persistencia;

public class ExercicioController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Exercício");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome do Exercício");
        TextField descricaoField = new TextField(); descricaoField.setPromptText("Descrição");
        TextField statusField = new TextField(); statusField.setPromptText("Status");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Exercicio> tabela = new TableView<>();
        TableColumn<Exercicio, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Exercicio, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Exercicio, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));

        TableColumn<Exercicio, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        tabela.getColumns().addAll(colId, colNome, colDescricao, colStatus);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
        try {
            int id = Integer.parseInt(idField.getText());
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            String status = statusField.getText();

            Exercicio exercicio = new Exercicio(id, nome, descricao, status);
            Persistencia.adicionarExercicio(exercicio);

            tabela.getItems().add(exercicio);
            new Alert(Alert.AlertType.INFORMATION, "Exercício salvo com sucesso!").show();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar exercício: " + ex.getMessage()).show();
        }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerExercicios());
        });

        btnExcluir.setOnAction(e -> {
            Exercicio selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Exercicio> exercicios = Persistencia.lerExercicios();

                // Remove apenas o exercício selecionado
                for (int i = 0; i < exercicios.size(); i++) {
                    if (exercicios.get(i).getId() == selecionado.getId()) {
                        exercicios.remove(i);
                        break;
                    }
                }

                Persistencia.salvarExercicios(exercicios);
                tabela.getItems().setAll(exercicios);
                new Alert(Alert.AlertType.INFORMATION, "Exercício excluído com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Exercicio selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setDescricao(descricaoField.getText());
                    selecionado.setStatus(statusField.getText());

                    ArrayList<Exercicio> exercicios = Persistencia.lerExercicios();
                    for (int i = 0; i < exercicios.size(); i++) {
                        if (exercicios.get(i).getId() == selecionado.getId()) {
                            exercicios.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarExercicios(exercicios);
                    tabela.getItems().setAll(exercicios);
                    new Alert(Alert.AlertType.INFORMATION, "Exercício atualizado com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar exercício: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, descricaoField, statusField, botoes, tabela);
        return box;
    }
}
