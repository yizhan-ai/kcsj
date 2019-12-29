package service;




import dao.ClientDao;
import domain.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class ClientService {
	private static ClientService clientService = new ClientService();
	private ClientDao clientDao = ClientDao.getInstance();

	private ClientService(){}
	
	public static ClientService getInstance(){
		return clientService;
	}


	public Collection<Client> findAll() throws SQLException {
		return clientDao.findAll();
	}

	public Client find(Integer id) throws SQLException{
		return clientDao.find(id);
	}

	public void update(Client client)throws SQLException {
		clientDao.update(client);
	}

	public void add(Client client) throws SQLException {
		clientDao.add(client);
	}
	public void add(Connection connection,Client client) throws SQLException {
		clientDao.add(connection,client);
	}
	public void delete(Integer id) throws SQLException {
		clientDao.delete(id);
	}
}
