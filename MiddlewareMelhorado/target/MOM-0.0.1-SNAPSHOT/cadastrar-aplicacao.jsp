<%-- 
    Document   : cadastrar-aplicacao
    Created on : Jun 30, 2015, 9:56:32 AM
    Author     : Lucas
--%>

<%@page import="java.util.List"%>
<%@page import="ufg.inf.ia.middleware.Aplicacao"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Cadastrar Aplicacao</title>
    </head>
    <body>

        <form action="Controlador" method="get">
            <input name="acao" type="hidden" value="cadastrarAplicacao">

            <div >
                <h1><label>Nome da Aplicação:</label </h1>  
                <br>
                <input style="width: 400px" name="nome" placeholder="Escolha do nome para identificar a aplicação no middleware!">
            </div>

            <br>

            <button type="submit" class="btn btn-default">Cadastrar</button>
            <button type="reset" class="btn btn-default">Limpar</button>

        </form>

        <br>

        <h2> <a href="index.jsp"> < voltar</a></h2>

        <h1>Aplicações Cadastradas:</h1>

        <%

            List<Aplicacao> listApp = (List<Aplicacao>) getServletContext().getAttribute("listApp");

            if (listApp == null || (listApp.size() == 0)) {
                out.println("Não há aplicação cadastrada!");

            } else {
                for (int i = 0; i < listApp.size(); i++) {

                    int codigo = i + 1;
                    out.println(" <h2> Aplicacao nº: " + codigo + " <b> Nome: " + listApp.get(i).getNome() + " </b>"
                            + "  <a href='Controlador?acao=excluirAplicacao&indice=" + i+ "' > [excluir] </a> </h2>     ");

                }

            }


        %>





    </body>
</html>
