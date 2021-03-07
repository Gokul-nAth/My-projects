import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class ShopsAndGo {
	ShopsAndGo() {
		new LoginPage();
	}

	public static void main(String[] args) {
		new ShopsAndGo();
	}
}

class LoginPage extends JFrame implements ActionListener {

	JButton customerLoginButton, shopkeeperLoginButton, customerRegisterButton, shopkeeperRegisterButton,
			showPanelButton;
	JButton aboutButton, contactButton;
	DatabaseHandler database;
	JPanel loginPanel;
	JLabel logoLabel, aboutLabel;
	int nav = 0;

	LoginPage() {
		database = new DatabaseHandler();
		loginPanel = new JPanel();
		logoLabel = new JLabel();
		aboutLabel = new JLabel();
		aboutLabel.setText("<html><b>About app:</b><br>"
				+ "Direct Contact between the <b>Shopkeepers</b> and <b> Customers </b>"
				+ "With easy <b>Online Shopping</b></html> ");

		loginPanel.setBounds(0, 0, 160, 500);
		logoLabel.setBounds(240, 10, 250, 250);
		loginPanel.setLayout(null);
		loginPanel.setBackground(Color.decode("#123456"));
		customerLoginButton = new JButton("Customer Login");
		shopkeeperLoginButton = new JButton("Shopkeeper Login");
		customerRegisterButton = new JButton("Customer Register");
		shopkeeperRegisterButton = new JButton("Shopkeeper Register");
		showPanelButton = new JButton();
		aboutButton = new JButton("About Us");
		contactButton = new JButton("Developer Contact");

		customerLoginButton.setContentAreaFilled(false);
		customerRegisterButton.setContentAreaFilled(false);
		shopkeeperLoginButton.setContentAreaFilled(false);
		shopkeeperRegisterButton.setContentAreaFilled(false);
		showPanelButton.setContentAreaFilled(false);
		aboutButton.setContentAreaFilled(false);
		contactButton.setContentAreaFilled(false);

		customerLoginButton.setBorderPainted(false);
		customerRegisterButton.setBorderPainted(false);
		shopkeeperLoginButton.setBorderPainted(false);
		shopkeeperRegisterButton.setBorderPainted(false);
		aboutButton.setBorderPainted(false);
		contactButton.setBorderPainted(false);

		customerLoginButton.setForeground(Color.WHITE);
		customerRegisterButton.setForeground(Color.WHITE);
		shopkeeperLoginButton.setForeground(Color.WHITE);
		shopkeeperRegisterButton.setForeground(Color.WHITE);
		aboutButton.setForeground(Color.WHITE);
		contactButton.setForeground(Color.WHITE);

		customerLoginButton.setBounds(0, 50, 150, 40);
		shopkeeperLoginButton.setBounds(0, 100, 150, 40);
		customerRegisterButton.setBounds(0, 150, 150, 40);
		shopkeeperRegisterButton.setBounds(0, 200, 160, 40);
		showPanelButton.setBounds(10, 10, 40, 40);
		aboutButton.setBounds(0, 250, 150, 40);
		contactButton.setBounds(0, 300, 160, 40);
		aboutLabel.setBounds(200, 180, 300, 300);

		customerLoginButton.addActionListener(this);
		shopkeeperLoginButton.addActionListener(this);
		customerRegisterButton.addActionListener(this);
		shopkeeperRegisterButton.addActionListener(this);
		showPanelButton.addActionListener(this);
		aboutButton.addActionListener(this);
		contactButton.addActionListener(this);

		loginPanel.setVisible(false);
		showPanelButton.setOpaque(true);
		try {

			BufferedImage image = ImageIO.read(new File(new ConstantPath().ICONS_URL + "appLogo1.PNG"));
			Area clip = new Area(new Rectangle(0, 0, image.getWidth(), image.getHeight()));
			Area oval = new Area(new Ellipse2D.Double(0, 0, image.getWidth() - 1, image.getHeight() - 1));
			clip.subtract(oval);
			Graphics g2d = image.createGraphics();
			g2d.setClip(clip);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
			Image scaledIcon = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
			logoLabel.setIcon(new ImageIcon(scaledIcon));
			BufferedImage navIconImage = ImageIO.read(new File(new ConstantPath().ICONS_URL + "navIcon1.png"));
			scaledIcon = navIconImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			showPanelButton.setIcon(new ImageIcon(scaledIcon));

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "logo error");
		}

		//createExpectedTables();

		add(showPanelButton);
		add(loginPanel);
		add(logoLabel);
		add(aboutLabel);
		loginPanel.add(customerLoginButton);
		loginPanel.add(shopkeeperLoginButton);
		loginPanel.add(customerRegisterButton);
		loginPanel.add(shopkeeperRegisterButton);
		loginPanel.add(aboutButton);
		loginPanel.add(contactButton);
		frameInitialize();
	}

	private void createExpectedTables() {
		database.createTable("CREATE TABLE SHOPKEEPER(NAME TEXT,USERNAME TEXT,PASSWORD TEXT,EMAIL TEXT)");
		database.createTable("CREATE TABLE CUSTOMER(NAME TEXT,USERNAME TEXT,PASSWORD TEXT,EMAIL TEXT)");
		database.createTable("CREATE TABLE SHOPS(USERNAME TEXT,SHOPNAME TEXT,SHOPTYPE TEXT,SHOPCITY TEXT)");
		database.createTable("CREATE TABLE PRODUCTS(USERNAME TEXT,PRODUCTNAME TEXT,"
				+ "PRODUCTPRICE TEXT,PRODUCTAVAILABILITY TEXT,PRODUCTDESCRIPTION TEXT)");
		database.createTable("CREATE TABLE CATEGORIES(USERNAME TEXT,CATEGORY TEXT,PRODUCTNAME TEXT)");
		database.createTable("CREATE TABLE ORDERS(CUSTOMER_USERNAME TEXT,"
				+ "SHOPKEEPER_USERNAME TEXT,SHOP_NAME TEXT,CUSTOMER_NAME TEXT,ORDER_INFO TEXT,ORDERED_TIME TEXT,CUSTOMER_ADDRESS TEXT)");
		database.createTable("CREATE TABLE CART(CUSTOMER_USERNAME TEXT,"
				+ "SHOPKEEPER_USERNAME TEXT,SHOP_NAME TEXT,CUSTOMER_NAME TEXT,ORDER_INFO TEXT,ORDERED_TIME TEXT)");
		database.createTable("CREATE TABLE ORDERSTATUS(CUSTOMER_USERNAME TEXT,SHOPKEEPER_USERNAME TEXT,ORDERED_TIME TEXT"
				+ ",ORDER_STATUS TEXT,CONTACT_NUMBER TEXT,ORDER_INFO TEXT)");

	}

	private void frameInitialize() {
		setResizable(false);
		setSize(600, 500);
		getContentPane().setBackground(Color.WHITE);
		setLocation(400, 100);
		setTitle("User Type");
		setLayout(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == showPanelButton) {
			if (nav == 0) {
				nav = 1;
				loginPanel.setVisible(true);
			} else {
				nav = 0;
				loginPanel.setVisible(false);
			}
		}

		if (e.getSource() == aboutButton) {
			aboutLabel.setText(
					"<html><b>About Us :</b><br> We are 6 Young Entrepreuners developed an E-commerce application<br>"
							+ "developed with 0 investment .<br>" + "Created with open source platforms<br></html>");
		}

		if (e.getSource() == contactButton) {
			aboutLabel.setText("<html><b>Developers :</b><br>Prakash D -- 18euit104@skcet.ac.in<br>"
					+ "Sarath Gopal G -- 18eucs098@skcet.ac.in<br>" + "Gokulnath C -- 18euit040@skcet.ac.in<br>"
					+ "Shridhar K.E -- 18eumc139@skcet.ac.in<br>" + "Nitish S -- 18eucs074@skcet.ac.in<br>"
					+ "Sibi Sarath K -- 18eucs512@skcet.ac.in<br></html>");
		}

		if (e.getSource() == customerLoginButton) {
			// JOptionPane.showMessageDialog(this, "Done");
			onLoginButtonClick(0);
		}
		if (e.getSource() == shopkeeperLoginButton) {

			// JOptionPane.showMessageDialog(this, "Done");
			onLoginButtonClick(1);
		}
		if (e.getSource() == customerRegisterButton) {
			// JOptionPane.showMessageDialog(this, "Done");
			onRegisterButtonClick(0);
		}
		if (e.getSource() == shopkeeperRegisterButton) {
			// JOptionPane.showMessageDialog(this, "Done");
			onRegisterButtonClick(1);
		}
	}

	private void onRegisterButtonClick(int flag) {
		setVisible(false);
		new UserRegister(flag, false, "");
	}

	public void onLoginButtonClick(int flag) {
		setVisible(false);
		new UserLogin(flag);
	}
}

