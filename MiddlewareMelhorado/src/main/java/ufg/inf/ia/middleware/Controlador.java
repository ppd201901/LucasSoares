package ufg.inf.ia.middleware;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if ("cadastrarAplicacao".equals(acao)) {

            String nome = request.getParameter("nome");

            cadastrarAplicacao(nome);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + "cadastrar-aplicacao.jsp");
            requestDispatcher.forward(request, response);

        }

        if ("adicionarCanal".equals(acao)) {

            String ativo = request.getParameter("ativo");

            adicionarCanal(ativo);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + "cadastrar-canal.jsp");
            requestDispatcher.forward(request, response);

        }

        if ("excluirAplicacao".equals(acao)) {
            String indice = request.getParameter("indice");

            int i = Integer.parseInt(indice);
            excluirAplicacao(i);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + "cadastrar-aplicacao.jsp");
            requestDispatcher.forward(request, response);

        }

        if ("desativarCanal".equals(acao)) {
            String indice = request.getParameter("indice");

            int i = Integer.parseInt(indice);

            desativarCanal(i);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + "cadastrar-canal.jsp");
            requestDispatcher.forward(request, response);

        }

        if ("ativarCanal".equals(acao)) {
            String indice = request.getParameter("indice");

            int i = Integer.parseInt(indice);

            ativarCanal(i);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + "cadastrar-canal.jsp");
            requestDispatcher.forward(request, response);

        }

        if ("excluirCanal".equals(acao)) {
            String id = request.getParameter("id");

            int identificador = Integer.parseInt(id);
            System.out.println(id);
            excluirCanal(identificador);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + "cadastrar-canal.jsp");
            requestDispatcher.forward(request, response);

        }

        if ("associarAplicacao".equals(acao)) {
            String aplicacao = request.getParameter("aplicacao");
            String tipo = request.getParameter("tipo");
            String canal = request.getParameter("canal");

            associarAplicacao( Integer.parseInt(aplicacao), tipo, Integer.parseInt(canal));

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + "cadastrar-canal.jsp");
            requestDispatcher.forward(request, response);

        }

    }

    public void cadastrarAplicacao(String nome) {
        Aplicacao app = new Aplicacao(nome);
        List<Aplicacao> list = (List<Aplicacao>) getServletContext().getAttribute("listApp");

        if (list == null) {
            list = new ArrayList();
            list.add(app);
        } else {
            list.add(app);
        }
        getServletContext().setAttribute("listApp", list);

    }

    public void adicionarCanal(String ativo) {
        Canal canal;
        if ("true".equals(ativo)) {
            canal = new Canal(true);
        } else {
            canal = new Canal(false);
        }

        List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");

        if (listCanal == null) {
            listCanal = new ArrayList();
            canal.setId(1);
            listCanal.add(canal);
        } else {

            if (listCanal.size() - 1 < 0) {
                canal.setId(1);
                listCanal.add(canal);
            } else {
                canal.setId(listCanal.get(listCanal.size() - 1).getId() + 1);
                listCanal.add(canal);

            }
        }
        getServletContext().setAttribute("listCanal", listCanal);

    }

    public void excluirAplicacao(int indice) {

        List<Aplicacao> listApp = (List<Aplicacao>) getServletContext().getAttribute("listApp");
        listApp.remove(indice);
        getServletContext().setAttribute("listApp", listApp);
    }

    public void desativarCanal(int canal) {
        List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");
        listCanal.get(canal).setAtivo(false);
        getServletContext().setAttribute("listCanal", listCanal);

    }

    private void ativarCanal(int canal) {
        List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");
        listCanal.get(canal).setAtivo(true);
        getServletContext().setAttribute("listCanal", listCanal);
    }

    public void excluirCanal(int id) {
        List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");
        for (int i = 0; i < listCanal.size(); i++) {
            if (listCanal.get(i).getId() == id) {
                listCanal.remove(i);
            }
        }
        getServletContext().setAttribute("listCanal", listCanal);

    }

    public void associarAplicacao( int indiceAplicacao, String tipo, int id) {
        List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");
        List<Aplicacao> listApp = (List<Aplicacao>) getServletContext().getAttribute("listApp");

        for (int i = 0; i < listCanal.size(); i++) {
            if (listCanal.get(i).getId() == id) {
                if ("escritora".equals(tipo)) {
                	
                	List <Aplicacao> escritoresTemp = listCanal.get(i).getAppsEscritores(); 
                	
                	Aplicacao appTemp = listApp.get(indiceAplicacao);
                	
                	if(escritoresTemp == null) {
                		escritoresTemp = (List <Aplicacao>)new ArrayList();
                	}
                	
                	if(!escritoresTemp.contains(appTemp))
                		escritoresTemp.add(appTemp);
                	
                    listCanal.get(i).setAppsEscritores(escritoresTemp);
                    
                } else {
                	
                	
                	List <Aplicacao> leitoresTemp = listCanal.get(i).getAppsLeitores(); 
                	
                	Aplicacao appTemp = listApp.get(indiceAplicacao);
                	
                	if(leitoresTemp == null) {
                		leitoresTemp = (List <Aplicacao>)new ArrayList();
                	}
                	
                	if(!leitoresTemp.contains(appTemp))
                		leitoresTemp.add(appTemp);
                	
                    listCanal.get(i).setAppsLeitores(leitoresTemp);

                }
            }
        }

        getServletContext().setAttribute("listCanal", listCanal);

    }
    
    
    private void resposta(HttpServletResponse response, String resposta) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("content-type", "application/x-www-form-urlencoded");
        response.getWriter().write("{\"mensagem\":\"" + resposta + "\"}");

    }
    

}