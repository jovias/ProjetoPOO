package com.mitra;

import com.mitra.controllers.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        // Abas principais
        Tab tabAtleta      = new Tab("Atleta", new AtletaController().mostrar());
        Tab tabTreinador   = new Tab("Treinador", new TreinadorController().mostrar());
        Tab tabModalidades = new Tab("Modalidades", new ModalidadesController().mostrar());
        Tab tabEquipes     = new Tab("Equipes", new EquipesController().mostrar());
        Tab tabJogos       = new Tab("Jogos", new JogoController().mostrar());
        Tab tabMedico      = new Tab("Médico", new MedicoController().mostrar());
        Tab tabPresidente  = new Tab("Presidente", new PresidenteController().mostrar());
        Tab tabExercicio   = new Tab("Exercício", new ExercicioController().mostrar());
        Tab tabAvaliacao   = new Tab("Avaliação Física", new AvaliacaoFisicaController().mostrar());
        Tab tabMassagista  = new Tab("Massagista", new MassagistaController().mostrar());
        Tab tabImprensa    = new Tab("Imprensa", new ImprensaController().mostrar());

        // Abas de login e perfil
        Tab tabLogin  = new Tab("Login");
        Tab tabPerfil = new Tab("Perfil");

        // Logout
        Runnable aoLogout = () -> {
            try {
                tabPane.getTabs().clear();
                tabPane.getTabs().add(tabLogin);
                tabPane.getSelectionModel().select(tabLogin);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao voltar para login: " + ex.getMessage()).show();
            }
        };

        // Login
        LoginController loginController = new LoginController(() -> {
            try {
                tabPerfil.setContent(new UsuarioController(aoLogout).mostrar());
                tabPane.getTabs().clear();
                tabPane.getTabs().addAll(
                        tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabJogos,
                        tabMedico, tabPresidente, tabExercicio, tabAvaliacao,
                        tabMassagista, tabImprensa, tabPerfil
                );
                tabPane.getSelectionModel().select(tabPerfil);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao liberar abas: " + ex.getMessage()).show();
            }
        });

        tabLogin.setContent(loginController.mostrar());
        tabPerfil.setContent(new UsuarioController(aoLogout).mostrar());

        // Verifica sessão
        try {
            if (loginController.temSessaoAtiva()) {
                tabPane.getTabs().addAll(
                        tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabJogos,
                        tabMedico, tabPresidente, tabExercicio, tabAvaliacao,
                        tabMassagista, tabImprensa, tabPerfil
                );
                tabPane.getSelectionModel().select(tabPerfil);
            } else {
                tabPane.getTabs().add(tabLogin);
            }
        } catch (Exception ex) {
            tabPane.getTabs().add(tabLogin);
            new Alert(Alert.AlertType.ERROR, "Erro ao verificar sessão: " + ex.getMessage()).show();
        }

        // Todas as abas não podem ser fechadas
        for (Tab tab : tabPane.getTabs()) {
            tab.setClosable(false);
        }

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
