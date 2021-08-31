package ua.lviv.lgs.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				+ "Відкриття Login Form...");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Отримання значень полів з Login Form...");
		request.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		User user = null;

		try {
			log.trace("Отримання користувача з бази даних...");
			user = userService.readByEmail(login);
		} catch (DAOException e) {
			log.error("Не вдалося отримати користувача електронною поштою!", e);
		}

		if (user == null) {
			log.warn("Немає користувача з логіном \"" + login + "\" \r\n"
					+ "в базі даних!");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Користувача з логіном \"" + login + "\" в базі даних не існує!");
		} else {
			log.trace("Перевірка пароля користувача для відповідності бази даних...");
			if (user.getPassword().equals(password)) {
				log.trace("Перенаправлення на сторінку облікового запису користувача...");
				UserLogin userLogin = new UserLogin(user.getEmail(), "jsp/cabinet.jsp");
				String json = new Gson().toJson(userLogin);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
				log.warn("Пароль користувача не відповідає базі даних!");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("Введений пароль не збігається з базою даних!");
			}
		}

	}

}