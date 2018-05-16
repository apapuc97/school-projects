package frames;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import actors.User;
import dataBase.ClientDataBase;
import dataBase.DataBase;
import dataBase.UsersDataBase;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JMenuItem;

public class MainView {

	public  JFrame		mainViewFrame;
	public static MainView	mainView = null;
	private JMenuBar	menuBar;
	private JMenu		mnCont;
	private JMenu 		mnIesire;
	private User 		user;
	private JMenuItem 	mntmLogare;
	private JLabel 		lblNewLabel_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.mainViewFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		mainView = this; 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainViewFrame = new JFrame();
		mainViewFrame.setTitle("Courier Service");
		mainViewFrame.setBounds(100, 100, 540, 431);
		mainViewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainViewFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 282, 346);
		mainViewFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("res/drawable/slider_2.jpg"));
		lblNewLabel.setBounds(0, 0, 282, 346);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(302, 11, 212, 346);
		mainViewFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Courier Service", SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Sitka Text", Font.BOLD, 18));
		lblNewLabel_1.setBounds(10, 47, 192, 33);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("<html>Suntem liderul serviciilor de curierat "
				+ "din Romania inca din anul 2006 si ne dorim sa ramanem in continuare "
				+ "partenerul de incredere al tuturor celor care aleg serviciile noastre, "
				+ "cu aceeasi pasiune si daruire pentru fiecare livrare efectuata.</html>");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 90, 192, 158);
		panel_1.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Cerere noua");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientRequest.deschideCerereNoua(mainViewFrame);
			}
		});
		btnNewButton.setBounds(10, 302, 192, 33);
		panel_1.add(btnNewButton);
		
		lblNewLabel_3 = new JLabel("Salut Andrei!");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(10, 11, 192, 25);
		lblNewLabel_3.setVisible(false);
		panel_1.add(lblNewLabel_3);
		
		menuBar = new JMenuBar();
		mainViewFrame.setJMenuBar(menuBar);
		
		mnCont = new JMenu("Cont");
		mntmLogare = new JMenuItem("Logare");
		mntmLogare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (mntmLogare.getText().equals("Logare"))
					loginAccount();
				else
					exitAccount();
			}
		});
		mnCont.add(mntmLogare);
		
		menuBar.add(mnCont);
		
	}
	
	public void	exitAccount() {
		int dialogResult;
		int dialogButton;
		
		dialogButton = JOptionPane.YES_NO_OPTION;		
		dialogResult = JOptionPane.showConfirmDialog (null, "Esti sigur ca doresti sa iesi din cont ?",
											"Confirmare", dialogButton);
		if (dialogResult != 0)
			return;
		mntmLogare.setText("Logare");
		lblNewLabel_3.setVisible(false);
		user = null;
	}
	
	public void	loginAccount() {
		Hashtable<String, String>	logininformation;
		String						username;
		String						password;
		String						name;
		int							userId;
		int							clientId;
		String						clientCnp;
		
		logininformation = loginDialog(mainViewFrame);
		username = logininformation.get("user");
		password = logininformation.get("pass");
		user = UsersDataBase.getUser(username, password);
		if (user == null) {
			clientCnp = ClientDataBase.getClientCnp(username, password);
			if (clientCnp != null) {
				ClientView.openWindow(mainViewFrame, clientCnp);
				mntmLogare.setText("Iesire");
				mainViewFrame.setVisible(false);
			}
			else {
				DataBase.err("Utilizatorul nu exista/parola incorecta!");
			}
			return;
		}
		mntmLogare.setText("Iesire");
		if (user.getRole() == User.Role.client) {
			name = ClientDataBase.getClientFieldById(user.getClientId(), ClientDataBase.name);
			lblNewLabel_3.setText("Salut " + name + "!");
			lblNewLabel_3.setVisible(true);
		}
		else if (user.getRole() == User.Role.functionar) {
			EmployeeView.openWindow(mainViewFrame);
			mainViewFrame.setVisible(false);
			//clientViewFrame.dispose();
		}
		else if (user.getRole() == User.Role.sofer) {
			userId = user.getId();
			CourierView.openWindow(mainViewFrame, userId);
			mainViewFrame.setVisible(false);
			//clientViewFrame.dispose();
		}
	}
	
	public Hashtable<String, String> loginDialog(JFrame frame) {
	    Hashtable<String, String> logininformation = new Hashtable<String, String>();

	    JPanel panel = new JPanel(new BorderLayout(5, 5));

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
	    label.add(new JLabel("Password", SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    JTextField username = new JTextField();
	    controls.add(username);
	    JPasswordField password = new JPasswordField();
	    controls.add(password);
	    panel.add(controls, BorderLayout.CENTER);
	    username.setDocument(new JTextFieldLimit(254));
	    password.setDocument(new JTextFieldLimit(15));

	    JOptionPane.showMessageDialog(frame, panel, "login", JOptionPane.QUESTION_MESSAGE);

	    logininformation.put("user", username.getText());
	    logininformation.put("pass", new String(password.getPassword()));
	    return logininformation;
	}
	
	 class JTextFieldLimit extends PlainDocument {
		  private int limit;

		  JTextFieldLimit(int limit) {
		   super();
		   this.limit = limit;
		   }

		  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		    if (str == null) return;

		    if ((getLength() + str.length()) <= limit) {
		    	super.insertString(offset, str, attr);
		    }
		  }
	}
}

