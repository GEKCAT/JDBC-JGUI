package idv.peter.jframe.add_customer;

import static idv.peter.common.Common.PASSWORD;
import static idv.peter.common.Common.URL;
import static idv.peter.common.Common.USER;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import idv.peter.common.Common;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AddcustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfCustomer_id;
	private JTextField tfCustomer_name;
	private JTextField tfPhone;
	private JTextField tfAddress;
	private JLabel lblNewLabel;
	private JLabel lblCustomername;
	private JLabel lblPhone;
	private JLabel lblAdress;
	private JButton btAdd;
	private JButton btClear;
	private JScrollPane spResult;
	private JTextArea taResult;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddcustomerFrame frame = new AddcustomerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddcustomerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfCustomer_id = new JTextField();
		tfCustomer_id.setBounds(160, 20, 90, 20);
		contentPane.add(tfCustomer_id);
		tfCustomer_id.setColumns(10);
		
		tfCustomer_name = new JTextField();
		tfCustomer_name.setColumns(10);
		tfCustomer_name.setBounds(160, 50, 90, 20);
		contentPane.add(tfCustomer_name);
		
		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(160, 80, 90, 20);
		contentPane.add(tfPhone);
		
		tfAddress = new JTextField();
		tfAddress.setColumns(10);
		tfAddress.setBounds(160, 110, 90, 20);
		contentPane.add(tfAddress);
		
		lblNewLabel = new JLabel("Customer ID");
		lblNewLabel.setBounds(60, 23, 100, 15);
		contentPane.add(lblNewLabel);
		
		lblCustomername = new JLabel("Customer_name");
		lblCustomername.setBounds(60, 53, 100, 15);
		contentPane.add(lblCustomername);
		
		lblPhone = new JLabel("Phone");
		lblPhone.setBounds(60, 83, 60, 15);
		contentPane.add(lblPhone);
		
		lblAdress = new JLabel("Adress");
		lblAdress.setBounds(60, 113, 60, 15);
		contentPane.add(lblAdress);
		
		//事件處理要先 ActionEvent 了之後才會執行
		btAdd = new JButton("Add");
		btAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String customer_id = tfCustomer_id.getText();
				String customer_name = tfCustomer_name.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();

				Customer customer = new Customer(customer_id, customer_name, phone, address);
				taResult.setText(customer.toString());
				
				Common common = new Common();
				common.JDBCRegistered();
				
				String sql = "INSERT INTO CUSTOMER(CUSTOMER_ID, CUSTOMER_NAME, PHONE, ADDRESS) " +
				"VALUES(?, ?, ?, ?)";
					
				try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
						PreparedStatement ps = connection.prepareStatement(sql);) {
					ps.setString(1, customer_id);
					ps.setString(2, customer_name);
					ps.setString(3, phone);
					ps.setString(4, address);
					int rowCount = ps.executeUpdate();
					taResult.append("\n" + rowCount + " row(s) inserted!!");
					
					tfAddress.setText("");
					tfCustomer_id.setText("");
					tfCustomer_name.setText("");
					tfPhone.setText("");
					//object,getText 取得內容回傳為字串
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
		btAdd.setBounds(280, 79, 87, 23);
		contentPane.add(btAdd);
		
		btClear = new JButton("Clear");
		btClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taResult.setText("");
				tfAddress.setText("");
				tfCustomer_id.setText("");
				tfCustomer_name.setText("");
				tfPhone.setText("");
			}
		});
		btClear.setBounds(280, 109, 87, 23);
		contentPane.add(btClear);
		
		spResult = new JScrollPane();
		spResult.setBounds(40, 150, 350, 101);
		contentPane.add(spResult);
		
		taResult = new JTextArea();
		spResult.setViewportView(taResult);
	}

}