class UserLogin extends JFrame implements ActionListener {
	JTextField usernameField;
	JPasswordField passwordField;
	JButton loginButton;
	JButton backButton, forgotPasswordButton, showPasswordButton;
	JLabel helperText, usernameLabel, passwordLabel, backgroundLabel;
	JPanel panel;
	int flag = 0;
	DatabaseHandler database;
	int show = 0;

	UserLogin(int flag) {
		database = new DatabaseHandler();
		this.flag = flag;
		panel = new JPanel();
		helperText = new JLabel("");
		backgroundLabel = new JLabel();
		usernameLabel = new JLabel("Enter username");
		passwordLabel = new JLabel("Enter password");
		forgotPasswordButton = new JButton("Forgot password?");
		helperText.setForeground(Color.RED);
		usernameField = new JTextField("Enter username");
		passwordField = new JPasswordField("Enter password");
		backButton = new JButton("Back");
		passwordField.setEchoChar((char) 0);
		loginButton = new JButton("Login");
		showPasswordButton = new JButton();
		helperText.setOpaque(true);
		helperText.setBackground(Color.WHITE);

		usernameField.setBounds(100, 100, 250, 40);
		usernameLabel.setBounds(100, 75, 250, 30);
		passwordField.setBounds(100, 170, 250, 40);
		passwordLabel.setBounds(100, 145, 250, 30);
		showPasswordButton.setBounds(350, 170, 40, 40);
		loginButton.setBounds(100, 250, 250, 40);
		backButton.setBounds(100, 300, 250, 40);
		forgotPasswordButton.setBounds(80, 210, 150, 40);
		helperText.setBounds(100, 350, 250, 40);
		backgroundLabel.setBounds(0, 0, 500, 500);

		loginButton.setForeground(Color.decode("#003300"));
		loginButton.setFont(new Font("Timesroman", Font.BOLD, 16));
		forgotPasswordButton.setBorderPainted(false);
		forgotPasswordButton.setContentAreaFilled(false);
		loginButton.addActionListener(this);
		backButton.addActionListener(this);
		forgotPasswordButton.addActionListener(this);
		showPasswordButton.addActionListener(this);
		usernameField.setBorder(new MatteBorder(0, 0, 2, 0, Color.decode("#00ffff")));
		passwordField.setBorder(new MatteBorder(0, 0, 2, 0, Color.decode("#00ffff")));

		usernameField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (usernameField.getText().equals("Enter username")) {
					usernameField.setText("");
					usernameLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (usernameField.getText().isEmpty()) {
					usernameField.setText("Enter username");
					usernameLabel.setVisible(false);
					repaint();
				}
			}

		});
		passwordField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (passwordField.getText().equals("Enter password")) {
					passwordField.setEchoChar('*');
					passwordField.setText("");
					passwordLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (passwordField.getText().isEmpty()) {
					passwordField.setEchoChar((char) 0);
					passwordField.setText("Enter password");
					passwordLabel.setVisible(false);
					repaint();
				}
			}

		});
		try {
			BufferedImage backIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "backIcon.jpg"));
			Image scaledIcon = backIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			backButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage showIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "showPasswordIcon.png"));
			scaledIcon = showIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			showPasswordButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage background = ImageIO.read(new File(new ConstantPath().ICONS_URL + "appLogo10.jpg"));
			scaledIcon = background.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
			backgroundLabel.setIcon(new ImageIcon(scaledIcon));
			showPasswordButton.setBorderPainted(false);
			showPasswordButton.setContentAreaFilled(false);
			backButton.setBorderPainted(false);
			backButton.setContentAreaFilled(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
		usernameLabel.setOpaque(true);
		passwordLabel.setOpaque(true);
		usernameLabel.setBackground(Color.WHITE);
		passwordLabel.setBackground(Color.WHITE);
		usernameLabel.setVisible(false);
		passwordLabel.setVisible(false);
		setContentPane(backgroundLabel);
		helperText.setVisible(false);
		add(usernameLabel);
		add(passwordLabel);
		add(usernameField);
		add(passwordField);
		add(loginButton);
		add(backButton);
		add(helperText);
		add(forgotPasswordButton);
		add(showPasswordButton);
		// add(panel);

		// repaint();
		panel.setBounds(80, 50, 310, 300);
		// panel.setOpaque(true);
		panel.setBackground(new Color(0, 0, 0, 0.5f));

		panel.setLayout(null);
		// add(panel);
		frameInitialize();
	}

	private void frameInitialize() {
		setResizable(false);
		setSize(500, 500);
		setLocation(400, 100);
		if (flag == 0) {
			setTitle("Customer Login Page");
		} else if (flag == 1) {
			setTitle("Shopkeeper Login Page");
		}

		setLayout(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			if (flag == 0) {
				verifyCustomer();
			} else if (flag == 1) {
				verifyShopkeeper();
			}
		}
		if (e.getSource() == backButton) {
			onBack();
		}
		if (e.getSource() == forgotPasswordButton) {
			String username = usernameField.getText();
			ArrayList usernameList = new ArrayList();
			if (flag == 0) {
				usernameList = database.getUsernameList("CUSTOMER");
			} else if (flag == 1) {
				usernameList = database.getUsernameList("SHOPKEEPER");
			}
			if (username.isEmpty() || username.equals("Enter username")) {
				helperText.setText("Username required !");
			} else if (usernameList.contains(username)) {
				setVisible(false);
				//new UserRegister(flag, true, username);
				String email="";
				if (flag == 0) {
					email=database.getEmail("CUSTOMER", username);
				} else if (flag == 1) {
					email=database.getEmail("SHOPKEEPER", username);
				}
				String OTP="";
				for(int i=0;i<4;i++) {
					OTP+=String.valueOf(Math.random()*10).charAt(0);
				}
				//JOptionPane.showMessageDialog(this, OTP);
				new PasswordVerification(email, OTP,flag,true,username);
			} else if(username.isEmpty()) {
				helperText.setVisible(true);
				helperText.setText("Enter username");
				
			}
			else {
				helperText.setVisible(true);
				helperText.setText("Username not found register first !");
			}
		}
		if (e.getSource() == showPasswordButton) {
			if (show == 0 && !passwordField.getText().equals("Enter password")) {
				show = 1;
				passwordField.setEchoChar((char) 0);
				try {

					BufferedImage showIcon = ImageIO
							.read(new File(new ConstantPath().ICONS_URL + "hidePasswordIcon.png"));
					Image scaledIcon = showIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
					showPasswordButton.setIcon(new ImageIcon(scaledIcon));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex);
				}
			} else if (show == 1 && !passwordField.getText().equals("Enter password")) {
				show = 0;
				passwordField.setEchoChar('*');
				try {

					BufferedImage showIcon = ImageIO
							.read(new File(new ConstantPath().ICONS_URL + "showPasswordIcon.png"));
					Image scaledIcon = showIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
					showPasswordButton.setIcon(new ImageIcon(scaledIcon));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex);
				}
			}
		}

	}

	private void onBack() {
		helperText.setText("");
		helperText.setVisible(false);
		setVisible(false);
		new LoginPage();
	}

	private void verifyShopkeeper() {

		helperText.setText("");
		helperText.setVisible(false);
		String username = usernameField.getText();
		String password = passwordField.getText();
		ArrayList usernameList = new ArrayList();
		usernameList = database.getUsernameList("SHOPKEEPER");
		ArrayList shopUsernameList = new ArrayList();
		shopUsernameList = database.getUsernameList("SHOPS");
		if (username.isEmpty() || username.equals("Enter username") || password.isEmpty()
				|| password.equals("Enter password")) {
			helperText.setText("All details required !");
			helperText.setVisible(true);
		} else if (usernameList.contains(username)) {
			if (password.equals(database.getPassword("SHOPKEEPER", username))) {
				JOptionPane.showMessageDialog(this, "Successfully logged in");
				setVisible(false);
				helperText.setText("");
				helperText.setVisible(false);

				if (shopUsernameList.contains(username)) {
					new ShopkeeperPage(username);
				} else {
					new NoShops(username);
				}
			} else {
				helperText.setText("Incorrect password !");
				helperText.setVisible(true);
			}
		} else {
			helperText.setText("Username not found register first !");
			helperText.setVisible(true);
		}

	}

	private void verifyCustomer() {
		helperText.setText("");
		helperText.setVisible(false);
		String username = usernameField.getText();
		String password = passwordField.getText();
		ArrayList usernameList = new ArrayList();
		usernameList = database.getUsernameList("CUSTOMER");
		if (username.isEmpty() || username.equals("Enter username") || password.isEmpty()
				|| password.equals("Enter password")) {
			helperText.setText("All details required !");
			helperText.setVisible(true);
		} else if (usernameList.contains(username)) {
			if (password.equals(database.getPassword("CUSTOMER", username))) {
				JOptionPane.showMessageDialog(this, "Successfully logged in");
				setVisible(false);
				new CustomerPage(username, null, 0, 0);
			} else {
				helperText.setText("Incorrect password !");
				helperText.setVisible(true);
			}
		} else {
			helperText.setText("Username not found register first !");
			helperText.setVisible(true);
		}
	}
}

