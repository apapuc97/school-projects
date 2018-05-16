package frames;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import actors.Client;
import actors.Request;
import actors.Request.Cities;
import dataBase.DataBase;
import dataBase.RequestDataBase;
import dataBase.StatusDataBase;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.HashMap;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class EmployeeSendForm {

	
	public JFrame frmFormularDeExpediere;
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
	//private JTextField cityTF;
	private JTextField weightTF;
	private Request	request = null;
	private JCheckBox fragilCheckBox;
	private JCheckBox periculosCheckBox;
	private JCheckBox pretiosCheckBox;
	private JLabel priceLB;
	private JLabel deliveryTimeLB;
	private JLabel statusLB;
	private JButton btnAnulare;
	private JButton btnOk;
	private static Border	defaultBorder = null;
	public static boolean	fieldsValidated = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSendForm window = new EmployeeSendForm();
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
	public EmployeeSendForm() {
		request = new Request();
		initialize();
	}
	
	public static void	setDefaultBorder(JTextField textField) {
		textField.setBorder(defaultBorder);
	}

	public static void	setErrorBorder(JTextField textField) {			
		Toolkit.getDefaultToolkit().beep();
		textField.setBorder(BorderFactory.createLineBorder(Color.red));
		fieldsValidated = false;
	}
	public EmployeeSendForm(int id) {
		Client	client;
		int		specialCondition;
		
		//DataBase.msg(id);
		request = RequestDataBase.getRequestById(id);
		//DataBase.msg(request.getCity());
		if (request == null) {
			DataBase.msg("Nu a fost gasita cererea cu id-ul " + id + "!");
			//return;
			request = new Request();
		}
		initialize();
		client = request.getSender();
		senderNameTF.setText(client.getName());
		senderSurnameTF.setText(client.getSurname());
		senderCnpTF.setText(client.getCnp());
		senderPhoneNumberTF.setText(client.getPhoneNumber());
		senderEmailTF.setText(client.getEmail());

		client = request.getReceiver();
		receiverNameTF.setText(client.getName());
		receiverSurnameTF.setText(client.getSurname());
		receiverCnpTF.setText(client.getCnp());
		receiverPhoneNumberTF.setText(client.getPhoneNumber());
		receiverEmailTF.setText(client.getEmail());
		
		citiesComboBox.setSelectedItem(request.getCity());
		//DataBase.msg(request.getCity() + " vs" + citiesComboBox.getSelectedItem());
		statusLB.setText(request.getStatus());
		specialCondition = request.getSpecialCondition();
		if ((specialCondition & 1) == 1)
			fragilCheckBox.setSelected(true);
		if ((specialCondition & 2) == 2)
			periculosCheckBox.setSelected(true);
		if ((specialCondition & 4) == 4)
			pretiosCheckBox.setSelected(true);
		weightTF.setText(request.getWeight() + "");
		changeWeight();
		changeSpecialCondition();
		calculateDeliveryTime();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void		setEnabled(boolean enabled) {
		senderNameTF.setEditable(enabled);
		senderSurnameTF.setEditable(enabled);
		senderCnpTF.setEditable(enabled);
		senderPhoneNumberTF.setEditable(enabled);
		senderEmailTF.setEditable(enabled);
		receiverNameTF.setEditable(enabled);
		receiverSurnameTF.setEditable(enabled);
		receiverCnpTF.setEditable(enabled);
		receiverPhoneNumberTF.setEditable(enabled);
		receiverEmailTF.setEditable(enabled);
		receiverPhoneNumberTF.setEditable(enabled);
		weightTF.setEditable(enabled);
		citiesComboBox.setEditable(enabled);
		citiesComboBox.setEnabled(enabled);
		fragilCheckBox.setEnabled(enabled);
		periculosCheckBox.setEnabled(enabled);
		pretiosCheckBox.setEnabled(enabled);
		btnAnulare.setVisible(enabled);
	}
	
	private void initialize() {
		
		frmFormularDeExpediere = new JFrame();
		frmFormularDeExpediere.setTitle("Formular de expediere");
		frmFormularDeExpediere.setBounds(100, 100, 710, 420);
		//frmFormularDeExpediere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmFormularDeExpediere.getContentPane().setLayout(null);
		
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
		if (defaultBorder == null)
			defaultBorder = senderNameTF.getBorder();
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
		layeredPane_1.setBounds(10, 130, 674, 84);
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
		/*
		cityTF = new JTextField();
		cityTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cityTF.setColumns(10);
		cityTF.setBounds(412, 58, 88, 20);
		layeredPane_1.add(cityTF);*/
		
		citiesComboBox = new JComboBox(Request.Cities.values());
		citiesComboBox.removeItem(Cities.Timisoara);
		citiesComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				calculateDeliveryTime();
			}
		});
		citiesComboBox.setBounds(412, 58, 187, 20);
		layeredPane_1.add(citiesComboBox);
		
		JLabel lblDestinatar = new JLabel("Destinatar:");
		lblDestinatar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblDestinatar.setBounds(12, 112, 73, 19);
		frmFormularDeExpediere.getContentPane().add(lblDestinatar);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane_2.setBounds(10, 237, 672, 106);
		frmFormularDeExpediere.getContentPane().add(layeredPane_2);
		
		JLabel lblGreutate = new JLabel("Greutate (grame):");
		lblGreutate.setBounds(27, 77, 112, 19);
		layeredPane_2.add(lblGreutate);
		lblGreutate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		
		weightTF = new JTextField();
		weightTF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		weightTF.getDocument().addDocumentListener(new SimpleDocumentListener() {
		    @Override
		    public void update(DocumentEvent e) {
		    	changeWeight();
		    }
		});

		weightTF.setColumns(10);
		weightTF.setBounds(140, 77, 112, 20);
		layeredPane_2.add(weightTF);
		
		JLabel lblConditiiSpeciale = new JLabel("Conditii speciale:");
		lblConditiiSpeciale.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblConditiiSpeciale.setBounds(328, 12, 112, 18);
		layeredPane_2.add(lblConditiiSpeciale);
		
		fragilCheckBox = new JCheckBox("Fragil");
		fragilCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				changeSpecialCondition();
			}
		});
		fragilCheckBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		fragilCheckBox.setBounds(349, 31, 97, 23);
		layeredPane_2.add(fragilCheckBox);
		
		periculosCheckBox = new JCheckBox("Periculos");
		periculosCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				changeSpecialCondition();
			}
		});
		periculosCheckBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		periculosCheckBox.setBounds(349, 55, 97, 23);
		layeredPane_2.add(periculosCheckBox);
		
		pretiosCheckBox = new JCheckBox("Pretios");
		pretiosCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				changeSpecialCondition();
			}
		});
		pretiosCheckBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		pretiosCheckBox.setBounds(349, 77, 97, 23);
		layeredPane_2.add(pretiosCheckBox);
		
		JLabel lblCod = new JLabel("Timp de livrare:");
		lblCod.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCod.setBounds(27, 33, 94, 19);
		layeredPane_2.add(lblCod);
		
		deliveryTimeLB = new JLabel("");
		deliveryTimeLB.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		deliveryTimeLB.setBounds(140, 33, 112, 18);
		layeredPane_2.add(deliveryTimeLB);
		
		JLabel lblPret = new JLabel("Pret:");
		lblPret.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblPret.setBounds(27, 55, 73, 19);
		layeredPane_2.add(lblPret);
		
		priceLB = new JLabel("0 RON");
		priceLB.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		priceLB.setBounds(140, 55, 112, 18);
		layeredPane_2.add(priceLB);
		
		JLabel label_11 = new JLabel("Statut:");
		label_11.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label_11.setBounds(27, 11, 94, 19);
		layeredPane_2.add(label_11);
		
		statusLB = new JLabel("");
		statusLB.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		statusLB.setBounds(140, 12, 112, 18);
		layeredPane_2.add(statusLB);
		
		JLabel lblColet = new JLabel("Colet:");
		lblColet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblColet.setBounds(12, 219, 73, 19);
		frmFormularDeExpediere.getContentPane().add(lblColet);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new OkButtonClick());
		/*btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aprobareCerere();
			}
		});*/
		btnOk.setBounds(595, 347, 89, 23);
		frmFormularDeExpediere.getContentPane().add(btnOk);
		
		btnAnulare = new JButton("Anulare");
		btnAnulare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmFormularDeExpediere.setVisible(false);
			}
		});
		btnAnulare.setBounds(12, 347, 89, 23);
		frmFormularDeExpediere.getContentPane().add(btnAnulare);
		//calculateDeliveryTime();
	}
	
	public String	getText(JTextField textField) {
		String	result;
		try {
			result = textField.getText();
			if (result.length() > 0)
				return result;
		} catch (NullPointerException e) {
			return null;
		}
		return null;
	}

	
	private void	changeWeight() {
		float	weight;
		
		try {
			weight = Float.parseFloat(weightTF.getText());
			if (weight <= 0) {
				setErrorBorder(weightTF);
				return;
			}
			request.setWeight(weight);
			calculatePrice();
			setDefaultBorder(weightTF);
		}
		catch (NumberFormatException | NullPointerException exception) {
			setErrorBorder(weightTF);
			return;
		}
	}
	

	private void	changeSpecialCondition() {
		int		specialCondition;
		
		specialCondition = 0;
		if (fragilCheckBox.isSelected())
			specialCondition = specialCondition | 1;
		if (periculosCheckBox.isSelected())
			specialCondition = specialCondition | 2;
		if (pretiosCheckBox.isSelected())
			specialCondition = specialCondition | 4;
		request.setSpecialCondition(specialCondition);
		calculatePrice();
	}

	private void	calculateDeliveryTime() {
		int		distance;
		int		hours;
		int		minutes;
		int		specialCondition;
		
		specialCondition = request.getSpecialCondition();
		request.setCity((Cities) citiesComboBox.getSelectedItem());
		distance = request.getDistance();
		minutes = (int) (distance / 1.25 + 80 + specialCondition * 30);
		hours = minutes / 60;
		minutes %= 60;
		deliveryTimeLB.setText(hours + "h " + minutes + "m");
		calculatePrice();
	}
	
	private void	calculatePrice() {
		int		distance;
		float	weight;
		int		specialCondition;
		float	price;
		
		weight = request.getWeight();
		distance = request.getDistance();
		specialCondition = request.getSpecialCondition();
		price = 10 + weight / 100 * (specialCondition + 1) + distance / 100;
		request.setPrice(price);
		priceLB.setText(price + " RON");	
	}
	
	class OkButtonClick implements ActionListener	{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Client	client;

			if (btnAnulare.isVisible()) {
				//request.setWeight(weight);
				fieldsValidated = true;
				changeSpecialCondition();
				changeWeight();
				calculateDeliveryTime();
				request.setStatus("In tranzit direct");
				//request.setCity((Cities) citiesComboBox.getSelectedItem());
				//request.setStatus("In asteptare");
				//request.setCity((Cities) citiesComboBox.getSelectedItem());
				client = request.getSender();
				client.setName(senderNameTF);
				client.setSurname(senderSurnameTF);
				client.setCnp(senderCnpTF);
				client.setPhoneNumber(senderPhoneNumberTF);
				client.setEmail(senderEmailTF);
				
				client = request.getReceiver();
				client.setName(receiverNameTF);
				client.setSurname(receiverSurnameTF);
				client.setCnp(receiverCnpTF);
				client.setPhoneNumber(receiverPhoneNumberTF);
				client.setEmail(receiverEmailTF);
				if (fieldsValidated == false)
					return;
				RequestDataBase.updateRequest(request);
				StatusDataBase.updateStatus(request.getId(), "Timisoara", "Coletul a fost aprobat de functionar");
				frmFormularDeExpediere.setVisible(false);
				frmFormularDeExpediere.dispose();
				EmployeeView.afiseaza();
				DataBase.msg("Cererea a fost aprobata cu succes!");
				return;
			}
			//DataBase.msg("Conditia speciala: " + specialCondition);
			frmFormularDeExpediere.setVisible(false);
			frmFormularDeExpediere.dispose();
			EmployeeView.afiseaza();
		}
	}
}

@FunctionalInterface
interface SimpleDocumentListener extends DocumentListener {
    void update(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}

