<%-- 
    Document   : cadastrar-aplicacao
    Created on : Jun 30, 2015, 9:56:32 AM
    Author     : Lucas
--%>

<%@page import="ufg.inf.ia.middleware.Aplicacao"%>
<%@page import="java.util.List"%>
<%@page import="ufg.inf.ia.middleware.Canal"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Cadastrar Canal</title>

        <style type="text/css">
            p {
                border-width: medium;
                border-style: solid;
                border-color: #00f;
            }
        </style>
    </head>

    <body>

        <h1> <a href="#" onclick='adicionarCanal();'> Adicionar Canal [+]</a>


        </h1>

        ##### <input name="ativo" type="radio" value="true">Ativo #####
        <input name="ativo" type="radio" value="false">Inativo
        #####

        <br>

        <h2> <a href="index.jsp"> < voltar</a>   <br>       <a href="cadastrar-canal.jsp"> [Refresh Channel Dash Board ]</a></h2>



        <h1>Canais Cadastrados:</h1>


        <%

            List<Canal> listCanal = (List<Canal>) getServletContext().getAttribute("listCanal");

            if (listCanal == null || (listCanal.size() == 0)) {
                out.println("Não há canal cadastrado!");

            } else {
                for (int i = 0; i < listCanal.size(); i++) {

                    int id = listCanal.get(i).getId();

                    if (!listCanal.get(i).isAtivo()) {
                        out.println("<p>  <h2> Id: " + id + " <b style='color:#FF0000'> Inativo </b>"
                                + "  <a href='Controlador?acao=excluirCanal&id=" + id + "' > [excluir] </a>  "
                                + "  <a href='Controlador?acao=ativarCanal&indice=" + i + "'> [Ativar] </a></h2>   ");
                    } else {
                        out.println("<p>   <h2> Id: " + id + " <b style='color:#04B404'> Ativo </b>"
                                + "  <a href='Controlador?acao=excluirCanal&id=" + id + "' > [excluir] </a>     "
                                + "  <a href='Controlador?acao=desativarCanal&indice=" + i + "'> [Desativar]  </a></h2>  ");

                        out.println("<h2> Mensagens pendentes no canal: " + listCanal.get(i).getMensagensPendentes() + "</h2>");

                        out.println(" <h3> Aplicação Escritora: ");
                        if (listCanal.get(i).getEscritora() != null) {

                            out.println(listCanal.get(i).getEscritora().getNome());
                        } else {

                            out.println("Não há aplicação associada!");
                            List<Aplicacao> listApp = (List<Aplicacao>) getServletContext().getAttribute("listApp");
                            String html = "<form action='' method='get'> "
                                    + "<input name='acao' type='hidden' value='associarAplicacao'>"
                                    + "<input name='tipo' type='hidden' value='escritora'>"
                                    + "<input name='canal' type='hidden' value='" + listCanal.get(i).getId() + "'>"
                                    + " <select id='aplicacao' name='aplicacao' > <option value=''>Selecione Aplicação</option>";
                            for (int j = 0; j < listApp.size(); j++) {

                                html = html + "<option value='" + j + "'>" + listApp.get(j).getNome() + "</option>";

                            }
                            out.println(html + "  </select>"
                                    + "<button type='submit' >Associar</button> </form>  </h3>");
                        }

                        out.println(" <h3> Aplicação Leitora: ");
                        if (listCanal.get(i).getLeitora() != null) {

                            out.println(listCanal.get(i).getLeitora().getNome() + " </p>");
                        } else {

                            out.println("Não há aplicação associada!  ");
                            List<Aplicacao> listApp = (List<Aplicacao>) getServletContext().getAttribute("listApp");
                            String html = "<form action='' method='get'> "
                                    + "<input name='acao' type='hidden' value='associarAplicacao'>"
                                    + "<input name='tipo' type='hidden' value='leitora'>"
                                    + "<input name='canal' type='hidden' value='" + listCanal.get(i).getId() + "'>"
                                    + " <select id='aplicacao' name='aplicacao' > <option value=''>Selecione Aplicação</option>";
                            for (int j = 0; j < listApp.size(); j++) {

                                html = html + "<option value='" + j + "'>" + listApp.get(j).getNome() + "</option>";

                            }
                            out.println(html + "  </select>"
                                    + "<button type='submit' >Associar</button> </form>  </h3>   </p>");
                        }

                    }

                }

            }


        %>




        <script src="res/jquery-1.8.2.min.js" type="text/javascript"></script>

        <script>

            function adicionarCanal() {


                var ativo = $("input:radio[name=ativo]:checked").val();


                if (ativo !== 'true' && ativo !== 'false') {

                    alert('Favor definir se o canal está ativo ou não!');
                } else {
                    var url = 'Controlador?acao=adicionarCanal&ativo=' + ativo;
                    window.open(url, "_self");
                }

            }


            function geraToken() {

                var time = new Date().getMilliseconds();

                time = time * 345;

                $("#token").val(time);


            }


        </script>


    </body>

</html>