class UserRegister extends JFrame implements ActionListener {
	JTextField nameField, usernameField, emailField;
	JPasswordField passwordField;
	JButton registerButton;
	JButton backButton, showPasswordButton;
	JLabel helperText, nameLabel, usernameLabel, passwordLabel, emailLabel, backgroundLabel;
	JPanel panel;
	int flag = 0;
	boolean editable;
	String username;
	DatabaseHandler database;
	int show = 0;

	UserRegister(int flag, boolean editable, String username) {
		database = new DatabaseHandler();
		this.editable = editable;
		this.username = username;
		backgroundLabel = new JLabel();
		this.flag = flag;
		panel = new JPanel();
		helperText = new JLabel("");
		nameLabel = new JLabel("Enter name");
		usernameLabel = new JLabel("Enter username");
		passwordLabel = new JLabel("Enter password");
		emailLabel = new JLabel("Enter email");
		helperText.setForeground(Color.RED);
		nameField = new JTextField("Enter name");
		usernameField = new JTextField("Enter username");
		passwordField = new JPasswordField("Enter password");
		emailField = new JTextField("Enter email");
		backButton = new JButton("Back");
		passwordField.setEchoChar((char) 0);
		registerButton = new JButton("Register");
		showPasswordButton = new JButton();

		nameLabel.setBackground(Color.WHITE);
		usernameLabel.setBackground(Color.WHITE);
		passwordLabel.setBackground(Color.WHITE);
		emailLabel.setBackground(Color.WHITE);

		nameField.setBounds(100, 50, 250, 40);
		nameLabel.setBounds(100, 25, 250, 30);
		usernameField.setBounds(100, 120, 250, 40);
		usernameLabel.setBounds(100, 95, 250, 30);
		passwordField.setBounds(100, 190, 250, 40);
		passwordLabel.setBounds(100, 165, 250, 30);
		showPasswordButton.setBounds(350, 190, 40, 40);
		emailField.setBounds(100, 260, 250, 40);
		emailLabel.setBounds(100, 235, 250, 30);
		registerButton.setBounds(100, 320, 250, 40);
		backButton.setBounds(100, 370, 250, 40);
		helperText.setBounds(100, 420, 250, 40);
		backgroundLabel.setBounds(0, 0, 500, 500);
		panel.setBounds(80, 10, 310, 430);

		registerButton.addActionListener(this);
		backButton.addActionListener(this);
		showPasswordButton.addActionListener(this);

		registerButton.setForeground(Color.decode("#003300"));
		registerButton.setFont(new Font("Timesroman", Font.BOLD, 14));
		nameField.setBorder(new MatteBorder(0, 0, 2, 0, Color.decode("#00ffff")));
		passwordField.setBorder(new MatteBorder(0, 0, 2, 0, Color.decode("#00ffff")));
		usernameField.setBorder(new MatteBorder(0, 0, 2, 0, Color.decode("#00ffff")));
		passwordField.setBorder(new MatteBorder(0, 0, 2, 0, Color.decode("#00ffff")));
		emailField.setBorder(new MatteBorder(0, 0, 2, 0, Color.decode("#00ffff")));

		nameField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (nameField.getText().equals("Enter name")) {
					nameField.setText("");
					nameLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (nameField.getText().isEmpty()) {
					nameField.setText("Enter name");
					nameLabel.setVisible(false);
					repaint();
				}

			}

		});

		usernameField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (usernameField.getText().equals("Enter username")) {
					usernameField.setText("");
					usernameLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (usernameField.getText().isEmpty()) {
					usernameField.setText("Enter username");
					usernameLabel.setVisible(false);
					repaint();
				}
			}

		});
		passwordField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (passwordField.getText().equals("Enter password")) {
					passwordField.setEchoChar('*');
					passwordField.setText("");
					passwordLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (passwordField.getText().isEmpty()) {
					passwordField.setEchoChar((char) 0);
					passwordField.setText("Enter password");
					passwordLabel.setVisible(false);
					repaint();
				}
			}

		});
		emailField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (emailField.getText().equals("Enter email")) {
					emailField.setText("");
					emailLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (emailField.getText().isEmpty()) {
					emailField.setText("Enter email");
					emailLabel.setVisible(false);
					repaint();
				}
			}

		});
		try {
			BufferedImage backIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "backIcon.jpg"));
			Image scaledIcon = backIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			backButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage registerIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "registerIcon.png"));
			scaledIcon = registerIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			registerButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage showIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "showPasswordIcon.png"));
			scaledIcon = showIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			showPasswordButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage background = ImageIO.read(new File(new ConstantPath().ICONS_URL + "appLogo10.jpg"));
			scaledIcon = background.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
			backgroundLabel.setIcon(new ImageIcon(scaledIcon));
			backButton.setBorderPainted(false);
			backButton.setContentAreaFilled(false);
			showPasswordButton.setBorderPainted(false);
			showPasswordButton.setContentAreaFilled(false);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
		nameLabel.setVisible(false);
		usernameLabel.setVisible(false);
		passwordLabel.setVisible(false);
		emailLabel.setVisible(false);

		nameLabel.setBackground(Color.WHITE);
		usernameLabel.setBackground(Color.WHITE);
		passwordLabel.setBackground(Color.WHITE);
		emailLabel.setBackground(Color.WHITE);

		nameLabel.setOpaque(true);
		usernameLabel.setOpaque(true);
		passwordLabel.setOpaque(true);
		emailLabel.setOpaque(true);

		panel.setBackground(new Color(0, 0, 0, 0.5f));

		setContentPane(backgroundLabel);
		add(usernameLabel);
		add(passwordLabel);
		add(emailLabel);
		add(nameField);
		add(nameLabel);
		add(usernameField);
		add(passwordField);
		add(emailField);
		add(registerButton);
		add(backButton);
		add(helperText);
		add(showPasswordButton);
		// add(panel);
		if (editable) {
			setTitle("Edit User");
			usernameField.setText(username);
			usernameField.setEditable(false);
			usernameField.setEnabled(false);
			registerButton.setText("Update");
			if (flag == 0) {
				nameField.setText(database.getName("CUSTOMER", username));
				emailField.setText(database.getEmail("CUSTOMER", username));
				passwordField.setText(database.getPassword("CUSTOMER", username));
			} else if (flag == 1) {
				nameField.setText(database.getName("SHOPKEEPER", username));
				emailField.setText(database.getEmail("SHOPKEEPER", username));
				passwordField.setText(database.getPassword("SHOPKEEPER", username));
			}
		}

		frameInitialize();
	}

	private void frameInitialize() {
		setResizable(false);
		setSize(500, 500);
		setLocation(400, 100);
		getContentPane().setBackground(Color.WHITE);
		if (flag == 0) {
			setTitle("Customer Register Page");
		} else if (flag == 1) {
			setTitle("Shopkeeper Register Page");
		}
		setLayout(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			if (flag == 0) {
				addCustomer();
			} else if (flag == 1) {
				addShopkeeper();
			}
		}
		if (e.getSource() == backButton) {
			onBack();
		}
		if (e.getSource() == showPasswordButton) {
			if (show == 0 && !passwordField.getText().equals("Enter password")) {
				show = 1;
				passwordField.setEchoChar((char) 0);
				try {

					BufferedImage showIcon = ImageIO
							.read(new File(new ConstantPath().ICONS_URL + "hidePasswordIcon.png"));
					Image scaledIcon = showIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
					showPasswordButton.setIcon(new ImageIcon(scaledIcon));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex);
				}
			} else if (show == 1 && !passwordField.getText().equals("Enter password")) {
				show = 0;
				passwordField.setEchoChar('*');
				try {

					BufferedImage showIcon = ImageIO
							.read(new File(new ConstantPath().ICONS_URL + "showPasswordIcon.png"));
					Image scaledIcon = showIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
					showPasswordButton.setIcon(new ImageIcon(scaledIcon));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex);
				}
			}
		}
	}

	private void onBack() {
		if (flag == 0 || editable) {
			helperText.setText("");
			setVisible(false);
			new LoginPage();
		} else if (flag == 1) {
			helperText.setText("");
			setVisible(false);
			new LoginPage();
		}
	}

	private void addShopkeeper() {
		helperText.setText("");
		String name = nameField.getText();
		String username = usernameField.getText();
		String password = passwordField.getText();
		String email = emailField.getText();
		ArrayList usernameList = new ArrayList();
		usernameList = database.getUsernameList("SHOPKEEPER");
		if (name.isEmpty() || name.equals("Enter name") || username.isEmpty() || username.equals("Enter username")
				|| password.isEmpty() || password.equals("Enter password") || email.isEmpty()
				|| email.equals("Enter email")) {
			helperText.setText("All details required");
		} else if (usernameList.contains(username) && !editable) {
			helperText.setText("Username already exists try another");
		} else {
			if (email.contains("@") && (email.endsWith(".com") || email.endsWith(".in"))) {
				if (editable) {
					database.updateTable("SHOPKEEPER", name, username, password, email);
					JOptionPane.showMessageDialog(this, "Successfully Updated");
					setVisible(false);
					new LoginPage();
				} else {
					database.add("SHOPKEEPER", name, username, password, email);
					JOptionPane.showMessageDialog(this, "Successfully added");
					setVisible(false);
					new ShopDetails(username, 0);
					//new LoginPage();
				}
			} else {
				helperText.setText("Enter valid email");
			}
		}
	}

	private void addCustomer() {
		helperText.setText("");
		String name = nameField.getText();
		String username = usernameField.getText();
		String password = passwordField.getText();
		String email = emailField.getText();
		ArrayList usernameList = new ArrayList();
		usernameList = database.getUsernameList("CUSTOMER");
		if (name.isEmpty() || name.equals("Enter name") || username.isEmpty() || username.equals("Enter username")
				|| password.isEmpty() || password.equals("Enter password") || email.isEmpty()
				|| email.equals("Enter email")) {
			helperText.setText("All details required");
		} else if (usernameList.contains(username) && !editable) {
			helperText.setText("Username already exists try another");
		} else {
			if (email.contains("@") && (email.endsWith(".com") || email.endsWith(".in"))) {
				if (editable) {
					database.updateTable("CUSTOMER", name, username, password, email);
					JOptionPane.showMessageDialog(this, "Successfully Updated");
					setVisible(false);
					new LoginPage();
				} else {
					database.add("CUSTOMER", name, username, password, email);
					JOptionPane.showMessageDialog(this, "Successfully added");
					setVisible(false);
					new LoginPage();
				}
			} else {
				helperText.setText("Enter valid email");
			}
		}
	}
}

