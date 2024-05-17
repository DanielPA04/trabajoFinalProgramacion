import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hibernate.gui.App;

class PruebasMusicMy {
	private static int num;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Pruebas iniciadas");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Pruebas finalizadas");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Inicio prueba numero " + num);
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Fin prueba numero " + num + "\n");
		num++;
	}

	@Test
	void testComprobarER() {
		Boolean erVal = App.comprobarER("[A-Z]+", "aaaaa");
		assertFalse(erVal);

	}

	@Test
	void testCambiarFormatoFechaJavaNat() {
		LocalDate fecha = App.cambiarFormatoFechaJavaNat("20/04/2008");
		System.out.println(fecha);
		assertEquals("2008-04-20", String.valueOf(fecha));
	}

	@Test
	void testCambiarFormatoFechaH() {
		LocalDate fecha = LocalDate.parse("2008-04-20");
		String fechaS = App.cambiarFormatoFechaH(fecha);
		System.out.println(fechaS);
		
		assertEquals("20/04/2008", fechaS);

	}

	@Test
	void testFileToBlob() {
		try {
			File f = new File("/home/a026944757g/Imágenes/Fondos de escritorio/images.png");
			Blob img = App.fileToBlob(f);
			System.out.println("Blob transformado"+img);
			assertNotEquals(img, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
