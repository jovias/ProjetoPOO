package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

import com.mitra.model.Metricas;
import com.mitra.model.Persistencia;

public class MetricasController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Métricas");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome da Métrica");
        TextField descricaoField = new TextField(); descricaoField.setPromptText("Descrição");
        TextField unidadeField = new TextField(); unidadeField.setPromptText("Unidade de Medida (ex: kg, metros, %)");
        TextField statusField = new TextField(); statusField.setPromptText("Status");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Metricas> tabela = new TableView<>();
        TableColumn<Metricas, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Metricas, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Metricas, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));

        TableColumn<Metricas, String> colUnidade = new TableColumn<>("Unidade");
        colUnidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUnidadeMedida()));

        TableColumn<Metricas, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        tabela.getColumns().addAll(colId, colNome, colDescricao, colUnidade, colStatus);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                String descricao = descricaoField.getText();
                String unidade = unidadeField.getText();
                String status = statusField.getText();

                Metricas metrica = new Metricas(id, nome, descricao, unidade, status);
                Persistencia.adicionarMetrica(metrica);

                tabela.getItems().add(metrica);
                new Alert(Alert.AlertType.INFORMATION, "Métrica salva com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar métrica: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerMetricas());
        });

        btnExcluir.setOnAction(e -> {
            Metricas selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Metricas> metricas = Persistencia.lerMetricas();

                // Remove apenas a métrica selecionada
                for (int i = 0; i < metricas.size(); i++) {
                    if (metricas.get(i).getId() == selecionado.getId()) {
                        metricas.remove(i);
                        break;
                    }
                }

                Persistencia.salvarMetricas(metricas);
                tabela.getItems().setAll(metricas);
                new Alert(Alert.AlertType.INFORMATION, "Métrica excluída com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Metricas selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setDescricao(descricaoField.getText());
                    selecionado.setUnidadeMedida(unidadeField.getText());
                    selecionado.setStatus(statusField.getText());

                    ArrayList<Metricas> metricas = Persistencia.lerMetricas();
                    for (int i = 0; i < metricas.size(); i++) {
                        if (metricas.get(i).getId() == selecionado.getId()) {
                            metricas.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarMetricas(metricas);
                    tabela.getItems().setAll(metricas);
                    new Alert(Alert.AlertType.INFORMATION, "Métrica atualizada com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar métrica: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, descricaoField, unidadeField, statusField, botoes, tabela);
        return box;
    }
}