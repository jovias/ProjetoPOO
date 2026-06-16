package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

import com.mitra.model.Equipes;
import com.mitra.model.Persistencia;

public class EquipesController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Equipes");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome da Equipe");
        TextField categoriaField = new TextField(); categoriaField.setPromptText("Categoria");
        TextField modalidadeField = new TextField(); modalidadeField.setPromptText("Modalidade");
        TextField generoField = new TextField(); generoField.setPromptText("Gênero");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Equipes> tabela = new TableView<>();
        TableColumn<Equipes, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Equipes, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Equipes, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoria()));

        TableColumn<Equipes, String> colModalidade = new TableColumn<>("Modalidade");
        colModalidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModalidade()));

        TableColumn<Equipes, String> colGenero = new TableColumn<>("Gênero");
        colGenero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero()));

        tabela.getColumns().addAll(colId, colNome, colCategoria, colModalidade, colGenero);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                String categoria = categoriaField.getText();
                String modalidade = modalidadeField.getText();
                String genero = generoField.getText();

                Equipes equipe = new Equipes(id, nome, categoria, modalidade, genero);
                Persistencia.adicionarEquipe(equipe);

                tabela.getItems().add(equipe);
                new Alert(Alert.AlertType.INFORMATION, "Equipe salva com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar equipe: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerEquipes());
        });

        btnExcluir.setOnAction(e -> {
            Equipes selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Equipes> equipes = Persistencia.lerEquipes();

                // Remove apenas a equipe selecionada
                for (int i = 0; i < equipes.size(); i++) {
                    if (equipes.get(i).getId() == selecionado.getId()) {
                        equipes.remove(i);
                        break;
                    }
                }

                Persistencia.salvarEquipes(equipes);
                tabela.getItems().setAll(equipes);
                new Alert(Alert.AlertType.INFORMATION, "Equipe excluída com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Equipes selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setCategoria(categoriaField.getText());
                    selecionado.setModalidade(modalidadeField.getText());
                    selecionado.setGenero(generoField.getText());

                    ArrayList<Equipes> equipes = Persistencia.lerEquipes();
                    for (int i = 0; i < equipes.size(); i++) {
                        if (equipes.get(i).getId() == selecionado.getId()) {
                            equipes.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarEquipes(equipes);
                    tabela.getItems().setAll(equipes);
                    new Alert(Alert.AlertType.INFORMATION, "Equipe atualizada com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar equipe: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, categoriaField, modalidadeField, generoField, botoes, tabela);
        return box;
    }
}
