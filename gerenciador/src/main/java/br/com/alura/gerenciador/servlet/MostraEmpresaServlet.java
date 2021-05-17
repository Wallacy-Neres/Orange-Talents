package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/mostraEmpresa")
public class MostraEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramId = request.getParameter("id");
		Integer id = Integer.valueOf(paramId);
		
		Banco banco = new Banco();
		
		Empresa empresa = banco.buscaEmpresaPeloId(id);
		
		System.out.println(empresa.getNome());
		
		request.setAttribute("empresa", empresa);
		RequestDispatcher rd = request.getRequestDispatcher("/formAlteraEmpresa.jsp");
		rd.forward(request, response);
		
	}

}
