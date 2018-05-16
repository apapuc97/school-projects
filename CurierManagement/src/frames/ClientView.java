package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import actors.Request;
import dataBase.ClientDataBase;
import dataBase.DataBase;
import dataBase.RequestDataBase;
import dataBase.StatusDataBase;

public class ClientView extends JFrame {

	private JPanel contentPane;
	private static ClientView			clientView = null;
	private JTable 						table;
	private String						clientCnp;
	private static DefaultTableModel	tableModel;

	public void		onCloseAction() {
		clientView = null;
        //frmFormularDeExpediere.setVisible(false);
		this.dispose();
		
	}
	
	public static void openWindow(Frame frm, String clientCnp) {
		if (clientView != null) {
			clientView.setVisible(false);
			clientView.dispose();
		}
		clientView = new ClientView(clientCnp);
		clientView.setVisible(true);
		clientView.setLocationRelativeTo(frm);
		clientView.afiseaza();
	}
	
	/**
	 * Create the frame.
	 */
	public ClientView(String clientCnp) {
		super();
		this.clientCnp = clientCnp;
		getContentPane().setBackground(new Color(0, 255, 0));
		setTitle("Curierat Rapid");
		setBounds(100, 100, 643, 365);
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
		getContentPane().setLayout(null);

		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	onCloseAction();
            }
        });
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	clientView = null;
            }
        });
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 0));
		panel.setBounds(451, 0, 176, 315);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnCerereNoua = new JButton("Cerere noua");
		btnCerereNoua.setBounds(10, 11, 156, 23);
		panel.add(btnCerereNoua);
		
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
				employeeForm.frmFormularDeExpediere.setLocationRelativeTo(clientView);
				employeeForm.setEnabled(false);
			}
		});
		btnDetalii.setBounds(10, 38, 156, 23);
		panel.add(btnDetalii);
		
		JButton button = new JButton("Vizualizare statut");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vizualizareStatut();
			}
		});
		button.setBounds(10, 64, 156, 23);
		panel.add(button);
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
		btnCerereNoua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClientRequest.deschideCerereNoua(clientView);
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
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.setRowSelectionAllowed(true);
		table.getColumnModel().setSelectionModel( new DefaultListSelectionModel()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public int getLeadSelectionIndex()
		    {
		        return -1;
		    }
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(table);
		afiseaza();
	}
	

	public void		afiseaza()
	{
		int						senderId;
		String					senderName;
		String					senderCnp;
		int						receiverId;
		String					receiverName;
		String					receiverCnp;
		String					query;
		int						id;
		int						requestId;
		int						rowCount;
		SQLiteStatement			sq;
		//ArrayList<String>		a = new ArrayList<String>();
		
		if (clientView == null)
			return;

		//Remove rows one by one from the end of the table
		rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		query = "SELECT Id, Id_Sender, Id_Receiver, City, Status FROM request ORDER BY Status, Id";
		//query += " WHERE cnp = " + clientView.clientCnp;
		sq = DataBase.sqlSelect(query);
		try{
			while(sq.step()){
				senderId = Integer.parseInt(sq.columnString(1));
				receiverId = Integer.parseInt(sq.columnString(2));
				senderCnp = ClientDataBase.getClientFieldById(senderId, ClientDataBase.cnp);
				receiverCnp = ClientDataBase.getClientFieldById(receiverId, ClientDataBase.cnp);
				if (senderCnp.equals(clientCnp) || receiverCnp.equals(clientCnp)) {
					requestId = sq.columnInt(0);
					receiverName = ClientDataBase.getClientFieldById(receiverId, ClientDataBase.surname);
					senderName = ClientDataBase.getClientFieldById(senderId, ClientDataBase.surname);
					/*
					row = String.format("%-3s %-10s ", "" + sq.columnInt(0), senderName);
					row += String.format("%-10s %-10s %-10s", receiverName, sq.columnString(3), sq.columnString(4));
					model.addElement(row);*/
					tableModel.addRow(new Object[]{requestId, senderName, receiverName, sq.columnString(3), sq.columnString(4)});
					
				}
			}
		}catch(SQLiteException e){DataBase.msg("!!!");}
		prepareForDeleting();
	}
	
	public void		prepareForDeleting() {
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
	
	public void		vizualizareStatut() {
		int		request_id;
		
		if (table.getSelectedRow() < 0) {
			DataBase.msg("Selectati o cerere din tabel pentru a o putea vizualiza statutul!");
			return;
		}
		request_id = (Integer) tableModel.getValueAt(table.getSelectedRow(), 0);
		ViewStatus.openWindow(clientView, request_id);
	}
}
