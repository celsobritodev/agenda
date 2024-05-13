package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
    	System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request,response);
			
		}  else if (action.equals("/select")) {
			listarContato(request,response);
			
		}else {
			response.sendRedirect("index.html");
		}
	}

	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<JavaBeans> lista = dao.listarContatos();
		request.setAttribute("contatos", lista);
	    RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
        rd.forward(request, response);	    
/*		
		for (int i=0; i<lista.size(); i++) {
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
				}
*/			
			
	

	}
	
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	//	System.out.println(request.getParameter("nome"));
	//	System.out.println(request.getParameter("fone"));
	//	System.out.println(request.getParameter("email"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.inserirContato(contato);
		response.sendRedirect("main");
		

	}
	
	
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	   String idcon = request.getParameter("idcon");
	   contato.setIdcon(idcon);
	   dao.selecionarContato(contato);
	   request.setAttribute("idcon", contato.getIdcon());
	   request.setAttribute("nome", contato.getNome());
	   request.setAttribute("fone", contato.getFone());
	   request.setAttribute("email", contato.getEmail());
	   RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
       rd.forward(request, response);  			   
	

	}


}
