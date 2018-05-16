package dataBase;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import actors.User;

public class UsersDataBase extends DataBase{
	private static final String		tableName = "users";
	
	public static User	getUser(String username, String password) {
		User			user = null;
		String			role = null;
		int				clientId = 0;
		int				id;
		SQLiteStatement	sq;
		String			statement;
		
		statement = "SELECT role, client_id, id FROM users";
		statement += " WHERE username = \"" + username + "\" AND password = \"" + password + "\"";
		sq = sqlSelect(statement);
		try{
			if (sq.step()) {
				role = sq.columnString(0);
				try {
					clientId = sq.columnInt(1);
				} catch (Exception e) {
					clientId = 0;
				}
				id = sq.columnInt(2);
				user = new User();
				user.setClientId(clientId);
				user.setPassword(password);
				user.setRole(role);
				user.setUsername(username);
				user.setId(id);
			}
		} catch(Exception e){DataBase.msg(e);}
		return user;
	}
	
	public static int	getClientyId(int id) {
		SQLiteStatement	sq;
		
		sq = sqlSelect("SELECT client_id FROM " + tableName + " WHERE id = " + id);
		try {
			sq.step();
			return sq.columnInt(0);
		} catch (SQLiteException e) {
			return 0;
		}
	}
}