class ShopDetails extends JFrame implements ActionListener {

	JTextField shopNameField, shopTypeField, shopCityField;
	JButton addShopButton;
	DatabaseHandler database;
	String username;
	JLabel helperText, shopNameLabel, shopTypeLabel, shopCityLabel, backgroundLabel;
	JPanel backgroundPanel;
	int flag;

	ShopDetails(String username, int flag) {
		this.flag = flag;
		this.username = username;
		helperText = new JLabel();
		helperText.setForeground(Color.RED);
		database = new DatabaseHandler();
		shopNameField = new JTextField("Enter shop name");
		shopTypeField = new JTextField("Enter shop type");
		shopCityField = new JTextField("Enter shop city");
		addShopButton = new JButton("Add");
		shopNameLabel = new JLabel("Enter Shopname");
		shopTypeLabel = new JLabel("Enter Shoptype");
		shopCityLabel = new JLabel("Enter Shopcity");
		backgroundLabel = new JLabel();
		backgroundPanel = new JPanel();

		addShopButton.addActionListener(this);
		shopNameField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (shopNameField.getText().equals("Enter shop name")) {
					shopNameField.setText("");
					shopNameLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (shopNameField.getText().isEmpty()) {
					shopNameField.setText("Enter shop name");
					shopNameLabel.setVisible(false);
					repaint();
				}

			}

		});
		shopTypeField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (shopTypeField.getText().equals("Enter shop type")) {
					shopTypeField.setText("");
					shopTypeLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (shopTypeField.getText().isEmpty()) {
					shopTypeField.setText("Enter shop type");
					shopTypeLabel.setVisible(false);
					repaint();
				}

			}

		});
		shopCityField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (shopCityField.getText().equals("Enter shop city")) {
					shopCityField.setText("");
					shopCityLabel.setVisible(true);
					repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (shopCityField.getText().isEmpty()) {
					shopCityField.setText("Enter shop city");
					shopCityLabel.setVisible(false);
					repaint();
				}
			}

		});

		backgroundLabel.setBounds(0, 0, 500, 500);
		backgroundPanel.setBounds(80, 50, 300, 350);
		shopNameField.setBounds(100, 100, 250, 40);
		shopNameLabel.setBounds(100, 70, 250, 30);
		shopTypeField.setBounds(100, 180, 250, 40);
		shopTypeLabel.setBounds(100, 150, 250, 30);
		shopCityField.setBounds(100, 260, 250, 40);
		shopCityLabel.setBounds(100, 230, 250, 30);
		addShopButton.setBounds(100, 310, 250, 40);
		helperText.setBounds(100, 350, 250, 40);

		shopNameField.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#00ffff")));
		shopTypeField.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#00ffff")));
		shopCityField.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#00ffff")));

		shopNameLabel.setVisible(false);
		shopTypeLabel.setVisible(false);
		shopCityLabel.setVisible(false);

		shopNameLabel.setBackground(Color.WHITE);
		shopCityLabel.setBackground(Color.WHITE);
		shopTypeLabel.setBackground(Color.WHITE);

		shopNameLabel.setOpaque(true);
		shopTypeLabel.setOpaque(true);
		shopCityLabel.setOpaque(true);

		backgroundPanel.setBackground(new Color(0, 0, 0, 0.5f));

		try {
			BufferedImage shopIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "addShopIcon.jpg"));
			Image scaledIcon = shopIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			addShopButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage background = ImageIO.read(new File(new ConstantPath().ICONS_URL + "appLogo10.jpg"));
			scaledIcon = background.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
			backgroundLabel.setIcon(new ImageIcon(scaledIcon));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}

		setContentPane(backgroundLabel);
		add(shopNameField);
		add(shopTypeField);
		add(shopCityField);
		add(addShopButton);
		add(helperText);
		add(shopNameLabel);
		add(shopTypeLabel);
		add(shopCityLabel);
		// add(backgroundPanel);

		if (flag == 1) {
			setTitle("Edit Shop details");
			addShopButton.setText("Update");
			shopNameField.setText(database.getShopDetails("SHOPNAME", username));
			shopTypeField.setText(database.getShopDetails("SHOPTYPE", username));
			shopCityField.setText(database.getShopDetails("SHOPCITY", username));
		}
		frameInitialize();
	}

	private void frameInitialize() {
		setResizable(false);
		setSize(500, 500);
		setLocation(400, 100);
		setTitle("Shopkeeper Page");
		setLayout(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addShopButton) {
			addNewShop();
		}
	}

	private void addNewShop() {
		helperText.setText("");
		String shopName = shopNameField.getText();
		String shopType = shopTypeField.getText();
		String shopCity = shopCityField.getText();
		if (shopName.isEmpty() || shopName.equals("Enter shop name") || shopType.isEmpty()
				|| shopType.equals("Enter shop type") || shopCity.isEmpty() || shopCity.equals("Enter shop city")) {
			helperText.setText("All details required");
		} else {
			if (flag == 0) {
				database.addShop(username, shopName, shopType, shopCity);
				JOptionPane.showMessageDialog(this, "Successfully added");
				setVisible(false);
				helperText.setText("");
				new LoginPage();
			} else if (flag == 1) {
				database.updateShop(username, shopName, shopType, shopCity);
				JOptionPane.showMessageDialog(this, "Successfully updated");
				setVisible(false);
				helperText.setText("");
				new ShopkeeperPage(username);
			}
		}
	}
}

