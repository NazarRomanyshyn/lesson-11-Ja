package ua.lviv.lgs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;
import org.apache.log4j.Logger;

public class DAOFactory {
	private static DAOFactory instance;
	private Connection connection;

	private String url = "jdbc:mysql://localhost/magazine_shop?serverTimezone=" + TimeZone.getDefault().getID();
	private String user = "root";
	private String password = "nr150677";

	private static Logger log = Logger.getLogger(DAOFactory.class);

	private DAOFactory() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			log.error("�� ������� ������ ���� �������� ���� �����!" + e);
		} catch (SQLException e) {
			log.error("������� ��������� ���������� �� ���� �����!" + e);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static DAOFactory getInstance() {
		if (instance == null) {
			log.trace("³������� �'�������...");
			instance = new DAOFactory();
		} else
			try {
				if (instance.getConnection().isClosed()) {
					log.trace("�������� �������� 璺������...");
					instance = new DAOFactory();
				}
			} catch (SQLException e) {
				log.error("������� ������� �� ���� �����!" + e);
			}

		return instance;
	}
}
