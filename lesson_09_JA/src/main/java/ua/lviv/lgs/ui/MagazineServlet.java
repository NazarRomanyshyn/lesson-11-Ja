package ua.lviv.lgs.ui;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.domain.Magazine;
import ua.lviv.lgs.service.MagazineService;
import ua.lviv.lgs.service.impl.MagazineServiceImpl;

public class MagazineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MagazineService magazineService = MagazineServiceImpl.getMagazineService();

	private Logger log = Logger.getLogger(RegistrationServlet.class);
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("\r\n"
				+ "��������� ������� ���� �� ����� ��������� ������...");
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String publishDate = request.getParameter("publishDate");
		String subscribePrice = request.getParameter("subscribePrice");
		
		try {
			log.trace("���������� ������� � ��� �����...");
			magazineService.insert(new Magazine(title, description, LocalDate.parse(publishDate), Integer.parseInt(subscribePrice)));
		} catch (DAOException e) {
			log.error("�� ������� �������� ������!", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("������ \"" + title + "\" ������ ���������� � ���� �����!");		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("��������� �������� �������������� � ������ �������...");
		String magazineID = request.getParameter("id");
		
		Magazine magazine = null;
		
		try {
			log.trace("��������� ������� �� ��������������� � ���� �����...");
			magazine = magazineService.readByID(Integer.parseInt(magazineID));
		} catch (NumberFormatException | DAOException e) {
			log.error("�������� ������ �� ��������������� �� �������!", e);		}
		
		if (magazine == null) {
			log.warn("���� ������� � ���������������=" + magazineID + " � ��� �����!");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("� ��� ����� ������ � id=" + magazineID + " �������!");
		} else {
			log.trace("��������������� �� ������� ������ �������...");
			request.setAttribute("magazine", magazine);
			request.getRequestDispatcher("magazineCard.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
