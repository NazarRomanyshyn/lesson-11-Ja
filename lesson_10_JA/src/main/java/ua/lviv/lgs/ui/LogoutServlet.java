package ua.lviv.lgs.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/loggingOut")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(LogoutServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Вихід із системи...");
		HttpSession session = request.getSession();

		log.trace("Поточний сеанс недійсний...");
		if (session != null) {
			session.invalidate();
		}

		log.trace("Перенаправлення на Login page...");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("login.jsp");
	}

}