class ShopkeeperPage extends JFrame implements ActionListener {

	String username;
	JLabel titleLabel, nameLabel, backgroundLabel;
	DatabaseHandler database;
	JComboBox productListBox, categoryListBox;
	JTextField categoryField;
	JButton infoButton, addProductsButton, editButton, ordersButton, deleteAccountButton, editUserButton,
			editShopButton, deleteButton;
	JButton addCategoryButton, deleteCategoryButton, showCategoryProductsButton, editCategoryButton, statsButton;
	JPanel backgroundPanel;
	JPopupMenu popupMenu;
	JMenuItem logout;

	ShopkeeperPage(String username) {

		database = new DatabaseHandler();
		ArrayList categoryList = new ArrayList();
		categoryList.add("CATEGORIES");
		categoryList.add("All");
		categoryList.addAll(database.getCategories(username));
		categoryList = removeDuplicates(categoryList);
		backgroundPanel = new JPanel();
		this.username = username;
		infoButton = new JButton("Info");
		statsButton = new JButton("Stats");
		showCategoryProductsButton = new JButton("Show Products");
		addCategoryButton = new JButton("Add Category");
		deleteCategoryButton = new JButton("Delete Category");
		editUserButton = new JButton("Edit User");
		editShopButton = new JButton("Edit Shop");
		editCategoryButton = new JButton("Edit Category");
		deleteAccountButton = new JButton("Delete Account");
		addProductsButton = new JButton("Add Products");
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		ordersButton = new JButton("Orders");
		backgroundLabel = new JLabel();
		popupMenu=new JPopupMenu();
		logout=new JMenuItem("Logout");
		
		popupMenu.add(logout);

		ArrayList productList = new ArrayList();
		productList.add("PRODUCTS");
		productList.addAll(database.getProducts(username));
		productListBox = new JComboBox(productList.toArray());

		categoryListBox = new JComboBox(categoryList.toArray());

		
		String shopNameString = capitalize(database.getShopDetails("SHOPNAME", username));

		titleLabel = new JLabel(shopNameString, SwingConstants.CENTER);
		// titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(150, 50, 400, 40);
		titleLabel.setFont(new Font("Timesroman", Font.BOLD, 24));
		// titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
		titleLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

		String shopkeeperName = database.getName("SHOPKEEPER", username);
		nameLabel = new JLabel(
				"Welcome Mr." + shopkeeperName.substring(0, 1).toUpperCase() + shopkeeperName.substring(1));
		nameLabel.setBounds(150, 100, 400, 40);
		nameLabel.setFont(new Font("Timesroman", Font.BOLD, 16));
		// nameLabel.setForeground(Color.WHITE);

		productListBox.setBounds(150, 150, 200, 40);
		categoryListBox.setBounds(360, 150, 200, 40);
		infoButton.setBounds(150, 200, 200, 40);
		showCategoryProductsButton.setBounds(360, 200, 200, 40);
		editButton.setBounds(150, 250, 200, 40);
		addCategoryButton.setBounds(360, 350, 200, 40);
		deleteButton.setBounds(150, 300, 200, 40);
		deleteCategoryButton.setBounds(360, 300, 200, 40);
		addProductsButton.setBounds(150, 350, 200, 40);
		editCategoryButton.setBounds(360, 250, 200, 40);
		ordersButton.setBounds(0, 0, 120, 40);
		deleteAccountButton.setBounds(120, 0, 180, 40);
		editUserButton.setBounds(300, 0, 150, 40);
		editShopButton.setBounds(590, 0, 135, 40);
		statsButton.setBounds(450, 0, 140, 40);

		productListBox.setBackground(Color.WHITE);
		categoryListBox.setBackground(Color.WHITE);
		infoButton.setBackground(Color.WHITE);
		showCategoryProductsButton.setBackground(Color.WHITE);
		editButton.setBackground(Color.WHITE);
		addCategoryButton.setBackground(Color.WHITE);
		deleteButton.setBackground(Color.WHITE);
		deleteCategoryButton.setBackground(Color.WHITE);
		addProductsButton.setBackground(Color.WHITE);
		editCategoryButton.setBackground(Color.WHITE);
		ordersButton.setBackground(Color.WHITE);
		deleteAccountButton.setBackground(Color.WHITE);
		editUserButton.setBackground(Color.WHITE);
		editShopButton.setBackground(Color.WHITE);
		statsButton.setBackground(Color.WHITE);

		try {
			BufferedImage logoutIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "deleteIcon1.png"));
			Image scaledIcon = logoutIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			deleteAccountButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage editIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "editIcon1.png"));
			scaledIcon = editIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			editButton.setIcon(new ImageIcon(scaledIcon));
			editUserButton.setIcon(new ImageIcon(scaledIcon));
			editShopButton.setIcon(new ImageIcon(scaledIcon));
			editCategoryButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage addIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "addIcon.png"));
			scaledIcon = addIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			addProductsButton.setIcon(new ImageIcon(scaledIcon));
			addCategoryButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage deleteIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "deleteIcon1.png"));
			scaledIcon = deleteIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			deleteButton.setIcon(new ImageIcon(scaledIcon));
			deleteCategoryButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage infoIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "infoIcon.png"));
			scaledIcon = infoIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			infoButton.setIcon(new ImageIcon(scaledIcon));
			showCategoryProductsButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage ordersIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "ordersIcon.jpg"));
			scaledIcon = ordersIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			ordersButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage statsIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "statsIcon.png"));
			scaledIcon = statsIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			statsButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage background = ImageIO.read(new File(new ConstantPath().ICONS_URL + "appLogo10.jpg"));
			scaledIcon = background.getScaledInstance(740, 500, Image.SCALE_SMOOTH);
			backgroundLabel.setIcon(new ImageIcon(scaledIcon));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}

		infoButton.addActionListener(this);
		addProductsButton.addActionListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
		ordersButton.addActionListener(this);
		deleteAccountButton.addActionListener(this);
		editUserButton.addActionListener(this);
		editShopButton.addActionListener(this);
		addCategoryButton.addActionListener(this);
		deleteCategoryButton.addActionListener(this);
		showCategoryProductsButton.addActionListener(this);
		editCategoryButton.addActionListener(this);
		statsButton.addActionListener(this);
		logout.addActionListener(this);

		setContentPane(backgroundLabel);
		add(titleLabel);
		add(nameLabel);
		add(productListBox);
		add(categoryListBox);
		add(infoButton);
		add(addProductsButton);
		add(showCategoryProductsButton);
		add(editButton);
		add(deleteButton);
		add(ordersButton);
		add(deleteAccountButton);
		add(editUserButton);
		add(editShopButton);
		add(addCategoryButton);
		add(deleteCategoryButton);
		add(editCategoryButton);
		add(statsButton);
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenu.show(ShopkeeperPage.this, e.getX(), e.getY());
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		frameInitialize();
	}
	
	private String capitalize(String name) {
		String shopName[] = name.split(" ");
		String shopNameString = "";
		for (int i = 0; i < shopName.length; i++) {
			shopNameString += shopName[i].substring(0, 1).toUpperCase() + shopName[i].substring(1) + " ";
		}
		return shopNameString;
	}

	private ArrayList removeDuplicates(ArrayList categoryList) {
		for (int i = 0; i < categoryList.size(); i++) {
			for (int j = i + 1; j < categoryList.size(); j++) {
				if (String.valueOf(categoryList.get(i)).equalsIgnoreCase(String.valueOf(categoryList.get(j)))) {
					categoryList.remove(j);
				}
			}
		}
		return categoryList;
	}

	private void frameInitialize() {
		// getContentPane().setBackground(new Color(0.0f,0.0f,1.0f,0.5f));
		setResizable(false);
		setSize(740, 500);
		setLocationRelativeTo(null);
		setTitle("Shopkeeper Page");
		setLayout(null);
		setVisible(true);
	}

	private void productBoxFunction(String category) {
		try {
			remove(productListBox);
		} catch (Exception e) {

		}

		ArrayList productList = new ArrayList();
		productList.add("PRODUCTS");
		if (category.equals("")) {
			productList.addAll(database.getProducts(username));
		} else {
			productList.addAll(database.getCategoryProducts(username, category));
		}
		JComboBox newBox = new JComboBox(productList.toArray());
		productListBox = newBox;
		productListBox.setBounds(150, 150, 200, 40);
		add(productListBox);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == infoButton) {
			// JOptionPane.showMessageDialog(this, "updating...");
			String data = "";
			String productName = (String) productListBox.getSelectedItem();
			if (productName.isEmpty() || productName.equalsIgnoreCase("PRODUCTS")) {
				JOptionPane.showMessageDialog(this, "Select some product");
			} else {
				data += "Product name : " + database.getProductDetails("PRODUCTNAME", username, productName);
				data += "\nProduct price : " + database.getProductDetails("PRODUCTPRICE", username, productName);
				data += "\nProduct availability : "
						+ database.getProductDetails("PRODUCTAVAILABILITY", username, productName);
				data += "\nProduct description : "
						+ database.getProductDetails("PRODUCTDESCRIPTION", username, productName);
				JOptionPane.showMessageDialog(this, data);
			}

		}
		if (e.getSource() == addProductsButton) {
			setVisible(false);
			new AddProducts(username, false, "");
		}
		if (e.getSource() == editButton) {
			String productName = (String) productListBox.getSelectedItem();
			if (productName.isEmpty() || productName.equalsIgnoreCase("PRODUCTS")) {
				JOptionPane.showMessageDialog(this, "Select some product");
			} else {
				setVisible(false);
				new AddProducts(username, true, productName);
			}

		}
		if (e.getSource() == deleteButton) {
			String productName = (String) productListBox.getSelectedItem();
			if (productName.isEmpty() || productName.equalsIgnoreCase("PRODUCTS")) {
				JOptionPane.showMessageDialog(this, "Select some product");
			} else {
				database.deleteProduct(username, productName);
				JOptionPane.showMessageDialog(this, "Deleted");
				setVisible(false);
				new ShopkeeperPage(username);
			}

		}
		if (e.getSource() == deleteAccountButton) {

			int result = JOptionPane.showConfirmDialog(this, "Are you sure want to delete your Account?");
			if (result == JOptionPane.YES_OPTION) {
				setVisible(false);
				database.logoutShopkeeper(username);
				new LoginPage();
			}
		}
		if (e.getSource() == ordersButton) {
			setVisible(false);
			new OrdersPage(username);
		}
		if (e.getSource() == statsButton) {
			// JOptionPane.showMessageDialog(this, "Waiting for deliveries...");
			new ShopkeeperStatsPage(username);
			setVisible(false);
		}
		if (e.getSource() == editUserButton) {
			setVisible(false);
			new UserRegister(1, true, username);
		}
		if (e.getSource() == editShopButton) {
			setVisible(false);
			new ShopDetails(username, 1);
		}
		if (e.getSource() == addCategoryButton) {
			setVisible(false);
			new AddCategory(username, "", false);
		}
		if (e.getSource() == deleteCategoryButton) {
			String categoryName = (String) categoryListBox.getSelectedItem();
			if (categoryName.isEmpty() || categoryName.equalsIgnoreCase("CATEGORIES")) {
				JOptionPane.showMessageDialog(this, "Select some category");
			} else {
				database.deleteCategory(username, categoryName);
				JOptionPane.showMessageDialog(this, "Deleted");
				setVisible(false);
				new ShopkeeperPage(username);
			}
		}
		if (e.getSource() == showCategoryProductsButton) {
			String categoryName = (String) categoryListBox.getSelectedItem();
			if (categoryName.isEmpty() || categoryName.equalsIgnoreCase("CATEGORIES")) {
				JOptionPane.showMessageDialog(this, "Select some category");
			} else if (categoryName.equalsIgnoreCase("All")) {
				String data = "PRODUCTS :\n";
				ArrayList categoryProductsList = new ArrayList();
				categoryProductsList = database.getProducts(username);
				for (Object products : categoryProductsList) {
					data += (String) products + ",\n";
				}
				JOptionPane.showMessageDialog(this, data);
				productBoxFunction("");
			} else {
				String data = "PRODUCTS :\n";
				ArrayList categoryProductsList = new ArrayList();
				categoryProductsList = database.getCategoryProducts(username, categoryName);
				for (Object products : categoryProductsList) {
					data += (String) products + ",\n";
				}
				JOptionPane.showMessageDialog(this, data);
				productBoxFunction(categoryName);
			}
		}
		if (e.getSource() == editCategoryButton) {
			String categoryName = (String) categoryListBox.getSelectedItem();
			if (categoryName.isEmpty() || categoryName.equalsIgnoreCase("CATEGORIES")) {
				JOptionPane.showMessageDialog(this, "Select some category");
			} else {
				setVisible(false);
				new AddCategory(username, categoryName, true);
			}

		}
		if (e.getSource()==logout) {
			int n=JOptionPane.showConfirmDialog(this, "Are you sure want to exit?");
			if (JOptionPane.YES_OPTION==n) {
				dispose();
				new LoginPage();
			}
		}
	}
}

