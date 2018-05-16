package frames;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import actors.Request;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import dataBase.ClientDataBase;
import dataBase.DataBase;
import dataBase.RequestDataBase;
import dataBase.StatusDataBase;

import java.awt.Color;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

public class EmployeeView {
	private static EmployeeView			employeeView = null;
	private static JFrame 				frmCurieratRapid = null;
	private JTable 						table;
	private static DefaultTableModel	tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeView window = new EmployeeView();
					employeeView = window;
					window.frmCurieratRapid.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void		onCloseAction() {
		employeeView = null;
        //frmFormularDeExpediere.setVisible(false);
		frmCurieratRapid.dispose();
		
	}
	
	public static void openWindow(Frame frm) {
		if (employeeView != null) {
			employeeView.frmCurieratRapid.setVisible(false);
			employeeView.frmCurieratRapid.dispose();
		}
		employeeView = new EmployeeView();
		employeeView.frmCurieratRapid.setVisible(true);
		employeeView.frmCurieratRapid.setLocationRelativeTo(frm);
	}
	
	/**
	 * Create the application.
	 */
	public EmployeeView() {
		initialize();
		afiseaza();
	}
	
	public static void		afiseaza()
	{
		DefaultListModel<String>		model = new DefaultListModel<String>();
		//String					header;
		String					row;
		String					senderName;
		String					receiverName;
		String					query;
		int						id;
		int						requestId;
		int						rowCount;
		SQLiteStatement			sq;
		//ArrayList<String>		a = new ArrayList<String>();
		
		if (frmCurieratRapid == null)
			return;

		//Remove rows one by one from the end of the table
		rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		//header = String.format("Id  %-10s %-10s %-10s %-10s", "Expeditor", "Destinatar", "Destinatie", "Statut");
		//model.addElement(header);
		query = "SELECT Id, Id_Sender, Id_Receiver, City, Status FROM request ORDER BY Status, Id";
		sq = DataBase.sqlSelect(query);
		try{
			while(sq.step()){
				requestId = sq.columnInt(0);
				id = Integer.parseInt(sq.columnString(1));
				senderName = ClientDataBase.getClientFieldById(id, ClientDataBase.surname);
				row = String.format("%-3s %-10s ", "" + sq.columnInt(0), senderName);
				id = Integer.parseInt(sq.columnString(2));
				receiverName = ClientDataBase.getClientFieldById(id, ClientDataBase.surname);
				row += String.format("%-10s %-10s %-10s", receiverName, sq.columnString(3), sq.columnString(4));
				model.addElement(row);
				tableModel.addRow(new Object[]{requestId, senderName, receiverName, sq.columnString(3), sq.columnString(4)});
				}
			}catch(SQLiteException e){DataBase.msg("!!!");}
		prepareForDeleting();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCurieratRapid = new JFrame();
		frmCurieratRapid.getContentPane().setBackground(new Color(0, 255, 0));
		frmCurieratRapid.setTitle("Curierat Rapid");
		frmCurieratRapid.setBounds(100, 100, 643, 365);
		//frmCurieratRapid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCurieratRapid.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmCurieratRapid.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	MainView.mainView.exitAccount();
		    	MainView.mainView.mainViewFrame.setVisible(true);
		    	frmCurieratRapid.setVisible(false);
		    	frmCurieratRapid.dispose();
		    }
		});
		frmCurieratRapid.getContentPane().setLayout(null);

		frmCurieratRapid.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	onCloseAction();
            }
        });
		frmCurieratRapid.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	frmCurieratRapid = null;
            }
        });
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 0));
		panel.setBounds(451, 0, 176, 315);
		frmCurieratRapid.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnCerereNoua = new JButton("Cerere noua");
		btnCerereNoua.setBounds(10, 11, 156, 23);
		panel.add(btnCerereNoua);
		
		JButton btnAprobareCerere = new JButton("Aprobare cerere");
		btnAprobareCerere.setBounds(10, 38, 156, 23);
		panel.add(btnAprobareCerere);
		
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
				employeeForm.frmFormularDeExpediere.setLocationRelativeTo(frmCurieratRapid);
				employeeForm.setEnabled(false);
			}
		});
		btnDetalii.setBounds(10, 65, 156, 23);
		panel.add(btnDetalii);
		
		JButton button = new JButton("Vizualizare statut");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vizualizareStatut();
			}
		});
		button.setBounds(10, 93, 156, 23);
		panel.add(button);
		
		JButton btnPreluareColet = new JButton("Preluare colet");
		btnPreluareColet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preluareColet();
			}
		});
		btnPreluareColet.setBounds(10, 121, 156, 23);
		panel.add(btnPreluareColet);
		
		JButton btnLivrareColet = new JButton("Livrare colet");
		btnLivrareColet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				livrareColet();
			}
		});
		btnLivrareColet.setBounds(10, 176, 156, 23);
		panel.add(btnLivrareColet);
		
		JButton btnRetur = new JButton("Retur");
		btnRetur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnareColet();
			}
		});
		btnRetur.setBounds(10, 149, 156, 23);
		panel.add(btnRetur);
		
		JButton btnPrivatizare = new JButton("Privatizare");
		btnPrivatizare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				privatizareColet();
			}
		});
		btnPrivatizare.setBounds(10, 203, 156, 23);
		panel.add(btnPrivatizare);
		tableModel = new DefaultTableModel(
			new Object[][] {
				{new Long(1L), "Vrabie", "Vulpes", "Bucuresti", "In asteptare"},
			},
			new String[] {
				"Id", "Expeditor", "Destinatar", "Destinatie", "Statut"
			}
		) {
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
		btnAprobareCerere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int		id;
				String	status;
				
				if (table.getSelectedRow() < 0) {
					DataBase.msg("Selectati o cerere din tabel pentru a o putea aproba!");
					return;
				}
				status = (String) tableModel.getValueAt(table.getSelectedRow(), 4);
				if (status.equals("In asteptare") == false) {
					DataBase.msg("Puteti prelua doar cererile marcate cu statutul \"In asteptare\"!");
					return;					
				}
				id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
				//DataBase.msg("Id-ul selectat e: " + id);
				
				EmployeeSendForm	employeeForm = new EmployeeSendForm(id);
				
				employeeForm.frmFormularDeExpediere.setVisible(true);
				employeeForm.frmFormularDeExpediere.setLocationRelativeTo(frmCurieratRapid);
				
			}
		});
		btnCerereNoua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClientRequest.deschideCerereNoua(frmCurieratRapid);
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 11, 446, 304);
		frmCurieratRapid.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 446, 304);
		panel_2.add(scrollPane_1);
		
		table = new JTable();
		table.setBounds(0, 0, 450, 16);
		table.setModel(tableModel);
		//table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.setRowSelectionAllowed(true);
		//tableModel.
		//table.setColumnSelectionAllowed(false);
		//table.setCellSelectionEnabled(false);
		table.getColumnModel().setSelectionModel( new DefaultListSelectionModel()
		{
		    @Override
		    public int getLeadSelectionIndex()
		    {
		        return -1;
		    }
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(table);
	}
	
	public static void		prepareForDeleting() {
		Vector<Integer>	requests;
		Request			request;
		
		requests = RequestDataBase.getOldRequests();
		if (requests == null)
			return;
		for (int request_id: requests) {
			request = RequestDataBase.getRequestById(request_id);
			request.setStatus("Termen depasit");
			RequestDataBase.updateRequest(request);
			StatusDataBase.updateStatus(request_id, request.getCity() + "", "Coletul a depasit termenul de pastrare de 7 zile");
		}
		if (requests.size() > 0)
			afiseaza();
	}
	
	public void		preluareColet() {
		int		request_id;
		String	status;
		Request	request;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea prelua!");
			return;
		}
		status = (String) tableModel.getValueAt(table.getSelectedRow(), 4);
		if (status.equals("In tranzit direct") == false && status.equals("In tranzit retur") == false) {
			DataBase.msg("Puteti prelua doar cererile marcate cu statutul \"In tranzit direct(retur)\"!");
			return;					
		}		
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		request = RequestDataBase.getRequestById(request_id);
		request.setStatus("In asteptare destinatar");
		request.setDriver_id(0);
		RequestDataBase.updateRequest(request);
		StatusDataBase.updateStatus(request_id, request.getCity().toString(), "Coletul a fost adus in orasul de destinatie");
		afiseaza();
		//DriverForm.openWindow(frmCurieratRapid, request_id);
	}
	
	public void		returnareColet() {
		int		request_id;
		String	status;
		Request	request;
		int		option;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea prelua!");
			return;
		}
		status = (String) tableModel.getValueAt(table.getSelectedRow(), 4);
		if (status.equals("In asteptare destinatar") == false) {
			DataBase.msg("Puteti livra doar cererile marcate cu statutul \"In asteptare destinatar\"!");
			return;					
		}		
		option = JOptionPane.showConfirmDialog(null, "Sigur doresti sa returnezi coletul ?",
				"Confirmare", JOptionPane.OK_CANCEL_OPTION);
		if (option != JOptionPane.OK_OPTION)
			return;
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		request = RequestDataBase.getRequestById(request_id);
		request.setCity("Timisoara");		
		request.setStatus("In tranzit retur");
		RequestDataBase.updateRequest(request);
		StatusDataBase.updateStatus(request_id, request.getCity().toString(), "Coletul a fost transmis expeditorului pentru retur");
		afiseaza();
		//DriverForm.openWindow(frmCurieratRapid, request_id);
	}
	
	public void		livrareColet() {
		int		request_id;
		String	status;
		Request	request;
		int		fine;
		int		option;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea prelua!");
			return;
		}
		status = (String) tableModel.getValueAt(table.getSelectedRow(), 4);
		if (status.equals("In asteptare destinatar") == false) {
			DataBase.msg("Puteti livra doar cererile marcate cu statutul \"In asteptare destinatar\"!");
			return;					
		}		
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		request = RequestDataBase.getRequestById(request_id);
		fine = calculateFine(request);
		option = JOptionPane.showConfirmDialog(null, "Taxa de magazie constituie "
						+ fine + " RON", "Confirmare", JOptionPane.OK_CANCEL_OPTION);
		if (option != JOptionPane.OK_OPTION)
			return;
		
		request.setStatus("Livrat");
		RequestDataBase.updateRequest(request);
		StatusDataBase.updateStatus(request_id, request.getCity().toString(), "Coletul a fost livrat");
		afiseaza();
		//DriverForm.openWindow(frmCurieratRapid, request_id);
	}
	
	public void		privatizareColet() {
		int		request_id;
		String	status;
		Request	request;
		int		fine;
		int		option;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea privatiza!");
			return;
		}
		status = (String) tableModel.getValueAt(table.getSelectedRow(), 4);
		if (status.equals("Termen depasit") == false) {
			DataBase.msg("Puteti livra doar cererile marcate cu statutul \"Termen depasit\"!");
			return;					
		}		
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		request = RequestDataBase.getRequestById(request_id);
		request.setStatus("Privatizat");
		RequestDataBase.updateRequest(request);
		StatusDataBase.updateStatus(request_id, request.getCity().toString(), "Coletul a fost trecut in posesia magazinului");
		afiseaza();
		//DriverForm.openWindow(frmCurieratRapid, request_id);
	}
	
	public int		calculateFine(Request request) {
		int					fine = 0;
		long				daysBetween;
		SimpleDateFormat 	dateFormat;
		Date				received;
		Date				nowDate;
		String				lastDate;
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		lastDate = StatusDataBase.getLastDate(request.getId());
		try {
			received = dateFormat.parse(lastDate);
			nowDate = new Date();
			daysBetween = nowDate.getTime() - received.getTime();
			daysBetween = TimeUnit.DAYS.convert(daysBetween, TimeUnit.MILLISECONDS);
			//DataBase.msg(daysBetween);
			fine = (int) (daysBetween * request.getSpecialCondition() * request.getWeight() / 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fine;
	}
	
	public void		vizualizareStatut() {
		int		request_id;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea vizualiza statutul!");
			return;
		}
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		ViewStatus.openWindow(frmCurieratRapid, request_id);
	}
}
