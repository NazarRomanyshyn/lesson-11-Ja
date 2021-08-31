package ua.lviv.lgs.service.impl;

import java.util.List;

import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.dao.SubscribeDAO;
import ua.lviv.lgs.dao.impl.SubscribeDAOImpl;
import ua.lviv.lgs.domain.Subscribe;
import ua.lviv.lgs.service.SubscribeService;

public class SubscribeServiceImpl implements SubscribeService {

	private SubscribeDAO subscribeDAO = new SubscribeDAOImpl();

	@Override
	public Subscribe insert(Subscribe t) throws DAOException {
		return subscribeDAO.insert(t);
	}

	@Override
	public List<Subscribe> readAll() throws DAOException {
		return subscribeDAO.readAll();
	}

	@Override
	public Subscribe readByID(int id) throws DAOException {
		return subscribeDAO.readByID(id);
	}

	@Override
	public boolean updateByID(Subscribe t) throws DAOException {
		return subscribeDAO.updateByID(t);
	}

	@Override
	public boolean delete(int id) throws DAOException {
		return subscribeDAO.delete(id);
	}

}
