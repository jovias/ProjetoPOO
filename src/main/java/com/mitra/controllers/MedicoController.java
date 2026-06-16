package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mitra.Medico;
import com.mitra.Persistencia;

public class MedicoController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Médico");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome");
        DatePicker nascimentoField = new DatePicker(); nascimentoField.setPromptText("Data de Nascimento");
        TextField crmField = new TextField(); crmField.setPromptText("CRM");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Medico> tabela = new TableView<>();
        TableColumn<Medico, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Medico, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Medico, String> colNascimento = new TableColumn<>("Nascimento");
        colNascimento.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataNascimento())));

        TableColumn<Medico, String> colCRM = new TableColumn<>("CRM");
        colCRM.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCRM())));

        tabela.getColumns().addAll(colId, colNome, colNascimento, colCRM);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                LocalDate nascimento = nascimentoField.getValue();
                int crm = Integer.parseInt(crmField.getText());

                Medico medico = new Medico(id, nome, nascimento, crm);
                Persistencia.adicionarMedico(medico);

                tabela.getItems().add(medico);
                new Alert(Alert.AlertType.INFORMATION, "Médico salvo com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar médico: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerMedico());
        });

        btnExcluir.setOnAction(e -> {
            Medico selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Medico> medicos = Persistencia.lerMedico();

                // Remove apenas o médico selecionado
                for (int i = 0; i < medicos.size(); i++) {
                    if (medicos.get(i).getId() == selecionado.getId()) {
                        medicos.remove(i);
                        break;
                    }
                }

                Persistencia.salvarMedico(medicos);
                tabela.getItems().setAll(medicos);
                new Alert(Alert.AlertType.INFORMATION, "Médico excluído com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Medico selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setDataNascimento(nascimentoField.getValue());
                    selecionado.setCRM(Integer.parseInt(crmField.getText()));

                    ArrayList<Medico> medicos = Persistencia.lerMedico();
                    for (int i = 0; i < medicos.size(); i++) {
                        if (medicos.get(i).getId() == selecionado.getId()) {
                            medicos.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarMedico(medicos);
                    tabela.getItems().setAll(medicos);
                    new Alert(Alert.AlertType.INFORMATION, "Médico atualizado com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar médico: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, nascimentoField, crmField, botoes, tabela);
        return box;
    }
}
