package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mitra.model.AvaliacaoFisica;
import com.mitra.model.Persistencia;

public class AvaliacaoFisicaController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Avaliação Física");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField idAtletaField = new TextField(); idAtletaField.setPromptText("ID do Atleta");
        TextField pesoField = new TextField(); pesoField.setPromptText("Peso (kg)");
        TextField alturaField = new TextField(); alturaField.setPromptText("Altura (m)");
        TextField percentualGorduraField = new TextField(); percentualGorduraField.setPromptText("Percentual de Gordura (%)");
        DatePicker dataAvaliacaoField = new DatePicker(); dataAvaliacaoField.setPromptText("Data da Avaliação");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<AvaliacaoFisica> tabela = new TableView<>();
        TableColumn<AvaliacaoFisica, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<AvaliacaoFisica, String> colIdAtleta = new TableColumn<>("ID Atleta");
        colIdAtleta.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getIdAtleta())));

        TableColumn<AvaliacaoFisica, String> colPeso = new TableColumn<>("Peso (kg)");
        colPeso.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPeso())));

        TableColumn<AvaliacaoFisica, String> colAltura = new TableColumn<>("Altura (m)");
        colAltura.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAltura())));

        TableColumn<AvaliacaoFisica, String> colGordura = new TableColumn<>("Gordura (%)");
        colGordura.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPercentualGordura())));

        TableColumn<AvaliacaoFisica, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataAvaliacao())));

        tabela.getColumns().addAll(colId, colIdAtleta, colPeso, colAltura, colGordura, colData);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int idAtleta = Integer.parseInt(idAtletaField.getText());
                double peso = Double.parseDouble(pesoField.getText());
                double altura = Double.parseDouble(alturaField.getText());
                double percentualGordura = Double.parseDouble(percentualGorduraField.getText());
                LocalDate dataAvaliacao = dataAvaliacaoField.getValue();

                AvaliacaoFisica avaliacao = new AvaliacaoFisica(id, idAtleta, peso, altura, percentualGordura, dataAvaliacao);
                Persistencia.adicionarAvaliacaoFisica(avaliacao);

                tabela.getItems().add(avaliacao);
                new Alert(Alert.AlertType.INFORMATION, "Avaliação física salva com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar avaliação física: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerAvaliacoesFisicas());
        });

        btnExcluir.setOnAction(e -> {
            AvaliacaoFisica selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<AvaliacaoFisica> avaliacoes = Persistencia.lerAvaliacoesFisicas();

                // Remove apenas a avaliação selecionada
                for (int i = 0; i < avaliacoes.size(); i++) {
                    if (avaliacoes.get(i).getId() == selecionado.getId()) {
                        avaliacoes.remove(i);
                        break;
                    }
                }

                Persistencia.salvarAvaliacoesFisicas(avaliacoes);
                tabela.getItems().setAll(avaliacoes);
                new Alert(Alert.AlertType.INFORMATION, "Avaliação física excluída com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            AvaliacaoFisica selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setIdAtleta(Integer.parseInt(idAtletaField.getText()));
                    selecionado.setPeso(Double.parseDouble(pesoField.getText()));
                    selecionado.setAltura(Double.parseDouble(alturaField.getText()));
                    selecionado.setPercentualGordura(Double.parseDouble(percentualGorduraField.getText()));
                    selecionado.setDataAvaliacao(dataAvaliacaoField.getValue());

                    ArrayList<AvaliacaoFisica> avaliacoes = Persistencia.lerAvaliacoesFisicas();
                    for (int i = 0; i < avaliacoes.size(); i++) {
                        if (avaliacoes.get(i).getId() == selecionado.getId()) {
                            avaliacoes.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarAvaliacoesFisicas(avaliacoes);
                    tabela.getItems().setAll(avaliacoes);
                    new Alert(Alert.AlertType.INFORMATION, "Avaliação física atualizada com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar avaliação física: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, idAtletaField, pesoField, alturaField, percentualGorduraField, dataAvaliacaoField, botoes, tabela);
        return box;
    }
}