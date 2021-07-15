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

public class Libro {

	private String ISBN;
	private String nombre;
	private String autor;
	private String genero;
	private String tematica;
	
	/**
	 * Constructor de Libro
	 * @param ISBN
	 * @param nombre
	 * @param autor
	 * @param genero
	 * @param tematica
	 */
	public Libro (String ISBN, String nombre, String autor, String genero, String tematica) {
		this.ISBN = ISBN;
		this.nombre = nombre;
		this.autor = autor;
		this.genero = genero;
		this.tematica = tematica;
	}
	
	//Getters y Setters
	
	/**
	 * @return ISBN
	 */
	public String getISBN() {
		return this.ISBN;
	}
	/**
	 * @param ISBN
	 */
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	/**
	 * @return Nombre
	 */
	public String getNombre() {
		return this.nombre;
	}
	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Autor
	 */
	public String getAutor() {
		return this.autor;
	}
	/**
	 * @param autor
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}
	/**
	 * @return Genero
	 */
	public String getGenero() {
		return this.genero;
	}
	/**
	 * @param genero
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}
	/**
	 * @return Tematica
	 */
	public String getTematica() {
		return this.tematica;
	}
	/**
	 * @param tematica
	 */
	public void setTematica(String tematica) {
		this.tematica = tematica;
	}
	
	/**
	 * Devuelve un Libro como un String
	 */
	@Override
	public String toString() {
		return "ISBN:\t\t" + this.ISBN + "\n" 
				+ "Nombre:\t\t" + this.nombre + "\n"
				+ "Autor:\t\t" + this.autor + "\n"
				+ "Genero:\t\t" + this.genero + "\n"
				+ "Tematica:\t" + this.tematica;
	}
}
