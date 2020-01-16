package idv.peter.publishers.dao;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import idv.peter.publishers.Publisher;

public class PublisherDaoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6997510731984482783L;
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
	
	Dao<Publisher> daoPublishers = new PublisherDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublisherDaoFrame frame = new PublisherDaoFrame();
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
	public PublisherDaoFrame() {
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
		showAll();
		
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
				Publisher updatePublisher = new Publisher(
						lblShowPublisher_ID.getText(), tfPublisher_Name.getText(), tfPhone.getText(), tfContact.getText());
				
				int rows = daoPublishers.update(updatePublisher);
				if (rows < 0) {
					taResult.setText("UPDATE FAIL!!");
				} else {
					String text = String.format(
						"%s %s %n%s %d", lblShowPublisher_ID.getText(), "updated.", "Rows impacted : ", rows);
				taResult.setText(text);
				showAll();
				}
			}
		});
		btUpdate.setBounds(53, 529, 87, 23);
		contentPane.add(btUpdate);
		
		btDelete = new JButton("Delete");
		btDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Publisher deletePublisher = new Publisher(
						lblShowPublisher_ID.getText(), tfPublisher_Name.getText(), tfPhone.getText(), tfContact.getText());
				
				int rows = daoPublishers.update(deletePublisher);
				if (rows < 0) {
					taResult.setText("DELETE FAIL!!");
				} else {
					String text = String.format(
						"%s %s %n%s %d", lblShowPublisher_ID.getText(), "updated.", "Rows impacted : ", rows);
				taResult.setText(text);
				showAll();
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
	
	public void showAll() {
		publishers = daoPublishers.getAll();
		if (publishers.size() > 0 && publishers != null)
			listView.setListData(publishers.toArray(new Publisher[publishers.size()]));
	}

}
