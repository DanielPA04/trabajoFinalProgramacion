package com.hibernate.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePicker;
import com.hibernate.dao.AlbumDAO;
import com.hibernate.dao.ArtistaDAO;
import com.hibernate.dao.DiscograficaDAO;
import com.hibernate.model.Album;
import com.hibernate.model.Artista;
import com.hibernate.model.Discografica;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class App {

	JFrame frame;
	// Artista
	private int codArtistaSelec;
	private DefaultTableModel modelArtista;
	private JTable tableArtista;
	private Artista artista;
	private List<Artista> artistas;
	private List<Discografica> discograficasArtista = new ArrayList<Discografica>();;
	private ArtistaDAO artistaDAO = new ArtistaDAO();
	private JScrollPane scrollPaneArtista;
	private Blob fotoArtista = null;
	private JTextField txtNombreArtista;
	private JTextField txtCodigoArtista;
	private JTextField txtImagenArtista;
	private JTextArea txtDiscograficasArtista;
	private JLabel lblFotoArtista;
	private JComboBox comboBoxA;
	private JComboBox comboBoxQ;
	DatePicker datePickerArtista;
	// Album
	private DefaultTableModel modelAlbum;
	private JTable tableAlbum;
	private Album album;
	private List<Album> albums;
	private AlbumDAO albumDAO = new AlbumDAO();
	private JScrollPane scrollPaneAlbum;
	private Blob fotoAlbum = null;
	private JTextField txtCodigoAlbum;
	private JTextField txtNombreAlbum;
	private JTextField txtGenerosAlbum;
	private JTextField txtArtistaAlbum;
	private JTextField txtImagenAlbum;
	private JTextArea txtrDescripcionAlbum;
	private JLabel lblFotoAlbum;
	DatePicker datePickerAlbum;
	private JComboBox comboBoxDiscograficaAlbum;

	// Discografica
	int codDiscograficaSelec;
	private DefaultTableModel modelDiscografica;
	private JTable tableDiscografica;
	private Discografica discografica;
	private List<Discografica> discograficas;
	private DiscograficaDAO discograficaDAO = new DiscograficaDAO();
	private JScrollPane scrollPaneDiscografica;
	private Blob fotoDiscografica = null;
	private JTextField txtCodigoDiscografica;
	private JTextField txtNombreDiscografica;
	private JTextField txtPaisDiscografica;
	private JTextField txtImagenDiscografica;
	private JLabel lblFotoDiscografica;
	private JTextField textField;
	DatePicker datePickerDiscografica;

	public void nullFotos() {
		fotoAlbum = null;
		fotoArtista = null;
		fotoDiscografica = null;
	}

	// Metodos para cargar datos
	// Estan sobrecargados, unos es para cargar todos los datos de la tabla y otros
	// para cargar solo los de los objetos que se le pase a la funcion
	public void loadAllData() {
		loadDataArtista();
		modelAlbum.setRowCount(0);
		loadDataDiscografica();
	}

	public void loadDataArtista() {
		artistas = artistaDAO.selectAllArtistas();
		modelArtista.setRowCount(0);
		artistas.forEach(s -> {
			Object[] row = new Object[3];
			row[0] = s.getCodArt();
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

	public void loadDataArtista(List<Artista> artistas) {
		modelArtista.setRowCount(0);
		artistas.forEach(s -> {
			Object[] row = new Object[3];
			row[0] = s.getCodArt();
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

	public void loadAlbumes() {
		albums = albumDAO.selectAllAlbums();
		modelAlbum.setRowCount(0);
		albums.forEach(a -> {
			Object[] row = new Object[6];
			row[0] = a.getCodAlb();
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

	public void loadAlbumes(List<Album> albums) {
		modelAlbum.setRowCount(0);
		albums.forEach(a -> {
			Object[] row = new Object[6];
			row[0] = a.getCodAlb();
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

	public void loadDataDiscografica() {
		comboBoxA.removeAllItems();
		comboBoxDiscograficaAlbum.removeAllItems();
		discograficas = discograficaDAO.selectAllDiscograficas();
		modelDiscografica.setRowCount(0);
		discograficas.forEach(s -> {
			Object[] row = new Object[4];
			row[0] = s.getCodDis();
			row[1] = s.getNombre();
			row[2] = s.getPais();
			String fecha;
			try {
				fecha = App.cambiarFormatoFechaH(s.getFundacion());
			} catch (NullPointerException e) {
				fecha = null;
			}
			row[3] = fecha;
			modelDiscografica.addRow(row);
			comboBoxA.addItem(s.getCodDis());
			comboBoxDiscograficaAlbum.addItem(s.getCodDis());

		});

	}

	public void loadDataDiscografica(List<Discografica> discograficas) {
		comboBoxA.removeAllItems();
		modelDiscografica.setRowCount(0);
		discograficas.forEach(s -> {
			Object[] row = new Object[4];
			row[0] = s.getCodDis();
			row[1] = s.getNombre();
			String fecha;
			try {
				fecha = App.cambiarFormatoFechaH(s.getFundacion());
			} catch (NullPointerException e) {
				fecha = null;
			}
			row[2] = fecha;
			row[3] = s.getPais();

			modelDiscografica.addRow(row);
			comboBoxA.addItem(s.getCodDis());
		});

	}

	// Metodos para eliminar datos itroducidos durante el tiemp ode ejecuccion de
	// los txt, lbl, etc
	public void clearAllData() {
		// Artista
		clearArtistaData();
		// Album
		clearAlbumData();
		txtArtistaAlbum.setText(null);
		// Discografica
		clearDiscograficaData();
	}

	public void clearArtistaData() {
		datePickerArtista.setDate(null);
		txtNombreArtista.setText(null);
		txtCodigoArtista.setText(null);
		txtImagenArtista.setText(null);
		lblFotoArtista.setText(null);
		lblFotoArtista.setIcon(null);

		txtDiscograficasArtista.setText(null);
		comboBoxQ.removeAllItems();
		comboBoxA.removeAllItems();

	}

	public void clearAlbumData() {
		txtCodigoAlbum.setText(null);
		txtNombreAlbum.setText(null);
		datePickerAlbum.setDate(null);
		txtGenerosAlbum.setText(null);
//		txtDiscograficaAlbum.setText(null);
		txtImagenAlbum.setText(null);
		txtrDescripcionAlbum.setText(null);
		lblFotoAlbum.setText(null);
		lblFotoAlbum.setIcon(null);
	}

	public void clearDiscograficaData() {
		txtCodigoDiscografica.setText(null);
		txtNombreDiscografica.setText(null);
		txtPaisDiscografica.setText(null);
		datePickerDiscografica.setDate(null);
		txtImagenDiscografica.setText(null);
		lblFotoDiscografica.setText(null);
		lblFotoDiscografica.setIcon(null);
	}

	// Metodo comprobar Expresiones Regulares
	public static boolean comprobarER(String er, String cadena) {
		Pattern pat = Pattern.compile(er);

		Matcher mat = pat.matcher(cadena);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}

	// Metodo para cambiar de formato europeo al formato de java
	public static LocalDate cambiarFormatoFechaJavaNat(String fecha) {

		DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate fechaOriginal = LocalDate.parse(fecha, formatoOriginal);

		LocalDate fechaFormateada = LocalDate.parse(fechaOriginal.format(formatoDeseado));

		return fechaFormateada;
	}

	// Metodo para cambiar del formato de java al formato europeo
	public static String cambiarFormatoFechaH(LocalDate fecha) {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateada = fecha.format(pattern);
		return fechaFormateada;
	}

	// Metodo para pasar un file a un Blob
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
	
	//Metodos sobrecargados para mostrar una imagen en un tipo de objeto (lbl, jbutton)
	public void showImg(Object o, JLabel lbl) {
		InputStream in = null;
		try {
			if (o instanceof Album) {
				in = ((Album) o).getImagen().getBinaryStream();
			} else if (o instanceof Artista) {
				in = ((Artista) o).getImagen().getBinaryStream();
			} else if (o instanceof Discografica) {
				in = ((Discografica) o).getImagen().getBinaryStream();
			}
			BufferedImage img = ImageIO.read(in);
			Image dimg = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(dimg);
			lbl.setIcon(icon);
			lbl.setText(null);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e2) {
			lbl.setIcon(null);
			lbl.setText("No foto");
		}
	}

	public void showImg(Blob b, JLabel lbl) {
		try {
			InputStream in = b.getBinaryStream();
			BufferedImage img = ImageIO.read(in);
			Image dimg = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(dimg);
			lbl.setIcon(icon);
			lbl.setText(null);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e2) {
			lbl.setIcon(null);
			lbl.setText("No foto");
		}
	}

	public void showImg(File f, JButton b) {
		try {
			BufferedImage img = ImageIO.read(f);
			Image dimg = img.getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(dimg);
			b.setIcon(icon);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e2) {
			e2.printStackTrace();
		}
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
		frame.setBounds(100, 100, 2000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Menu
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnAcciones = new JMenu("Acciones");
		menuBar.add(mnAcciones);

		JMenuItem mntmLimpiar = new JMenuItem("Limpiar");
		mnAcciones.add(mntmLimpiar);

		JMenuItem mntmMostrarAlbumes = new JMenuItem("Mostrar albumes");
		mnAcciones.add(mntmMostrarAlbumes);

		mntmLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearAllData();
				loadAllData();
				nullFotos();
			}
		});
		mntmMostrarAlbumes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadAlbumes();
			}
		});

		// Artista
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
			row[0] = s.getCodArt();
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
		scrollPaneArtista.setBounds(0, 34, 308, 189);
		frame.getContentPane().add(scrollPaneArtista);

		JLabel label = new JLabel("New label");
		scrollPaneArtista.setColumnHeaderView(label);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tableArtista.getColumnModel().getColumnCount(); i++) {
			tableArtista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

//		TableRowSorter<TableModel> rowSorterArtista = new TableRowSorter<TableModel>(modelArtista);
//		tableArtista.setRowSorter(rowSorterArtista);

		JLabel lblArtista = new JLabel("Artista");
		lblArtista.setBounds(134, 9, 46, 14);
		frame.getContentPane().add(lblArtista);

		JLabel lblCodigoArtista = new JLabel("Codigo: ");
		lblCodigoArtista.setBounds(12, 250, 139, 15);
		frame.getContentPane().add(lblCodigoArtista);

		JLabel lblNombreArtista = new JLabel("Nombre: ");
		lblNombreArtista.setBounds(12, 276, 139, 15);
		frame.getContentPane().add(lblNombreArtista);

		JLabel lblFechaNacimientoArtista = new JLabel("Fecha Nacimiento:");
		lblFechaNacimientoArtista.setBounds(12, 302, 139, 15);
		frame.getContentPane().add(lblFechaNacimientoArtista);

		DatePickerSettings datePickerSettingsArtista = new DatePickerSettings();
		datePickerSettingsArtista.setFormatForDatesCommonEra("dd/MM/yyyy");
		datePickerSettingsArtista.setAllowKeyboardEditing(false);
		datePickerArtista = new DatePicker(datePickerSettingsArtista);
		datePickerArtista.setBounds(161, 297, 212, 25);
		frame.getContentPane().add(datePickerArtista);

		txtNombreArtista = new JTextField();
		txtNombreArtista.setBounds(161, 273, 114, 19);
		frame.getContentPane().add(txtNombreArtista);
		txtNombreArtista.setColumns(10);

		txtCodigoArtista = new JTextField();
		txtCodigoArtista.setEditable(false);
		txtCodigoArtista.setBounds(161, 247, 114, 19);
		frame.getContentPane().add(txtCodigoArtista);
		txtCodigoArtista.setColumns(10);

		txtImagenArtista = new JTextField();
		txtImagenArtista.setEditable(false);
		txtImagenArtista.setBounds(12, 328, 139, 19);
		frame.getContentPane().add(txtImagenArtista);
		txtImagenArtista.setColumns(10);

		JButton btnElegirFotoArtista = new JButton("Elegir foto");
		btnElegirFotoArtista.setBounds(161, 326, 117, 25);
		frame.getContentPane().add(btnElegirFotoArtista);

		JButton btnCrearArtista = new JButton();
		btnCrearArtista.setBounds(108, 358, 25, 25);
		frame.getContentPane().add(btnCrearArtista);
		showImg(new File("img/crear.png"), btnCrearArtista);

		JButton btnEditarArtista = new JButton();
		btnEditarArtista.setBounds(155, 358, 25, 25);
		frame.getContentPane().add(btnEditarArtista);
		showImg(new File("img/editar.png"), btnEditarArtista);

		JButton btnEliminarArtista = new JButton();
		btnEliminarArtista.setBounds(204, 358, 25, 25);
		frame.getContentPane().add(btnEliminarArtista);
		showImg(new File("img/eliminar.png"), btnEliminarArtista);

		JButton btnAnyadir = new JButton();
		btnAnyadir.setBounds(126, 424, 25, 25);
		frame.getContentPane().add(btnAnyadir);
		showImg(new File("img/anadir.png"), btnAnyadir);

		JButton btnQuitar = new JButton();
		btnQuitar.setBounds(126, 460, 25, 25);
		frame.getContentPane().add(btnQuitar);
		showImg(new File("img/boton-eliminar.png"), btnQuitar);

		comboBoxA = new JComboBox();
		comboBoxA.setBounds(12, 424, 102, 23);
		frame.getContentPane().add(comboBoxA);

		comboBoxQ = new JComboBox();
		comboBoxQ.setBounds(12, 458, 100, 25);
		frame.getContentPane().add(comboBoxQ);

		lblFotoArtista = new JLabel("");
		lblFotoArtista.setBounds(307, 34, 139, 189);
		frame.getContentPane().add(lblFotoArtista);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(161, 442, 174, 25);
		frame.getContentPane().add(scrollPane);

		txtDiscograficasArtista = new JTextArea();
		scrollPane.setViewportView(txtDiscograficasArtista);
		txtDiscograficasArtista.setEditable(false);
		txtDiscograficasArtista.setColumns(10);

		// Album
		comboBoxDiscograficaAlbum = new JComboBox();
		comboBoxDiscograficaAlbum.setBounds(798, 314, 114, 22);
		frame.getContentPane().add(comboBoxDiscograficaAlbum);

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

		for (int i = 0; i < tableAlbum.getColumnModel().getColumnCount(); i++) {
			tableAlbum.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

//		TableRowSorter<TableModel> rowSorterAlbum = new TableRowSorter<TableModel>(modelAlbum);
//		tableAlbum.setRowSorter(rowSorterAlbum);

		DatePickerSettings datePickerSettingsAlbum = new DatePickerSettings();
		datePickerSettingsAlbum.setFormatForDatesCommonEra("dd/MM/yyyy");
		datePickerSettingsAlbum.setAllowKeyboardEditing(false);
		datePickerAlbum = new DatePicker(datePickerSettingsAlbum);
		datePickerAlbum.setBounds(798, 255, 114, 19);
		frame.getContentPane().add(datePickerAlbum);

		txtCodigoAlbum = new JTextField();
		txtCodigoAlbum.setEditable(false);
		txtCodigoAlbum.setBounds(798, 199, 114, 19);
		frame.getContentPane().add(txtCodigoAlbum);
		txtCodigoAlbum.setColumns(10);

		txtNombreAlbum = new JTextField();
		txtNombreAlbum.setBounds(798, 226, 114, 19);
		frame.getContentPane().add(txtNombreAlbum);
		txtNombreAlbum.setColumns(10);

		lblFotoAlbum = new JLabel("");
		lblFotoAlbum.setBounds(476, 1, 139, 189);
		frame.getContentPane().add(lblFotoAlbum);

		txtGenerosAlbum = new JTextField();
		txtGenerosAlbum.setBounds(798, 282, 114, 19);
		frame.getContentPane().add(txtGenerosAlbum);
		txtGenerosAlbum.setColumns(10);

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

		JButton btnCrearAlbum = new JButton();
		btnCrearAlbum.setBounds(740, 424, 25, 25);
		frame.getContentPane().add(btnCrearAlbum);
		showImg(new File("img/crear.png"), btnCrearAlbum);

		JButton btnEditarAlbum = new JButton();
		btnEditarAlbum.setBounds(740, 461, 25, 25);
		frame.getContentPane().add(btnEditarAlbum);
		showImg(new File("img/editar.png"), btnEditarAlbum);

		JButton btnEliminarAlbum = new JButton();
		btnEliminarAlbum.setBounds(740, 498, 25, 25);
		frame.getContentPane().add(btnEliminarAlbum);
		showImg(new File("img/eliminar.png"), btnEliminarAlbum);

		JScrollPane scrollPaneDescripcionAlbum = new JScrollPane();
		scrollPaneDescripcionAlbum.setBounds(476, 387, 174, 171);
		frame.getContentPane().add(scrollPaneDescripcionAlbum);

		txtrDescripcionAlbum = new JTextArea();
		scrollPaneDescripcionAlbum.setViewportView(txtrDescripcionAlbum);

		JLabel lblDescripcionAlbum = new JLabel("Descripcion:");
		lblDescripcionAlbum.setBounds(476, 368, 100, 15);
		frame.getContentPane().add(lblDescripcionAlbum);

		DatePickerSettings datePickerSettingsDiscografica = new DatePickerSettings();
		datePickerSettingsDiscografica.setFormatForDatesCommonEra("dd/MM/yyyy");
		datePickerSettingsDiscografica.setAllowKeyboardEditing(false);
		datePickerDiscografica = new DatePicker(datePickerSettingsDiscografica);
		datePickerDiscografica.setBounds(1415, 303, 114, 19);
		frame.getContentPane().add(datePickerDiscografica);

		// Discografica

		modelDiscografica = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableDiscografica = new JTable(modelDiscografica);
		modelDiscografica.addColumn("Codigo");
		modelDiscografica.addColumn("Nombre");
		modelDiscografica.addColumn("Pais");
		modelDiscografica.addColumn("Fecha de fundación");

		discograficas = discograficaDAO.selectAllDiscograficas();

		discograficas.forEach(s -> {
			Object[] row = new Object[4];
			row[0] = s.getCodDis();
			row[1] = s.getNombre();
			row[2] = s.getPais();
			String fecha;
			try {
				fecha = App.cambiarFormatoFechaH(s.getFundacion());
			} catch (NullPointerException e) {
				fecha = null;
			}
			row[3] = fecha;
			comboBoxA.addItem(s.getCodDis());
			comboBoxDiscograficaAlbum.addItem(s.getCodDis());
			modelDiscografica.addRow(row);
		});

		frame.getContentPane().setLayout(null);
		tableDiscografica.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableDiscografica.setBounds(0, 0, 50, 50);
		scrollPaneDiscografica = new JScrollPane(tableDiscografica);
		scrollPaneDiscografica.setBounds(1211, 0, 318, 199);
		frame.getContentPane().add(scrollPaneDiscografica);

		for (int i = 0; i < tableDiscografica.getColumnModel().getColumnCount(); i++) {
			tableDiscografica.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

//		TableRowSorter<TableModel> rowSorterDiscografica = new TableRowSorter<TableModel>(modelDiscografica);
//		tableDiscografica.setRowSorter(rowSorterDiscografica);

		txtCodigoDiscografica = new JTextField();
		txtCodigoDiscografica.setEditable(false);
		txtCodigoDiscografica.setBounds(1415, 211, 114, 19);
		frame.getContentPane().add(txtCodigoDiscografica);
		txtCodigoDiscografica.setColumns(10);

		txtNombreDiscografica = new JTextField();
		txtNombreDiscografica.setBounds(1415, 242, 114, 19);
		frame.getContentPane().add(txtNombreDiscografica);
		txtNombreDiscografica.setColumns(10);

		txtPaisDiscografica = new JTextField();
		txtPaisDiscografica.setBounds(1415, 273, 114, 19);
		frame.getContentPane().add(txtPaisDiscografica);
		txtPaisDiscografica.setColumns(10);

		JLabel lblCodigoDiscografica = new JLabel("Codigo:");
		lblCodigoDiscografica.setBounds(1267, 211, 130, 15);
		frame.getContentPane().add(lblCodigoDiscografica);

		JLabel lblNombreDiscografica = new JLabel("Nombre");
		lblNombreDiscografica.setBounds(1267, 244, 130, 15);
		frame.getContentPane().add(lblNombreDiscografica);

		JLabel lblPaisDiscografica = new JLabel("Pais");
		lblPaisDiscografica.setBounds(1267, 275, 130, 15);
		frame.getContentPane().add(lblPaisDiscografica);

		JLabel lblFundacionDiscografica = new JLabel("Fecha Fundacion:");
		lblFundacionDiscografica.setBounds(1267, 306, 130, 15);
		frame.getContentPane().add(lblFundacionDiscografica);

		JButton btnElegirFotoDiscografica = new JButton("Elegir foto");
		btnElegirFotoDiscografica.setBounds(1415, 335, 117, 25);
		frame.getContentPane().add(btnElegirFotoDiscografica);

		txtImagenDiscografica = new JTextField();
		txtImagenDiscografica.setEditable(false);
		txtImagenDiscografica.setBounds(1267, 338, 130, 19);
		frame.getContentPane().add(txtImagenDiscografica);
		txtImagenDiscografica.setColumns(10);

		JButton btnCrearDiscografica = new JButton("");
		btnCrearDiscografica.setBounds(1347, 387, 117, 25);
		frame.getContentPane().add(btnCrearDiscografica);
		showImg(new File("img/crear.png"), btnCrearDiscografica);

		JButton btnEditarDiscografica = new JButton("");
		btnEditarDiscografica.setBounds(1347, 424, 117, 25);
		frame.getContentPane().add(btnEditarDiscografica);
		showImg(new File("img/editar.png"), btnEditarDiscografica);

		JButton btnEliminarDiscografica = new JButton("");
		btnEliminarDiscografica.setBounds(1347, 461, 117, 25);
		frame.getContentPane().add(btnEliminarDiscografica);
		showImg(new File("img/eliminar.png"), btnEliminarDiscografica);

		lblFotoDiscografica = new JLabel("");
		lblFotoDiscografica.setBounds(1068, 0, 139, 189);
		frame.getContentPane().add(lblFotoDiscografica);

		JLabel lblDiscograficasArtista = new JLabel("Discograficas: ");
		lblDiscograficasArtista.setBounds(12, 394, 117, 15);
		frame.getContentPane().add(lblDiscograficasArtista);

		;

		// Botones Artista
		// Crear Artista
		btnCrearArtista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = txtNombreArtista.getText();
				String codS = txtCodigoArtista.getText();

				if (nombre == null || nombre.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nombre vacio");
					txtNombreArtista.requestFocus();
					return;
				}

				if (!comprobarER("[A-Za-z ]{1,45}", nombre)) {
					JOptionPane.showMessageDialog(null,
							"Nombre incorrecto, solo letras, espacios y maximo 45 caracteres");
					txtNombreArtista.requestFocus();
					return;
				}

				try {
					String fechaNacS = datePickerArtista.getDate().toString();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Fecha de nacimiento vacia");
					return;
				}

				LocalDate fechaNac = datePickerArtista.getDate();

				artista = new Artista(nombre, fechaNac, fotoArtista);
				discograficasArtista.forEach(d -> {
					artista.anyadirDiscografica(d);

				});
				artistaDAO.insertArtista(artista);

				artista = null;
				discograficasArtista.clear();

				if (codS == null || codS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Artista creado correctamente");
				} else {
					JOptionPane.showMessageDialog(null,
							"Artista creado correctamente, pero el id asignado es aleatorio aún que haya seleccionado uno");
				}

				loadDataArtista();
				clearAllData();
				fotoArtista = null;
				loadDataDiscografica();

			}
		});
		// Editar Artista
		btnEditarArtista.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (!txtCodigoArtista.getText().isEmpty()) {
					int cod = Integer.valueOf(txtCodigoArtista.getText());
					artista = artistaDAO.selectArtistaById(cod);

					String nombre = txtNombreArtista.getText();

					if (nombre == null || nombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Nombre vacio", "Error Artista",
								JOptionPane.ERROR_MESSAGE);
						txtNombreArtista.requestFocus();
						return;
					}

					if (!comprobarER("[A-Za-z ]{1,45}", nombre)) {
						JOptionPane.showMessageDialog(null,
								"Nombre incorrecto, solo letras, espacios y maximo 45 caracteres");
						txtNombreArtista.requestFocus();
						return;
					}

					try {
						String fechaNacS = datePickerArtista.getDate().toString();
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(null, "Fecha de nacimiento vacia");
						return;
					}

					LocalDate fechaNac = datePickerArtista.getDate();

					artista.setNombre(nombre);
					artista.setFechaNac(fechaNac);
					if (fotoArtista != null) {
						artista.setImagen(fotoArtista);
					}
					artista.getDiscograficas().clear();
					discograficasArtista.forEach(d -> {
						artista.anyadirDiscografica(d);
					});
					artistaDAO.updateArtista(artista);

					artista = null;

					loadDataArtista();
					clearAllData();
					fotoArtista = null;
					loadDataDiscografica();

				} else {
					JOptionPane.showMessageDialog(null, "Ningun artista seleccionado, realice un clic en la tabla");
				}
			}

		});
		// Eliminar Artista
		btnEliminarArtista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!txtCodigoArtista.getText().isEmpty()) {
					if (JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminarlo?", "Eliminar",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						int cod = Integer.valueOf(txtCodigoArtista.getText());
						try {
							artistaDAO.deleteArtista(cod);
							JOptionPane.showMessageDialog(null, "Artista eliminado/a");
							loadDataArtista();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
						clearAllData();
						fotoArtista = null;
						loadDataDiscografica();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Ningun artista seleccionado, realice un clic en la tabla");
				}

			}

		});
		// Elegir foto Artista
		btnElegirFotoArtista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();

					txtImagenArtista.setText(f.getAbsolutePath());

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
		// Anyadir
		btnAnyadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int cod = Integer.valueOf(comboBoxA.getSelectedItem().toString());
					txtDiscograficasArtista.setText(txtDiscograficasArtista.getText() + " " + cod + " ");

					discografica = discograficaDAO.selectDiscograficaById(cod);
					discograficasArtista.add(discografica);
					comboBoxA.removeItem(cod);
					comboBoxQ.addItem(cod);
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Ninguna discografica que anyadir");
				}

			}
		});
		// Quitar
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int cod = Integer.valueOf(comboBoxQ.getSelectedItem().toString());
					txtDiscograficasArtista
							.setText(txtDiscograficasArtista.getText().replace((" " + String.valueOf(cod) + " "), ""));

					discografica = discograficaDAO.selectDiscograficaById(cod);

					discograficasArtista.removeIf(d -> String.valueOf(d.getCodDis()).equals(String.valueOf(cod)));

					comboBoxQ.removeItem(cod);
					comboBoxA.addItem(cod);
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Ninguna discografica que quitar");
				}

			}
		});

		// Crear Album
		btnCrearAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String artistaS = txtArtistaAlbum.getText();
				String nombre = txtNombreAlbum.getText();
				String generos = txtGenerosAlbum.getText();
				String discografica = comboBoxDiscograficaAlbum.getSelectedItem().toString();
				String descripcion = txtrDescripcionAlbum.getText();

				if (artistaS == null || artistaS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Artista no seleccionado, realice un clic en la tabla de artistas");
					txtArtistaAlbum.requestFocus();
					return;
				}

				if (nombre == null || nombre.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nombre vacio");
					txtNombreAlbum.requestFocus();
					return;
				}

				if (!comprobarER("[A-Za-z ]{1,45}", nombre)) {
					JOptionPane.showMessageDialog(null,
							"Nombre incorrecto, solo letras, espacios y maximo 45 caracteres");
					txtNombreAlbum.requestFocus();
					return;
				}

				try {
					String fechaS = datePickerAlbum.getDate().toString();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Fecha vacia");
					return;
				}
				if (generos == null || generos.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Generos vacios");
					txtGenerosAlbum.requestFocus();
					return;
				}
				if (!comprobarER("[a-zA-Z, ]{1,100}", generos)) {
					JOptionPane.showMessageDialog(null,
							"Generos incorrectos, solo letras, espacios, comas y maximo 100 caracteres");
					txtGenerosAlbum.requestFocus();
					return;
				}

				if (descripcion == null || descripcion.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Descripcion vacia");
					txtrDescripcionAlbum.requestFocus();
					return;
				}
				if (!comprobarER("[a-zA-Z\\-!\"#$%&'()*+,./:;?@ ]{1,500}", descripcion)) {
					JOptionPane.showMessageDialog(null,
							"Descripcion incorrecta, solo letras, espacios, puntos, enters, comas y maximo 500 caracteres");
					txtrDescripcionAlbum.requestFocus();
					return;
				}
				String codS = txtCodigoAlbum.getText();
				int artista = Integer.valueOf(artistaS);
				LocalDate fecha = datePickerAlbum.getDate();

				album = new Album(nombre, fecha, generos, descripcion, discografica, fotoAlbum, artista);
				albumDAO.insertAlbum(album);
				album = null;

				albums = albumDAO.selectAllAlbumsByArtista(Integer.valueOf(txtCodigoArtista.getText()));
				loadAlbumes(albums);
				txtArtistaAlbum.setText(String.valueOf(txtCodigoArtista.getText()));

				clearAlbumData();
				fotoAlbum = null;

				if (codS == null || codS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Album creado correctamente");
				} else {
					JOptionPane.showMessageDialog(null,
							"Album creado correctamente, pero el id asignado es aleatorio aún que haya seleccionado uno");
				}
			}

		});
		// Editar Album
		btnEditarAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtArtistaAlbum.getText() == null || txtArtistaAlbum.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Artista no seleccionado, realice un clic en la tabla de artistas");
					txtArtistaAlbum.requestFocus();
					return;
				}
				if (!txtCodigoAlbum.getText().isEmpty()) {
					String nombre = txtNombreAlbum.getText();
					String generos = txtGenerosAlbum.getText();
					String discografica = comboBoxDiscograficaAlbum.getSelectedItem().toString();
					String descripcion = txtrDescripcionAlbum.getText();
					int cod = Integer.valueOf(txtCodigoAlbum.getText());
					album = albumDAO.selectAlbumById(cod);

					if (nombre == null || nombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Nombre vacio");
						txtNombreAlbum.requestFocus();
						return;
					}
					if (!comprobarER("[A-Za-z ]{1,45}", nombre)) {
						JOptionPane.showMessageDialog(null,
								"Nombre incorrecto, solo letras, espacios y maximo 45 caracteres");
						txtNombreAlbum.requestFocus();
						return;
					}
					try {
						String fechaS = datePickerAlbum.getDate().toString();
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(null, "Fecha vacia");
						return;
					}
					if (generos == null || generos.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Generos vacios");
						txtGenerosAlbum.requestFocus();
						return;
					}
					if (!comprobarER("[A-Za-z ]{1,100}", generos)) {
						JOptionPane.showMessageDialog(null,
								"Generos incorrectos, solo letras, espacios y maximo 100 caracteres");
						txtGenerosAlbum.requestFocus();
						return;
					}

					if (descripcion == null || descripcion.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Descripción vacia");
						txtrDescripcionAlbum.requestFocus();
						return;
					}
					LocalDate fecha = datePickerAlbum.getDate();

					album.setNombre(nombre);
					album.setFecha(fecha);
					album.setGeneros(generos);
					album.setDiscografica(discografica);
					album.setDescripcion(descripcion);
					if (fotoAlbum != null) {
						album.setImagen(fotoAlbum);
					}
					albumDAO.updateAlbum(album);
					album = null;

					albums = albumDAO.selectAllAlbumsByArtista(Integer.valueOf(txtCodigoArtista.getText()));
					loadAlbumes(albums);
					txtArtistaAlbum.setText(String.valueOf(txtCodigoArtista.getText()));

					clearAlbumData();
					fotoAlbum = null;

				} else {
					JOptionPane.showMessageDialog(null, "Ningun album seleccionado, realice un clic en la tabla");
				}

			}
		});
		// Eliminar Album
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
						fotoAlbum = null;
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ninguna album seleccionado, realice un clic en la tabla");
				}

			}
		});
		// Elegir foto Album
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
					txtImagenAlbum.setText(f.getAbsolutePath());
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

		// Botones Discografica
		// Crear Discografica
		btnCrearDiscografica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = txtNombreDiscografica.getText();
				String pais = txtPaisDiscografica.getText();
				String codS = txtCodigoDiscografica.getText();

				if (nombre == null || nombre.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nombre vacio");
					txtNombreDiscografica.requestFocus();
					return;
				}

				if (!comprobarER("[A-Za-z ]{1,45}", nombre)) {
					JOptionPane.showMessageDialog(null,
							"Nombre incorrecto, solo letras, espacios y maximo 45 caracteres");
					txtNombreDiscografica.requestFocus();
					return;
				}

				if (pais == null || pais.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Pais vacio");
					txtPaisDiscografica.requestFocus();
					return;
				}
				if (!comprobarER("[A-Za-z ]{1,45}", pais)) {
					JOptionPane.showMessageDialog(null,
							"Pais incorrecto, solo letras, espacios y maximo 45 caracteres");
					txtNombreDiscografica.requestFocus();
					return;
				}
				try {
					String fechaFunS = datePickerDiscografica.getDate().toString();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Fecha de fundacion vacia");
					return;
				}

				LocalDate fechaFun = datePickerDiscografica.getDate();

				discografica = new Discografica(nombre, pais, fechaFun, fotoDiscografica);
				discograficaDAO.insertDiscografica(discografica);
				discografica = null;
				fotoDiscografica = null;

				if (codS == null || codS.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Discografica creada correctamente");
				} else {
					JOptionPane.showMessageDialog(null,
							"Discografica creada correctamente, pero el id asignado es aleatorio aún que haya seleccionado uno");
				}

				loadDataDiscografica();
				clearAllData();
				fotoArtista = null;
			}
		});
		// Editar Discografica
		btnEditarDiscografica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtCodigoDiscografica.getText().isEmpty()) {
					int cod = Integer.valueOf(txtCodigoDiscografica.getText());
					discografica = discograficaDAO.selectDiscograficaById(cod);

					String nombre = txtNombreDiscografica.getText();
					String pais = txtPaisDiscografica.getText();

					if (nombre == null || nombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Nombre vacio");
						txtNombreDiscografica.requestFocus();
						return;
					}

					if (!comprobarER("[A-Za-z ]{1,45}", nombre)) {
						JOptionPane.showMessageDialog(null,
								"Nombre incorrecto, solo letras, espacios y maximo 45 caracteres");
						txtNombreDiscografica.requestFocus();
						return;
					}

					if (pais == null || pais.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Pais vacio");
						txtPaisDiscografica.requestFocus();
						return;
					}
					if (!comprobarER("[A-Za-z ]{1,45}", pais)) {
						JOptionPane.showMessageDialog(null,
								"Pais incorrecto, solo letras, espacios y maximo 45 caracteres");
						txtNombreDiscografica.requestFocus();
						return;
					}

					try {
						String fechaFunS = datePickerDiscografica.getDate().toString();
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(null, "Fecha de fundacion vacia");
						return;
					}

					LocalDate fechaFun = datePickerDiscografica.getDate();

					discografica.setNombre(nombre);
					discografica.setFundacion(fechaFun);
					if (fotoDiscografica != null) {
						discografica.setImagen(fotoDiscografica);
					}
					discograficaDAO.updateDiscografica(discografica);
					discografica = null;
					loadDataDiscografica();
					clearDiscograficaData();
					fotoDiscografica = null;

				} else {
					JOptionPane.showMessageDialog(null,
							"Ninguna discografica seleccionada, realice un clic en la tabla");
				}
			}
		});
		// Eliminar Discografica
		btnEliminarDiscografica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!txtCodigoDiscografica.getText().isEmpty()) {
					if (JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminarla?", "Eliminar",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						int cod = Integer.valueOf(txtCodigoDiscografica.getText());
						discograficaDAO.deleteDiscografica(cod);
						JOptionPane.showMessageDialog(null, "Discografica eliminada");
						loadDataDiscografica();
						clearDiscograficaData();
						fotoDiscografica = null;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Ninguna discografica seleccionada, realice un clic en la tabla");
				}

			}
		});
		// Elegir foto Discografica
		btnElegirFotoDiscografica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();
					txtImagenDiscografica.setText(f.getAbsolutePath());

					try {
						fotoDiscografica = fileToBlob(f);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					BufferedImage img = ImageIO.read(f);
					Image dimg = img.getScaledInstance(lblFotoDiscografica.getWidth(), lblFotoDiscografica.getHeight(),
							Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(dimg);
					lblFotoDiscografica.setIcon(icon);
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// Tablas
		// Tabla Artista
		tableArtista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearAlbumData();
				int index = tableArtista.getSelectedRow();
				TableModel modelArtista = tableArtista.getModel();
				codArtistaSelec = (int) modelArtista.getValueAt(index, 0);

				txtCodigoArtista.setText(String.valueOf(codArtistaSelec));
				txtNombreArtista.setText(modelArtista.getValueAt(index, 1).toString());

				datePickerArtista.setDate(cambiarFormatoFechaJavaNat(modelArtista.getValueAt(index, 2).toString()));

				artista = artistaDAO.selectArtistaById(codArtistaSelec);

//				showBlobOnLabel(artista, lblFotoArtista);

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

				loadDataDiscografica();
				discograficasArtista = artista.getDiscograficas();

				txtDiscograficasArtista.setText(null);

				comboBoxQ.removeAllItems();
				discograficasArtista.forEach(d -> {
					txtDiscograficasArtista.setText(txtDiscograficasArtista.getText() + " " + d.getCodDis() + " ");
					comboBoxA.removeItem(d.getCodDis());
					comboBoxQ.addItem(d.getCodDis());
				});

				albums = albumDAO.selectAllAlbumsByArtista(codArtistaSelec);
				loadAlbumes(albums);
				txtArtistaAlbum.setText(String.valueOf(codArtistaSelec));

			}

		});

		// Tabla Album
		tableAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableAlbum.getSelectedRow();
				TableModel modelAlbum = tableAlbum.getModel();
				int cod = Integer.valueOf(modelAlbum.getValueAt(index, 0).toString());

				txtCodigoAlbum.setText(String.valueOf(cod));
				txtNombreAlbum.setText(modelAlbum.getValueAt(index, 1).toString());
				datePickerAlbum.setDate(cambiarFormatoFechaJavaNat(modelAlbum.getValueAt(index, 2).toString()));
				txtGenerosAlbum.setText(modelAlbum.getValueAt(index, 3).toString());
				comboBoxDiscograficaAlbum.setSelectedItem(modelAlbum.getValueAt(index, 4));

				txtArtistaAlbum.setText(String.valueOf(codArtistaSelec));

				album = albumDAO.selectAlbumById(cod);

				txtrDescripcionAlbum.setText(album.getDescripcion());

				InputStream in;
				try {
					in = album.getImagen().getBinaryStream();
					BufferedImage img = ImageIO.read(in);
					Image dimg = img.getScaledInstance(lblFotoAlbum.getWidth(), lblFotoAlbum.getHeight(),
							Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(dimg);
					lblFotoAlbum.setIcon(icon);
					lblFotoAlbum.setText(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NullPointerException e2) {
					lblFotoAlbum.setIcon(null);
					lblFotoAlbum.setText("No foto");
				}

			}
		});
		// Tabla Discografica
		tableDiscografica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableDiscografica.getSelectedRow();
				TableModel modelDiscografica = tableDiscografica.getModel();
				codDiscograficaSelec = (int) modelDiscografica.getValueAt(index, 0);

				txtCodigoDiscografica.setText(String.valueOf(codDiscograficaSelec));
				txtNombreDiscografica.setText(modelDiscografica.getValueAt(index, 1).toString());
				txtPaisDiscografica.setText(modelDiscografica.getValueAt(index, 2).toString());
				datePickerDiscografica
						.setDate(cambiarFormatoFechaJavaNat(modelDiscografica.getValueAt(index, 3).toString()));
				discografica = discograficaDAO.selectDiscograficaById(codDiscograficaSelec);

				InputStream in;
				try {
					in = discografica.getImagen().getBinaryStream();
					BufferedImage img = ImageIO.read(in);
					Image dimg = img.getScaledInstance(lblFotoDiscografica.getWidth(), lblFotoDiscografica.getHeight(),
							Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(dimg);
					lblFotoDiscografica.setIcon(icon);
					lblFotoDiscografica.setText(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NullPointerException e2) {
					lblFotoDiscografica.setIcon(null);
					lblFotoDiscografica.setText("No foto");
				}

			}
		});

	}
}
