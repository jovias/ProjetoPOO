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

        Tab tabAtleta = new Tab("Atleta", new AtletaController().mostrar());
        Tab tabTreinador = new Tab("Treinador", new TreinadorController().mostrar());
        Tab tabModalidades = new Tab("Modalidades", new ModalidadesController().mostrar());
        Tab tabEquipes = new Tab("Equipes", new EquipesController().mostrar());
        Tab tabJogos = new Tab("Jogos", new JogoController().mostrar());
        Tab tabMedico = new Tab("Médico", new MedicoController().mostrar());
        Tab tabPresidente = new Tab("Presidente", new PresidenteController().mostrar());
        Tab tabExercicio = new Tab("Exercício", new ExercicioController().mostrar());
        Tab tabMetricas = new Tab("Métricas", new MetricasController().mostrar());
        Tab tabLogin = new Tab("Login");
        Tab tabPerfil = new Tab("Perfil");

        Runnable aoLogout = () -> {
            try {
                tabPane.getTabs().removeAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabJogos, tabMedico, tabPresidente, tabPerfil, tabExercicio, tabMetricas);
                tabPane.getTabs().add(tabLogin);
                tabPane.getSelectionModel().select(tabLogin);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao voltar para login: " + ex.getMessage()).show();
            }
        };

        LoginController loginController = new LoginController(() -> {
            try {
                tabPerfil.setContent(new UsuarioController(aoLogout).mostrar());
                tabPane.getTabs().remove(tabLogin);
                tabPane.getTabs().removeAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabJogos, tabMedico, tabPresidente, tabPerfil, tabExercicio, tabMetricas);
                tabPane.getTabs().addAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabJogos, tabMedico, tabPresidente, tabPerfil, tabExercicio, tabMetricas);
                tabPane.getSelectionModel().select(tabPerfil);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao liberar abas: " + ex.getMessage()).show();
            }
        });

        tabLogin.setContent(loginController.mostrar());
        tabPerfil.setContent(new UsuarioController(aoLogout).mostrar());

        try {
            if (loginController.temSessaoAtiva()) {
                tabPane.getTabs().addAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabJogos, tabMedico, tabPresidente, tabPerfil, tabExercicio, tabMetricas);
                tabPane.getSelectionModel().select(tabPerfil);
            } else {
                tabPane.getTabs().add(tabLogin);
            }
        } catch (Exception ex) {
            tabPane.getTabs().add(tabLogin);
            new Alert(Alert.AlertType.ERROR, "Erro ao verificar sessao: " + ex.getMessage()).show();
        }

        tabLogin.setClosable(false);
        tabPerfil.setClosable(false);
        tabAtleta.setClosable(false);
        tabTreinador.setClosable(false);
        tabModalidades.setClosable(false);
        tabEquipes.setClosable(false);
        tabJogos.setClosable(false);
        tabMedico.setClosable(false);
        tabPresidente.setClosable(false);
        tabExercicio.setClosable(false);
        tabMetricas.setClosable(false);

        Scene scene = new Scene(tabPane, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Sistema MITRA");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
