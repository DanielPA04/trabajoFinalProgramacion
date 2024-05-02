package com.hibernate.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hibernate.dao.AlbumDAO;
import com.hibernate.dao.ArtistaDAO;
import com.hibernate.model.Album;
import com.hibernate.model.Artista;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JTextArea;

public class App {

	private JFrame frame;
	// Artista
	int codArtistaSelec;
	DefaultTableModel modelArtista;
	JTable tableArtista;
	Artista artista;
	List<Artista> artistas;
	ArtistaDAO artistaDAO = new ArtistaDAO();
	JScrollPane scrollPaneArtista;
	Blob fotoArtista = null;
	private JTextField txtFechanacArtista;
	private JTextField txtNombreArtista;
	private JTextField txtCodigoArtista;
	private JTextField txtImagenArtista;
	JLabel lblFotoArtista;
	// Album
	DefaultTableModel modelAlbum;
	JTable tableAlbum;
	Album album;
	List<Album> albums;
	AlbumDAO albumDAO = new AlbumDAO();
	JScrollPane scrollPaneAlbum;
	Blob fotoAlbum = null;
	private JTextField txtCodigoAlbum;
	private JTextField txtNombreAlbum;
	private JTextField txtFechaAlbum;
	private JTextField txtGenerosAlbum;
	private JTextField txtDiscograficaAlbum;
	private JTextField txtArtistaAlbum;
	private JTextField txtImagenAlbum;
	private JTextArea txtrDescripcionAlbum;
	private JLabel lblFotoAlbum;

	public void loadData() {
		artistas = artistaDAO.selectAllArtistas();
		modelArtista.setRowCount(0);
		artistas.forEach(s -> {
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
			modelArtista.addRow(row);
		});

	}

	public void loadAlbumes(List<Album> albums) {
		modelAlbum.setRowCount(0);
		albums.forEach(a -> {
			Object[] row = new Object[6];
			row[0] = a.getCod();
			row[1] = a.getNombre();
			String fecha;
			try {
				fecha = App.cambiarFormatoFechaH(a.getFecha());
			} catch (NullPointerException e) {
				fecha = null;
			}
			row[2] = fecha;
			row[3] = a.getGeneros();
			row[4] = a.getDiscografica();
			row[5] = a.getArtista();

			modelAlbum.addRow(row);

		});
	}

	public void clearAllData() {
		// Artista
		txtFechanacArtista.setText(null);
		txtNombreArtista.setText(null);
		txtCodigoArtista.setText(null);
		txtImagenArtista.setText(null);
		lblFotoArtista.setText(null);
		lblFotoArtista.setIcon(null);
		// Album
		clearAlbumData();
		txtArtistaAlbum.setText(null);
	}

