
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Middleware</title>
        <script src="res/jquery-1.8.2.min.js" type="text/javascript"></script>

    </head>

    <body>

        <form action="middleware" method="GET">



        </form>
        <h1>Bem vindo ao Middleware!</h1>
        <h2> <a href="cadastrar-aplicacao.jsp"> Cadastrar Aplicação </a></h2>
        <h2> <a href="cadastrar-canal.jsp"> Cadastrar Canal </a></h2>










        <h1>Histórico Mensagens:</h1>
        <h2><%

            Object hist = getServletContext().getAttribute("historico");

            if (hist != null) {
                List<String> historico = (List<String>) hist;

                for (int i = 0; i < historico.size(); i++) {

                    out.println(historico.get(i)+"<br>");

                }

            } else {
                out.println("Não foram enviadas mensagens!");

            }


            %></h2>


    </body>
</html>
