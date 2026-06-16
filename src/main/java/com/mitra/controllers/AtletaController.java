package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mitra.Atleta;
import com.mitra.Persistencia;

public class AtletaController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Atleta");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome");
        DatePicker nascimentoField = new DatePicker(); nascimentoField.setPromptText("Data de Nascimento");
        TextField pesoField = new TextField(); pesoField.setPromptText("Peso");
        TextField alturaField = new TextField(); alturaField.setPromptText("Altura");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Atleta> tabela = new TableView<>();
        TableColumn<Atleta, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Atleta, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Atleta, String> colNascimento = new TableColumn<>("Nascimento");
        colNascimento.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataNascimento())));

        TableColumn<Atleta, String> colPeso = new TableColumn<>("Peso");
        colPeso.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPeso())));

        TableColumn<Atleta, String> colAltura = new TableColumn<>("Altura");
        colAltura.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAltura())));

        tabela.getColumns().addAll(colId, colNome, colNascimento, colPeso, colAltura);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                LocalDate nascimento = nascimentoField.getValue();
                double peso = Double.parseDouble(pesoField.getText());
                double altura = Double.parseDouble(alturaField.getText());

                Atleta atleta = new Atleta(id, nome, nascimento, peso, altura);
                Persistencia.adicionarAtleta(atleta);

                tabela.getItems().add(atleta);
                new Alert(Alert.AlertType.INFORMATION, "Atleta salvo com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar atleta: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerAtletas());
        });

        btnExcluir.setOnAction(e -> {
            Atleta selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Atleta> atletas = Persistencia.lerAtletas();

                // Remove apenas o atleta selecionado
                for (int i = 0; i < atletas.size(); i++) {
                    if (atletas.get(i).getId() == selecionado.getId()) {
                        atletas.remove(i);
                        break;
                    }
                }

                Persistencia.salvarAtletas(atletas);
                tabela.getItems().setAll(atletas);
                new Alert(Alert.AlertType.INFORMATION, "Atleta excluído com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Atleta selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setDataNascimento(nascimentoField.getValue());
                    selecionado.setPeso(Double.parseDouble(pesoField.getText()));
                    selecionado.setAltura(Double.parseDouble(alturaField.getText()));

                    ArrayList<Atleta> atletas = Persistencia.lerAtletas();
                    for (int i = 0; i < atletas.size(); i++) {
                        if (atletas.get(i).getId() == selecionado.getId()) {
                            atletas.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarAtletas(atletas);
                    tabela.getItems().setAll(atletas);
                    new Alert(Alert.AlertType.INFORMATION, "Atleta atualizado com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar atleta: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, nascimentoField, pesoField, alturaField, botoes, tabela);
        return box;
    }
}
