package ua.lviv.lgs.service;

import java.util.Map;

import ua.lviv.lgs.dao.DAOAbstractCRUD;
import ua.lviv.lgs.dao.DAOException;
import ua.lviv.lgs.domain.Magazine;

public interface MagazineService extends DAOAbstractCRUD<Magazine> {
	
	public Map<Integer, Magazine> readAllMap() throws DAOException;

}
