package com.mitra.controllers;

import com.mitra.model.Persistencia;
import com.mitra.model.SessaoAtual;
import com.mitra.model.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class UsuarioController {

    private Runnable aoLogout;

    public UsuarioController() {
        this(null);
    }

    public UsuarioController(Runnable aoLogout) {
        this.aoLogout = aoLogout;
    }

    public VBox mostrar() {
        try {
            Usuario usuarioLogado = usuarioSessaoAtual();

            if (usuarioLogado == null) {
                VBox box = new VBox(10);
                Label titulo = new Label("Perfil");
                titulo.setFont(new Font("Arial", 20));
                Label mensagem = new Label("Nenhum usuario logado.");
                box.getChildren().addAll(titulo, mensagem);
                return box;
            }

            return exibirPerfil(usuarioLogado);
        } catch (Exception ex) {
            VBox box = new VBox(10);
            Label titulo = new Label("Perfil");
            titulo.setFont(new Font("Arial", 20));
            Label mensagem = new Label("Erro ao carregar perfil: " + ex.getMessage());
            box.getChildren().addAll(titulo, mensagem);
            return box;
        }
    }

    public boolean temSessaoAtiva() {
        try {
            return usuarioSessaoAtual() != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public VBox exibirPerfil(Usuario usuarioLogado) {
        return exibirPerfil(usuarioLogado, aoLogout);
    }

    public VBox exibirPerfil(Usuario usuarioLogado, Runnable aoLogout) {
        VBox box = new VBox(10);

        Label titulo = new Label("Perfil");
        titulo.setFont(new Font("Arial", 20));

        Label usuarioLabel = new Label("Usuario");
        TextField usuarioField = new TextField(usuarioLogado.getNome());

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField(usuarioLogado.getEmail());

        Label senhaLabel = new Label("Senha");
        PasswordField passField = new PasswordField();
        passField.setText(usuarioLogado.getSenha());

        Label confirmarSenhaLabel = new Label("Confirmar senha");
        PasswordField confirmarPassField = new PasswordField();
        confirmarPassField.setText(usuarioLogado.getSenha());

        Button btnEditar = new Button("Editar");
        Button btnSalvar = new Button("Salvar");
        Button btnSair = new Button("Sair");
        Button btnExcluir = new Button("Excluir conta");
        HBox botoes = new HBox(10, btnEditar, btnSalvar, btnSair, btnExcluir);

        alterarModoEdicao(false, btnEditar, btnSalvar, usuarioField, emailField, passField, confirmarPassField);

        btnEditar.setOnAction((ActionEvent e) -> {
            alterarModoEdicao(true, btnEditar, btnSalvar, usuarioField, emailField, passField, confirmarPassField);
        });

        btnSalvar.setOnAction((ActionEvent e) -> {
            try {
                String usuarioNome = usuarioField.getText();
                String email = emailField.getText();
                String senha = passField.getText();
                String confirmarSenha = confirmarPassField.getText();

                if (usuarioNome.isBlank() || email.isBlank() || senha.isBlank() || confirmarSenha.isBlank()) {
                    new Alert(Alert.AlertType.ERROR, "Preencha usuario, email, senha e confirmacao de senha.").show();
                    return;
                }

                if (!senha.equals(confirmarSenha)) {
                    new Alert(Alert.AlertType.ERROR, "As senhas nao conferem.").show();
                    return;
                }

                if (usuarioJaCadastrado(usuarioNome, usuarioLogado.getId())) {
                    new Alert(Alert.AlertType.ERROR, "Ja existe outro usuario cadastrado com esse nome.").show();
                    return;
                }

                if (emailJaCadastrado(email, usuarioLogado.getId())) {
                    new Alert(Alert.AlertType.ERROR, "Ja existe outro usuario cadastrado com esse email.").show();
                    return;
                }

                usuarioLogado.setNome(usuarioNome.trim());
                usuarioLogado.setEmail(email.trim());
                usuarioLogado.setSenha(senha);
                atualizarUsuario(usuarioLogado);
                Persistencia.salvarSessaoAtual(new SessaoAtual(usuarioLogado.getId(), true));

                new Alert(Alert.AlertType.INFORMATION, "Perfil atualizado com sucesso!").show();
                alterarModoEdicao(false, btnEditar, btnSalvar, usuarioField, emailField, passField, confirmarPassField);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao atualizar perfil: " + ex.getMessage()).show();
            }
        });

        btnSair.setOnAction((ActionEvent e) -> {
            try {
                Persistencia.encerrarSessaoAtual();

                if (aoLogout != null) {
                    aoLogout.run();
                }

                new Alert(Alert.AlertType.INFORMATION, "Sessao encerrada com sucesso!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao sair: " + ex.getMessage()).show();
            }
        });

        btnExcluir.setOnAction((ActionEvent e) -> {
            try {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir sua conta? Esta acao nao podera ser desfeita.");
                confirmacao.showAndWait().ifPresent(resposta -> {
                    if (resposta == ButtonType.OK) {
                        try {
                            ArrayList<Usuario> usuarios = Persistencia.lerUsuarios();
                            usuarios.removeIf(u -> u.getId() == usuarioLogado.getId());
                            Persistencia.salvarUsuarios(usuarios);
                            Persistencia.encerrarSessaoAtual();

                            if (aoLogout != null) {
                                aoLogout.run();
                            }

                            new Alert(Alert.AlertType.INFORMATION, "Conta excluida com sucesso!").show();
                        } catch (Exception ex) {
                            new Alert(Alert.AlertType.ERROR, "Erro ao excluir conta: " + ex.getMessage()).show();
                        }
                    }
                });
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao confirmar exclusao: " + ex.getMessage()).show();
            }
        });

        box.getChildren().addAll(
                titulo,
                usuarioLabel, usuarioField,
                emailLabel, emailField,
                senhaLabel, passField,
                confirmarSenhaLabel, confirmarPassField,
                botoes
        );
        return box;
    }

    private void alterarModoEdicao(
            boolean editando,
            Button btnEditar,
            Button btnSalvar,
            TextField usuarioField,
            TextField emailField,
            PasswordField passField,
            PasswordField confirmarPassField
    ) {
        usuarioField.setDisable(!editando);
        emailField.setDisable(!editando);
        passField.setDisable(!editando);
        confirmarPassField.setDisable(!editando);
        btnEditar.setDisable(editando);
        btnSalvar.setDisable(!editando);
    }

    private boolean usuarioJaCadastrado(String nome, int idIgnorado) {
        for (Usuario usuario : Persistencia.lerUsuarios()) {
            if (usuario.getId() != idIgnorado && usuario.getNome().equalsIgnoreCase(nome.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean emailJaCadastrado(String email, int idIgnorado) {
        for (Usuario usuario : Persistencia.lerUsuarios()) {
            if (usuario.getId() != idIgnorado && usuario.getEmail().equalsIgnoreCase(email.trim())) {
                return true;
            }
        }
        return false;
    }

    private void atualizarUsuario(Usuario usuarioAtualizado) {
        ArrayList<Usuario> usuarios = Persistencia.lerUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioAtualizado.getId()) {
                usuarios.set(i, usuarioAtualizado);
                break;
            }
        }
        Persistencia.salvarUsuarios(usuarios);
    }

    private Usuario usuarioSessaoAtual() {
        SessaoAtual sessaoAtual = Persistencia.lerSessaoAtual();
        if (!sessaoAtual.isLogado()) {
            return null;
        }

        Usuario usuario = buscarUsuarioPorId(sessaoAtual.getIdUsuario());
        if (usuario == null) {
            Persistencia.encerrarSessaoAtual();
        }
        return usuario;
    }

    private Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : Persistencia.lerUsuarios()) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }
}
