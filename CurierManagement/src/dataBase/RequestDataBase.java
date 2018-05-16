package dataBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import actors.Client;
import actors.Request;
import actors.Request.Cities;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public class RequestDataBase extends DataBase {
	private static final String		tableName = "request";
	
	public static long	insertNewRequest(long senderId, long receiverId, String city, String status) {
		return DataBase.sqlInsert("INSERT INTO " + tableName + " (id, id_sender, id_receiver, city, "
				+ "status) VALUES (NULL, \"" + senderId + "\", \""
				+ receiverId + "\", \"" + city + "\", \""
				+ status + "\")");
	}
	
	public static long	insertNewRequest(Request request) {
		String			query;
		long			senderId;
		long			receiverId;
		long				id;

		senderId = request.getSender().getId();
		receiverId = request.getReceiver().getId();
		query = "INSERT INTO " + tableName + " (";
		query += "id, id_sender, id_receiver, city, weight, special_condition, price, status) ";
		query += "VALUES ( NULL";
		query += ", " + senderId;
		query += ", " + receiverId;
		query += ", " + "\"" + request.getCity() + "\"";
		query += ", " + request.getWeight();
		query += ", " + request.getSpecialCondition();
		query += ", " + request.getPrice();
		query += ", " + "\"" + request.getStatus() + "\"";
		query += ")";
		//DataBase.msg(query);
		id = DataBase.sqlInsert(query);
		request.setId((int) id);
		return id;
	}
	/**
	 Selecteaza toate request-urile pentru soferul cu id-ul driver_id.
	 
	 Daca ultimul statut al acelui request deja are orasul curent setat la new_city,
	 atunci id-ul lui nu se va adauga in vectorul final.
	 @return a Vector with all id's of the selected requests
	 */
	public static Vector<Integer>	getRequests(int driver_id, Cities newCity) {
		SQLiteStatement	sq;
		String			query;
		Vector<Integer>	requestsId = null;
		
		query = "SELECT request.id, MAX(status.id), status.current_city";
		query += " FROM " + tableName + ", " + StatusDataBase.tableName;
		query += " WHERE (request.status = 'In tranzit direct' OR request.status = 'In tranzit retur')";
		if (driver_id > 0)
			query += " AND request.driver_id = " + driver_id;
		query += " AND status.request_id = request.Id";
		query += " GROUP BY request.id";
		sq = sqlSelect(query);
		try {
			while (sq.step()) {
				if (requestsId == null)
					requestsId = new Vector<>();
				if (Cities.valueOf(sq.columnString(2)) != newCity)
					requestsId.add(sq.columnInt(0));
			}
			return requestsId;
		} catch (SQLiteException e) {
			err(e);
		}
		return null;
	}
	
	public static Vector<Integer>	getOldRequests() {
		SQLiteStatement		sq;
		String				query;
		Vector<Integer>		requestsId = null;
		Date				nowDate = new Date();
		Date				lastDate;
		SimpleDateFormat	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long				daysBetween;
		int					id;
		
		query = "SELECT request.id, MAX(status.id)";
		query += " FROM " + tableName + ", " + StatusDataBase.tableName;
		query += " WHERE (request.status = 'In asteptare destinatar')";
		query += " AND status.request_id = request.Id";
		query += " GROUP BY request.id";
		sq = sqlSelect(query);
		try {
			while (sq.step()) {
				if (requestsId == null)
					requestsId = new Vector<>();
				id = sq.columnInt(0);
				lastDate = dateFormat.parse(StatusDataBase.getLastDate(id));
				daysBetween = nowDate.getTime() - lastDate.getTime();
				daysBetween = TimeUnit.DAYS.convert(daysBetween, TimeUnit.MILLISECONDS);
				if (daysBetween >= 8)
					requestsId.add(id);
			}
			return requestsId;
		} catch (SQLiteException | ParseException e) {
			err(e);
		}
		return null;
	}

	public static Request	getRequestById(int id) {
		SQLiteStatement	sq;
		Request			request;
		int				auxId;
		
		sq = sqlSelect("SELECT id_sender, id_receiver, city, weight, special_condition"
				+ ", price, driver_id, status  FROM " + tableName + " WHERE Id = " + id);
		try {
			if (sq.step()) {
				request = new Request();
				request.setId(id);
				auxId = sq.columnInt(0);
				request.setSender(ClientDataBase.getClientById(auxId));
				auxId = sq.columnInt(1);
				request.setReceiver(ClientDataBase.getClientById(auxId));
				request.setCity(sq.columnString(2));
				request.setWeight(sq.columnInt(3));
				request.setSpecialCondition(sq.columnInt(4));
				request.setPrice(sq.columnInt(5));
				request.setDriver_id(sq.columnString(6));
				request.setStatus(sq.columnString(7));
				//DataBase.msg(request.getCity());
				return request;
			}
		} catch (SQLiteException e) {
			err(e);
		}
		return null;
	}
	
	public static void		updateDriver(int requestId, int driverId) {
		String			query;
		
		if (getRequestById(requestId) == null) {
			DataBase.err("Nu exista nici o cerere cu id-ul " + requestId + "!");
			return;
		}
		query = "UPDATE " + tableName + " SET driver_id = " + driverId;
		query += " WHERE id = " + requestId;
		DataBase.sqlUpdate(query);		
	}
	
	public static void		updateRequest(Request request) {
		String			query;
		
		ClientDataBase.updateClient(request.getSender());
		ClientDataBase.updateClient(request.getReceiver());
		if (getRequestById(request.getId()) == null) {
			insertNewRequest(request);
			return;
		}
		query = "UPDATE " + tableName + " SET city = \"" + request.getCity();
		query += "\", weight = " + request.getWeight();
		query += ", special_condition = " + request.getSpecialCondition();
		query += ", price = " + request.getPrice();
		query += ", status = '" + request.getStatus();
		query += "', driver_id = ";
		if (request.getDriver_id() == 0)
			query += "NULL";
		else
			query += request.getDriver_id();
		query += " WHERE id = " + request.getId();
		DataBase.sqlUpdate(query);		
	}
}
