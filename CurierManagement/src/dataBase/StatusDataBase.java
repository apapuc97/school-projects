package dataBase;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Vector;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import actors.Client;
import actors.Request.Cities;
import dataBase.RequestDataBase;

public class StatusDataBase extends DataBase {
	public static final String	tableName = "status";
	
	public static void	updatePosition(int driverId, Cities newCity, String status) {
		Vector<Integer>	requestsId = null;
		
		requestsId = RequestDataBase.getRequests(driverId, newCity);
		for (Integer requestId: requestsId) {
			updateStatus(requestId, newCity.toString(), status);/*
			query = "INSERT INTO " + tableName + " (";
			query += "id, request_id, current_city, date, time, status) ";
			query += "VALUES ( NULL";
			query += ", " + requestId;
			query += ", " + "\"" + newCity + "\"";
			query += ", " + "\"" + sqlDate + "\"";
			query += ", " + "\"" + time + "\"";
			//query += ", " + sqlDate;
			query += ", " + "\"" + status + "\"";
			query += ")";
			DataBase.sqlInsert(query);*/
		}
	}
	
	public static void	updateStatus(int request_id, String current_city, String status) {
		String			query;
		java.util.Date 	myDate = new java.util.Date();
		java.sql.Date 	sqlDate = new java.sql.Date(myDate.getTime());
		Time			time = new Time(myDate.getTime());
		
		query = "INSERT INTO " + tableName + " (";
		query += "id, request_id, current_city, date, time, status) ";
		query += "VALUES ( NULL";
		query += ", " + request_id;
		if (current_city == null)
			query += ", NULL";
		else
			query += ", " + "\"" + current_city + "\"";
		query += ", " + "\"" + sqlDate + "\"";
		query += ", " + "\"" + time + "\"";
		//query += ", " + sqlDate;
		query += ", " + "\"" + status + "\"";
		query += ")";
		DataBase.sqlInsert(query);
	}
	
	public static String	getLastCity(int request_id) {
		SQLiteStatement	sq;
		
		sq = sqlSelect("SELECT current_city FROM " + tableName + " WHERE request_id = " + request_id + " ORDER BY date DESC, time DESC");
		try {
			if (sq.step())
				return sq.columnString(0);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String	getLastDate(int request_id) {
		SQLiteStatement	sq;
		
		sq = sqlSelect("SELECT date FROM " + tableName + " WHERE request_id = " + request_id + " ORDER BY date DESC, time DESC");
		try {
			if (sq.step())
				return sq.columnString(0);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
