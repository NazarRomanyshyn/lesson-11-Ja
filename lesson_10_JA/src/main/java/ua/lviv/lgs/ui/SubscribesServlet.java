package ua.lviv.lgs.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.domain.Magazine;
import ua.lviv.lgs.domain.Subscribe;
import ua.lviv.lgs.dto.SubscribesDTO;
import ua.lviv.lgs.service.MagazineService;
import ua.lviv.lgs.service.SubscribeService;
import ua.lviv.lgs.service.impl.MagazineServiceImpl;
import ua.lviv.lgs.service.impl.SubscribeServiceImpl;

@WebServlet("/subscribes")
public class SubscribesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MagazineService magazineService = MagazineServiceImpl.getMagazineService();
	private SubscribeService subscribeService = SubscribeServiceImpl.getSubscribeService();

	private Logger log = Logger.getLogger(SubscribesServlet.class);

	public List<SubscribesDTO> map(List<Subscribe> subscribes, Map<Integer, Magazine> magazinesMap) {

		return subscribes.stream().map(value -> {
			SubscribesDTO subscribesDTO = new SubscribesDTO();

			subscribesDTO.id = value.getId();
			subscribesDTO.title = magazinesMap.get(value.getMagazineID()).getTitle();
			subscribesDTO.description = magazinesMap.get(value.getMagazineID()).getDescription();
			subscribesDTO.publishDate = magazinesMap.get(value.getMagazineID()).getPublishDate();
			subscribesDTO.subscribePrice = magazinesMap.get(value.getMagazineID()).getSubscribePrice();
			subscribesDTO.subscribeStatus = value.getSubscribeStatus();
			subscribesDTO.subscribeDate = value.getSubscribeDate();
			subscribesDTO.subscribePeriod = value.getSubscribePeriod();

			return subscribesDTO;
		}).collect(Collectors.toList());

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Subscribe> subscribes = null;

		try {
			log.trace("Отримання списку підписок з бази даних...");
			subscribes = subscribeService.readAll();
		} catch (DAOException e) {
			log.error("Не вдалося отримати список підписок!", e);
		}

		Map<Integer, Magazine> magazinesMap = null;

		try {
			log.trace("Отримання карти журналів з бази даних...");
			magazinesMap = magazineService.readAllMap();
		} catch (DAOException e) {
			log.error("Не вдалося отримати карту журналів!", e);
		}

		List<SubscribesDTO> subscribesDTO = map(subscribes, magazinesMap);

		if (subscribes == null || magazinesMap == null) {
			log.warn("У базі даних немає жодного журналу чи передплатника!");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("У базі даних не зареєстрована жодна підписка або журнал!");
		} else {
			log.trace("Повертається список передплачених DTO...");
			String json = new Gson().toJson(subscribesDTO);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}
	
}
