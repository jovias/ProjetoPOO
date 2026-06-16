package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

import com.mitra.Modalidades;
import com.mitra.Persistencia;

public class ModalidadesController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Modalidades");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome da Modalidade");
        TextField statusField = new TextField(); statusField.setPromptText("Status");
        TextField treinadorField = new TextField(); treinadorField.setPromptText("Treinador Responsável");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Modalidades> tabela = new TableView<>();
        TableColumn<Modalidades, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Modalidades, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Modalidades, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        TableColumn<Modalidades, String> colTreinador = new TableColumn<>("Treinador Responsável");
        colTreinador.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTreinadorResponsavel()));

        tabela.getColumns().addAll(colId, colNome, colStatus, colTreinador);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                String status = statusField.getText();
                String treinador = treinadorField.getText();

                Modalidades modalidade = new Modalidades(id, nome, status, treinador);
                Persistencia.adicionarModalidade(modalidade);

                tabela.getItems().add(modalidade);
                new Alert(Alert.AlertType.INFORMATION, "Modalidade salva com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar modalidade: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerModalidades());
        });

        btnExcluir.setOnAction(e -> {
            Modalidades selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Modalidades> modalidades = Persistencia.lerModalidades();

                // Remove apenas a modalidade selecionada
                for (int i = 0; i < modalidades.size(); i++) {
                    if (modalidades.get(i).getId() == selecionado.getId()) {
                        modalidades.remove(i);
                        break;
                    }
                }

                Persistencia.salvarModalidades(modalidades);
                tabela.getItems().setAll(modalidades);
                new Alert(Alert.AlertType.INFORMATION, "Modalidade excluída com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Modalidades selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setStatus(statusField.getText());
                    selecionado.setTreinadorResponsavel(treinadorField.getText());

                    ArrayList<Modalidades> modalidades = Persistencia.lerModalidades();
                    for (int i = 0; i < modalidades.size(); i++) {
                        if (modalidades.get(i).getId() == selecionado.getId()) {
                            modalidades.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarModalidades(modalidades);
                    tabela.getItems().setAll(modalidades);
                    new Alert(Alert.AlertType.INFORMATION, "Modalidade atualizada com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar modalidade: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, statusField, treinadorField, botoes, tabela);
        return box;
    }
}
