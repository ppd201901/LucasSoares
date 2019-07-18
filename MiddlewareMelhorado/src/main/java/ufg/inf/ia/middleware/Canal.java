/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.ia.middleware;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author Lucas
 */
public class Canal {

    private int id;
    private List<Mensagem> mensagens;
    private boolean ativo;
    private List<Aplicacao> appsLeitores;
    private List<Aplicacao> appsEscritores;

    public Canal(boolean ativo) {
        this.ativo = ativo;
        this.mensagens = new ArrayList();

    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Aplicacao> getAppsLeitores() {
		return appsLeitores;
	}

	public void setAppsLeitores(List<Aplicacao> appsLeitores) {
		this.appsLeitores = appsLeitores;
	}

	public List<Aplicacao> getAppsEscritores() {
		return appsEscritores;
	}

	public void setAppsEscritores(List<Aplicacao> appsEscritores) {
		this.appsEscritores = appsEscritores;
	}

	public int getMensagensPendentes() {

        int numMensagens = 0;

        if (!getMensagens().isEmpty()) {

            for (int i = 0; i < getMensagens().size(); i++) {
                if (!getMensagens().get(i).isEnviada()) {
                    numMensagens++;
                }
            }
        }
        return numMensagens;
    }

    public void registrarLog(ServletContext servlet, String tipo, String app, String mensagem) {

        Object hist = servlet.getAttribute("historico");

        if (hist != null) {
            List<String> historico = (List<String>) hist;
            historico.add("Mensagem: " +mensagem+" "+ tipo +" em " + new Date().toString() + " pela aplicação "+ app);
        } else {
            List<String> historico = new ArrayList();
            historico.add("Mensagem: " +mensagem+" "+tipo +" em " + new Date().toString() + " pela aplicação "+ app);
            servlet.setAttribute("historico", historico);
        }

    }

}
