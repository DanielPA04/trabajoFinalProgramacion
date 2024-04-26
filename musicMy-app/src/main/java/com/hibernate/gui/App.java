package com.hibernate.gui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hibernate.dao.ArtistaDAO;
import com.hibernate.model.Artista;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Blob;
import java.awt.event.ActionEvent;

public class App {

	private JFrame frame;
	DefaultTableModel model;
	JTable table;
	Artista artista;
	List<Artista> series;
	ArtistaDAO artistaDAO = new ArtistaDAO();
	JScrollPane scrollPane;
	private JTextField txtFechanac;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private JTextField txtImagen;

	public void loadData() {

		series = artistaDAO.selectAllArtistas();
		model.setRowCount(0);

		series.forEach(s -> {
			Object[] row = new Object[3];
			row[0] = s.getCod();
			row[1] = s.getNombre();
			row[2] = s.getFechaNac();

			model.addRow(row);
		});

	}

	public void clearData() {

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
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
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Music My");
		frame.setBounds(100, 100, 755, 573);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);

		model.addColumn("Codigo");
		model.addColumn("Nombre");
		model.addColumn("Fecha de nacimiento");

		series = artistaDAO.selectAllArtistas();

		series.forEach(s -> {
			Object[] row = new Object[3];
			row[0] = s.getCod();
			row[1] = s.getNombre();
			row[2] = s.getFechaNac();

			model.addRow(row);
		});

		frame.getContentPane().setLayout(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBounds(0, 0, 50, 50);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 0, 308, 189);
		frame.getContentPane().add(scrollPane);

		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setBounds(12, 201, 139, 15);
		frame.getContentPane().add(lblCodigo);

		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(12, 228, 139, 15);
		frame.getContentPane().add(lblNombre);

		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
		lblFechaNacimiento.setBounds(12, 255, 139, 15);
		frame.getContentPane().add(lblFechaNacimiento);

		txtFechanac = new JTextField();
		txtFechanac.setBounds(169, 253, 114, 19);
		frame.getContentPane().add(txtFechanac);
		txtFechanac.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setBounds(169, 226, 114, 19);
		frame.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(169, 199, 114, 19);
		frame.getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);

		txtImagen = new JTextField();
		txtImagen.setEnabled(false);
		txtImagen.setBounds(12, 282, 139, 19);
		frame.getContentPane().add(txtImagen);
		txtImagen.setColumns(10);

		JButton btnElegirFoto = new JButton("Elegir foto");
		btnElegirFoto.setBounds(169, 279, 117, 25);
		frame.getContentPane().add(btnElegirFoto);

		JButton btnCrear = new JButton("Crear");
		btnCrear.setBounds(97, 313, 117, 25);
		frame.getContentPane().add(btnCrear);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(97, 350, 117, 25);
		frame.getContentPane().add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(97, 387, 117, 25);
		frame.getContentPane().add(btnEliminar);
		//Botones Artista
		//Crear
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		//Editar
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		// Eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		// Elegir foto
		btnElegirFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Blob foto;
					
					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();
					String fileName = f.getAbsolutePath();
					
					} catch (NullPointerException e1) {
						e1.printStackTrace();
					}
			}
		});
	}
}
