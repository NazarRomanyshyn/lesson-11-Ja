package ua.lviv.lgs.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.google.gson.Gson;

import ua.lviv.lgs.domain.Magazine;
import ua.lviv.lgs.service.MagazineService;
import ua.lviv.lgs.service.impl.MagazineServiceImpl;

@WebServlet("/magazines")
public class MagazinesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MagazineService magazineServise = MagazineServiceImpl.getMagazineService();

	private Logger log = Logger.getLogger(MagazinesServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Magazine> magazines = null;

		try {
			log.trace("Отримання списку журналів з бази даних...");
			magazines = magazineServise.readAll();
		} catch (Exception e) {
			log.error("Не вдалося отримати список журналів", e);
		}

		if (magazines == null) {
			log.warn("У базі даних не зареєстровано жодного журналу!");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("У базі даних не зареєстровано жодного журналу!");
		} else {
			log.trace("Повернення списку журналів ...");
			String json = new Gson().toJson(magazines);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}
}
