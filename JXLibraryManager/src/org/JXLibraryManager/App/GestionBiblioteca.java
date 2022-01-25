/**
 * 	 JXLibraryManager
 * 
 *   Copyright (C) 2021  JesusXD88
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.JXLibraryManager.App;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.sqlite.SQLiteDataSource;

import de.vandermeer.asciitable.AT_Cell;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a8.A8_Grids;

/**
 * Clase que se encarga de interactuar con la base de datos, gestionando la biblioteca de libros.
 * @author jesusxd88
 *
 */
public class GestionBiblioteca {
	
	private Connection con = null; //Objeto conexion
	private Statement stmt = null; //Objeto statement
	private SQLiteDataSource ds = new SQLiteDataSource(); //Objeto SQLiteDataSource
	
	/**
	 * Constructor por defecto de GestionBiblioteca
	 */
	public GestionBiblioteca() {
		
	}
	
	/**
	 * Método para conectarse a la DB local
	 */
	public void conexionDB() {
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			ds.setUrl("jdbc:sqlite:JXLM_DB.db");
			con = ds.getConnection();
			stmt = con.createStatement();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("BD cargada con exito!");
	}
	
	
	//Apartado Gestion de Libros
	
	/**
	 * Método para insertar libros en la biblioteca
	 * @param libro Libro
	 * @return boolean
	 */
	public boolean insertarLibro(Libro libro) {
		String query = "INSERT INTO Libros VALUES (" 
			+ libro.getISBN() + ", '"
			+ libro.getNombre() + "', '"
			+ libro.getAutor() + "', '"
			+ libro.getGenero() + "', '"
			+ libro.getTematica() +"');";
		String query2 = "INSERT INTO Biblioteca VALUES ("
			+ libro.getISBN() + ", "
			+ "DATETIME('now'), "
			+ "NULL);";
		
		try {
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
		} catch (Exception e) {
			System.out.println("No se ha podido insertar el libro");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		System.out.println("Se ha insertado con exito!");
		return true;
	}
	
	/**
	 * Método para modificar los libros
	 * @param libro Libro
	 * @param ISBN El ISBN del Libro
	 * @return boolean
	 */
	public boolean modificarLibro(Libro libro, String ISBN) {
		String query = "UPDATE Libros SET"
				+ " ISBN = " + libro.getISBN()
				+ ", Nombre = '" + libro.getNombre()
				+ "', Autor = '" + libro.getAutor()
				+ "', Genero = '" + libro.getGenero()
				+ "', Tematica = '" + libro.getTematica()
				+ "' WHERE ISBN = " + ISBN + ";";	
		String query2 = "UPDATE Biblioteca SET"
				+ " ISBN = " + libro.getISBN() 
				+ " WHERE ISBN = " + ISBN + ";";
		try {
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
		} catch (Exception e) {
			System.out.println("No se ha podido modificar el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		System.out.println("Los cambios se han efectuado con exito!");
		return true;
	}
	
	/**
	 * Método para eliminar un libro de la biblioteca
	 * @param libro Libro
	 * @return boolean
	 */
	public boolean eliminarLibro(Libro libro) {
		String query = "DELETE FROM Libros WHERE ISBN = " + libro.getISBN() + ";";
		String query2 = "DELETE FROM Biblioteca WHERE ISBN = " + libro.getISBN() + ";";
		try {
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
		} catch (Exception e) {
			System.out.println("No se ha podido eliminar el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		System.out.println("El libro se ha eliminado con exito!");
		return true;
	}
	
	
	
	/**
	 * Método que devuelve todo el inventario de libros
	 * @return ArrayList de Libros
	 */
	public ArrayList<Libro> getInventarioLibros() {
		String query = "SELECT * FROM Libros";
		ResultSet result;
		boolean checker = false;
		ArrayList<Libro> lista = new ArrayList<Libro>();
		try {
			result = stmt.executeQuery(query);
			while (result.next()) {
				lista.add(new Libro(result.getString("ISBN"), result.getString("Nombre"), result.getString("Autor"), result.getString("Genero"), result.getString("Tematica")));
				checker = true;
			}
			if (checker == false) return null;
		} catch (Exception e) {
			System.out.println("No se ha encontrado el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return lista;
	}
	
	/**
	 * Método que devuelve un String con una tabla del inventario de libros.
	 * @return Tabla con el inventario.
	 */
	public String mostrarInventario() {
		ArrayList<Libro> inventario = this.getInventarioLibros();
		if (inventario == null) return null;
		AsciiTable at = new AsciiTable();
		at.getContext().setWidth(132);
		//at.getContext().setGrid(A8_Grids.lineDobuleTripple());
		at.addRule();
		at.addRow("ISBN","Nombre","Autor","Género","Temática");
		for (Libro libro : inventario) {
			at.addRule();
			AT_Cell cell = at.addRow(libro.getISBN(),libro.getNombre(),libro.getAutor(),libro.getGenero(),libro.getTematica()).getCells().get(1);
			cell.getContext().setPaddingRight(3);
		}
		at.addRule();
		
		return at.render();
	}
	
	//Devolver toda la biblioteca
	
	/**
	 * Método que devuelve todos los libros de la biblioteca en un mapa.
	 * @return TreeMap de libros - fechas.
	 */
	public TreeMap<Libro, ArrayList<String>> getBiblioteca() {
		String query = "SELECT Biblioteca.ISBN, Libros.Nombre, Libros.Autor, Libros.Genero, Libros.Tematica, Biblioteca.FechaAnadido, Biblioteca.FechaDevolucion FROM Biblioteca INNER JOIN Libros ON Biblioteca.ISBN = Libros.ISBN;";
		ResultSet result;
		boolean checker = false;
		TreeMap<Libro, ArrayList<String>> biblioteca = new TreeMap<Libro, ArrayList<String>>();
		try {
			result = stmt.executeQuery(query);
			while (result.next()) {
				ArrayList<String> lista = new ArrayList<String>();
				lista.add(result.getString("FechaAnadido"));
				if (result.getString("FechaDevolucion") != null) {
					lista.add(result.getString("FechaDevolucion"));
				} else {
					lista.add("---");
				}
				biblioteca.put(new Libro(result.getString("ISBN"), result.getString("Nombre"), result.getString("Autor"), result.getString("Genero"), result.getString("Tematica")), lista);
				checker = true;
			}
			if (checker == false) return null;
		} catch (Exception e) {
			System.out.println("No se ha encontrado el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return biblioteca;
	}
	
	/**
	 * Método que devuelve la tabla LibrosExtraidos en un Mapa
	 * @return TreeMap de Libro - String (FechaExtraccion)
	 */
	public TreeMap<Libro, String> getLibrosExtraidos() {
		String query = "SELECT LibrosExtraidos.ISBN, Libros.Nombre, Libros.Autor, Libros.Genero, Libros.Tematica, LibrosExtraidos.FechaExtraccion FROM LibrosExtraidos INNER JOIN Libros ON LibrosExtraidos.ISBN = Libros.ISBN;";
		ResultSet result;
		boolean checker = false;
		TreeMap<Libro, String> biblioteca = new TreeMap<Libro, String>();
		try {
			result = stmt.executeQuery(query);
			while (result.next()) {
				biblioteca.put(new Libro(result.getString("ISBN"), result.getString("Nombre"), result.getString("Autor"), result.getString("Genero"), result.getString("Tematica")), result.getString("FechaExtraccion"));
				checker = true;
			}
			if (checker == false) return null;
		} catch (Exception e) {
			System.out.println("No se ha encontrado el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return biblioteca;
	} 
	
	/**
	 * Método que devuelve un String con una tabla del contenido de la biblioteca.
	 * @return String con la biblioteca.
	 */
	private String mostrarBiblioteca() {
		TreeMap<Libro, ArrayList<String>> biblioteca = this.getBiblioteca();
		if (biblioteca == null) return null;
		AsciiTable at = new AsciiTable();
		at.getContext().setWidth(132);
		at.getContext().setGrid(A8_Grids.lineDobuleTripple());
		at.addRule();
		at.addRow("ISBN","Nombre","Autor","Género","Temática","Anadido","Devuelto");
		for (Entry<Libro, ArrayList<String>> entrada : biblioteca.entrySet()) {
			at.addRule();
			AT_Cell cell= at.addRow(entrada.getKey().getISBN(),entrada.getKey().getNombre(),entrada.getKey().getAutor(),entrada.getKey().getGenero(),entrada.getKey().getTematica(),entrada.getValue().get(0),entrada.getValue().get(1)).getCells().get(1);
			cell.getContext().setPaddingRight(3);
		}
		at.addRule();
		
		return at.render();
	}
	
	/**
	 * Método que genera una tabla con los libros extraidos de la biblioteca
	 * @return Tabla con libros extraidos de la biblioteca
	 */
	public String mostrarLibrosExtraidos() {
		TreeMap<Libro, String> ext = this.getLibrosExtraidos();
		if (ext == null) return null;
		AsciiTable at = new AsciiTable();
		at.getContext().setWidth(132);
		at.getContext().setGrid(A8_Grids.lineDobuleTripple());
		at.addRule();
		at.addRow("ISBN","Nombre","Autor","Género","Temática","Extraido");
		for (Entry<Libro, String> entrada : ext.entrySet()) {
			at.addRule();
			AT_Cell cell= at.addRow(entrada.getKey().getISBN(),entrada.getKey().getNombre(),entrada.getKey().getAutor(),entrada.getKey().getGenero(),entrada.getKey().getTematica(),entrada.getValue()).getCells().get(1);
			cell.getContext().setPaddingRight(3);
		}
		at.addRule();
		
		return at.render();
	}
	
	/**
	 * Método que extrae un libro de la biblioteca
	 * @param libro Libro
	 * @return boolean
	 */
	public boolean extraerLibro(Libro libro) {
		String query = "INSERT INTO LibrosExtraidos VALUES ((SELECT ISBN FROM Libros WHERE ISBN = " + libro.getISBN() + "), (SELECT FechaAnadido FROM Biblioteca WHERE ISBN = " + libro.getISBN() + "), DATETIME('now'), );";
		String query2 = "DELETE FROM Biblioteca WHERE ISBN = (SELECT ISBN FROM Libros WHERE ISBN =" + libro.getISBN() + ");";
		try {
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
		} catch (Exception e) {
			System.out.println("No se ha podido extraer el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método que permite devolver un libro a la biblioteca
	 * @param libro Libro
	 * @return boolean
	 */
	public boolean devolverLibro(Libro libro) {
		String query = "INSERT INTO Biblioteca VALUES ((SELECT ISBN FROM Libros WHERE ISBN = " + libro.getISBN() + "), (SELECT FechaInsercion FROM LibrosExtraidos WHERE ISBN = " + libro.getISBN() + "), DATETIME('now'));";
		String query2 = "DELETE FROM LibrosExtraidos WHERE ISBN = (SELECT ISBN FROM Libros WHERE ISBN = " + libro.getISBN() + ");";
		try {
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
		} catch (Exception e) {
			System.out.println("No se ha podido devolver el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método que busca un libro por ISBN y lo devuelve
	 * @param ISBN El ISBN del Libro
	 * @return Libro
	 */
	public Libro retrieveLibroPorISBN(String ISBN) {
		ResultSet result;
		String nombre = "", autor = "", genero = "", tematica = "";
		String query = "SELECT * FROM Libros WHERE Libros.ISBN=" + ISBN + ";";
		try {
			result = stmt.executeQuery(query);
			boolean empty = true;
			//Verificar si el resultado es nulo, en dicho caso devolver null
			
			while (result.next()) {
				nombre = result.getString("Nombre");
				autor = result.getString("Autor");
				genero = result.getString("Genero");
				tematica = result.getString("Tematica");
				empty = false;
			}
			if (empty) return null;
		} catch (Exception e) {
			System.out.println("No se ha encontrado el libro!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return new Libro(ISBN, nombre, autor, genero, tematica);
	}
	
	//Gestion de personas
	
	/**
	 * Método que inserta una persona en la BD
	 * @param persona
	 * @return boolean
	 */
	public boolean insertarPersona(Persona persona) {
		String nombre = persona.getNombre();
		String apellidos = persona.getApellidos();
		String query = "INSERT INTO Persona (Nombre, Apellidos) VALUES (" + nombre + ", " + apellidos + ");";
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("No se ha podido insertar la persona!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método que elimina todos los datos de la base de datos
	 * @return boolean
	 */
	public boolean truncateDB() {
		String query = "DELETE FROM Biblioteca;";
		String query2 = "DELETE FROM LibrosExtraidos;";
		String query3 = "DELETE FROM Libros;";
		try {
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
			stmt.executeUpdate(query3);
		} catch (Exception e) {
			System.out.println("No se ha podido eliminar la base de datos!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método que realiza una operación de INSERT, UPDATE o DELETE según la cadena SQL que se le pase por parámetro
	 * @param query SQL query (INSERT, UPDATE o DELETE)
	 * @return boolean
	 */
	public boolean executeUpdatSQL(String query) {
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("No se ha podido realizar la operación!");
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Método toString.
	 * Devuelve la tabla que genera el método mostrarBiblioteca.
	 */
	@Override
	public String toString() {
		return this.mostrarBiblioteca();
	}
	
}
