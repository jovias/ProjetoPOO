package com.mitra;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainUI extends Application {

    @Override
    public void start(Stage stage) {
        // TabPane é o container que permite criar várias abas
        TabPane tabPane = new TabPane();

        // Criamos a aba de Atleta
        Tab tabAtleta = criarAbaAtleta();

        // Criamos a aba de Treinador
        Tab tabTreinador = criarAbaTreinador();

        // Criamos a aba Modalidades
        Tab tabModalidades = criarAbaModalidades();

        // Criamos a aba Equipes
        Tab tabEquipes = criarAbaEquipes();

        // Adiciona as abas ao TabPane
        tabPane.getTabs().addAll(tabAtleta, tabTreinador, tabModalidades, tabEquipes);

        // Cena principal
        Scene scene = new Scene(tabPane, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Sistema MITRA");
        stage.show();
    }

    // ------------------- CRUD ATLETA -------------------
    private Tab criarAbaAtleta() {
        // Layout vertical para organizar os campos e botões
        VBox atletaBox = new VBox(10);

        // Campos de entrada de dados
        TextField idAtleta = new TextField();
        idAtleta.setPromptText("ID");
        TextField nomeAtleta = new TextField();
        nomeAtleta.setPromptText("Nome");
        DatePicker nascimentoAtleta = new DatePicker(); // campo de data
        TextField pesoAtleta = new TextField();
        pesoAtleta.setPromptText("Peso");
        TextField alturaAtleta = new TextField();
        alturaAtleta.setPromptText("Altura");

        // Tabela para mostrar os atletas cadastrados
        TableView<Atleta> tabelaAtleta = new TableView<>();

        // Coluna ID (precisa converter int para String)
        TableColumn<Atleta, String> colIdA = new TableColumn<>("ID");
        colIdA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        // Coluna Nome
        TableColumn<Atleta, String> colNomeA = new TableColumn<>("Nome");
        colNomeA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        // Coluna Data de Nascimento
        TableColumn<Atleta, String> colNascimentoA = new TableColumn<>("Nascimento");
        colNascimentoA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDataNascimento())));

        // Coluna Peso
        TableColumn<Atleta, String> colPesoA = new TableColumn<>("Peso");
        colPesoA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPeso())));

        // Coluna Altura
        TableColumn<Atleta, String> colAlturaA = new TableColumn<>("Altura");
        colAlturaA.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAltura())));

        // Adiciona todas as colunas na tabela
        tabelaAtleta.getColumns().addAll(colIdA, colNomeA, colNascimentoA, colPesoA, colAlturaA);

        // Botões CRUD
        Button salvarAtleta = new Button("Inserir");
        Button listarAtleta = new Button("Listar");
        Button atualizarAtleta = new Button("Atualizar");
        Button excluirAtleta = new Button("Excluir");

        // ---------------- INSERIR ----------------
        salvarAtleta.setOnAction(e -> {
            try {
                // Cria um novo objeto Atleta com os dados dos campos
                Atleta atleta = new Atleta(
                        Integer.parseInt(idAtleta.getText()),
                        nomeAtleta.getText(),
                        nascimentoAtleta.getValue(),
                        Double.parseDouble(pesoAtleta.getText()),
                        Double.parseDouble(alturaAtleta.getText())
                );
                // Adiciona o atleta na lista e salva no arquivo binário
                Persistencia.adicionarAtleta(atleta);
                new Alert(Alert.AlertType.INFORMATION, "Atleta salvo!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
            }
        });

        // ---------------- LISTAR ----------------
        listarAtleta.setOnAction(e -> {
            // Limpa a tabela e carrega todos os atletas do arquivo
            tabelaAtleta.getItems().clear();
            tabelaAtleta.getItems().addAll(Persistencia.lerAtletas());
        });

        // ---------------- ATUALIZAR ----------------
        atualizarAtleta.setOnAction(e -> {
            Atleta selecionado = tabelaAtleta.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    // Atualiza os dados do objeto selecionado
                    selecionado.setNome(nomeAtleta.getText());
                    selecionado.setPeso(Double.parseDouble(pesoAtleta.getText()));
                    selecionado.setAltura(Double.parseDouble(alturaAtleta.getText()));

                    // Regrava a lista inteira no arquivo
                    ArrayList<Atleta> lista = new ArrayList<>(tabelaAtleta.getItems());
                    Persistencia.salvarAtletas(lista);

                    // Atualiza a tabela
                    listarAtleta.fire();
                    new Alert(Alert.AlertType.INFORMATION, "Atleta atualizado!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
                }
            }
        });

        // ---------------- EXCLUIR ----------------
        excluirAtleta.setOnAction(e -> {
            Atleta selecionado = tabelaAtleta.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                // Remove da tabela
                tabelaAtleta.getItems().remove(selecionado);

                // Regrava a lista sem o atleta excluído
                ArrayList<Atleta> lista = new ArrayList<>(tabelaAtleta.getItems());
                Persistencia.salvarAtletas(lista);

                new Alert(Alert.AlertType.INFORMATION, "Atleta excluído!").show();
            }
        });

        // Adiciona todos os componentes na aba
        atletaBox.getChildren().addAll(idAtleta, nomeAtleta, nascimentoAtleta, pesoAtleta, alturaAtleta,
                salvarAtleta, listarAtleta, atualizarAtleta, excluirAtleta, tabelaAtleta);

        return new Tab("Atleta", atletaBox);
    }

    // ------------------- CRUD TREINADOR -------------------
    private Tab criarAbaTreinador() {
        // Layout vertical para organizar os campos e botões
        VBox treinadorBox = new VBox(10);

        // Campos de entrada de dados
        TextField idTreinador = new TextField();
        idTreinador.setPromptText("ID");
        TextField nomeTreinador = new TextField();
        nomeTreinador.setPromptText("Nome");
        TextField telefoneTreinador = new TextField();
        telefoneTreinador.setPromptText("Telefone");
        TextField emailTreinador = new TextField();
        emailTreinador.setPromptText("Email");
        TextField especialidadeTreinador = new TextField();
        especialidadeTreinador.setPromptText("Especialidade");

        // Tabela para mostrar os treinadores cadastrados
        TableView<Treinador> tabelaTreinador = new TableView<>();

        // Coluna ID (precisa converter int para String)
        TableColumn<Treinador, String> colIdT = new TableColumn<>("ID");
        colIdT.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        // Coluna Nome
        TableColumn<Treinador, String> colNomeT = new TableColumn<>("Nome");
        colNomeT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        // Coluna Telefone
        TableColumn<Treinador, String> colTelefoneT = new TableColumn<>("Telefone");
        colTelefoneT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefone()));

        // Coluna Email
        TableColumn<Treinador, String> colEmailT = new TableColumn<>("Email");
        colEmailT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        // Coluna Especialidade
        TableColumn<Treinador, String> colEspT = new TableColumn<>("Especialidade");
        colEspT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEspecialidade()));

        // Adiciona todas as colunas na tabela
        tabelaTreinador.getColumns().addAll(colIdT, colNomeT, colTelefoneT, colEmailT, colEspT);

        // Botões CRUD
        Button salvarTreinador = new Button("Inserir");
        Button listarTreinador = new Button("Listar");
        Button atualizarTreinador = new Button("Atualizar");
        Button excluirTreinador = new Button("Excluir");

        // ---------------- INSERIR ----------------
        salvarTreinador.setOnAction(e -> {
            try {
                // Cria um novo objeto Treinador com os dados dos campos
                Treinador treinador = new Treinador(
                        Integer.parseInt(idTreinador.getText()),
                        nomeTreinador.getText(),
                        telefoneTreinador.getText(),
                        emailTreinador.getText(),
                        especialidadeTreinador.getText()
                );
                // Adiciona o treinador na lista e salva no arquivo binário
                Persistencia.adicionarTreinador(treinador);
                new Alert(Alert.AlertType.INFORMATION, "Treinador salvo!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
            }
        });

        // ---------------- LISTAR ----------------
        listarTreinador.setOnAction(e -> {
            // Limpa a tabela e carrega todos os treinadores do arquivo
            tabelaTreinador.getItems().clear();
            tabelaTreinador.getItems().addAll(Persistencia.lerTreinadores());
        });

        // ---------------- ATUALIZAR ----------------
        atualizarTreinador.setOnAction(e -> {
            Treinador selecionado = tabelaTreinador.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    // Atualiza os dados do objeto selecionado
                    selecionado.setNome(nomeTreinador.getText());
                    selecionado.setTelefone(telefoneTreinador.getText());
                    selecionado.setEmail(emailTreinador.getText());
                    selecionado.setEspecialidade(especialidadeTreinador.getText());

                    // Regrava a lista inteira no arquivo
                    ArrayList<Treinador> lista = new ArrayList<>(tabelaTreinador.getItems());
                    Persistencia.salvarTreinadores(lista);

                    // Atualiza a tabela
                    listarTreinador.fire();
                    new Alert(Alert.AlertType.INFORMATION, "Treinador atualizado!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
                }
            }
        });

        // ---------------- EXCLUIR ----------------
        excluirTreinador.setOnAction(e -> {
            Treinador selecionado = tabelaTreinador.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                // Remove da tabela
                tabelaTreinador.getItems().remove(selecionado);

                // Regrava a lista sem o treinador excluído
                ArrayList<Treinador> lista = new ArrayList<>(tabelaTreinador.getItems());
                Persistencia.salvarTreinadores(lista);

                new Alert(Alert.AlertType.INFORMATION, "Treinador excluído!").show();
            }
        });

        // Adiciona todos os componentes na aba
        treinadorBox.getChildren().addAll(idTreinador, nomeTreinador, telefoneTreinador, emailTreinador, especialidadeTreinador,
                salvarTreinador, listarTreinador, atualizarTreinador, excluirTreinador, tabelaTreinador);

        return new Tab("Treinador", treinadorBox);
    }

    // ------------------- CRUD MODALIDADES -------------------

    private Tab criarAbaModalidades() {
        // Layout vertical para organizar os campos e botões
        VBox modalidadesBox = new VBox(10);

        // Campos de entrada de dados
        TextField idModalidade = new TextField();
        idModalidade.setPromptText("ID");
        TextField nomeModalidade = new TextField();
        nomeModalidade.setPromptText("Nome");
        TextField statusModalidade = new TextField();
        statusModalidade.setPromptText("Status");
        TextField treinadorModalidade = new TextField();
        treinadorModalidade.setPromptText("Treinador responsável pela modalidade");

        // Tabela para mostrar as modalidades cadastradas
        TableView<Modalidades> tabelaModalidade = new TableView<>();

        // Coluna ID (int)
        TableColumn<Modalidades, Integer> colIdM = new TableColumn<>("ID");
        colIdM.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        //Coluna nome
        TableColumn<Modalidades, String> colNomeM = new TableColumn<>("Nome");
        colNomeM.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        //Coluna status
        TableColumn<Modalidades, String> colstatusM = new TableColumn<>("Status");
        colstatusM.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        //Coluna treinador responsável
        TableColumn<Modalidades, String> coltreinadorM = new TableColumn<>("Treinador responsável");
        coltreinadorM.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTreinadorResponsavel()));

        // Adiciona todas as colunas na tabela
        tabelaModalidade.getColumns().addAll(colIdM, colNomeM, colstatusM, coltreinadorM);

        // Botões CRUD
        Button salvarModalidade = new Button("Inserir");
        Button listarModalidade = new Button("Listar");
        Button atualizarModalidade = new Button("Atualizar");
        Button excluirModalidade = new Button("Excluir");

        // ---------------- INSERIR ----------------
        salvarModalidade.setOnAction(e -> {
            try {
                // Cria um novo objeto modalidade com os dados dos campos
                Modalidades modalidades = new Modalidades(
                        Integer.parseInt(idModalidade.getText()), // converte o que o usuário digitou para int
                        nomeModalidade.getText(),
                        statusModalidade.getText(),
                        treinadorModalidade.getText()
                );

                // Adiciona a modalidade na lista e salva no arquivo binário
                Persistencia.adicionarModalidade(modalidades);
                new Alert(Alert.AlertType.INFORMATION, "Modalidade salva!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
            }
        });

        // ---------------- LISTAR ----------------
        listarModalidade.setOnAction(e -> {
            // Limpa a tabela e carrega todas modalidades do arquivo
            tabelaModalidade.getItems().clear();
            tabelaModalidade.getItems().addAll(Persistencia.lerModalidades());
        });

        // ---------------- ATUALIZAR ----------------
        atualizarModalidade.setOnAction(e -> {
            Modalidades selecionada = tabelaModalidade.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                try {
                    // Atualiza os dados do objeto selecionado
                    selecionada.setNome(nomeModalidade.getText());
                    selecionada.setStatus(statusModalidade.getText());
                    selecionada.setTreinadorResponsavel(treinadorModalidade.getText());

                    // Regrava a lista inteira no arquivo
                    ArrayList<Modalidades> lista = new ArrayList<>(tabelaModalidade.getItems());
                    Persistencia.salvarModalidades(lista);

                    // Atualiza a tabela
                    listarModalidade.fire();
                    new Alert(Alert.AlertType.INFORMATION, "Modalidade atualizada!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
                }
            }
        });

        // ---------------- EXCLUIR ----------------
        excluirModalidade.setOnAction(e -> {
            Modalidades selecionada = tabelaModalidade.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                //remove da tabela
                tabelaModalidade.getItems().remove(selecionada);

                //Regrava a lista sem a modalidade excluída
                ArrayList<Modalidades> lista = new ArrayList<>(tabelaModalidade.getItems());
                Persistencia.salvarModalidades(lista);

                new Alert(Alert.AlertType.INFORMATION, "Modalidade excluída!").show();
            }
        });

        // Adiciona todos os componentes na aba
        modalidadesBox.getChildren().addAll(idModalidade, nomeModalidade, statusModalidade, treinadorModalidade,
                salvarModalidade, listarModalidade, atualizarModalidade, excluirModalidade, tabelaModalidade);

        return new Tab("Modalidades", modalidadesBox);
    }

    // ------------------- CRUD EQUIPES -------------------
    private Tab criarAbaEquipes() {
        // Layout vertical para organizar os campos e botões
        VBox equipesBox = new VBox(10);

        // Campos de entrada de dados
        TextField idEquipe = new TextField();
        idEquipe.setPromptText("ID");
        TextField nomeEquipe = new TextField();
        nomeEquipe.setPromptText("Nome da equipe");
        TextField categoriaEquipe = new TextField();
        categoriaEquipe.setPromptText("Categoria");
        TextField modalidadeEquipe = new TextField();
        modalidadeEquipe.setPromptText("Modalidade da equipe");
        TextField generoEquipe = new TextField();
        generoEquipe.setPromptText("Gênero");

        // Tabela para mostrar as equipes cadastradas
        TableView<Equipes> tabelaEquipe = new TableView<>();

        // Coluna ID (int)
        TableColumn<Equipes, Integer> colIdE = new TableColumn<>("ID");
        colIdE.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        // Coluna Nome
        TableColumn<Equipes, String> colNomeE = new TableColumn<>("Nome");
        colNomeE.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        // Coluna Categoria
        TableColumn<Equipes, String> colCategoriaE = new TableColumn<>("Categoria");
        colCategoriaE.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoria()));

        // Coluna Modalidade
        TableColumn<Equipes, String> colModalidadeE = new TableColumn<>("Modalidade");
        colModalidadeE.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModalidade()));

        // Coluna Gênero
        TableColumn<Equipes, String> colGeneroE = new TableColumn<>("Gênero");
        colGeneroE.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero()));

        // Adiciona todas as colunas na tabela
        tabelaEquipe.getColumns().addAll(colIdE, colNomeE, colCategoriaE, colModalidadeE, colGeneroE);

        // Botões CRUD
        Button salvarEquipe = new Button("Inserir");
        Button listarEquipe = new Button("Listar");
        Button atualizarEquipe = new Button("Atualizar");
        Button excluirEquipe = new Button("Excluir");

        // ---------------- INSERIR ----------------

        salvarEquipe.setOnAction(e -> {
            try {
                // Cria um novo objeto da classe equipes com os dados dos campos
                Equipes equipe = new Equipes(
                        Integer.parseInt(idEquipe.getText()),
                        nomeEquipe.getText(),
                        categoriaEquipe.getText(),
                        modalidadeEquipe.getText(),
                        generoEquipe.getText()
                );
                // Adiciona a equipe na lista e salva no arquivo binário
                Persistencia.adicionarEquipe(equipe);
                new Alert(Alert.AlertType.INFORMATION, "Equipe salva!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
            }
        });

        // ---------------- LISTAR ----------------

        listarEquipe.setOnAction(e -> {
            // Limpa a tabela e carrega todas as equipes do arquivo
            tabelaEquipe.getItems().clear();
            tabelaEquipe.getItems().addAll(Persistencia.lerEquipes());
        });

        // ---------------- ATUALIZAR ----------------
        atualizarEquipe.setOnAction(e -> {
            Equipes selecionada = tabelaEquipe.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                try {
                    // Atualiza os dados do objeto selecionado
                    selecionada.setNome(nomeEquipe.getText());
                    selecionada.setCategoria(categoriaEquipe.getText());
                    selecionada.setModalidade(modalidadeEquipe.getText());
                    selecionada.setGenero(generoEquipe.getText());

                    // Regrava a lista inteira no arquivo
                    ArrayList<Equipes> lista = new ArrayList<>(tabelaEquipe.getItems());
                    Persistencia.salvarEquipes(lista);

                    // Atualiza a tabela
                    listarEquipe.fire();
                    new Alert(Alert.AlertType.INFORMATION, "Equipe atualizada!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).show();
                }
            }
        });

        // ---------------- EXCLUIR ----------------

        excluirEquipe.setOnAction(e -> {
            Equipes selecionada = tabelaEquipe.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                // Remove da tabela
                tabelaEquipe.getItems().remove(selecionada);

                // Regrava a lista sem a equipe excluída
                ArrayList<Equipes> lista = new ArrayList<>(tabelaEquipe.getItems());
                Persistencia.salvarEquipes(lista);

                new Alert(Alert.AlertType.INFORMATION, "Modalidade excluída!").show();
            }
        });

        // Adiciona todos os componentes na aba
        equipesBox.getChildren().addAll(idEquipe, nomeEquipe, categoriaEquipe, modalidadeEquipe, generoEquipe,
                salvarEquipe, listarEquipe, atualizarEquipe, excluirEquipe, tabelaEquipe);

        return new Tab("Equipes", equipesBox);
    }
}


