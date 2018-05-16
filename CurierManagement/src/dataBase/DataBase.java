package dataBase;

import java.io.File;

import javax.swing.JOptionPane;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public abstract class DataBase {
	private static SQLiteConnection dataBase = null;
	
	static {
		if (dataBase == null)
			initialize();
	}
	
	public static void initialize(){
		if (dataBase != null)
			return;
		dataBase = new SQLiteConnection(new File("curierat.s3db"));
		try {
			dataBase.open(false);
		}catch (SQLiteException ex){
			err(ex);
			System.exit(-1); 
		}
	}
	public static void	err(Object text)
	{
		JOptionPane.showMessageDialog(null, text,"Eroare", JOptionPane.ERROR_MESSAGE);
	}

	public static void	msg(Object text)
	{
		JOptionPane.showMessageDialog(null, text,"Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}

	public static SQLiteStatement	sqlSelect(String sql)
	{
		try {
			SQLiteStatement st = dataBase.prepare(sql);
			return st;
		} catch (SQLiteException ex) {
			err(ex);
		}
		return null;
	}

	public static long		sqlInsert(String sql)
	{
		try {
			dataBase.exec(sql);
			return dataBase.getLastInsertId();
		} catch (SQLiteException ex) {
			err(ex);
		}
		return -1;
	}

	public static void		sqlUpdate(String sql)
	{
		try {
			dataBase.exec(sql);
		} catch (SQLiteException ex) {
			err(ex);
		}
		return;
	}
	
}
