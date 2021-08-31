package ua.lviv.lgs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.dao.DAOFactory;
import ua.lviv.lgs.dao.UserDAO;
import ua.lviv.lgs.domain.User;

public class UserDAOImpl implements UserDAO {
	private Logger log = Logger.getLogger(UserDAOImpl.class);


	public User insert(User user)
			throws DAOException {
		log.info("Створення нового користувача в базі даних...");
		String sqlQuery = "insert into user(`first_name`, `last_name`, `email`, `password`, `access_level`) values (?, ?, ?, ?, ?)";

		User newUser = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getAccessLevel());

			log.trace("Отримання набору результатів...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядків(s) добавлено!", rows));

			log.trace("Виконання оновлення бази даних...");
			if (rows == 0) {
				log.error("Помилка створення користувача!");
				throw new DAOException("Помилка створення користувача, жоден рядок не постраждав!");
			} else {
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					log.trace("Створення користувача для повернення...");
					newUser = new User(resultSet.getInt(1), user.getFirstName(), user.getLastName(), user.getEmail(),
							user.getPassword(), user.getAccessLevel());
				}
			}
		} catch (SQLException e) {
			log.error("Помилка створення користувача!");
			throw new DAOException("Помилка створення користувача!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("Результат не може бути довавлений!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Підготовлений statement не можна закрити!");
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


		log.trace("Повернення значення User...");
		log.info(user + " додано до бази даних!");
		return user;
	}

	public List<User> readAll() throws DAOException {
		log.info("Отримання всіх користувачів з бази даних...");
		String sqlQuery = "select * from user";

		List<User> userList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("\r\n" + "Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("\r\n" + "Створення підготовленого statement...");
			statement = connection.prepareStatement(sqlQuery);

			log.trace("Отримання набору результатів...");
			resultSet = statement.executeQuery();

			log.trace("Створення списку користувачів для повернення...");
			while (resultSet.next()) {
				userList.add(new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level")));
			}
		} catch (SQLException e) {
			log.error("Не вдалося отримати список користувачів!");
			throw new DAOException("Не вдалося отримати список користувачів!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
				} catch (SQLException e) {
					log.error("Результат не може бути довавлений!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Параметри statement закрито!");
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
		log.trace("Створення списку користувачів для повернення...");
		return userList;
	}


	public User readByID(int id) throws DAOException {
		log.info("Отримання користувача за ід з бази даних...");
		String sqlQuery = "select * from user where id = ?";

		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення підготовленого statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("Створення списку користувачів для повернення...");
			resultSet = statement.executeQuery();

			log.trace("Створення користувача для повернення...");
			while (resultSet.next()) {
				user = new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level"));
			}
		} catch (SQLException e) {
			log.error("Не вдалося отримати користувача за ід!");
			throw new DAOException("Не вдалося отримати користувача за ID!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Параметри statement закрито!");
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

		log.trace("Повертаємо User...");
		log.error(user + " отримується з бази даних!");
		return user;
	}

	public User readByEmail(String email) throws DAOException {
		log.info("Отримання користувача по електронній пошті з бази даних...");
		String sqlQuery = "select * from user where email = ?";

		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();
			log.trace("\r\n" + "Створення підготовленого statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, email);
			log.trace("Створення списку користувачів для повернення...");
			resultSet = statement.executeQuery();
			log.trace("Створення користувача для повернення...");

			while (resultSet.next()) {
				user = new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level"));
			}
		} catch (SQLException e) {
			log.error("Не вдалося отримати користувача електронної пошти!");
			throw new DAOException("Не вдалося отримати користувача електронної пошти!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("\r\n" + "Набір результатів закрито!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Параметри statement закрито!");
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
		log.trace("Повертаємо User...");
		log.error(user + " отримується з бази даних!");
		return user;
	}

	public boolean updateByID(User user)
			throws DAOException {
		log.info("Оновлення користувача за ід у базі даних...");
		String sqlQuery = "update user set first_name = ?, last_name = ?, email = ?, password = ?, access_level = ? where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Creating prepared statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getAccessLevel());
			statement.setInt(6, user.getId());
			log.trace("Виконання оновлення бази даних...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядків(s) оновлено!\n", rows));

			if (rows == 0) {
				log.error("Помилка оновлення користувача!");
				throw new DAOException("Помилка оновлення користувача, жоден рядок не постраждав!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("Помилка оновлення користувача!");
			throw new DAOException("Помилка оновлення користувача!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Параметри statement закрито!");
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
			log.info("Помилка оновлення користувача, жоден рядок не постраждав!");
		} else {
			log.trace("Повернення результату...");
			log.info("Користувач з ID#" + user.getId() + " оновлюється в базі даних!");
		}
		return result;
	}

	public boolean updateByEmail(User user)
			throws DAOException {
		log.info("Оновлення користувача з електронною поштою в базі даних...");
		String sqlQuery = "update user set first_name = ?, last_name = ?, password = ?, access_level = ? where email = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getAccessLevel());
			statement.setString(5, user.getEmail());

			log.trace("Виконання оновлення бази даних...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d row(s) updated!", rows));
			
			if (rows == 0) {
				log.error("Помилка оновлення користувача!");
				throw new DAOException("Помилка оновлення користувача, жоден рядок не постраждав!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException("Помилка оновлення користувача!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Параметри statement закрито!");
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
			log.info("Помилка оновлення користувача, жоден рядок не постраждав!");
		} else {
			log.trace("Повернення результату...");
			log.info("Користувач з електронною поштою: " + user.getEmail() + " оновлюється в базі даних!");
		}
		return result;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		log.info("Видалення користувача за ід з бази даних...");
		String sqlQuery = "delete from user where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("Відкриття з'єднання...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Створення statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("Виконання оновлення бази даних...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d рядків(s) оновлено!\n", rows));
			
			if (rows == 0) {
				log.error("Не вдалося видалити користувача!");
				throw new DAOException("Не вдалося видалити користувача, жоден рядок не вплинув!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("Не вдалося видалити користувача!");
			throw new DAOException("\r\n" + "Не вдалося видалити користувача!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Параметри statement закрито!");
			} catch (SQLException e) {
				log.error("Підготовлений statement не можна закрити!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("З'єднання закрито!");
			} catch (SQLException e) {
				log.error("З’єднання не можна розірвати!" + e);
			}
		}

		if (result == false) {
			log.info("Не вдалося видалити користувача, жоден рядок не вплинув!");
		} else {
			log.trace("Повернення результату...");
			log.info("Користувач з ID#" + id + " видаляється з бази даних!");
		}
		return result;
	}
}