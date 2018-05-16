package actors;

import dataBase.DataBase;

public class User {
	public enum Role {
		client,
		functionar,
		sofer
	};
	
	private String	username;
	private String	password;
	private Role	role;
	private int		clientId;
	private int		id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(String role) {
		try {
			this.role = Role.valueOf(role);
		} catch (IllegalArgumentException | NullPointerException e) {
			this.role = Role.client;
			DataBase.msg(role);
		}
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	
}
