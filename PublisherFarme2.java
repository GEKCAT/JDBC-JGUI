package idv.peter.publishers;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import idv.peter.common.Common;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import static idv.peter.common.Common.URL;
import static idv.peter.common.Common.USER;
import static idv.peter.common.Common.PASSWORD;
import static idv.peter.common.Common.DRIVER_CLASS;

public class PublisherFarme2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 978594624553896019L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JList<Publisher> listView;
	private JTextArea taResult;
	private JLabel lblPublisher_ID;
	private JLabel lblPublisher_Name;
	private JLabel lblContact;
	private JLabel lblPhone;
	private JButton btUpdate;
	private JButton btDelete;
	private JLabel lblShowPublisher_ID;
	private JTextField tfPublisher_Name;
	private JTextField tfContact;
	private JTextField tfPhone;
	private List<Publisher> publishers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublisherFarme2 frame = new PublisherFarme2();
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
	public PublisherFarme2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 10, 360, 280);
		contentPane.add(scrollPane);
		
		listView = new JList<Publisher>();
		listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listView.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = listView.getSelectedIndex();
				// 內容重設時(Search 時)會導致 JList.getSelectedIndex() 回傳 -1
				if (index < 0)
					// Block 直接結束
					return;
				
				Publisher publisher = publishers.get(index);

				lblShowPublisher_ID.setText(publisher.getPublisher_id());
				tfPublisher_Name.setText(publisher.getPublisher_name());
				tfPhone.setText(publisher.getPhone());
				tfContact.setText(publisher.getContact());
				
				
			}
		});
		scrollPane.setViewportView(listView);
		showall();
		
		taResult = new JTextArea();
		taResult.setBounds(50, 300, 360, 50);
		contentPane.add(taResult);
		
		lblPublisher_ID = new JLabel("Publisher ID");
		lblPublisher_ID.setBounds(50, 360, 140, 15);
		contentPane.add(lblPublisher_ID);
		
		lblPublisher_Name = new JLabel("Publisher Name");
		lblPublisher_Name.setBounds(50, 385, 140, 15);
		contentPane.add(lblPublisher_Name);
		
		lblContact = new JLabel("Contact");
		lblContact.setBounds(50, 410, 140, 15);
		contentPane.add(lblContact);
		
		lblPhone = new JLabel("Phone");
		lblPhone.setBounds(50, 435, 140, 15);
		contentPane.add(lblPhone);
		
		btUpdate = new JButton("Update");
		btUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Common common = new Common();
				common.JDBCRegistered();
				
				String sql = "UPDATE PUBLISHER SET PUBLISHER_NAME = ?,"
						+ "CONTACT = ?,"
						+ "PHONE = ? "
						+ "WHERE PUBLISHER_ID = ?";
				
				try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
						PreparedStatement ps = connection.prepareStatement(sql);) {
					ps.setString(1, tfPublisher_Name.getText());
					ps.setString(2, tfContact.getText());
					ps.setString(3, tfPhone.getText());
					ps.setString(4, lblShowPublisher_ID.getText());
					
					int rows = ps.executeUpdate();
					String text = String.format("%s %s %n%s %d", lblShowPublisher_ID.getText(), "updated.", "Rows impacted : ", rows);
					taResult.setText(text);
					showall();
					
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
		btUpdate.setBounds(53, 529, 87, 23);
		contentPane.add(btUpdate);
		
		btDelete = new JButton("Delete");
		btDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Common common = new Common();
				common.JDBCRegistered();
				
				String sql = "DELETE FROM PUBLISHER "
						+ "WHERE PUBLISHER_ID = ?";
						
				
				try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
						PreparedStatement ps = connection.prepareStatement(sql);) {
					ps.setString(1, lblShowPublisher_ID.getText());
					
					int rows = ps.executeUpdate();
					String text = String.format("%s %s %n%s %d", lblShowPublisher_ID.getText(), "delete.", "Rows impacted : ", rows);
					taResult.setText(text);
					showall();
					
				} catch (SQLException sqle2) {
					// TODO: handle exception
					sqle2.printStackTrace();
					taResult.setText("刪除失敗!");
					
				}
			}
		});
		btDelete.setBounds(275, 529, 87, 23);
		contentPane.add(btDelete);
		
		lblShowPublisher_ID = new JLabel("");
		lblShowPublisher_ID.setBounds(216, 360, 96, 15);
		contentPane.add(lblShowPublisher_ID);
		
		tfPublisher_Name = new JTextField();
		tfPublisher_Name.setBounds(216, 382, 96, 21);
		contentPane.add(tfPublisher_Name);
		tfPublisher_Name.setColumns(10);
		
		tfContact = new JTextField();
		tfContact.setBounds(216, 407, 96, 21);
		contentPane.add(tfContact);
		tfContact.setColumns(10);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(216, 432, 96, 21);
		contentPane.add(tfPhone);
		tfPhone.setColumns(10);
	}
	
	private List<Publisher> getAll() {
		// 載入 Driver
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		
		
		List<Publisher> publishers = new ArrayList<>();
		// 開啟連線, 並建立 Statement
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			// 執行 sql 語句
			String sql = "SELECT PUBLISHER_ID, PUBLISHER_NAME, PHONE, CONTACT FROM PUBLISHER";
			ResultSet rs = stmt.executeQuery(sql);
			// 從 ResultSet 取值丟到 List 內
			while(rs.next()) {
				String publisher_id = rs.getString("PUBLISHER_ID");
				String publisher_name = rs.getString("PUBLISHER_NAME");
				String phone = rs.getString("PHONE");
				String contact = rs.getString("CONTACT");
				Publisher publisher = new Publisher(publisher_id, publisher_name, phone, contact);
				publishers.add(publisher);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return publishers;
		}
	
	private void showall() {
		publishers = getAll();
		if (publishers.size() > 0 && publishers != null)
			listView.setListData(publishers.toArray(new Publisher[publishers.size()]));
	}
	
//	private List<Publisher> getSearched(String text) {
//		List<Publisher> searchedPublishers = new ArrayList<>();
//		for (Publisher publisher : searchedPublishers) {
//			if (publisher.getPublisher_id().toUpperCase().contains(text.toUpperCase()));
//				searchedPublishers.add(publisher);
//		}
//		return searchedPublishers;
//	}
	
}
