package frames;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JButton;

import actors.Client;
import actors.Request;
import actors.Request.Cities;
import dataBase.ClientDataBase;
import dataBase.DataBase;
import dataBase.RequestDataBase;
import dataBase.StatusDataBase;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientRequest {
	private static ClientRequest	clientRequestFrame = null;

	public  JFrame frmFormularDeExpediere;

	private JTextField senderNameTF;
	private JTextField senderSurnameTF;
	private JTextField senderCnpTF;
	private JTextField senderPhoneNumberTF;
	private JTextField senderEmailTF;
	private JTextField receiverNameTF;
	private JTextField receiverSurnameTF;
	private JTextField receiverCnpTF;
	private JTextField receiverPhoneNumberTF;
	private JTextField receiverEmailTF;
	private JComboBox<Cities> citiesComboBox;
	private JButton btnAnulare;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientRequest window = new ClientRequest();
					window.frmFormularDeExpediere.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientRequest() {
		initialize();
	}

	public void		onCloseAction() {
        clientRequestFrame = null;
        //frmFormularDeExpediere.setVisible(false);
        frmFormularDeExpediere.dispose();
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFormularDeExpediere = new JFrame();
		frmFormularDeExpediere.setTitle("Formular de expediere");
		frmFormularDeExpediere.setBounds(100, 100, 710, 293);
		//frmFormularDeExpediere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFormularDeExpediere.getContentPane().setLayout(null);
		frmFormularDeExpediere.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	onCloseAction();
            }
        });

		JLabel label = new JLabel("Expeditor:");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label.setBounds(12, 5, 73, 19);
		frmFormularDeExpediere.getContentPane().add(label);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setToolTipText("Client");
		layeredPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane.setBounds(10, 23, 674, 84);
		frmFormularDeExpediere.getContentPane().add(layeredPane);
		
		JLabel label_1 = new JLabel("Nume:");
		label_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_1.setBounds(27, 12, 73, 18);
		layeredPane.add(label_1);
		
		senderNameTF = new JTextField();
		senderNameTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		senderNameTF.setColumns(10);
		senderNameTF.setBounds(108, 11, 187, 20);
		layeredPane.add(senderNameTF);
		
		JLabel label_2 = new JLabel("Prenume:");
		label_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_2.setBounds(329, 12, 73, 19);
		layeredPane.add(label_2);
		
		senderSurnameTF = new JTextField();
		senderSurnameTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		senderSurnameTF.setColumns(10);
		senderSurnameTF.setBounds(412, 11, 187, 20);
		layeredPane.add(senderSurnameTF);
		
		senderCnpTF = new JTextField();
		senderCnpTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		senderCnpTF.setColumns(10);
		senderCnpTF.setBounds(108, 34, 187, 20);
		layeredPane.add(senderCnpTF);
		
		JLabel label_3 = new JLabel("CNP:");
		label_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_3.setBounds(28, 34, 73, 18);
		layeredPane.add(label_3);
		
		senderPhoneNumberTF = new JTextField();
		senderPhoneNumberTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		senderPhoneNumberTF.setColumns(10);
		senderPhoneNumberTF.setBounds(412, 34, 187, 20);
		layeredPane.add(senderPhoneNumberTF);
		
		JLabel label_4 = new JLabel("Telefon:");
		label_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_4.setBounds(329, 34, 73, 19);
		layeredPane.add(label_4);
		
		JLabel label_5 = new JLabel("Email:");
		label_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_5.setBounds(27, 58, 73, 19);
		layeredPane.add(label_5);
		
		senderEmailTF = new JTextField();
		senderEmailTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		senderEmailTF.setColumns(10);
		senderEmailTF.setBounds(108, 57, 187, 20);
		layeredPane.add(senderEmailTF);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setToolTipText("Client");
		layeredPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane_1.setBounds(12, 130, 674, 84);
		frmFormularDeExpediere.getContentPane().add(layeredPane_1);
		
		JLabel label_6 = new JLabel("Nume:");
		label_6.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_6.setBounds(27, 12, 73, 18);
		layeredPane_1.add(label_6);
		
		receiverNameTF = new JTextField();
		receiverNameTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		receiverNameTF.setColumns(10);
		receiverNameTF.setBounds(108, 11, 187, 20);
		layeredPane_1.add(receiverNameTF);
		
		JLabel label_7 = new JLabel("Prenume:");
		label_7.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_7.setBounds(329, 12, 73, 19);
		layeredPane_1.add(label_7);
		
		receiverSurnameTF = new JTextField();
		receiverSurnameTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		receiverSurnameTF.setColumns(10);
		receiverSurnameTF.setBounds(412, 11, 187, 20);
		layeredPane_1.add(receiverSurnameTF);
		
		receiverCnpTF = new JTextField();
		receiverCnpTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		receiverCnpTF.setColumns(10);
		receiverCnpTF.setBounds(108, 34, 187, 20);
		layeredPane_1.add(receiverCnpTF);
		
		JLabel label_8 = new JLabel("CNP:");
		label_8.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_8.setBounds(28, 34, 73, 18);
		layeredPane_1.add(label_8);
		
		receiverPhoneNumberTF = new JTextField();
		receiverPhoneNumberTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		receiverPhoneNumberTF.setColumns(10);
		receiverPhoneNumberTF.setBounds(412, 34, 187, 20);
		layeredPane_1.add(receiverPhoneNumberTF);
		
		JLabel label_9 = new JLabel("Telefon:");
		label_9.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_9.setBounds(329, 34, 73, 19);
		layeredPane_1.add(label_9);
		
		JLabel label_10 = new JLabel("Email:");
		label_10.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_10.setBounds(27, 58, 73, 19);
		layeredPane_1.add(label_10);
		
		receiverEmailTF = new JTextField();
		receiverEmailTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		receiverEmailTF.setColumns(10);
		receiverEmailTF.setBounds(108, 57, 187, 20);
		layeredPane_1.add(receiverEmailTF);
		
		JLabel lblOras = new JLabel("Oras:");
		lblOras.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblOras.setBounds(329, 58, 73, 19);
		layeredPane_1.add(lblOras);
		
		citiesComboBox = new JComboBox(Request.Cities.values());
		citiesComboBox.removeItem(Cities.Timisoara);
		citiesComboBox.setBounds(412, 58, 187, 20);
		layeredPane_1.add(citiesComboBox);
		
		JLabel lblDestinatar = new JLabel("Destinatar:");
		lblDestinatar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblDestinatar.setBounds(14, 112, 73, 19);
		frmFormularDeExpediere.getContentPane().add(lblDestinatar);

		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client	sender;
				Client	receiver;
				Request	request;
				
				request = new Request();
				request.setStatus("In asteptare");
				EmployeeSendForm.fieldsValidated = true;
				sender = request.getSender();
				sender.setName(senderNameTF);
				sender.setSurname(senderSurnameTF);
				sender.setCnp(senderCnpTF);
				sender.setPhoneNumber(senderPhoneNumberTF);
				sender.setEmail(senderEmailTF);
				
				receiver = request.getReceiver();
				receiver.setName(receiverNameTF);
				receiver.setSurname(receiverSurnameTF);
				receiver.setCnp(receiverCnpTF);
				receiver.setPhoneNumber(receiverPhoneNumberTF);
				receiver.setEmail(receiverEmailTF);

				if (EmployeeSendForm.fieldsValidated == false)
					return;
				frmFormularDeExpediere.setVisible(false);
				ClientDataBase.insertNewClient(sender);
				ClientDataBase.insertNewClient(receiver);
				request.setCity((Cities) citiesComboBox.getSelectedItem());
				long requestId = RequestDataBase.insertNewRequest(request);
				DataBase.msg("Cererea a fost inregistrata cu id-ul "+ requestId + "!");
				StatusDataBase.updateStatus(request.getId(), "Timisoara", "Cererea a fost inregistrata");
				EmployeeView.afiseaza();
            	onCloseAction();
			}
		});
		btnAnulare = new JButton("Anulare");
		btnAnulare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	onCloseAction();
			}
		});
		btnAnulare.setBounds(12, 225, 89, 23);
		btnNewButton.setBounds(595, 225, 89, 23);
		frmFormularDeExpediere.getContentPane().add(btnNewButton);
		frmFormularDeExpediere.getContentPane().add(btnAnulare);
	}
	

	public static void deschideCerereNoua(Frame frm) {
		if (clientRequestFrame != null)
			return;
		clientRequestFrame = new ClientRequest();
		clientRequestFrame.frmFormularDeExpediere.setVisible(true);
		clientRequestFrame.frmFormularDeExpediere.setLocationRelativeTo(frm);
	}
}
