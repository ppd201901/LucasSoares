/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.ia.middleware;

/**
 *
 * @author Lucas
 */
public class Mensagem {

    private String conteudo;
    private boolean lida;

    public Mensagem() {

        this.lida = false;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public boolean isEnviada() {
        return lida;
    }

    public void setEnviada(boolean lida) {
        this.lida = lida;
    }

}
