package mainPackage;

import java.sql.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterfaceDesign {

	private JFrame frame;
	private JTextField txtPname;
	private JTextField txtQuantity;
	private JTextField txtPrice;
	private JTextField txtSearchId;
	private JTextField txtSellId;
	private JTextField txtSellQuantity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceDesign window = new InterfaceDesign();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceDesign() {
		initialize();
		buildConnection();
		loadTable();
		
	}
	
	Connection con;
	PreparedStatement prestm;
	ResultSet rst;
	private JTable table_1;
	
	public void buildConnection() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/swingprojectdatabase","root","Aman100@");
			System.out.println("Done with the stable connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void loadTable() {
		
		try {
			prestm = con.prepareStatement("select * from inventorytable");
			rst = prestm.executeQuery();
			table_1.setModel(DbUtils.resultSetToTableModel(rst));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 774, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Inventory Management System");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(152, 10, 488, 85);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 116, 252, 235);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Product Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 29, 131, 32);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(10, 79, 131, 32);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price Per Item");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1_1.setBounds(10, 135, 131, 32);
		panel.add(lblNewLabel_1_1_1);
		
		txtPname = new JTextField();
		txtPname.setBounds(130, 38, 112, 23);
		panel.add(txtPname);
		txtPname.setColumns(10);
		
		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(130, 88, 112, 23);
		panel.add(txtQuantity);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(130, 144, 112, 23);
		panel.add(txtPrice);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name ,quantity,price;
				name = txtPname.getText();
				quantity = txtQuantity.getText();
				price = txtPrice.getText();
				
				try {
					prestm = con.prepareStatement("insert into inventorytable(ProductName, Quantity, PricePerItem)values(?,?,?)");
					
					prestm.setString(1, name);
					prestm.setString(2, quantity);
					prestm.setString(3, price);
					
					prestm.executeUpdate();
					JOptionPane.showMessageDialog(null, "Item Added !!");
					loadTable();
					
					txtPname.setText("");
					txtQuantity.setText("");
					txtPrice.setText("");
					
					txtPname.requestFocus();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnAddItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAddItem.setBackground(new Color(240, 240, 240));
		btnAddItem.setBounds(10, 185, 115, 29);
		panel.add(btnAddItem);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPname.setText("");
				txtQuantity.setText("");
				txtPrice.setText("");
				
				txtPname.requestFocus();
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClear.setBackground(UIManager.getColor("Button.background"));
		btnClear.setBounds(130, 185, 115, 29);
		panel.add(btnClear);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(30, 367, 252, 101);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Enter Product ID");
		lblNewLabel_1_1_1_1.setBounds(10, 30, 136, 20);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblNewLabel_1_1_1_1);
		
		txtSearchId = new JTextField();
		txtSearchId.setColumns(10);
		txtSearchId.setBounds(156, 30, 86, 23);
		panel_1.add(txtSearchId);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String searchId = txtSearchId.getText();
				
				try {
					prestm = con.prepareStatement("select * from inventorytable where ID=?");
					prestm.setString(1, searchId);
					
					rst = prestm.executeQuery();
					
					if(rst.next()) {
						txtPname.setText(rst.getString(2));
						txtQuantity.setText(rst.getString(3));
						txtPrice.setText(rst.getString(4));
						
			        }else {
			        	
			        	txtPname.setText("");
						txtQuantity.setText("");
						txtPrice.setText("");
						
						txtPname.requestFocus();	
			        }
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
				
				
				
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearch.setBackground(UIManager.getColor("Button.background"));
		btnSearch.setBounds(55, 60, 115, 31);
		panel_1.add(btnSearch);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(30, 489, 252, 88);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name ,quantity,price,id;
				name = txtPname.getText();
				quantity = txtQuantity.getText();
				price = txtPrice.getText();
				id = txtSearchId.getText();
				
				
				try {
					prestm = con.prepareStatement("update inventorytable set ProductName=?, Quantity=?, PricePerItem=? where ID=?");
					
					prestm.setString(1, name);
					prestm.setString(2, quantity);
					prestm.setString(3, price);
					prestm.setString(4, id);
					
					prestm.executeUpdate();
					JOptionPane.showMessageDialog(null, "Item Updated !!");
					
					loadTable();
					
					txtPname.setText("");
					txtQuantity.setText("");
					txtPrice.setText("");
					
					txtPname.requestFocus();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(10, 20, 89, 35);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUpdate.setBackground(UIManager.getColor("Button.background"));
		panel_2.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = txtSearchId.getText();
				try {
					prestm = con.prepareStatement("delete from inventorytable where ID=?");
					prestm.setString(1, id);
					
					prestm.executeUpdate();
					JOptionPane.showMessageDialog(null, "Item Deleted !!");
					
                    loadTable();
					
					txtPname.setText("");
					txtQuantity.setText("");
					txtPrice.setText("");
					
					txtPname.requestFocus();
					
					
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBackground(UIManager.getColor("Button.background"));
		btnDelete.setBounds(150, 20, 81, 35);
		panel_2.add(btnDelete);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnExit.setBackground(UIManager.getColor("Button.background"));
		btnExit.setBounds(635, 90, 81, 22);
		frame.getContentPane().add(btnExit);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(302, 450, 414, 127);
		frame.getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Enter Item ID");
		lblNewLabel_1_1_1_1_1.setBounds(36, 23, 136, 20);
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_4.add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1_1_1_1_1.setBounds(36, 53, 136, 20);
		panel_4.add(lblNewLabel_1_1_1_1_1_1);
		
		txtSellId = new JTextField();
		txtSellId.setColumns(10);
		txtSellId.setBounds(182, 26, 127, 19);
		panel_4.add(txtSellId);
		
		txtSellQuantity = new JTextField();
		txtSellQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String sellId = txtSellId.getText();
				String sellQuantity = txtSellQuantity.getText();
				
				try {
					prestm = con.prepareStatement("select * from inventorytable where ID=?");
					prestm.setString(1, sellId);
					
					rst = prestm.executeQuery();
					
					
						
					if(rst.next()) {
						if(Integer.parseInt(sellQuantity)<=Integer.parseInt(rst.getString(3))) {
							
							txtPname.setText(rst.getString(2));
							txtQuantity.setText(rst.getString(3));
							txtPrice.setText(rst.getString(4));
							
						}else {
							txtPname.setText("");
							txtQuantity.setText("");
							txtPrice.setText("");
							
							txtPname.requestFocus();
						}
			        }else {
			        	
			        	txtPname.setText("");
						txtQuantity.setText("");
						txtPrice.setText("");
						
						txtPname.requestFocus();	
			        }
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		txtSellQuantity.setColumns(10);
		txtSellQuantity.setBounds(182, 56, 127, 19);
		panel_4.add(txtSellQuantity);
		
		JButton btnSellItem = new JButton("Sell Item");
		btnSellItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name ,quantity,price,id;
				name = txtPname.getText();
				quantity = txtQuantity.getText();
				price = txtPrice.getText();
				id = txtSellId.getText();
				String sellQuantity = txtSellQuantity.getText();
				
				Integer diffQuantity = Integer.parseInt(quantity) - Integer.parseInt(sellQuantity);
				
				try {
					prestm = con.prepareStatement("update inventorytable set  Quantity=? where ID=?");
					
					prestm.setString(1, diffQuantity.toString());
					prestm.setString(2, id);
					
					prestm.executeUpdate();
					JOptionPane.showMessageDialog(null, "Items Sold !!");
					
					loadTable();
					
					txtPname.setText("");
					txtQuantity.setText("");
					txtPrice.setText("");
					
					txtPname.requestFocus();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnSellItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSellItem.setBackground(UIManager.getColor("Button.background"));
		btnSellItem.setBounds(107, 83, 115, 34);
		panel_4.add(btnSellItem);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(302, 116, 414, 324);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 394, 304);
		panel_3.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
	}
}
