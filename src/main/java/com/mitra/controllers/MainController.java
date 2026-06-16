package com.mitra.controllers;

import com.mitra.controllers.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainController extends Application {

    @Override
    public void start(Stage stage) {
        // Cria o TabPane principal
        TabPane tabPane = new TabPane();

        // Cada aba recebe diretamente o VBox retornado pelo Controller
        Tab tabAtleta = new Tab("Atleta", new AtletaController().mostrar());
        Tab tabTreinador = new Tab("Treinador", new TreinadorController().mostrar());
        Tab tabModalidades = new Tab("Modalidades", new ModalidadesController().mostrar());
        Tab tabEquipes = new Tab("Equipes", new EquipesController().mostrar());
        Tab tabMedico = new Tab("Médico", new MedicoController().mostrar());
        Tab tabPresidente = new Tab("Presidente", new PresidenteController().mostrar());

        // Adiciona todas as abas ao TabPane
        tabPane.getTabs().addAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabMedico, tabPresidente);

        // Cena principal
        Scene scene = new Scene(tabPane, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Sistema MITRA");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
