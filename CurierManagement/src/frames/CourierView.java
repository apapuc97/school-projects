package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import actors.Request;
import actors.Request.Cities;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import dataBase.ClientDataBase;
import dataBase.DataBase;
import dataBase.RequestDataBase;
import dataBase.StatusDataBase;
import dataBase.UsersDataBase;

public class CourierView extends JFrame {
	private static CourierView			courierView = null;
	private JTable 						table;
	private static DefaultTableModel	tableModel;
	private static int					driverId = 4;

	private JPanel contentPane;
	private JComboBox<Cities> citiesComboBox = null;


	/**
	 * Create the frame.
	 */
	public CourierView(int driverId) {
		CourierView.driverId = driverId;
		courierView = this;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	MainView.mainView.exitAccount();
		    	MainView.mainView.mainViewFrame.setVisible(true);
		    	setVisible(false);
		    	dispose();
		    }
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		contentPane.setBackground(new Color(0, 255, 0));
		setTitle("Curierat Rapid");
		setBounds(100, 100, 643, 365);
		contentPane.setLayout(null);

		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	onCloseAction();
            }
        });
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 0));
		panel.setBounds(451, 0, 176, 315);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnDenuntarePozitie = new JButton("Denuntare pozitie");
		btnDenuntarePozitie.setBounds(10, 65, 156, 23);
		panel.add(btnDenuntarePozitie);
		
		JButton btnDetalii = new JButton("Vezi detalii");
		btnDetalii.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int					id;
				EmployeeSendForm	employeeForm;
				
				if (table.getSelectedRow() < 0) {
					DataBase.msg("Selectati o cerere din tabel pentru a putea vedea detaliile!");
					return;
				}
				id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
				employeeForm = new EmployeeSendForm(id);				
				employeeForm.frmFormularDeExpediere.setVisible(true);
				employeeForm.frmFormularDeExpediere.setLocationRelativeTo(courierView);
				employeeForm.setEnabled(false);
			}
		});
		btnDetalii.setBounds(10, 11, 156, 23);
		panel.add(btnDetalii);
		
		JButton btnPreluareColet = new JButton("Preluare colet");
		btnPreluareColet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				preluareColet();
			}
		});
		btnPreluareColet.setBounds(10, 38, 156, 23);
		panel.add(btnPreluareColet);
		
		JButton btnVizualizareStatut = new JButton("Vizualizare statut");
		btnVizualizareStatut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vizualizareStatut();
			}
		});
		btnVizualizareStatut.setBounds(10, 92, 156, 23);
		panel.add(btnVizualizareStatut);
		tableModel = new DefaultTableModel(
			new Object[][] {
				{new Long(1L), "Vrabie", "Vulpes", "Bucuresti", "In asteptare"},
			},
			new String[] {
				"Id", "Oras curent", "Destinatie", "Sofer", "Statut"
			}
		) {
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
		//panel_1.add(table);
		btnDenuntarePozitie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				denuntarePozitieBtnClick();
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 11, 446, 304);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 446, 304);
		panel_2.add(scrollPane_1);
		
		table = new JTable();
		table.setBounds(0, 0, 450, 16);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(table);
		afiseaza();
	}

	public void		vizualizareStatut() {
		int		request_id;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea prelua!");
			return;
		}
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		ViewStatus.openWindow(this, request_id);
	}
	
	public void		preluareColet() {
		int		request_id;
		String	sofer;
		String	status;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea prelua!");
			return;
		}
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		sofer = (String) tableModel.getValueAt(table.getSelectedRow(), 3);
		status = (String) tableModel.getValueAt(table.getSelectedRow(), 4);
		if (sofer != null) {
			DataBase.msg("Cererea data deja este preluata de soferul " + sofer + "!");
			return;					
		}
		if (status.equals("In tranzit direct") == false && status.equals("In tranzit retur") == false) {
			DataBase.msg("Puteti prelua doar cererile marcate cu statutul \"In tranzit direct(retur)\"!");
			return;					
		}
		RequestDataBase.updateDriver(request_id, driverId);
		StatusDataBase.updateStatus(request_id, "Timisoara", "Coletul a fost preluat de catre sofer");
		afiseaza();
	}
	
	public void		denuntarePozitieBtnClick() {
		ViewStatus	driverForm;
		int			id;
		String		sofer;
		String		status;
		Cities		newCity;
		int			option;
		
		
		if (citiesComboBox == null)
			citiesComboBox = new JComboBox<>(Request.Cities.values());
		citiesComboBox.setSelectedIndex(0);
		citiesComboBox.setEditable(true);
		option = JOptionPane.showConfirmDialog( null, citiesComboBox, "Selectati orasul", JOptionPane.OK_CANCEL_OPTION);
		if (option != JOptionPane.OK_OPTION)
			return;
		newCity = (Cities) citiesComboBox.getSelectedItem();
		StatusDataBase.updatePosition(driverId, newCity, "Soferul a ajuns in orasul " + newCity);
		afiseaza();
	}
	
	public static void		afiseaza()
	{
		String					driverName;
		String					currentCity;
		String					query;
		int						id;
		int						requestId;
		int						rowCount;
		SQLiteStatement			sq;
		
		if (courierView == null)
			return;

		//Remove rows one by one from the end of the table
		rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		query = "SELECT Id, City, driver_id, Status FROM request ORDER BY Status, Id";
		sq = DataBase.sqlSelect(query);
		try{
			while(sq.step()){
				requestId = sq.columnInt(0);
				//DataBase.msg(StatusDataBase.getLastCity(requestId));
				//id = Integer.parseInt(sq.columnString(1));
				//receiverName = ClientDataBase.getClientSurnameById(id);
				id = sq.columnInt(2);
				if (driverId == id)
					driverName = "Eu";
				else {
					id = UsersDataBase.getClientyId(id);
					driverName = ClientDataBase.getClientFieldById(id, ClientDataBase.surname);
				}
				//DataBase.msg(driverName);
				currentCity = StatusDataBase.getLastCity(requestId);
				if (currentCity == null)
					currentCity = sq.columnString(1);
				tableModel.addRow(new Object[]{requestId, 
						currentCity, 
						sq.columnString(1), 
						driverName, 
						sq.columnString(3)});
			}
		}catch(SQLiteException e){DataBase.msg("!!!");}
	}
	
	public static void		onCloseAction() {
		courierView.dispose();
		courierView = null;
		
	}
	
	public static void openWindow(Frame frm, int driverId) {
		if (courierView != null) {
			onCloseAction();
		}
		courierView = new CourierView(driverId);
		courierView.setVisible(true);
		courierView.setLocationRelativeTo(frm);
	}
}
