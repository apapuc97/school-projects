package frames;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import dataBase.DataBase;
import dataBase.RequestDataBase;
import dataBase.StatusDataBase;
import actors.Request;
import actors.Request.Cities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewStatus {

	public static JFrame frmVizualizareStatus;
	public static ViewStatus driverForm = null;
	private JComboBox<Cities> citiesComboBox;
	//private Request	request = null;
	private JLabel priceLB;
	private JLabel deliveryTimeLB;
	private JTable table;
	private JLabel lblEstimatedTime;
	private static DefaultTableModel tableModel;
	private static int		requestId = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ViewStatus();
					ViewStatus.frmVizualizareStatus.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ViewStatus() {
		initialize();
		driverForm = this;
		//requestId = 0;
	}
	
	public void		onCloseAction() {
		ViewStatus.frmVizualizareStatus.dispose();
		driverForm = null;
        //frmFormularDeExpediere.setVisible(false);
		
	}
	
	public static ViewStatus openWindow(Frame frm, int id) {
		if (driverForm != null) {
			ViewStatus.frmVizualizareStatus.setVisible(false);
			ViewStatus.frmVizualizareStatus.dispose();
		}
		driverForm = new ViewStatus(id);
		ViewStatus.frmVizualizareStatus.setVisible(true);
		ViewStatus.frmVizualizareStatus.setLocationRelativeTo(frm);
		return driverForm;
	}
	

	public ViewStatus(int id) {
		//initialize();
		
		ViewStatus.requestId = id;
		initialize();
		driverForm = this;
	}
	/**
	 * Initialize the contents of the frame.
	 */

	
	private void initialize() {
		frmVizualizareStatus = new JFrame();
		frmVizualizareStatus.getContentPane().setBackground(new Color(0, 255, 0));
		frmVizualizareStatus.setTitle("Curierat Rapid");
		frmVizualizareStatus.setBounds(100, 100, 643, 365);
	//	frmVizualizareStatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVizualizareStatus.getContentPane().setLayout(null);

		frmVizualizareStatus.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	onCloseAction();
            }
        });
		frmVizualizareStatus.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	frmVizualizareStatus = null;
            }
        });
		tableModel = new DefaultTableModel(
						new Object[][] {
							{null, "Vrabie", "Vulpes", "In asteptare"},
						},
						new String[] {
							"Data", "Ora", "Orasul curent", "Statut"
						}
					)  {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				Long.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 11, 612, 241);
		frmVizualizareStatus.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 612, 241);
		panel_2.add(scrollPane_1);
		
		table = new JTable();
		table.setBounds(0, 0, 450, 16);
		table.setModel(tableModel);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);/*
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);*/
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMinWidth(10);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setMinWidth(30);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(table);
		
		JButton btnOk_1 = new JButton("Ok");
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onCloseAction();
			}
		});
		btnOk_1.setBounds(528, 292, 89, 23);
		frmVizualizareStatus.getContentPane().add(btnOk_1);
		
		lblEstimatedTime = new JLabel("Timpul ramas:");
		lblEstimatedTime.setBounds(15, 264, 220, 14);
		frmVizualizareStatus.getContentPane().add(lblEstimatedTime);
		afiseaza();
		printEstimatedDeliveryTime();
		frmVizualizareStatus.setVisible(true);
	}
	
	public static void		afiseaza()
	{
		String					query;
		int						rowCount;
		SQLiteStatement			sq;
		String					date;
		String					time;
		String					current_city;
		String					status;
		
		if (frmVizualizareStatus == null)
			return;

		//Remove rows one by one from the end of the table
		rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		query = "SELECT date, time, current_city, status FROM status WHERE request_id = " + requestId + " ORDER BY date, time";
		sq = DataBase.sqlSelect(query);
		try{
			while(sq.step()){
				date = sq.columnString(0);
				time = sq.columnString(1);
				current_city = sq.columnString(2);
				status = sq.columnString(3);
				tableModel.addRow(new Object[]{date, time, current_city, status});
				}
			}catch(SQLiteException e){DataBase.err(e);}
	}
	
	private int	calculateDeliveryTime(Request request) {
		int		distance;
		int		minutes;
		int		specialCondition;
		
		specialCondition = request.getSpecialCondition();
	//	request.setCity((Cities) citiesComboBox.getSelectedItem());
		distance = request.getDistance();
		minutes = (int) (distance / 1.25 + 80 + specialCondition * 30);
		return minutes;
	}
	
	private void	printEstimatedDeliveryTime() {
		Request	request;
		int		allTime;
		int		time2;
		int		estimatedTime;
		int		minutes;
		int		hours;
		
		request = RequestDataBase.getRequestById(requestId);
		allTime = calculateDeliveryTime(request);
		request.setCity(StatusDataBase.getLastCity(requestId));
		//DataBase.msg(request.getCity());
		time2 = calculateDeliveryTime(request);
		estimatedTime = Math.abs(allTime - time2);
		hours = estimatedTime / 60;
		minutes = estimatedTime % 60;
		lblEstimatedTime.setText("Timpul estimat: " + hours + "h " + minutes + "m");
	}
}
