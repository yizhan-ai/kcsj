package service;




import dao.InformationDao;
import domain.Information;

import java.sql.SQLException;
import java.util.Collection;

public final class InformationService {
	private static InformationService informationService = new InformationService();
	private InformationDao informationDao = InformationDao.getInstance();

	private InformationService(){}
	
	public static InformationService getInstance(){
		return informationService;
	}

	public Collection<Information> findAllByManager(Integer manager_id)throws SQLException{
		return informationDao.findAllByManager(manager_id);
	}
	public Collection<Information> findAll() throws SQLException {
		return informationDao.findAll();
	}

	public Information find(Integer id) throws SQLException{
		return informationDao.find(id);
	}

	public void update(Information information)throws SQLException {
		informationDao.update(information);
	}

	public void add(Information information) throws SQLException {
		informationDao.add(information);
	}

	public void delete(Integer id) throws SQLException {
		informationDao.delete(id);
	}
}
