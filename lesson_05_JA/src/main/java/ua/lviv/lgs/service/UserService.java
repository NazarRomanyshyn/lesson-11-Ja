package ua.lviv.lgs.service;

import ua.lviv.lgs.dao.DAOAbstractCRUD;
import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.domain.User;

public interface UserService extends DAOAbstractCRUD<User>{
	
	User readByEmail(String email) throws DAOException;

	boolean updateByEmail(User t) throws DAOException;

}
