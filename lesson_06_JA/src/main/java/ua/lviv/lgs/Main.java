package ua.lviv.lgs;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.domain.Magazine;
import ua.lviv.lgs.domain.Subscribe;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.service.MagazineService;
import ua.lviv.lgs.service.SubscribeService;
import ua.lviv.lgs.service.UserService;
import ua.lviv.lgs.service.impl.MagazineServiceImpl;
import ua.lviv.lgs.service.impl.SubscribeServiceImpl;
import ua.lviv.lgs.service.impl.UserServiceImpl;

public class Main {
	public static void main(String[] args) throws DAOException {
		Logger log = Logger.getLogger(Main.class);
		PropertyConfigurator.configure("log4j.config.properties");
		log.trace("Запуск програми...");

		List<User> userList = new ArrayList<>();
		userList.add(new User("Максим", "Курик", "max@gmail.com", "242245", "Max"));
		userList.add(new User("Василь", "Бойко", "vas_boiko@gmail.com", "123456", "Boiko"));

		UserService userService = new UserServiceImpl();
		userList.forEach(user -> {
			try {
				System.out.println(userService.insert(user));
			} catch (DAOException e) {
				log.error("Error occured!", e);
				e.printStackTrace();
			}
		});

		System.out.println(userService.readByID(2));
		System.out.println(userService.readByEmail("max@gmail.com"));
		userService.updateByID(new User(1, "Джон", "Пітерс", "max@gmail.com", "123456", "АDMIN"));
		userService.updateByEmail(new User("Петро", "Бойко", "vas_boiko@gmail.com", "123456", "Boiko"));
		userService.delete(1);
		userService.readAll().forEach(System.out::println);

		MagazineService magazineService = new MagazineServiceImpl();
		System.out.println(
				magazineService.insert(new Magazine("Футбол", "Україна на ЄВРО2020!", LocalDate.parse("2021-06-14"), 6005)));
		magazineService.readAll().forEach(System.out::println);

		SubscribeService subscribeService = new SubscribeServiceImpl();
		System.out.println(subscribeService.insert(new Subscribe(2, 1, true, LocalDate.parse("2019-04-26"), 12)));
		subscribeService.readAll().forEach(System.out::println);

	}
}
