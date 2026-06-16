package com.mitra.model;

import java.io.Serializable;

public class SessaoAtual implements Serializable {
    private int idUsuario;
    private boolean logado;

    public SessaoAtual(int idUsuario, boolean logado) {
        this.idUsuario = idUsuario;
        this.logado = logado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
}