class NoShops extends JFrame implements ActionListener {

	JLabel titleLabel;
	JButton addShopButton, backButton;
	String username;
	JLabel backgroundLabel;

	NoShops(String username) {
		this.username = username;
		addShopButton = new JButton("Add Shop");
		backButton = new JButton("Back");
		titleLabel = new JLabel("No Shop Found !", SwingConstants.CENTER);
		backgroundLabel = new JLabel();

		titleLabel.setBounds(120, 100, 200, 40);
		addShopButton.setBounds(80, 150, 150, 40);
		backButton.setBounds(240, 150, 150, 40);
		addShopButton.addActionListener(this);
		backButton.addActionListener(this);

		addShopButton.setBackground(Color.WHITE);
		backButton.setBackground(Color.WHITE);
		titleLabel.setForeground(Color.RED);
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		addShopButton.setFocusPainted(false);
		addShopButton.setForeground(Color.decode("#006600"));
		backButton.setFocusPainted(false);

		try {
			BufferedImage addIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "addShopIcon.jpg"));
			Image scaledIcon = addIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			addShopButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage backIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "backIcon.jpg"));
			scaledIcon = backIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			backButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage background = ImageIO.read(new File(new ConstantPath().ICONS_URL + "appLogo10.jpg"));
			scaledIcon = background.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
			backgroundLabel.setIcon(new ImageIcon(scaledIcon));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}

		setContentPane(backgroundLabel);
		add(titleLabel);
		add(addShopButton);
		add(backButton);
		frameInitialize();
	}

	private void frameInitialize() {
		setSize(500, 500);
		setLocation(400, 100);
		setTitle("No shop found");
		setLayout(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addShopButton) {
			setVisible(false);
			new ShopDetails(username, 0);
		}
		if (e.getSource() == backButton) {
			setVisible(false);
			new LoginPage();
		}
	}

}

