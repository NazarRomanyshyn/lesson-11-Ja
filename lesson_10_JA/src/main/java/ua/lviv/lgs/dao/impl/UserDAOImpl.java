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
		log.info("��������� ������ ����������� � ��� �����...");
		String sqlQuery = "insert into user(`first_name`, `last_name`, `email`, `password`, `access_level`) values (?, ?, ?, ?, ?)";

		User newUser = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getAccessLevel());

			log.trace("��������� ������ ����������...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d �����(s) ���������!", rows));

			log.trace("��������� ��������� ���� �����...");
			if (rows == 0) {
				log.error("������� ��������� �����������!");
				throw new DAOException("������� ��������� �����������, ����� ����� �� ����������!");
			} else {
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					log.trace("��������� ����������� ��� ����������...");
					newUser = new User(resultSet.getInt(1), user.getFirstName(), user.getLastName(), user.getEmail(),
							user.getPassword(), user.getAccessLevel());
				}
			}
		} catch (SQLException e) {
			log.error("������� ��������� �����������!");
			throw new DAOException("������� ��������� �����������!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("���� ���������� �������!");
			} catch (SQLException e) {
				log.error("��������� �� ���� ���� ����������!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("ϳ����������� statement �� ����� �������!");
			} catch (SQLException e) {
				log.error("ϳ����������� statement �� ����� �������!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("ǒ������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
		}


		log.trace("���������� �������� User...");
		log.info(user + " ������ �� ���� �����!");
		return user;
	}

	public List<User> readAll() throws DAOException {
		log.info("��������� ��� ������������ � ���� �����...");
		String sqlQuery = "select * from user";

		List<User> userList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("\r\n" + "³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("\r\n" + "��������� ������������� statement...");
			statement = connection.prepareStatement(sqlQuery);

			log.trace("��������� ������ ����������...");
			resultSet = statement.executeQuery();

			log.trace("��������� ������ ������������ ��� ����������...");
			while (resultSet.next()) {
				userList.add(new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level")));
			}
		} catch (SQLException e) {
			log.error("�� ������� �������� ������ ������������!");
			throw new DAOException("�� ������� �������� ������ ������������!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("���� ���������� �������!");
				} catch (SQLException e) {
					log.error("��������� �� ���� ���� ����������!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("��������� statement �������!");
			} catch (SQLException e) {
				log.error("ϳ����������� statement �� ����� �������!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("ǒ������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
		}
		log.trace("��������� ������ ������������ ��� ����������...");
		return userList;
	}


	public User readByID(int id) throws DAOException {
		log.info("��������� ����������� �� �� � ���� �����...");
		String sqlQuery = "select * from user where id = ?";

		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� ������������� statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("��������� ������ ������������ ��� ����������...");
			resultSet = statement.executeQuery();

			log.trace("��������� ����������� ��� ����������...");
			while (resultSet.next()) {
				user = new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level"));
			}
		} catch (SQLException e) {
			log.error("�� ������� �������� ����������� �� ��!");
			throw new DAOException("�� ������� �������� ����������� �� ID!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("���� ���������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("��������� statement �������!");
			} catch (SQLException e) {
				log.error("ϳ����������� statement �� ����� �������!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("ǒ������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
		}

		log.trace("��������� User...");
		log.error(user + " ���������� � ���� �����!");
		return user;
	}

	public User readByEmail(String email) throws DAOException {
		log.info("��������� ����������� �� ���������� ���� � ���� �����...");
		String sqlQuery = "select * from user where email = ?";

		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();
			log.trace("\r\n" + "��������� ������������� statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, email);
			log.trace("��������� ������ ������������ ��� ����������...");
			resultSet = statement.executeQuery();
			log.trace("��������� ����������� ��� ����������...");

			while (resultSet.next()) {
				user = new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level"));
			}
		} catch (SQLException e) {
			log.error("�� ������� �������� ����������� ���������� �����!");
			throw new DAOException("�� ������� �������� ����������� ���������� �����!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("\r\n" + "���� ���������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("��������� statement �������!");
			} catch (SQLException e) {
				log.error("ϳ����������� statement �� ����� �������!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("ǒ������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
		}
		log.trace("��������� User...");
		log.error(user + " ���������� � ���� �����!");
		return user;
	}

	public boolean updateByID(User user)
			throws DAOException {
		log.info("��������� ����������� �� �� � ��� �����...");
		String sqlQuery = "update user set first_name = ?, last_name = ?, email = ?, password = ?, access_level = ? where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("Creating prepared statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getAccessLevel());
			statement.setInt(6, user.getId());
			log.trace("��������� ��������� ���� �����...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d �����(s) ��������!\n", rows));

			if (rows == 0) {
				log.error("������� ��������� �����������!");
				throw new DAOException("������� ��������� �����������, ����� ����� �� ����������!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("������� ��������� �����������!");
			throw new DAOException("������� ��������� �����������!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("��������� statement �������!");
			} catch (SQLException e) {
				log.error("ϳ����������� statement �� ����� �������!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("ǒ������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
		}

		if (result == false) {
			log.info("������� ��������� �����������, ����� ����� �� ����������!");
		} else {
			log.trace("���������� ����������...");
			log.info("���������� � ID#" + user.getId() + " ����������� � ��� �����!");
		}
		return result;
	}

	public boolean updateByEmail(User user)
			throws DAOException {
		log.info("��������� ����������� � ����������� ������ � ��� �����...");
		String sqlQuery = "update user set first_name = ?, last_name = ?, password = ?, access_level = ? where email = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getAccessLevel());
			statement.setString(5, user.getEmail());

			log.trace("��������� ��������� ���� �����...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d row(s) updated!", rows));
			
			if (rows == 0) {
				log.error("������� ��������� �����������!");
				throw new DAOException("������� ��������� �����������, ����� ����� �� ����������!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException("������� ��������� �����������!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("��������� statement �������!");
			} catch (SQLException e) {
				log.error("ϳ����������� statement �� ����� �������!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("ǒ������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
		}

		if (result == false) {
			log.info("������� ��������� �����������, ����� ����� �� ����������!");
		} else {
			log.trace("���������� ����������...");
			log.info("���������� � ����������� ������: " + user.getEmail() + " ����������� � ��� �����!");
		}
		return result;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		log.info("��������� ����������� �� �� � ���� �����...");
		String sqlQuery = "delete from user where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("��������� ��������� ���� �����...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d �����(s) ��������!\n", rows));
			
			if (rows == 0) {
				log.error("�� ������� �������� �����������!");
				throw new DAOException("�� ������� �������� �����������, ����� ����� �� �������!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("�� ������� �������� �����������!");
			throw new DAOException("\r\n" + "�� ������� �������� �����������!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("��������� statement �������!");
			} catch (SQLException e) {
				log.error("ϳ����������� statement �� ����� �������!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
				log.trace("�'������� �������!");
			} catch (SQLException e) {
				log.error("ǒ������� �� ����� ��������!" + e);
			}
		}

		if (result == false) {
			log.info("�� ������� �������� �����������, ����� ����� �� �������!");
		} else {
			log.trace("���������� ����������...");
			log.info("���������� � ID#" + id + " ����������� � ���� �����!");
		}
		return result;
	}
}