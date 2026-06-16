package com.mitra.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

import com.mitra.model.Treinador;
import com.mitra.model.Persistencia;

public class TreinadorController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Título
        Label titulo = new Label("Cadastro de Treinador");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nomeField = new TextField(); nomeField.setPromptText("Nome");
        TextField telefoneField = new TextField(); telefoneField.setPromptText("Telefone");
        TextField emailField = new TextField(); emailField.setPromptText("Email");
        TextField especialidadeField = new TextField(); especialidadeField.setPromptText("Especialidade");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir);

        // Tabela
        TableView<Treinador> tabela = new TableView<>();
        TableColumn<Treinador, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Treinador, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Treinador, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefone()));

        TableColumn<Treinador, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Treinador, String> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEspecialidade()));

        tabela.getColumns().addAll(colId, colNome, colTelefone, colEmail, colEspecialidade);

        // Ações dos botões
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                String telefone = telefoneField.getText();
                String email = emailField.getText();
                String especialidade = especialidadeField.getText();

                Treinador treinador = new Treinador(id, nome, telefone, email, especialidade);
                Persistencia.adicionarTreinador(treinador);

                tabela.getItems().add(treinador);
                new Alert(Alert.AlertType.INFORMATION, "Treinador salvo com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar treinador: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            tabela.getItems().setAll(Persistencia.lerTreinadores());
        });

        btnExcluir.setOnAction(e -> {
            Treinador selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                ArrayList<Treinador> treinadores = Persistencia.lerTreinadores();

                // Remove apenas o treinador selecionado
                for (int i = 0; i < treinadores.size(); i++) {
                    if (treinadores.get(i).getId() == selecionado.getId()) {
                        treinadores.remove(i);
                        break;
                    }
                }

                Persistencia.salvarTreinadores(treinadores);
                tabela.getItems().setAll(treinadores);
                new Alert(Alert.AlertType.INFORMATION, "Treinador excluído com sucesso!").show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Treinador selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    selecionado.setNome(nomeField.getText());
                    selecionado.setTelefone(telefoneField.getText());
                    selecionado.setEmail(emailField.getText());
                    selecionado.setEspecialidade(especialidadeField.getText());

                    ArrayList<Treinador> treinadores = Persistencia.lerTreinadores();
                    for (int i = 0; i < treinadores.size(); i++) {
                        if (treinadores.get(i).getId() == selecionado.getId()) {
                            treinadores.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarTreinadores(treinadores);
                    tabela.getItems().setAll(treinadores);
                    new Alert(Alert.AlertType.INFORMATION, "Treinador atualizado com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar treinador: " + ex.getMessage()).show();
                }
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, nomeField, telefoneField, emailField, especialidadeField, botoes, tabela);
        return box;
    }
}
