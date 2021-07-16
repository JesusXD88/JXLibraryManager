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

/**
 * Clase que se encarga de interactuar con la base de datos, gestionando la biblioteca de libros.
 * @author jesusxd88
 *
 */
public class GestionBiblioteca {
	
	private Connection con = null; //Objeto conexion
	private Statement stmt = null; //Objeto statement
	
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
			con = DriverManager.getConnection("jdbc:sqlite:db/JXLM_DB.db");
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
	
	//Devolver todo el inventario de libros
	
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
	
}
