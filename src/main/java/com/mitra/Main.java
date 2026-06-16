package com.mitra;

import com.mitra.controllers.*;
import javafx.application.Application;
import javafx.scene.Scene;
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
        Tab tabMedico = new Tab("Médico", new MedicoController().mostrar());
        Tab tabPresidente = new Tab("Presidente", new PresidenteController().mostrar());
        Tab tabLogin = new Tab("Login");
        Tab tabPerfil = new Tab("Perfil");

        Runnable aoLogout = () -> {
            tabPane.getTabs().removeAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabMedico, tabPresidente, tabPerfil);
            tabPane.getTabs().add(tabLogin);
            tabPane.getSelectionModel().select(tabLogin);
        };

        LoginController loginController = new LoginController(() -> {
            tabPerfil.setContent(new UsuarioController(aoLogout).mostrar());
            tabPane.getTabs().remove(tabLogin);
            tabPane.getTabs().removeAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabMedico, tabPresidente, tabPerfil);
            tabPane.getTabs().addAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabMedico, tabPresidente, tabPerfil);
            tabPane.getSelectionModel().select(tabPerfil);
        });

        tabLogin.setContent(loginController.mostrar());
        tabPerfil.setContent(new UsuarioController(aoLogout).mostrar());

        if (loginController.temSessaoAtiva()) {
            tabPane.getTabs().addAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes, tabMedico, tabPresidente, tabPerfil);
            tabPane.getSelectionModel().select(tabPerfil);
        } else {
            tabPane.getTabs().add(tabLogin);
        }

        tabLogin.setClosable(false);
        tabPerfil.setClosable(false);
        tabAtleta.setClosable(false);
        tabTreinador.setClosable(false);
        tabModalidades.setClosable(false);
        tabEquipes.setClosable(false);
        tabMedico.setClosable(false);
        tabPresidente.setClosable(false);

        Scene scene = new Scene(tabPane, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Sistema MITRA");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
