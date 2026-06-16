package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mitra.model.Presidente;
import com.mitra.model.Persistencia;

public class PresidenteController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Presidente");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome");
        DatePicker nascimentoField = new DatePicker(); nascimentoField.setPromptText("Data de Nascimento");
        TextField cpfField = new TextField(); cpfField.setPromptText("CPF");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Presidente> tabela = new TableView<>();
        TableColumn<Presidente, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Presidente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Presidente, String> colNascimento = new TableColumn<>("Nascimento");
        colNascimento.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataNascimento())));

        TableColumn<Presidente, String> colCPF = new TableColumn<>("CPF");
        colCPF.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCPF())));

        tabela.getColumns().addAll(colId, colNome, colNascimento, colCPF);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                LocalDate nascimento = nascimentoField.getValue();
                long cpf = Long.parseLong(cpfField.getText());

                Presidente presidente = new Presidente(id, nome, nascimento, cpf);
                Persistencia.adicionarPresidente(presidente);

                tabela.getItems().add(presidente);
                new Alert(Alert.AlertType.INFORMATION, "Presidente salvo com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar presidente: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerPresidente());
        });

        btnExcluir.setOnAction(e -> {
            Presidente selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Presidente> presidentes = Persistencia.lerPresidente();

                // Remove apenas o presidente selecionado
                for (int i = 0; i < presidentes.size(); i++) {
                    if (presidentes.get(i).getId() == selecionado.getId()) {
                        presidentes.remove(i);
                        break;
                    }
                }

                Persistencia.salvarPresidente(presidentes);
                tabela.getItems().setAll(presidentes);
                new Alert(Alert.AlertType.INFORMATION, "Presidente excluído com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Presidente selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setDataNascimento(nascimentoField.getValue());
                    selecionado.setCPF(Long.parseLong(cpfField.getText()));

                    ArrayList<Presidente> presidentes = Persistencia.lerPresidente();
                    for (int i = 0; i < presidentes.size(); i++) {
                        if (presidentes.get(i).getId() == selecionado.getId()) {
                            presidentes.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarPresidente(presidentes);
                    tabela.getItems().setAll(presidentes);
                    new Alert(Alert.AlertType.INFORMATION, "Presidente atualizado com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar presidente: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, nascimentoField, cpfField, botoes, tabela);
        return box;
    }
}