class AddProducts extends JFrame implements ActionListener {
	String username;
	JTextField productNameField, productPriceField, productAvailabilityField, productDescriptionField;
	JLabel productNameLabel, productPriceLabel, productAvailabilityLabel, productDescriptionLabel, helperText,
			backgroundLabel;
	JButton addButton, backButton;
	DatabaseHandler database;
	String passedProductName;
	boolean editable;

	AddProducts(String username, boolean editable, String passedProductName) {
		this.passedProductName = passedProductName;
		this.editable = editable;
		database = new DatabaseHandler();
		productNameField = new JTextField();
		productPriceField = new JTextField();
		productAvailabilityField = new JTextField();
		productDescriptionField = new JTextField();
		productNameLabel = new JLabel("Product name :");
		productPriceLabel = new JLabel("Product price :");
		productAvailabilityLabel = new JLabel("Product availability :");
		productDescriptionLabel = new JLabel("Product description :");
		backgroundLabel = new JLabel();
		addButton = new JButton("Add");
		backButton = new JButton("Back");
		helperText = new JLabel();
		helperText.setForeground(Color.RED);

		addButton.addActionListener(this);
		backButton.addActionListener(this);

		productNameField.setBounds(150, 100, 250, 40);
		productPriceField.setBounds(150, 150, 250, 40);
		productAvailabilityField.setBounds(150, 200, 250, 40);
		productDescriptionField.setBounds(150, 250, 250, 40);
		productNameLabel.setBounds(10, 100, 150, 40);
		productPriceLabel.setBounds(10, 150, 150, 40);
		productAvailabilityLabel.setBounds(10, 200, 150, 40);
		productDescriptionLabel.setBounds(10, 250, 150, 40);
		addButton.setBounds(100, 300, 150, 40);
		backButton.setBounds(100, 350, 150, 40);
		helperText.setBounds(50, 400, 250, 40);
		backgroundLabel.setBounds(0, 0, 500, 500);

		try {
			BufferedImage addIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "addIcon.png"));
			Image scaledIcon = addIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			addButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage backIcon = ImageIO.read(new File(new ConstantPath().ICONS_URL + "backIcon.jpg"));
			scaledIcon = backIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			backButton.setIcon(new ImageIcon(scaledIcon));
			BufferedImage background = ImageIO.read(new File(new ConstantPath().ICONS_URL + "appLogo10.jpg"));
			scaledIcon = background.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
			backgroundLabel.setIcon(new ImageIcon(scaledIcon));
			backButton.setBorderPainted(false);
			backButton.setContentAreaFilled(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}

		setContentPane(backgroundLabel);
		add(productNameField);
		add(productPriceField);
		add(productAvailabilityField);
		add(productDescriptionField);
		add(productNameLabel);
		add(productPriceLabel);
		add(productAvailabilityLabel);
		add(productDescriptionLabel);
		add(helperText);
		add(addButton);
		add(backButton);
		this.username = username;
		if (editable) {
			addButton.setText("Update");
			productNameField.setText(passedProductName);
			productNameField.setEditable(false);
			productNameField.setEnabled(false);
			productPriceField.setText(database.getProductDetails("PRODUCTPRICE", username, passedProductName));
			productAvailabilityField
					.setText(database.getProductDetails("PRODUCTAVAILABILITY", username, passedProductName));
			productDescriptionField
					.setText(database.getProductDetails("PRODUCTDESCRIPTION", username, passedProductName));
		}
		frameInitialize();
	}

	private void frameInitialize() {
		setResizable(false);
		setSize(500, 500);
		setLocation(400, 100);
		setTitle("Product details");
		setLayout(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			onAddProduct();
		}
		if (e.getSource() == backButton) {
			onBack();
		}
	}

	private void onAddProduct() {
		helperText.setText("");

		String productName = productNameField.getText();
		String productPrice = productPriceField.getText();
		String productAvail = productAvailabilityField.getText();
		String productDescription = productDescriptionField.getText();

		ArrayList productList = database.getProducts(username);
		if (productName.isEmpty() || productPrice.isEmpty() || productAvail.isEmpty() || productDescription.isEmpty()) {
			helperText.setText("All details required !");
		} else if (productList.contains(productName) && !editable) {
			helperText.setText("Product name already exists");
		} else {
			if (editable) {
				if (isValidPrice(productPrice) && isValidQuantity(productAvail)) {
					database.updateProduct(username, productName, productPrice, productAvail, productDescription);
					setVisible(false);
					helperText.setText("");
					JOptionPane.showMessageDialog(this, "Successfully updated");
					new ShopkeeperPage(username);
				} else {
					JOptionPane.showMessageDialog(this,
							"Invalid price or availability\nPrice must be in form of rate/unit"
									+ "\nEg.100/kg,2000/engine,100/l and\n"
									+ "Availability must be in form of quantity with unit \n"
									+ "Eg.100kg,2000engines,100l");
				}

			} else {
				if (isValidPrice(productPrice) && isValidQuantity(productAvail)) {
					database.addProduct(username, productName, productPrice, productAvail, productDescription);
					setVisible(false);
					helperText.setText("");
					JOptionPane.showMessageDialog(this, "Successfully added");
					new ShopkeeperPage(username);
				} else {
					JOptionPane.showMessageDialog(this,
							"Invalid price or availability\nPrice must be in form of rate/unit"
									+ "\nEg.100/kg,2000/engine,100/l and\n"
									+ "Availability must be in form of quantity with unit \n"
									+ "Eg.100kg,2000engines,100l");
				}

			}
		}

	}

	private boolean isValidPrice(String price) {
		Pattern p = Pattern.compile("[0-9]+/[a-z]+");
		return p.matcher(price).matches();
	}

	private boolean isValidQuantity(String quantity) {
		Pattern p = Pattern.compile("[0-9]+[a-z]+");
		return p.matcher(quantity).matches();
	}

	private void onBack() {
		helperText.setText("");
		setVisible(false);
		new ShopkeeperPage(username);
	}
}

class DatabaseHandler {

	Connection connection;
	Statement statement;

