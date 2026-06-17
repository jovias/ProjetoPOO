package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mitra.model.Imprensa;
import com.mitra.model.Persistencia;

public class ImprensaController {

    public VBox mostrar() {
        VBox box = new VBox(10);

        // Título da tela
        Label titulo = new Label("Cadastro de Imprensa");
        titulo.setFont(new Font("Arial", 20));

        // Campos de entrada
        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField veiculoField = new TextField();
        veiculoField.setPromptText("Veículo (TV, Jornal, Rádio...)");

        TextField credencialField = new TextField();
        credencialField.setPromptText("Credencial");

        DatePicker dataCredencialField = new DatePicker();
        dataCredencialField.setPromptText("Data da Credencial");

        // Botões de ação
        Button btnSalvar    = new Button("Salvar");
        Button btnListar    = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir   = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela de exibição
        TableView<Imprensa> tabela = new TableView<>();

        // Colunas da tabela
        TableColumn<Imprensa, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Imprensa, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Imprensa, String> colVeiculo = new TableColumn<>("Veículo");
        colVeiculo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVeiculo()));

        TableColumn<Imprensa, String> colCredencial = new TableColumn<>("Credencial");
        colCredencial.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCredencial()));

        TableColumn<Imprensa, String> colData = new TableColumn<>("Data Credencial");
        colData.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataCredencial())));

        tabela.getColumns().addAll(colId, colNome, colVeiculo, colCredencial, colData);

        // Preencher campos ao selecionar linha na tabela
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                idField.setText(String.valueOf(selecionado.getId()));
                nomeField.setText(selecionado.getNome());
                veiculoField.setText(selecionado.getVeiculo());
                credencialField.setText(selecionado.getCredencial());
                dataCredencialField.setValue(selecionado.getDataCredencial());
            }
        });

        // Adicionar
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id               = Integer.parseInt(idField.getText());
                String nome          = nomeField.getText();
                String veiculo       = veiculoField.getText();
                String credencial    = credencialField.getText();
                LocalDate dataCred   = dataCredencialField.getValue();

                Imprensa imprensa = new Imprensa(id, nome, veiculo, credencial, dataCred);
                Persistencia.adicionarImprensa(imprensa);

                tabela.getItems().add(imprensa);
                new Alert(Alert.AlertType.INFORMATION, "Imprensa salva com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar: " + ex.getMessage()).show();
            }
        });

        // Listar todos
        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerImprensa());
        });

        // Excluir selecionado
        btnExcluir.setOnAction(e -> {
            Imprensa selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Imprensa> lista = Persistencia.lerImprensa();

                // Busca pelo ID e remove
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getId() == selecionado.getId()) {
                        lista.remove(i);
                        break;
                    }
                }

                Persistencia.salvarImprensa(lista);
                tabela.getItems().setAll(lista);
                new Alert(Alert.AlertType.INFORMATION, "Imprensa excluída com sucesso!").show();
            }
        });

        // Atualizar selecionado
        btnAtualizar.setOnAction(e -> {
            Imprensa selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    // Aplica os novos valores
                    selecionado.setNome(nomeField.getText());
                    selecionado.setVeiculo(veiculoField.getText());
                    selecionado.setCredencial(credencialField.getText());
                    selecionado.setDataCredencial(dataCredencialField.getValue());

                    ArrayList<Imprensa> lista = Persistencia.lerImprensa();

                    // Substitui pelo ID
                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).getId() == selecionado.getId()) {
                            lista.set(i, selecionado);
                            break;
                        }
                    }

                    Persistencia.salvarImprensa(lista);
                    tabela.getItems().setAll(lista);
                    new Alert(Alert.AlertType.INFORMATION, "Imprensa atualizada com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar: " + ex.getMessage()).show();
                }
            }
        });

        // Monta o layout
        box.getChildren().addAll(
            titulo,
            idField, nomeField, veiculoField, credencialField, dataCredencialField,
            botoes, tabela
        );
        return box;
    }
}