	public void clearAlbumData() {
		txtCodigoAlbum.setText(null);
		txtNombreAlbum.setText(null);
		txtFechaAlbum.setText(null);
		txtGenerosAlbum.setText(null);
		txtDiscograficaAlbum.setText(null);
		txtImagenAlbum.setText(null);
		txtrDescripcionAlbum.setText(null);
		lblFotoAlbum.setText(null);
		lblFotoAlbum.setIcon(null);
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
		frame.setBounds(100, 100, 924, 607);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		modelArtista = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableArtista = new JTable(modelArtista);

		modelArtista.addColumn("Codigo");
		modelArtista.addColumn("Nombre");
		modelArtista.addColumn("Fecha de nacimiento");

		artistas = artistaDAO.selectAllArtistas();

		artistas.forEach(s -> {
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

			modelArtista.addRow(row);
		});

		frame.getContentPane().setLayout(null);
		tableArtista.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableArtista.setBounds(0, 0, 50, 50);
		scrollPaneArtista = new JScrollPane(tableArtista);
		scrollPaneArtista.setBounds(12, 0, 308, 189);
		frame.getContentPane().add(scrollPaneArtista);

		JLabel lblCodigoArtista = new JLabel("Codigo: ");
		lblCodigoArtista.setBounds(12, 201, 139, 15);
		frame.getContentPane().add(lblCodigoArtista);

		JLabel lblNombreArtista = new JLabel("Nombre: ");
		lblNombreArtista.setBounds(12, 228, 139, 15);
		frame.getContentPane().add(lblNombreArtista);

		JLabel lblFechaNacimientoArtista = new JLabel("Fecha Nacimiento:");
		lblFechaNacimientoArtista.setBounds(12, 255, 139, 15);
		frame.getContentPane().add(lblFechaNacimientoArtista);

		txtFechanacArtista = new JTextField();
		txtFechanacArtista.setBounds(169, 253, 114, 19);
		frame.getContentPane().add(txtFechanacArtista);
		txtFechanacArtista.setColumns(10);

		txtNombreArtista = new JTextField();
		txtNombreArtista.setBounds(169, 226, 114, 19);
		frame.getContentPane().add(txtNombreArtista);
		txtNombreArtista.setColumns(10);

		txtCodigoArtista = new JTextField();
		txtCodigoArtista.setEditable(false);
		txtCodigoArtista.setBounds(169, 199, 114, 19);
		frame.getContentPane().add(txtCodigoArtista);
		txtCodigoArtista.setColumns(10);

		txtImagenArtista = new JTextField();
		txtImagenArtista.setEnabled(false);
		txtImagenArtista.setBounds(12, 282, 139, 19);
		frame.getContentPane().add(txtImagenArtista);
		txtImagenArtista.setColumns(10);

		JButton btnElegirFotoArtista = new JButton("Elegir foto");
		btnElegirFotoArtista.setBounds(169, 279, 117, 25);
		frame.getContentPane().add(btnElegirFotoArtista);

		JButton btnCrearArtista = new JButton("Crear");
		btnCrearArtista.setBounds(97, 313, 117, 25);
		frame.getContentPane().add(btnCrearArtista);

		JButton btnEditarArtista = new JButton("Editar");
		btnEditarArtista.setBounds(97, 350, 117, 25);
		frame.getContentPane().add(btnEditarArtista);

		JButton btnEliminarArtista = new JButton("Eliminar");
		btnEliminarArtista.setBounds(97, 387, 117, 25);
		frame.getContentPane().add(btnEliminarArtista);

		lblFotoArtista = new JLabel("");
		lblFotoArtista.setBounds(320, 0, 139, 189);
		frame.getContentPane().add(lblFotoArtista);
		// Botones Artista
		// Crear
		btnCrearArtista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = txtNombreArtista.getText();
				String fechaNacS = txtFechanacArtista.getText();
				String codS = txtCodigoArtista.getText();

				if (nombre == null || nombre.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nombre vacío");
					txtNombreArtista.requestFocus();
					return;
				}

				if (fechaNacS == null || fechaNacS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fecha de nacimiento vacía");
					txtFechanacArtista.requestFocus();
					return;
				}

				LocalDate fechaNac = null;
				try {
					fechaNac = App.cambiarFormatoFechaJavaNat(fechaNacS);
				} catch (DateTimeParseException e) {
					JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto dd/MM/yyyy");
					return;
				}

				artista = new Artista(nombre, fechaNac, fotoArtista);
				artistaDAO.insertArtista(artista);
				artista = null;

				if (codS == null || codS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
				} else {
					JOptionPane.showMessageDialog(null,
							"Usuario creado correctamente, pero el id asignado es aleatorio aún que haya seleccionado uno");
				}

				loadData();
				clearAllData();

			}
		});
		// Editar
		btnEditarArtista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtCodigoArtista.getText().isEmpty()) {
					int cod = Integer.valueOf(txtCodigoArtista.getText());
					artista = artistaDAO.selectArtistaById(cod);

					String nombre = txtNombreArtista.getText();
					String fechaNacS = txtFechanacArtista.getText();

					if (nombre == null || nombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Nombre vacío");
						txtNombreArtista.requestFocus();
						return;
					}

					if (fechaNacS == null || fechaNacS.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Fecha de nacimiento vacía");
						txtFechanacArtista.requestFocus();
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
					artista.setImagen(fotoArtista);
					artistaDAO.updateArtista(artista);
					artista = null;
					loadData();
					clearAllData();

				} else {
					JOptionPane.showMessageDialog(null, "Ningun artista seleccionado, realice un clic en la tabla");
				}
			}

		});
		// Eliminar
		btnEliminarArtista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!txtCodigoArtista.getText().isEmpty()) {

					if (JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminarla?", "Eliminar",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						int cod = Integer.valueOf(txtCodigoArtista.getText());
						artistaDAO.deleteArtista(cod);
						JOptionPane.showMessageDialog(null, "Artista eliminado/a");
						loadData();
						clearAllData();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ningun artista seleccionado, realice un clic en la tabla");
				}

			}

		});
		// Elegir foto
		btnElegirFotoArtista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();
					String fileName = f.getAbsolutePath();
					txtImagenArtista.setText(fileName);

					try {
						fotoArtista = fileToBlob(f);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					BufferedImage img = ImageIO.read(f);
					Image dimg = img.getScaledInstance(lblFotoArtista.getWidth(), lblFotoArtista.getHeight(),
							Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(dimg);
					lblFotoArtista.setIcon(icon);
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// Tabla Artista
		tableArtista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableArtista.getSelectedRow();
				TableModel modelArtista = tableArtista.getModel();
				codArtistaSelec = (int) modelArtista.getValueAt(index, 0);

				txtCodigoArtista.setText(String.valueOf(codArtistaSelec));
				txtNombreArtista.setText(modelArtista.getValueAt(index, 1).toString());
				txtFechanacArtista.setText(modelArtista.getValueAt(index, 2).toString());

				artista = artistaDAO.selectArtistaById(codArtistaSelec);

				InputStream in;
				try {
					in = artista.getImagen().getBinaryStream();
					BufferedImage img = ImageIO.read(in);
					Image dimg = img.getScaledInstance(lblFotoArtista.getWidth(), lblFotoArtista.getHeight(),
							Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(dimg);
					lblFotoArtista.setIcon(icon);
					lblFotoArtista.setText(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NullPointerException e2) {
					lblFotoArtista.setIcon(null);
					lblFotoArtista.setText("No foto");
				}

				albums = albumDAO.selectAllAlbumsByArtista(codArtistaSelec);
				loadAlbumes(albums);
				txtArtistaAlbum.setText(String.valueOf(codArtistaSelec));

			}
		});

		// Album
		modelAlbum = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableAlbum = new JTable(modelAlbum);

		modelAlbum.addColumn("Codigo");
		modelAlbum.addColumn("Nombre");
		modelAlbum.addColumn("Fecha");
		modelAlbum.addColumn("Generos");
		modelAlbum.addColumn("Discografica");
		modelAlbum.addColumn("Artista");

		frame.getContentPane().setLayout(null);
		tableAlbum.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableAlbum.setBounds(0, 0, 50, 50);
		scrollPaneAlbum = new JScrollPane(tableAlbum);
		scrollPaneAlbum.setBounds(616, 0, 308, 189);
		frame.getContentPane().add(scrollPaneAlbum);

		txtCodigoAlbum = new JTextField();
		txtCodigoAlbum.setEditable(false);
		txtCodigoAlbum.setBounds(798, 199, 114, 19);
		frame.getContentPane().add(txtCodigoAlbum);
		txtCodigoAlbum.setColumns(10);

		txtNombreAlbum = new JTextField();
		txtNombreAlbum.setBounds(798, 226, 114, 19);
		frame.getContentPane().add(txtNombreAlbum);
		txtNombreAlbum.setColumns(10);

		txtFechaAlbum = new JTextField();
		txtFechaAlbum.setBounds(798, 253, 114, 19);
		frame.getContentPane().add(txtFechaAlbum);
		txtFechaAlbum.setColumns(10);

		lblFotoAlbum = new JLabel("");
		lblFotoAlbum.setBounds(476, 1, 139, 189);
		frame.getContentPane().add(lblFotoAlbum);

		txtGenerosAlbum = new JTextField();
		txtGenerosAlbum.setBounds(798, 282, 114, 19);
		frame.getContentPane().add(txtGenerosAlbum);
		txtGenerosAlbum.setColumns(10);

		txtDiscograficaAlbum = new JTextField();
		txtDiscograficaAlbum.setBounds(798, 316, 114, 19);
		frame.getContentPane().add(txtDiscograficaAlbum);
		txtDiscograficaAlbum.setColumns(10);

		txtArtistaAlbum = new JTextField();
		txtArtistaAlbum.setEditable(false);
		txtArtistaAlbum.setBounds(798, 353, 114, 19);
		frame.getContentPane().add(txtArtistaAlbum);
		txtArtistaAlbum.setColumns(10);

		JLabel lblCodigoAlbum = new JLabel("Codigo:");
		lblCodigoAlbum.setBounds(680, 201, 100, 15);
		frame.getContentPane().add(lblCodigoAlbum);

		JLabel lblNombreAlbum = new JLabel("Nombre:");
		lblNombreAlbum.setBounds(680, 228, 100, 15);
		frame.getContentPane().add(lblNombreAlbum);

		JLabel lblFechaAlbum = new JLabel("Fecha:");
		lblFechaAlbum.setBounds(680, 255, 100, 15);
		frame.getContentPane().add(lblFechaAlbum);

		JLabel lblGenerosAlbum = new JLabel("Generos");
		lblGenerosAlbum.setBounds(680, 284, 100, 15);
		frame.getContentPane().add(lblGenerosAlbum);

		JLabel lblDiscograficaAlbum = new JLabel("Discografica:");
		lblDiscograficaAlbum.setBounds(680, 318, 100, 15);
		frame.getContentPane().add(lblDiscograficaAlbum);

		JLabel lblArtistaAlbum = new JLabel("Artista:");
		lblArtistaAlbum.setBounds(680, 355, 100, 15);
		frame.getContentPane().add(lblArtistaAlbum);

		JButton btnElegirFotoAlbum = new JButton("Elegir foto");
		btnElegirFotoAlbum.setBounds(798, 387, 117, 25);
		frame.getContentPane().add(btnElegirFotoAlbum);

		txtImagenAlbum = new JTextField();
		txtImagenAlbum.setEditable(false);
		txtImagenAlbum.setBounds(666, 390, 114, 19);
		frame.getContentPane().add(txtImagenAlbum);
		txtImagenAlbum.setColumns(10);

		JButton btnCrearAlbum = new JButton("Crear");
		btnCrearAlbum.setBounds(740, 424, 117, 25);
		frame.getContentPane().add(btnCrearAlbum);

		JButton btnEditarAlbum = new JButton("Editar");
		btnEditarAlbum.setBounds(740, 461, 117, 25);
		frame.getContentPane().add(btnEditarAlbum);

		JButton btnEliminarAlbum = new JButton("Eliminar");
		btnEliminarAlbum.setBounds(740, 498, 117, 25);
		frame.getContentPane().add(btnEliminarAlbum);

		JScrollPane scrollPaneDescripcionAlbum = new JScrollPane();
		scrollPaneDescripcionAlbum.setBounds(476, 387, 174, 171);
		frame.getContentPane().add(scrollPaneDescripcionAlbum);

		txtrDescripcionAlbum = new JTextArea();
		scrollPaneDescripcionAlbum.setViewportView(txtrDescripcionAlbum);

		JLabel lblDescripcionAlbum = new JLabel("Descripcion:");
		lblDescripcionAlbum.setBounds(476, 368, 100, 15);
		frame.getContentPane().add(lblDescripcionAlbum);
		// Crear
		btnCrearAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String artistaS = txtArtistaAlbum.getText();
				String nombre = txtNombreAlbum.getText();
				String fechaS = txtFechaAlbum.getText();
				String generos = txtGenerosAlbum.getText();
				String discografica = txtDiscograficaAlbum.getText();
				String descripcion = txtrDescripcionAlbum.getText();

				if (artistaS == null || artistaS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Artista no seleccionado, realice un clic en la tabla de artistas");
					txtArtistaAlbum.requestFocus();
					return;
				}
				if (nombre == null || nombre.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nombre vacío");
					txtNombreAlbum.requestFocus();
					return;
				}
				if (fechaS == null || fechaS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fecha vacía");
					txtFechaAlbum.requestFocus();
					return;
				}
				if (generos == null || generos.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Generos vacíos");
					txtGenerosAlbum.requestFocus();
					return;
				}
				if (discografica == null || discografica.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Discografica vacía");
					txtDiscograficaAlbum.requestFocus();
					return;
				}
				if (descripcion == null || descripcion.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Discografica vacía");
					txtrDescripcionAlbum.requestFocus();
					return;
				}
				String codS = txtCodigoAlbum.getText();
				int artista = Integer.valueOf(artistaS);
				LocalDate fecha = null;
				try {
					fecha = App.cambiarFormatoFechaJavaNat(fechaS);
				} catch (DateTimeParseException e) {
					JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto dd/MM/yyyy");
					return;
				}

				album = new Album(nombre, fecha, generos, descripcion, discografica, fotoAlbum, artista);
				albumDAO.insertAlbum(album);
				album = null;

				albums = albumDAO.selectAllAlbumsByArtista(Integer.valueOf(txtCodigoArtista.getText()));
				loadAlbumes(albums);
				txtArtistaAlbum.setText(String.valueOf(txtCodigoArtista.getText()));

				clearAlbumData();

				if (codS == null || codS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
				} else {
					JOptionPane.showMessageDialog(null,
							"Usuario creado correctamente, pero el id asignado es aleatorio aún que haya seleccionado uno");
				}

			}
		});
		// Editar
		btnEditarAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtCodigoAlbum.getText().isEmpty()) {
					String nombre = txtNombreAlbum.getText();
					String fechaS = txtFechaAlbum.getText();
					String generos = txtGenerosAlbum.getText();
					String discografica = txtDiscograficaAlbum.getText();
					String descripcion = txtrDescripcionAlbum.getText();
					int cod = Integer.valueOf(txtCodigoAlbum.getText());
					album = albumDAO.selectAlbumById(cod);

					if (nombre == null || nombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Nombre vacío");
						txtNombreAlbum.requestFocus();
						return;
					}
					if (fechaS == null || fechaS.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Fecha vacía");
						txtFechaAlbum.requestFocus();
						return;
					}
					if (generos == null || generos.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Generos vacíos");
						txtGenerosAlbum.requestFocus();
						return;
					}
					if (discografica == null || discografica.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Discografica vacía");
						txtDiscograficaAlbum.requestFocus();
						return;
					}
					if (descripcion == null || descripcion.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Discografica vacía");
						txtrDescripcionAlbum.requestFocus();
						return;
					}
					LocalDate fecha = null;
					try {
						fecha = App.cambiarFormatoFechaJavaNat(fechaS);
					} catch (DateTimeParseException e) {
						JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto dd/MM/yyyy");
						return;
					}

					album.setNombre(nombre);
					album.setFecha(fecha);
					album.setGeneros(generos);
					album.setDiscografica(discografica);
					album.setDescripcion(descripcion);
					album.setImagen(fotoAlbum);
					albumDAO.updateAlbum(album);
					album = null;

					albums = albumDAO.selectAllAlbumsByArtista(Integer.valueOf(txtCodigoArtista.getText()));
					loadAlbumes(albums);
					txtArtistaAlbum.setText(String.valueOf(txtCodigoArtista.getText()));

					clearAlbumData();

				} else {
					JOptionPane.showMessageDialog(null, "Ningun album seleccionado, realice un clic en la tabla");
				}

			}
		});
		// Eliminar
		btnEliminarAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!txtCodigoAlbum.getText().isEmpty()) {

					if (JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminarlo?", "Eliminar",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						int cod = Integer.valueOf(txtCodigoAlbum.getText());
						albumDAO.deleteAlbum(cod);
						JOptionPane.showMessageDialog(null, "Album eliminado/a");

						albums = albumDAO.selectAllAlbumsByArtista(Integer.valueOf(txtCodigoArtista.getText()));
						loadAlbumes(albums);
						txtArtistaAlbum.setText(String.valueOf(txtCodigoArtista.getText()));

						clearAlbumData();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ninguna album seleccionado, realice un clic en la tabla");
				}

			}
		});
		// Elegir foto
		btnElegirFotoAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String artistaS = txtArtistaAlbum.getText();

				if (artistaS == null || artistaS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Album no seleccionado, realice un clic en la tabla de artistas");
					txtArtistaAlbum.requestFocus();
					return;
				}

				try {

					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();
					String fileName = f.getAbsolutePath();
					txtImagenAlbum.setText(fileName);
					try {
						fotoAlbum = fileToBlob(f);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					BufferedImage img = ImageIO.read(f);
					Image dimg = img.getScaledInstance(lblFotoAlbum.getWidth(), lblFotoAlbum.getHeight(),
							Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(dimg);
					lblFotoAlbum.setIcon(icon);
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		// Tabla
		tableAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableAlbum.getSelectedRow();
				TableModel modelArtista = tableArtista.getModel();
				TableModel modelAlbum = tableAlbum.getModel();

				txtCodigoAlbum.setText(modelAlbum.getValueAt(index, 0).toString());
				txtNombreAlbum.setText(modelAlbum.getValueAt(index, 1).toString());
				txtFechaAlbum.setText(modelAlbum.getValueAt(index, 2).toString());
				txtGenerosAlbum.setText(modelAlbum.getValueAt(index, 3).toString());
				txtDiscograficaAlbum.setText(modelAlbum.getValueAt(index, 4).toString());
				txtArtistaAlbum.setText(String.valueOf(codArtistaSelec));

			}
		});

	}
}