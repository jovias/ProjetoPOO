package com.mitra.controllers;

import com.mitra.model.Persistencia;
import com.mitra.model.SessaoAtual;
import com.mitra.model.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class LoginController {

    private Runnable aoLoginValido;

    public LoginController() {
        this(null);
    }

    public LoginController(Runnable aoLoginValido) {
        this.aoLoginValido = aoLoginValido;
    }

    public VBox mostrar() {
        garantirUsuarioPadrao();

        VBox vbox = new VBox(10);
        mostrarTelaLogin(vbox);
        return vbox;
    }

    public boolean temSessaoAtiva() {
        return usuarioSessaoAtual() != null;
    }

    private void mostrarTelaLogin(VBox vbox) {
        vbox.getChildren().clear();

        Label titulo = new Label("Login");
        titulo.setFont(new Font("Arial", 20));

        TextField emailField = new TextField(); emailField.setPromptText("Email");
        PasswordField passField = new PasswordField(); passField.setPromptText("Senha");

        Button btnLogin = new Button("Entrar");
        Button btnCadastrar = new Button("Cadastre-se");
        HBox botoes = new HBox(10, btnLogin, btnCadastrar);

        btnLogin.setOnAction((ActionEvent e) -> {
            try {
                Usuario usuario = buscarUsuarioPorLogin(emailField.getText(), passField.getText());

                if (usuario != null) {
                    Persistencia.salvarSessaoAtual(new SessaoAtual(usuario.getId(), true));
                    new Alert(Alert.AlertType.INFORMATION, "Login realizado com sucesso!").show();

                    if (aoLoginValido != null) {
                        aoLoginValido.run();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Email ou senha invalidos.").show();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao realizar login: " + ex.getMessage()).show();
            }
        });

        btnCadastrar.setOnAction((ActionEvent e) -> {
            try {
                mostrarTelaCadastro(vbox);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao abrir cadastro: " + ex.getMessage()).show();
            }
        });

        vbox.getChildren().addAll(titulo, emailField, passField, botoes);
    }

    private void mostrarTelaCadastro(VBox vbox) {
        vbox.getChildren().clear();

        Label titulo = new Label("Cadastro");
        titulo.setFont(new Font("Arial", 20));

        TextField usuarioField = new TextField(); usuarioField.setPromptText("Usuario");
        TextField emailField = new TextField(); emailField.setPromptText("Email");
        PasswordField passField = new PasswordField(); passField.setPromptText("Senha");
        PasswordField confirmarPassField = new PasswordField(); confirmarPassField.setPromptText("Confirmar senha");

        Button btnCadastrar = new Button("Cadastrar");
        Button btnVoltar = new Button("Voltar");
        HBox botoes = new HBox(10, btnCadastrar, btnVoltar);

        btnCadastrar.setOnAction((ActionEvent e) -> {
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

                if (usuarioJaCadastrado(usuarioNome, 0)) {
                    new Alert(Alert.AlertType.ERROR, "Ja existe um usuario cadastrado com esse nome.").show();
                    return;
                }

                if (emailJaCadastrado(email, 0)) {
                    new Alert(Alert.AlertType.ERROR, "Ja existe um usuario cadastrado com esse email.").show();
                    return;
                }

                Usuario usuario = new Usuario(proximoId(), usuarioNome.trim(), email.trim(), senha);
                Persistencia.adicionarUsuario(usuario);
                new Alert(Alert.AlertType.INFORMATION, "Usuario cadastrado com sucesso!").show();
                mostrarTelaLogin(vbox);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar usuario: " + ex.getMessage()).show();
            }
        });

        btnVoltar.setOnAction((ActionEvent e) -> {
            try {
                mostrarTelaLogin(vbox);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao voltar para login: " + ex.getMessage()).show();
            }
        });

        vbox.getChildren().addAll(titulo, usuarioField, emailField, passField, confirmarPassField, botoes);
    }

    public boolean validaLogin(String email, String senha){
        return buscarUsuarioPorLogin(email, senha) != null;
    }

    private Usuario buscarUsuarioPorLogin(String email, String senha) {
        if (email == null || senha == null || email.isBlank() || senha.isBlank()) {
            return null;
        }

        for (Usuario usuario : Persistencia.lerUsuarios()) {
            if (usuario.getEmail().equalsIgnoreCase(email.trim()) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
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

    private int proximoId() {
        int maiorId = 0;
        for (Usuario usuario : Persistencia.lerUsuarios()) {
            if (usuario.getId() > maiorId) {
                maiorId = usuario.getId();
            }
        }
        return maiorId + 1;
    }

    private void garantirUsuarioPadrao() {
        ArrayList<Usuario> usuarios = Persistencia.lerUsuarios();
        if (usuarios.isEmpty()) {
            Persistencia.adicionarUsuario(new Usuario(1, "Administrador", "admin@mitra.com", "123"));
        }
    }
}
