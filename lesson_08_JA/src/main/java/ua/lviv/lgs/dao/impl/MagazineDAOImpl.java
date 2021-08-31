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
		log.info("��������� ������ ������� � ��� �����...");
		String sqlQuery = "insert into magazine(`title`, `description`, `publish_date`, `subscribe_price`) values (?, ?, ?, ?)";

		Magazine newMagazine = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, magazine.getTitle());
			statement.setString(2, magazine.getDescription());
			statement.setDate(3, Date.valueOf(magazine.getPublishDate()));
			statement.setInt(4, magazine.getSubscribePrice());

			log.trace("��������� ��������� ���� �����...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d �����(s) ������!\n", rows));

			log.trace("��������� ������ ����������...");
			if (rows == 0) {
				log.error("�� ������� �������� ������!");
				throw new DAOException("�� ������� �������� magaziner, ����� ����� �� ����������!");
			} else {
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					log.trace("Creating Magaz��������� ������� ��� ����������...");
					newMagazine = new Magazine(resultSet.getInt(1), magazine.getTitle(), magazine.getDescription(),
							magazine.getPublishDate(), magazine.getSubscribePrice());
				}
			}
		} catch (SQLException e) {
			log.error("�� ������� �������� ������!");
			throw new DAOException("�� ������� �������� ������!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("���� ���������� �������!");
			} catch (SQLException e) {
				log.error("���� ���������� �� ����� �������!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("Statement ��������!");
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

		log.trace("���������� �������...");
		log.info(newMagazine + " ������ �� ���� �����!");
		return newMagazine;
	}


	public List<Magazine> readAll() throws DAOException {
		log.info("��������� ��� ������� � ���� �����...");
		String sqlQuery = "select * from magazine";

		List<Magazine> magazineList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery);

			log.trace("��������� ������ ����������...");
			resultSet = statement.executeQuery();

			log.trace("��������� ������ ������� ��� ����������...");
			while (resultSet.next()) {
				magazineList.add(new Magazine(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("description"), resultSet.getDate("publish_date").toLocalDate(),
						resultSet.getInt("subscribe_price")));
			}
		} catch (SQLException e) {
			log.error("�� ������� �������� ������ �������!");
			throw new DAOException("�� ������� �������� ������ �������!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("���� ���������� �������!");
			} catch (SQLException e) {
				log.error("���� ���������� �� ����� �������!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("ϳ����������� statement ��������!");
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

		log.trace("���������� ������ �������...");
		return magazineList;
	}

	public Magazine readByID(int id) throws DAOException {
		log.info("��������� ������� �� ��������������� � ���� �����...");
		String sqlQuery = "select * from magazine where id = ?";

		Magazine magazine = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("��������� ������ ����������...");
			resultSet = statement.executeQuery();

			log.trace("��������� ������� ��� ����������...");
			while (resultSet.next()) {
				magazine = new Magazine(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("description"), resultSet.getDate("publish_date").toLocalDate(),
						resultSet.getInt("subscribe_price"));
			}
		} catch (SQLException e) {
			log.error("�������� ������ �� �� �� �������!");
			throw new DAOException("�������� ������ �� ��������������� �� �������!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				log.trace("���� ���������� �������!");
			} catch (SQLException e) {
				log.error("���� ���������� �� ����� �������!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("ϳ����������� statement ��������!");
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

		log.trace("���������� �������...");
		log.info(magazine + " ���������� � ���� �����!");
		return magazine;
	}

	
	public boolean updateByID(Magazine magazine)
			throws DAOException {
		log.info("��������� ������� �� �� � ��� �����...");
		String sqlQuery = "update magazine set title = ?, description = ?, publish_date = ?, subscribe_price = ? where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, magazine.getTitle());
			statement.setString(2, magazine.getDescription());
			statement.setDate(3, Date.valueOf(magazine.getPublishDate()));
			statement.setInt(4, magazine.getSubscribePrice());
			statement.setInt(5, magazine.getId());

			log.trace("��������� ������ ����������...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d �����(s) ��������!\n", rows));

			if (rows == 0) {
				log.error("�� ������� ������� ������!");
				throw new DAOException("�� ������� ������� ������, ����� ����� �� ����������!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("������� ��������� �������!");
			throw new DAOException("������� ��������� �������!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("ϳ����������� statement ��������!");
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
			log.info("�� ������� ������� ������, ����� ����� �� ����������!");
		} else {
			log.trace("���������� ����������...");
			log.info("������ � ID#" + magazine.getId() + " �������� � ��� �����!");
		}
		return result;
	}

	
	public boolean delete(int id) throws DAOException {
		log.info("��������� ������� �� �� � ���� �����...");
		String sqlQuery = "delete from magazine where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			log.trace("³������� �'�������...");
			connection = DAOFactory.getInstance().getConnection();

			log.trace("��������� statement...");
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);

			log.trace("��������� ������ ����������...");
			int rows = statement.executeUpdate();
			log.trace(String.format("%d �����(s) ��������!\n", rows));

			if (rows == 0) {
				log.error("�� ������� �������� ������!");
				throw new DAOException("�� ������� �������� ������, ����� ����� �� ����������!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			log.error("�� ������� �������� ������!");
			throw new DAOException("�� ������� �������� ������!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				log.trace("ϳ����������� statement ��������!");
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
			log.info("�� ������� �������� ������, ����� ����� �� ����������!");
		} else {
			log.trace("���������� ����������...");
			log.info("������ � ID#" + id + " ����������� � ���� �����!");
		}
		return result;
	}
}

