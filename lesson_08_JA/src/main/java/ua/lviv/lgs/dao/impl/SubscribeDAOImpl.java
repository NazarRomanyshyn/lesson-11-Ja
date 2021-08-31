package ua.lviv.lgs.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.dao.DAOFactory;
import ua.lviv.lgs.dao.SubscribeDAO;
import ua.lviv.lgs.domain.Subscribe;

public class SubscribeDAOImpl implements SubscribeDAO {
	private Logger log = Logger.getLogger(SubscribeDAOImpl.class);

	public Subscribe insert(Subscribe subscribe)
			throws DAOException {
		log.info("Створення нової передплати в базі даних...");
		String sqlQuery = "insert into subscribe(`user_id`, `magazine_id`, `subscribe_status`, `subscribe_date`, `subscribe_period`) values (?, ?, ?, ?, ?)";

		Subscribe newSubscribe = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, subscribe.getUserID());
			statement.setInt(2, subscribe.getMagazineID());
			statement.setBoolean(3, subscribe.getSubscribeStatus());
			statement.setDate(4, Date.valueOf(subscribe.getSubscribeDate()));
			statement.setInt(5, subscribe.getSubscribePeriod());

			log.trace("Виконання оновлення бази даних...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядок(s) додано!\n", rows));

			log.trace("Отримання набору результатів...");
			if (rows == 0) {
				log.error("Creating subscribe failed, no rows affected!");
				throw new DAOException("Не вдалося створити підписку, жоден рядок не постраждав!");
			} else {
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					log.trace("Створення підписки для повернення...");
					newSubscribe = new Subscribe(resultSet.getInt(1), subscribe.getUserID(), subscribe.getMagazineID(),
							subscribe.getSubscribeStatus(), subscribe.getSubscribeDate(), subscribe.getSubscribePeriod());
				}
			}
		} catch (SQLException e) {
			log.error("Не вдалося створити підписку!");
			throw new DAOException("Не вдалося створити підписку!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("Набір результатів не можна закрити!", e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Підготовлена ​​заява закрита!");
			} catch (SQLException e) {
				log.error("Підготовлену заяву не можна закрити!", e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З'єднання закрито!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!", e);
			}
		}

		log.trace("Повернення передплати...");
		log.info(newSubscribe + " \r\n"
				+ "додано до бази даних!");
		return newSubscribe;
	}


	public List<Subscribe> readAll() throws DAOException {
		log.info("Отримання всіх підписок з бази даних...");
		String sqlQuery = "select * from subscribe";

		List<Subscribe> subscribeList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("\r\n"
					+ "Створення statement...");
			statement = connection.prepareStatement(sqlQuery);

			log.trace("Отримання набору результатів...");
			resultSet = statement.executeQuery();

			log.trace("Створення списку передплатників для повернення...");
			while (resultSet.next()) {
				subscribeList.add(new Subscribe(resultSet.getInt("id"), resultSet.getInt("user_id"),
						resultSet.getInt("magazine_id"), resultSet.getBoolean("subscribe_status"),
						resultSet.getDate("subscribe_date").toLocalDate(), resultSet.getInt("subscribe_period")));
			}
		} catch (SQLException e) {
			log.error("Не вдалося отримати список передплатників!");
			throw new DAOException("\r\n"
					+ "Не вдалося отримати список передплатників!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("\r\n"
						+ "Набір результатів не можна закрити!", e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Prepared statement закрито!");
			} catch (SQLException e) {
				log.error("Prepared statement не можна закрити!", e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З'єднання закрито!");
			} catch (SQLException e) {
				log.error("\r\n"
						+ "З’єднання не можна розірвати!", e);
			}
		}

		log.trace("Returning list of subscribes...");
		return subscribeList;
	}

	
	public Subscribe readByID(int id) throws DAOException {
		log.info("Отримання передплати за ід з бази даних...");
		String sqlQuery = "select * from subscribe where id = ?";

		Subscribe subscribe = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення prepared statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("Отримання набору результатів...");
			resultSet = statement.executeQuery();

			log.trace("Створення підписки для повернення...");
			while (resultSet.next()) {
				subscribe = new Subscribe(resultSet.getInt("id"), resultSet.getInt("user_id"),
						resultSet.getInt("magazine_id"), resultSet.getBoolean("subscribe_status"),
						resultSet.getDate("subscribe_date").toLocalDate(), resultSet.getInt("subscribe_period"));
			}
		} catch (SQLException e) {
			log.error("Не вдалося отримати підписку за ід!");
			throw new DAOException("Не вдалося отримати підписку за ід!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("Набір результатів не можна закрити!", e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Prepared statement закрито!");
			} catch (SQLException e) {
				log.error("Prepared statement не можна закрити!", e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З'єднання закрито!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!", e);
			}
		}

		log.trace("Повернення передплати...");
		log.info(subscribe + " отримується з бази даних!");
		return subscribe;
	}

	
	public boolean updateByID(Subscribe subscribe) throws DAOException {
		log.info("Оновлення передплати за ід у базі даних...");
		String sqlQuery = "update subscribe set user_id = ?, magazine_id = ?, subscribe_status = ?, subscribe_date = ?, subscribe_period = ? where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення prepared statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, subscribe.getUserID());
			statement.setInt(2, subscribe.getMagazineID());
			statement.setBoolean(3, subscribe.getSubscribeStatus());
			statement.setDate(4, Date.valueOf(subscribe.getSubscribeDate()));
			statement.setInt(5, subscribe.getSubscribePeriod());
			statement.setInt(6, subscribe.getId());

			log.trace("Виконання оновлення бази даних...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядків(s) оновлено!", rows));

			if (rows == 0) {
				log.error("Не вдалося оновити підписку, жоден рядок не вплинув!");
				throw new DAOException("Не вдалося оновити підписку, жоден рядок не вплинув!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("Не вдалося оновити підписку!");
			throw new DAOException("Не вдалося оновити підписку!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Prepared statement закрито!");
			} catch (SQLException e) {
				log.error("Prepared statement не можна закрити!", e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З'єднання закрито!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!", e);
			}
		}

		if (result == false) {
			log.info("Не вдалося оновити підписку, жоден рядок не вплинув!");
		} else {
			log.trace("\r\n"
					+ "Повернення результату...");
			log.info("Підписатися на ID#" + subscribe.getId() + " оновлюється в базі даних!");
		}
		return result;
	}


	public boolean delete(int id) throws DAOException {
		log.info("Видалення підписки за ідентифікатором з бази даних\r\n"
				+ "...");
		String sqlQuery = "delete from subscribe where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення prepared statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("Виконання оновлення бази даних...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядків(s) видалено!", rows));

			if (rows == 0) {
				log.error("Не вдалося видалити підписку, жоден рядок не постраждав!");
				throw new DAOException("Не вдалося видалити підписку, жоден рядок не постраждав!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("Не вдалося видалити підписку!");
			throw new DAOException("Не вдалося видалити підписку!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Prepared statement закрито!");
			} catch (SQLException e) {
				log.error("Prepared statement не можна закрити!", e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З'єднання закрито!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!", e);
			}
		}

		if (result == false) {
			log.info("Не вдалося видалити підписку, жоден рядок не постраждав!");
		} else {
			log.trace("Повернення результату...");
			log.info("Підписатися на ID#" + id + " видаляється з бази даних!");
		}
		return result;
	}
}