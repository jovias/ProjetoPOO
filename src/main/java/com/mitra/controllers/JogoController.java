package com.mitra.controllers;

import com.mitra.model.Equipes;
import com.mitra.model.Jogo;
import com.mitra.model.Modalidades;
import com.mitra.model.Persistencia;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.ArrayList;

public class JogoController {

    public VBox mostrar(){
        VBox box = new VBox(10);

        // Titulo
        Label titulo = new Label("Cadastro de Jogo");
        titulo.setFont(new Font("Arial", 20));

        // Campos
        TextField idField = new TextField(); idField.setPromptText("ID");
        ComboBox<String> equipeBox = new ComboBox<>(); equipeBox.setPromptText("Equipe");
        DatePicker dataField = new DatePicker(); dataField.setPromptText("Data");
        TextField adversarioField = new TextField(); adversarioField.setPromptText("Adversario");
        ComboBox<String> modalidadeBox = new ComboBox<>(); modalidadeBox.setPromptText("Modalidade");

        carregarSelects(equipeBox, modalidadeBox);

        // Botoes
        Button btnSalvar = new Button("Salvar");
        Button btnListar = new Button("Listar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnRecarregar = new Button("Recarregar selects");
        HBox botoes = new HBox(10, btnSalvar, btnListar, btnAtualizar, btnExcluir, btnRecarregar);

        // Tabela
        TableView<Jogo> tabela = new TableView<>();
        TableColumn<Jogo, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Jogo, String> colEquipe = new TableColumn<>("Equipe");
        colEquipe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEquipe()));

        TableColumn<Jogo, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getData())));

        TableColumn<Jogo, String> colAdversario = new TableColumn<>("Adversario");
        colAdversario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdversario()));

        TableColumn<Jogo, String> colModalidade = new TableColumn<>("Modalidade");
        colModalidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModalidade()));

        tabela.getColumns().addAll(colId, colEquipe, colData, colAdversario, colModalidade);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                idField.setText(String.valueOf(selecionado.getId()));
                equipeBox.setValue(selecionado.getEquipe());
                dataField.setValue(selecionado.getData());
                adversarioField.setText(selecionado.getAdversario());
                modalidadeBox.setValue(selecionado.getModalidade());
            }
        });

        // Acoes dos botoes
        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String equipe = equipeBox.getValue();
                LocalDate dataJogo = dataField.getValue();
                String adversario = adversarioField.getText();
                String modalidade = modalidadeBox.getValue();

                if (equipe == null || dataJogo == null || adversario.isBlank() || modalidade == null) {
                    new Alert(Alert.AlertType.ERROR, "Preencha equipe, data, adversario e modalidade.").show();
                    return;
                }

                Jogo jogo = new Jogo(id, equipe, dataJogo, adversario, modalidade);
                Persistencia.adicionarJogo(jogo);

                tabela.getItems().add(jogo);
                new Alert(Alert.AlertType.INFORMATION, "Jogo salvo com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar jogo: " + ex.getMessage()).show();
            }
        });

        btnListar.setOnAction(e -> {
            try {
                tabela.getItems().setAll(Persistencia.lerJogos());
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao listar jogos: " + ex.getMessage()).show();
            }
        });

        btnExcluir.setOnAction(e -> {
            try {
                Jogo selecionado = tabela.getSelectionModel().getSelectedItem();
                if (selecionado != null) {
                    ArrayList<Jogo> jogos = Persistencia.lerJogos();

                    // Remove apenas o jogo selecionado
                    for (int i = 0; i < jogos.size(); i++) {
                        if (jogos.get(i).getId() == selecionado.getId()) {
                            jogos.remove(i);
                            break;
                        }
                    }

                    Persistencia.salvarJogos(jogos);
                    tabela.getItems().setAll(jogos);
                    new Alert(Alert.AlertType.INFORMATION, "Jogo excluido com sucesso!").show();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao excluir jogo: " + ex.getMessage()).show();
            }
        });

        btnAtualizar.setOnAction(e -> {
            Jogo selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    if (equipeBox.getValue() == null || dataField.getValue() == null || adversarioField.getText().isBlank() || modalidadeBox.getValue() == null) {
                        new Alert(Alert.AlertType.ERROR, "Preencha equipe, data, adversario e modalidade.").show();
                        return;
                    }

                    selecionado.setEquipe(equipeBox.getValue());
                    selecionado.setData(dataField.getValue());
                    selecionado.setAdversario(adversarioField.getText());
                    selecionado.setModalidade(modalidadeBox.getValue());

                    ArrayList<Jogo> jogos = Persistencia.lerJogos();
                    for (int i = 0; i < jogos.size(); i++) {
                        if (jogos.get(i).getId() == selecionado.getId()) {
                            jogos.set(i, selecionado);
                            break;
                        }
                    }
                    Persistencia.salvarJogos(jogos);
                    tabela.getItems().setAll(jogos);
                    new Alert(Alert.AlertType.INFORMATION, "Jogo atualizado com sucesso!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao atualizar jogo: " + ex.getMessage()).show();
                }
            }
        });

        btnRecarregar.setOnAction(e -> {
            try {
                carregarSelects(equipeBox, modalidadeBox);
                new Alert(Alert.AlertType.INFORMATION, "Selects recarregados com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao recarregar selects: " + ex.getMessage()).show();
            }
        });

        // Monta layout
        box.getChildren().addAll(titulo, idField, equipeBox, dataField, adversarioField, modalidadeBox, botoes, tabela);
        return box;
    }

    private void carregarSelects(ComboBox<String> equipeBox, ComboBox<String> modalidadeBox) {
        String equipeSelecionada = equipeBox.getValue();
        String modalidadeSelecionada = modalidadeBox.getValue();

        equipeBox.getItems().clear();
        for (Equipes equipe : Persistencia.lerEquipes()) {
            equipeBox.getItems().add(equipe.getNome());
        }

        modalidadeBox.getItems().clear();
        for (Modalidades modalidade : Persistencia.lerModalidades()) {
            modalidadeBox.getItems().add(modalidade.getNome());
        }

        if (equipeSelecionada != null && equipeBox.getItems().contains(equipeSelecionada)) {
            equipeBox.setValue(equipeSelecionada);
        }

        if (modalidadeSelecionada != null && modalidadeBox.getItems().contains(modalidadeSelecionada)) {
            modalidadeBox.setValue(modalidadeSelecionada);
        }
    }
}
