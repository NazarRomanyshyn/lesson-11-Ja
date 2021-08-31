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
import ua.lviv.lgs.dao.MagazineDAO;
import ua.lviv.lgs.domain.Magazine;

public class MagazineDAOImpl implements MagazineDAO {
	private Logger log = Logger.getLogger(MagazineDAOImpl.class);


	public Magazine insert(Magazine magazine)
			throws DAOException {
		log.info("Створення нового журналу в базі даних...");
		String sqlQuery = "insert into magazine(`title`, `description`, `publish_date`, `subscribe_price`) values (?, ?, ?, ?)";

		Magazine newMagazine = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, magazine.getTitle());
			statement.setString(2, magazine.getDescription());
			statement.setDate(3, Date.valueOf(magazine.getPublishDate()));
			statement.setInt(4, magazine.getSubscribePrice());

			log.trace("Виконання оновлення бази даних...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядок(s) додано!\n", rows));

			log.trace("Отримання набору результатів...");
			if (rows == 0) {
				log.error("Не вдалося створити журнал!");
				throw new DAOException("Не вдалося створити magaziner, жоден рядок не постраждав!");
			} else {
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					log.trace("Creating MagazСтворення журналу для повернення...");
					newMagazine = new Magazine(resultSet.getInt(1), magazine.getTitle(), magazine.getDescription(),
							magazine.getPublishDate(), magazine.getSubscribePrice());
				}
			}
		} catch (SQLException e) {
			log.error("Не вдалося створити журнал!");
			throw new DAOException("Не вдалося створити журнал!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("Набір результатів не можна закрити!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Statement закритий!");
			} catch (SQLException e) {
				log.error("Підготовлений statement не можна закрити!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З’єднання закрите!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
		}

		log.trace("Повернення журналу...");
		log.info(newMagazine + " додано до бази даних!");
		return newMagazine;
	}


	public List<Magazine> readAll() throws DAOException {
		log.info("Отримання всіх журналів з бази даних...");
		String sqlQuery = "select * from magazine";

		List<Magazine> magazineList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery);

			log.trace("Отримання набору результатів...");
			resultSet = statement.executeQuery();

			log.trace("Створення списку журналів для повернення...");
			while (resultSet.next()) {
				magazineList.add(new Magazine(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("description"), resultSet.getDate("publish_date").toLocalDate(),
						resultSet.getInt("subscribe_price")));
			}
		} catch (SQLException e) {
			log.error("Не вдалося отримати список журналів!");
			throw new DAOException("Не вдалося отримати список журналів!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("Набір результатів не можна закрити!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Підготовлений statement закритий!");
			} catch (SQLException e) {
				log.error("Підготовлений statement не можна закрити!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З’єднання закрите!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
		}

		log.trace("Повернення списку журналів...");
		return magazineList;
	}

	public Magazine readByID(int id) throws DAOException {
		log.info("Отримання журналу за ідентифікатором з бази даних...");
		String sqlQuery = "select * from magazine where id = ?";

		Magazine magazine = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("Отримання набору результатів...");
			resultSet = statement.executeQuery();

			log.trace("Створення Журналу для повернення...");
			while (resultSet.next()) {
				magazine = new Magazine(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("description"), resultSet.getDate("publish_date").toLocalDate(),
						resultSet.getInt("subscribe_price"));
			}
		} catch (SQLException e) {
			log.error("Отримати журнал за ід не вдалося!");
			throw new DAOException("Отримати журнал за ідентифікатором не вдалося!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("Набір результатів не можна закрити!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Підготовлений statement закритий!");
			} catch (SQLException e) {
				log.error("Підготовлений statement не можна закрити!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З’єднання закрите!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
		}

		log.trace("Повернення журналу...");
		log.info(magazine + " отримується з бази даних!");
		return magazine;
	}

	
	public boolean updateByID(Magazine magazine)
			throws DAOException {
		log.info("Оновлення журналу за ід у базі даних...");
		String sqlQuery = "update magazine set title = ?, description = ?, publish_date = ?, subscribe_price = ? where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, magazine.getTitle());
			statement.setString(2, magazine.getDescription());
			statement.setDate(3, Date.valueOf(magazine.getPublishDate()));
			statement.setInt(4, magazine.getSubscribePrice());
			statement.setInt(5, magazine.getId());

			log.trace("Отримання набору результатів...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядок(s) оновлено!\n", rows));

			if (rows == 0) {
				log.error("Не вдалось оновити журнал!");
				throw new DAOException("Не вдалось оновити журнал, жоден рядок не постраждав!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("Помилка оновлення журналу!");
			throw new DAOException("Помилка оновлення журналу!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Підготовлений statement закритий!");
			} catch (SQLException e) {
				log.error("Підготовлений statement не можна закрити!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З’єднання закрите!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
		}

		if (result == false) {
			log.info("Не вдалось оновити журнал, жоден рядок не постраждав!");
		} else {
			log.trace("Повернення результату...");
			log.info("Журнал з ID#" + magazine.getId() + " оновлено в базі даних!");
		}
		return result;
	}

	
	public boolean delete(int id) throws DAOException {
		log.info("Видалення журналу за ід з бази даних...");
		String sqlQuery = "delete from magazine where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("Отримання набору результатів...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядок(s) видалено!\n", rows));

			if (rows == 0) {
				log.error("Не вдалося видалити журнал!");
				throw new DAOException("Не вдалося видалити журнал, жоден рядок не постраждав!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("Не вдалося видалити журнал!");
			throw new DAOException("Не вдалося видалити журнал!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Підготовлений statement закритий!");
			} catch (SQLException e) {
				log.error("Підготовлений statement не можна закрити!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З’єднання закрите!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
		}

		if (result == false) {
			log.info("Не вдалося видалити журнал, жоден рядок не постраждав!");
		} else {
			log.trace("Повернення результату...");
			log.info("Журнал з ID#" + id + " видаляється з бази даних!");
		}
		return result;
	}
}

