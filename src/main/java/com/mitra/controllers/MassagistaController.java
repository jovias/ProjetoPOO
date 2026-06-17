package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mitra.model.Massagista;
import com.mitra.model.Persistencia;

public class MassagistaController {

    public VBox mostrar() {
        VBox box = new VBox(10);

        // Título da tela
        Label titulo = new Label("Cadastro de Massagista");
        titulo.setFont(new Font("Arial", 20));

        // Campos de entrada
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome");
        DatePicker nascimentoField = new DatePicker(); nascimentoField.setPromptText("Data de Nascimento");
        TextField crefitoField = new TextField(); crefitoField.setPromptText("CREFITO");
        TextField especialidadeField = new TextField(); especialidadeField.setPromptText("Especialidade");

        // Botões
        Button btnSalvar    = new Button("Salvar");
        Button btnListar    = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir   = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Massagista> tabela = new TableView<>();
        TableColumn<Massagista, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Massagista, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Massagista, String> colNascimento = new TableColumn<>("Nascimento");
        colNascimento.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataNascimento())));

        TableColumn<Massagista, String> colCrefito = new TableColumn<>("CREFITO");
        colCrefito.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCrefito()));

        TableColumn<Massagista, String> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEspecialidade()));

        tabela.getColumns().addAll(colId, colNome, colNascimento, colCrefito, colEspecialidade);

        // Preencher campos ao selecionar linha
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                idField.setText(String.valueOf(selecionado.getId()));
                nomeField.setText(selecionado.getNome());
                nascimentoField.setValue(selecionado.getDataNascimento());
                crefitoField.setText(selecionado.getCrefito());
                especialidadeField.setText(selecionado.getEspecialidade());
            }
        });

        // Salvar novo
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                LocalDate nascimento = nascimentoField.getValue();
                String crefito = crefitoField.getText();
                String especialidade = especialidadeField.getText();

                Massagista massagista = new Massagista(id, nome, nascimento, crefito, especialidade);
                Persistencia.adicionarMassagista(massagista);

                tabela.getItems().setAll(Persistencia.lerMassagistas());
                new Alert(Alert.AlertType.INFORMATION, "Massagista salvo com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar: " + ex.getMessage()).show();
            }
        });

        // Listar todos
        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerMassagistas());
        });

        // Excluir selecionado
        btnExcluir.setOnAction(e -> {
            Massagista selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Massagista> lista = Persistencia.lerMassagistas();
                lista.removeIf(m -> m.getId() == selecionado.getId());
                Persistencia.salvarMassagistas(lista);
                tabela.getItems().setAll(lista);
                new Alert(Alert.AlertType.INFORMATION, "Massagista excluído com sucesso!").show();
            }
        });

        // Atualizar selecionado
        btnAtualizar.setOnAction(e -> {
            Massagista selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setDataNascimento(nascimentoField.getValue());
                    selecionado.setCrefito(crefitoField.getText());
                    selecionado.setEspecialidade(especialidadeField.getText());

                    ArrayList<Massagista> lista = Persistencia.lerMassagistas();
                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).getId() == selecionado.getId()) {
                            lista.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarMassagistas(lista);
                    tabela.getItems().setAll(lista);
                    new Alert(Alert.AlertType.INFORMATION, "Massagista atualizado com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar: " + ex.getMessage()).show();
                }
            }
        });

        // Layout final
        box.getChildren().addAll(titulo, idField, nomeField, nascimentoField, crefitoField, especialidadeField, botoes, tabela);
        return box;
    }
}
