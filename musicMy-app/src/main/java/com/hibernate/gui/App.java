package com.hibernate.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hibernate.dao.ArtistaDAO;
import com.hibernate.model.Artista;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private JTextArea txtrArtista;
	Blob foto = null;

	public void loadData() {
		series = artistaDAO.selectAllArtistas();
		model.setRowCount(0);
		series.forEach(s -> {
			Object[] row = new Object[3];
			row[0] = s.getCod();
			row[1] = s.getNombre();
			String fecha;
			try {
				fecha = App.cambiarFormatoFechaH(s.getFechaNac());
			} catch (NullPointerException e) {
				fecha = null;
			}
			row[2] = fecha;
			model.addRow(row);
		});

	}

	public void clearData() {
		txtFechanac.setText(null);
		txtNombre.setText(null);
		txtCodigo.setText(null);
		txtImagen.setText(null);
		txtrArtista.setText(null);
	}

	public static LocalDate cambiarFormatoFechaJavaNat(String fecha) {

		DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate fechaOriginal = LocalDate.parse(fecha, formatoOriginal);

		LocalDate fechaFormateada = LocalDate.parse(fechaOriginal.format(formatoDeseado));

		return fechaFormateada;
	}

	public static String cambiarFormatoFechaH(LocalDate fecha) {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateada = fecha.format(pattern);
		return fechaFormateada;

	}

	public static Blob fileToBlob(File file) throws SQLException {

		byte[] bArray = new byte[1000];

		List<Byte> byteList = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(file)) {

			// Converting input file in list of bytes
			while (fis.read(bArray) > 0) {
				for (byte b : bArray)
					byteList.add(b);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Converting list of bytes into array of bytes
		// as SerialBlob class takes array of bytes
		byte[] byteArray = new byte[byteList.size()];

		for (int i = 0; i < byteList.size(); i++) {
			byteArray[i] = (byte) byteList.get(i);
		}

		return new SerialBlob(byteArray);
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
			String fecha;
			try {
				fecha = App.cambiarFormatoFechaH(s.getFechaNac());
			} catch (NullPointerException e) {
				fecha = null;
			}
			row[2] = fecha;

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
		txtCodigo.setEditable(false);
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

		txtrArtista = new JTextArea();
		txtrArtista.setBounds(332, 1, 139, 189);
		frame.getContentPane().add(txtrArtista);
		// Botones Artista
		// Crear
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = txtNombre.getText();
				String fechaNacS = txtFechanac.getText();
				String codS = txtCodigo.getText();

				if (nombre == null || nombre.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nombre vacío");
					txtNombre.requestFocus();
					return;
				}

				if (fechaNacS == null || fechaNacS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fecha de nacimiento vacía");
					txtFechanac.requestFocus();
					return;
				}

				LocalDate fechaNac = null;
				try {
					fechaNac = App.cambiarFormatoFechaJavaNat(fechaNacS);
				} catch (DateTimeParseException e) {
					JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto dd/MM/yyyy");
					return;
				}

				artista = new Artista(nombre, fechaNac, foto);
				artistaDAO.insertArtista(artista);
				artista = null;

				if (codS == null || codS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
				} else {
					JOptionPane.showMessageDialog(null,
							"Usuario creado correctamente, pero el id asignado es aleatorio aún que haya seleccionado uno");
				}

				loadData();
				clearData();

			}
		});
		// Editar
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtCodigo.getText().isEmpty()) {
					int cod = Integer.valueOf(txtCodigo.getText());
					artista = artistaDAO.selectArtistaById(cod);

					String nombre = txtNombre.getText();
					String fechaNacS = txtFechanac.getText();

					if (nombre == null || nombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Nombre vacío");
						txtNombre.requestFocus();
						return;
					}

					if (fechaNacS == null || fechaNacS.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Fecha de nacimiento vacía");
						txtFechanac.requestFocus();
						return;
					}

					LocalDate fechaNac = null;
					try {
						fechaNac = App.cambiarFormatoFechaJavaNat(fechaNacS);
					} catch (DateTimeParseException e) {
						JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto dd/MM/yyyy");
						return;
					}
					artista.setNombre(nombre);
					artista.setFechaNac(fechaNac);
					artista.setImagen(foto);
					artistaDAO.updateArtista(artista);
					
					loadData();
					clearData();

				} else {
					JOptionPane.showMessageDialog(null, "Ninguna persona seleccionada, realice un clic en la tabla");
				}
			}

		});
		// Eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!txtCodigo.getText().isEmpty()) {

					if (JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminarla?", "Eliminar",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						int cod = Integer.valueOf(txtCodigo.getText());
						artistaDAO.deleteArtista(cod);
						JOptionPane.showMessageDialog(null, "Artista eliminado/a");
						loadData();
						clearData();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ninguna persona seleccionada, realice un clic en la tabla");
				}

			}

		});
		// Elegir foto
		btnElegirFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();
					String fileName = f.getAbsolutePath();
					
					try {
						foto=fileToBlob(f);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} catch (NullPointerException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Tablas
		// Artista
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel model = table.getModel();

				txtCodigo.setText(model.getValueAt(index, 0).toString());
				txtNombre.setText(model.getValueAt(index, 1).toString());
				txtFechanac.setText(model.getValueAt(index, 2).toString());

			}
		});
	}
}
