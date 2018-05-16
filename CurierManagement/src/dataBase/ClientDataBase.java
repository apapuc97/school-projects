package dataBase;

import actors.Client;
import actors.Request;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public class ClientDataBase extends DataBase {
	private static final String		tableName = "client";
	public static final String		name = "name";
	public static final String		surname = "surname";
	public static final String		cnp = "cnp";
	
	

	public static String	getClientFieldById(int id, String field) {
		SQLiteStatement	sq;
		
		sq = sqlSelect("SELECT " + field + " FROM " + tableName + " WHERE Id = " + id);
		try {
			if (sq.step())
				return sq.columnString(0);
			else
				return null;
		} catch (SQLiteException e) {
			return null;
		}
	}

	public static int	getClientId(String email, String cnp) {
		int				clientId = -1;
		SQLiteStatement	sq;
		
		if (email == null || cnp == null)
			return clientId;
		sq = sqlSelect("SELECT id FROM " + tableName + " WHERE email = '" + email + "' AND cnp = '" + cnp + "'");
		try {
			if (sq.step())
				clientId = sq.columnInt(0);
		} catch (SQLiteException e) {
			DataBase.err(e);
		}
		return clientId;
	}

	public static String	getClientCnp(String email, String cnp) {
		int				clientId;
		
		clientId = getClientId(email, cnp);
		return getClientFieldById(clientId, ClientDataBase.cnp);
	}
	
	public static long	insertNewClient(Client client) {
		String			query;
		long			id;

		query = "INSERT INTO " + tableName + " (";
		query += "id, name, surname, phone_number, email, cnp) ";
		query += "VALUES ( NULL";
		query += ", " + "\"" + client.getName() + "\"";
		query += ", " + "\"" + client.getSurname() + "\"";
		query += ", " + "\"" + client.getPhoneNumber() + "\"";
		query += ", " + "\"" + client.getEmail() + "\"";
		query += ", " + "\"" + client.getCnp() + "\"";
		query += ")";
		id = DataBase.sqlInsert(query);
		client.setId((int) id);
		return id;
	}
	

	public static Client	getClientById(int id) {
		SQLiteStatement	sq;
		Client			client;
		
		sq = sqlSelect("SELECT name, surname, phone_number, email, cnp  FROM " + tableName + " WHERE Id = " + id);
		try {
			if (sq.step()) {
				client = new Client();
				client.setId(id);
				client.setName(sq.columnString(0));
				client.setSurname(sq.columnString(1));
				client.setPhoneNumber(sq.columnString(2));
				client.setEmail(sq.columnString(3));
				client.setCnp(sq.columnString(4));
				return client;
			}
		} catch (SQLiteException e) {
			err(e);
		}
		return null;
	}
	

	public static void		updateClient(Client client) {
		String			query;
		int				id;
		
		if (getClientById(client.getId()) == null) {
			id = (int) insertNewClient(client);
			return;
		}
		query = "UPDATE " + tableName + " SET name = \"" + client.getName();
		query += "\", surname = \"" + client.getSurname();
		query += "\", phone_number = \"" + client.getPhoneNumber();
		query += "\", email = \"" + client.getEmail();
		query += "\", cnp = \"" + client.getCnp();
		query += "\" WHERE id = " + client.getId();
		DataBase.sqlUpdate(query);
	}
}
