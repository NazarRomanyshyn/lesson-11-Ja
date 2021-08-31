package ua.lviv.lgs.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.dto.UserLogin;
import ua.lviv.lgs.service.UserService;
import ua.lviv.lgs.service.impl.UserServiceImpl;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = UserServiceImpl.getUserService();

	private Logger log = Logger.getLogger(LoginServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("\r\n"
				+ "³������� Login Form...");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("��������� ������� ���� � Login Form...");
		request.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		User user = null;

		try {
			log.trace("��������� ����������� � ���� �����...");
			user = userService.readByEmail(login);
		} catch (DAOException e) {
			log.error("�� ������� �������� ����������� ����������� ������!", e);
		}

		if (user == null) {
			log.warn("���� ����������� � ������ \"" + login + "\" \r\n"
					+ "� ��� �����!");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("����������� � ������ \"" + login + "\" � ��� ����� �� ����!");
		} else {
			log.trace("�������� ������ ����������� ��� ���������� ���� �����...");
			if (user.getPassword().equals(password)) {
				log.trace("Keeping user's ID in opened session...");
				HttpSession session = request.getSession(true);
				session.setAttribute("userID", user.getId());
				session.setAttribute("firstName", user.getFirstName());
				session.setAttribute("lastName", user.getLastName());
				session.setAttribute("accessLevel", user.getAccessLevel());
				
				log.trace("��������������� �� ������� ��������� ������ �����������...");
				UserLogin userLogin = new UserLogin(user.getEmail(), "cabinet.jsp");
				String json = new Gson().toJson(userLogin);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
				log.warn("������ ����������� �� ������� ��� �����!");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("�������� ������ �� �������� � ����� �����!");
			}
		}

	}

}