	public int createTable(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.close();
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public void add(String tableName, String name, String username, String password, String email) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "INSERT INTO " + tableName + " VALUES('" + name + "','" + username + "','" + password + "','"
					+ email + "')";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addShop(String username, String shopName, String shopType, String shopCity) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "INSERT INTO SHOPS VALUES('" + username + "','" + shopName + "','" + shopType + "','"
					+ shopCity + "')";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCategory(String username, String category, String productName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "INSERT INTO CATEGORIES VALUES('" + username + "','" + category + "','" + productName + "')";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addProduct(String username, String productName, String productPrice, String productAvail,
			String productDesc) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "INSERT INTO PRODUCTS VALUES('" + username + "','" + productName + "','" + productPrice
					+ "','" + productAvail + "','" + productDesc + "')";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTable(String tableName, String name, String username, String password, String email) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "UPDATE " + tableName + " SET NAME='" + name + "'," + "PASSWORD='" + password + "',EMAIL='"
					+ email + "'" + "WHERE USERNAME='" + username + "'";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateShop(String username, String shopName, String shopType, String shopCity) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "UPDATE SHOPS SET SHOPNAME='" + shopName + "'," + "SHOPTYPE='" + shopType + "',SHOPCITY='"
					+ shopCity + "'" + "WHERE USERNAME='" + username + "'";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateProduct(String username, String productName, String productPrice, String productAvail,
			String productDesc) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "UPDATE PRODUCTS SET PRODUCTPRICE='" + productPrice + "'," + "PRODUCTAVAILABILITY='"
					+ productAvail + "',PRODUCTDESCRIPTION='" + productDesc + "'" + "WHERE USERNAME='" + username
					+ "' AND PRODUCTNAME='" + productName + "'";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList getUsernameList(String tableName) {
		ArrayList list = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM " + tableName;
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				list.add(rs.getObject("USERNAME"));
			}
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return list;
	}

	public String getPassword(String tableName, String username) {
		String password = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM " + tableName;
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username)) {
					password = rs.getObject("PASSWORD").toString();
					break;
				}
			}
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return password;
	}

	public String getName(String tableName, String username) {
		String name = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM " + tableName;
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username)) {
					name = rs.getObject("NAME").toString();
					break;
				}
			}
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return name;
	}

	public String getEmail(String tableName, String username) {
		String email = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM " + tableName;
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username)) {
					email = rs.getObject("EMAIL").toString();
					break;
				}
			}
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return email;
	}

	public String getShopDetails(String columnName, String username) {
		String data = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username)) {
					data = rs.getObject(columnName).toString();
					break;
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public String getCustomerDetails(String columnName, String username) {
		String data = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM CUSTOMER";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username)) {
					data = rs.getObject(columnName).toString();
					break;
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList<ShopInfo> getShops() {
		ArrayList<ShopInfo> data = new ArrayList<ShopInfo>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				ShopInfo shopInfo = new ShopInfo(rs.getString("USERNAME"), rs.getString("SHOPNAME"),
						rs.getString("SHOPTYPE"), rs.getString("SHOPCITY"));
				data.add(shopInfo);
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList<ShopInfo> getShopsWithType(String shopType) {
		ArrayList<ShopInfo> data = new ArrayList<ShopInfo>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getString("SHOPTYPE").equalsIgnoreCase(shopType)) {
					ShopInfo shopInfo = new ShopInfo(rs.getString("USERNAME"), rs.getString("SHOPNAME"),
							rs.getString("SHOPTYPE"), rs.getString("SHOPCITY"));
					data.add(shopInfo);
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList<ShopInfo> getShopsWithCity(String shopCity) {
		ArrayList<ShopInfo> data = new ArrayList<ShopInfo>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getString("SHOPCITY").equalsIgnoreCase(shopCity)) {
					ShopInfo shopInfo = new ShopInfo(rs.getString("USERNAME"), rs.getString("SHOPNAME"),
							rs.getString("SHOPTYPE"), rs.getString("SHOPCITY"));
					data.add(shopInfo);
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList<ShopInfo> getShopsWithTypeAndCity(String shopType, String shopCity) {
		ArrayList<ShopInfo> data = new ArrayList<ShopInfo>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getString("SHOPTYPE").equalsIgnoreCase(shopType)
						&& rs.getString("SHOPCITY").equalsIgnoreCase(shopCity)) {
					ShopInfo shopInfo = new ShopInfo(rs.getString("USERNAME"), rs.getString("SHOPNAME"),
							rs.getString("SHOPTYPE"), rs.getString("SHOPCITY"));
					data.add(shopInfo);
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList<ShopkeeperInfo> getShopkeepers() {
		ArrayList<ShopkeeperInfo> data = new ArrayList<ShopkeeperInfo>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPKEEPER";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				ShopkeeperInfo shopkeeperInfo = new ShopkeeperInfo(rs.getString("USERNAME"), rs.getString("NAME"),
						rs.getString("EMAIL"));
				data.add(shopkeeperInfo);

			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getShopTypes() {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (!data.contains(rs.getString("SHOPTYPE"))) {
					data.add(rs.getString("SHOPTYPE"));
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getShopCities() {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM SHOPS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (!data.contains(rs.getString("SHOPCITY"))) {
					data.add(rs.getString("SHOPCITY"));
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public String getProductDetails(String columnName, String username, String productName) {
		String data = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM PRODUCTS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username) && rs.getObject("PRODUCTNAME").equals(productName)) {
					data = rs.getObject(columnName).toString();
					break;
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getProducts(String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM PRODUCTS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username)) {
					data.add(rs.getObject("PRODUCTNAME").toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getCategories(String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM CATEGORIES";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username)) {
					data.add(rs.getObject("CATEGORY").toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getCategoryProducts(String username, String categoryName) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM CATEGORIES";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("USERNAME").equals(username) && rs.getObject("CATEGORY").equals(categoryName)) {
					data.add(rs.getObject("PRODUCTNAME").toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public void logoutShopkeeper(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "DELETE FROM SHOPKEEPER WHERE USERNAME='" + username + "'";
			statement.executeUpdate(query);
			query = "DELETE FROM SHOPS WHERE USERNAME='" + username + "'";
			statement.executeUpdate(query);
			query = "DELETE FROM PRODUCTS WHERE USERNAME='" + username + "'";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void deleteProduct(String username, String productName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "DELETE FROM PRODUCTS WHERE USERNAME='" + username + "' AND PRODUCTNAME='" + productName
					+ "'";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void deleteCategory(String username, String categoryName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "DELETE FROM CATEGORIES WHERE USERNAME='" + username + "' AND CATEGORY='" + categoryName
					+ "'";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void addOrderStatus(String customerUsername, String shopkeeperUsername, String orderTime, String orderStatus,
			String contactNumber, String orderInfo) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "INSERT INTO ORDERSTATUS VALUES('" + customerUsername + "','" + shopkeeperUsername + "','"
					+ orderTime + "','" + orderStatus + "','" + contactNumber + "','" + orderInfo + "')";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addOrders(String customerUsername, String shopkeeperUsername, String shopName, String customerName,
			String orderInfo, String orderTime, String customerAddress) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "INSERT INTO ORDERS VALUES('" + customerUsername + "','" + shopkeeperUsername + "','"
					+ shopName + "','" + customerName + "','" + orderInfo + "','" + orderTime + "','" + customerAddress
					+ "')";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCart(String customerUsername, String shopkeeperUsername, String shopName, String customerName,
			String orderInfo, String orderTime) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "INSERT INTO CART VALUES('" + customerUsername + "','" + shopkeeperUsername + "','"
					+ shopName + "','" + customerName + "','" + orderInfo + "','" + orderTime + "')";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList getCustomerOrderDetails(String columnName, String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM ORDERS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("CUSTOMER_USERNAME").equals(username)) {
					data.add(rs.getObject(columnName).toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getShopkeeperOrderDetails(String columnName, String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM ORDERS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("SHOPKEEPER_USERNAME").equals(username)) {
					data.add(rs.getObject(columnName).toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getCustomerCartDetails(String columnName, String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM CART";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("CUSTOMER_USERNAME").equals(username)) {
					data.add(rs.getObject(columnName).toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getOrderStatusDetails(String columnName, String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM ORDERSTATUS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("SHOPKEEPER_USERNAME").equals(username)) {
					data.add(rs.getObject(columnName).toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getOrderStatusDetailsForCustomer(String columnName, String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM ORDERSTATUS";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("CUSTOMER_USERNAME").equals(username)) {
					data.add(rs.getObject(columnName).toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public ArrayList getCartDetails(String columnName, String username) {
		ArrayList data = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "SELECT * FROM CART";
			ResultSet rs = statement.executeQuery(query);
			connection.setAutoCommit(true);
			while (rs.next()) {
				if (rs.getObject("CUSTOMER_USERNAME").equals(username)) {
					data.add(rs.getObject(columnName).toString());
				}
			}
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}

	public void deleteOrder(String customerUsername, String shopkeeperUsername, String orderTime) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "DELETE FROM ORDERS WHERE (( CUSTOMER_USERNAME='" + customerUsername
					+ "' AND SHOPKEEPER_USERNAME='" + shopkeeperUsername + "') AND ORDERED_TIME='" + orderTime + "')";
			// System.out.println(query);
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void deleteOrderInCart(String customerUsername, String shopkeeperUsername, String orderTime) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "DELETE FROM CART WHERE (( CUSTOMER_USERNAME='" + customerUsername
					+ "' AND SHOPKEEPER_USERNAME='" + shopkeeperUsername + "') AND ORDERED_TIME='" + orderTime + "')";
			// System.out.println(query);
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void updateOrderStatus(String customerUsername, String shopkeeperUsername, String orderTime, String status,
			String contactNumber) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "UPDATE ORDERSTATUS SET ORDER_STATUS='" + status + "', CONTACT_NUMBER='" + contactNumber
					+ "' WHERE (( CUSTOMER_USERNAME='" + customerUsername + "' AND SHOPKEEPER_USERNAME='"
					+ shopkeeperUsername + "') AND ORDERED_TIME='" + orderTime + "')";
			// System.out.println(query);
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public void deleteCustomer(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(new ConstantPath().DATABASE_URL,new ConstantPath().DATABASE_USER,new ConstantPath().DATABASE_PASSWORD);
			statement = connection.createStatement();
			String query = "DELETE FROM CUSTOMER WHERE USERNAME='" + username + "'";
			statement.executeUpdate(query);
			connection.setAutoCommit(true);
			// System.out.println("Successfull");
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}