package ua.lviv.lgs.ui;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.domain.Subscribe;
import ua.lviv.lgs.service.SubscribeService;
import ua.lviv.lgs.service.impl.SubscribeServiceImpl;

@WebServlet("/subscribe")
public class SubscribeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SubscribeService subscribeService = SubscribeServiceImpl.getSubscribeService();

	private Logger log = Logger.getLogger(SubscribeServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Отримання значень полів...");
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userID");
		String magazineID = request.getParameter("magazineID");

		Subscribe subscribe = new Subscribe(userId, Integer.parseInt(magazineID), true, LocalDate.now(), 10);

		try {
			log.trace("Збереження підписки в базі даних...");
			subscribeService.insert(subscribe);
		} catch (DAOException e) {
			log.error("Не вдалося створити підписку!", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Success");
	}

}
