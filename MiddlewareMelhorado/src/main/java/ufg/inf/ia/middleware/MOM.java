package ufg.inf.ia.middleware;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MOM extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        System.out.println(acao);

        if ("leitura".equals(acao)) {
            lerMensagem(request, response);
        }

        if ("escrita".equals(acao)) {
            escreverMensagem(request, response);
        }

    }

    private void resposta(HttpServletResponse response, String resposta) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("content-type", "application/x-www-form-urlencoded");
        response.getWriter().write("{\"mensagem\":\"" + resposta + "\"}");

    }

    private void lerMensagem(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String nomeApp = request.getParameter("nomeApp");
        boolean existeCanal = false;
        String idCanal = request.getParameter("idCanalLeitura");

        List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");

        int id = Integer.parseInt(idCanal);

        if (listCanal != null && !listCanal.isEmpty()) {
            for (int i = 0; i < listCanal.size(); i++) {
                if (listCanal.get(i).getId() == id) {
                    existeCanal = true;
                    
                    
                    boolean podeLer = false;
                    
                    Iterator <Aplicacao> appsTemp = listCanal.get(i).getAppsLeitores().iterator();
                    
                    while(appsTemp.hasNext()) {
                    	
                    	Aplicacao appTemp = appsTemp.next();
                    	if(appTemp.getNome().equals(nomeApp)) {
                    		podeLer = true;
                    	}
                                        	
                    }
                    

                    if (podeLer) {

                        if (listCanal.get(i).isAtivo()) {

                            if (listCanal.get(i).getMensagens().isEmpty()) {

                                resposta(response, "O canal não possui mensagens a serem lidas!");

                            } else {

                                int j = 0;
                                boolean leu = false;
                                while (leu == false && j < listCanal.get(i).getMensagens().size()) {
                                    if (listCanal.get(i).getMensagens().get(j).isEnviada() == false) {
                                        resposta(response, listCanal.get(i).getMensagens().get(j).getConteudo());
                                        listCanal.get(i).getMensagens().get(j).setEnviada(true);
                                        listCanal.get(i).registrarLog(getServletContext(), "lida", nomeApp, listCanal.get(i).getMensagens().get(j).getConteudo());

                                        leu = true;
                                    }
                                    j++;
                                }

                                if (leu == false) {
                                    resposta(response, "O canal não possui mensagens a serem lidas!");
                                }

                            }
                        } else {
                            resposta(response, "O canal não está ativo!");
                        }

                    } else {
                        resposta(response, "Esta aplicação não pode ler nesse canal!");
                    }

                }
            }

        }

        if (!existeCanal) {
            resposta(response, "Não existe o canal especificado!");
        }

    }

    private void escreverMensagem(HttpServletRequest request, HttpServletResponse response) throws IOException {

        boolean existeCanal = false;
        String mensagem = request.getParameter("mensagem");
        String nomeApp = request.getParameter("nomeApp");

        String idCanal = request.getParameter("idCanalEscrita");

        System.out.println(idCanal);

        List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");

        int id = Integer.parseInt(idCanal);

        if (listCanal != null && !listCanal.isEmpty()) {
            for (int i = 0; i < listCanal.size(); i++) {
                if (listCanal.get(i).getId() == id) {
                    existeCanal = true;
                    boolean podeEscrever = false;
                    
                    Iterator <Aplicacao> appsTemp = listCanal.get(i).getAppsEscritores().iterator();
                    
                    while(appsTemp.hasNext()) {
                    	
                    	Aplicacao appTemp = appsTemp.next();
                    	if(appTemp.getNome().equals(nomeApp)) {
                    		podeEscrever = true;
                    	}
                                        	
                    }
                    
                    
                    if (podeEscrever) {
                        if (listCanal.get(i).isAtivo()) {
                            Mensagem msg = new Mensagem();
                            msg.setConteudo(mensagem);
                            listCanal.get(i).getMensagens().add(msg);
                            listCanal.get(i).registrarLog(getServletContext(), "escrita", nomeApp, mensagem);
                            resposta(response, "Mensagem enviada ao repositório de mensagens!");
                        } else {
                            resposta(response, "O canal não está ativo!");
                        }
                    } else {
                        resposta(response, "Esta aplicação não pode escrever nesse canal!");
                    }

                }
            }
        }

        if (!existeCanal) {
            resposta(response, "Não existe o canal especificado!");
        }

    }

}
