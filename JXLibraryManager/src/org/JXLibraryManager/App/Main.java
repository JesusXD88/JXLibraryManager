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

import java.util.Scanner;

/**
 * Clase Principal (Main)
 * @author jesusxd88
 *
 */
public class Main {

	private static GestionBiblioteca library = new GestionBiblioteca(); //Biblioteca
	
	/**
	 * Main
	 * @param args Main args
	 */
	public static void main(String[] args) {
		
		library.conexionDB();
		int opcion;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("\t   |--------------------------------------|");
			System.out.println("\t\tBIENVENIDXS A JXLibraryManager\t\t");
			System.out.println("\t   |--------------------------------------|");
			
			System.out.println("\n\nPor favor, selecciona lo que deseas hacer:\n");
			System.out.println("1. Gestionar los libros de la biblioteca (añadir/modificar/eliminar).");
			System.out.println("2. Mostrar los libros actualmente en la biblioteca.");
			System.out.println("3. Mostrar los libros extraidos de la biblioteca.");
			System.out.println("4. Extraer un libro de la biblioteca.");
			System.out.println("5. Devolver un libro extraido a la biblioteca.");
			System.out.println("6. Opciones Avanzadas.\n");
			System.out.println("7. Salir.");
			
			System.out.println("\n(Para seleccionar una opción introduce un número, por ejemplo: 1)\n\n");
			System.out.print("=> ");
			try {
				opcion = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("\nIntroduce un número válido\n");
				opcion = 0;
			}
		} while (opcion < 1 || opcion > 7);
		
		System.out.println();
		System.out.println();
		
		switch (opcion) {
		case 1:
			gestionarLibros();
			break;
		case 2:
			showBiblioteca();
			break;
		case 3:
			showLibrosExtraidos();
			break;
		case 4:
			extractLibro();
			break;
		case 5:
			returnLibro();
			break;
		case 6:
			opcionesAvanzadas();
			break;
		case 7:
			System.out.println("Saliendo..... ¡Hasta luegooo!");
			System.exit(0);
		}
		
		scan.close();
	}
	
	/**
	 * Método que contiene el submenú para gestionar libros
	 */
	private static void gestionarLibros() {
		Scanner scan = new Scanner(System.in);
		int opcion2;
		do {
			System.out.println("\n\n---------------------------------------");
			System.out.println("\nOpción seleccionada: Gestionar los libros de la biblioteca (añadir/modificar/eliminar).");
			System.out.println("\n\nSelecciona lo que hacer a continuación:\n");
			System.out.println("1. Añadir libros a la biblioteca.");
			System.out.println("2. Modificar libros de la biblioteca.");
			System.out.println("3. Eliminar libros de la biblioteca.");
			System.out.println("4. Mostrar todo el inventario de libros.\n");
			System.out.println("5. Volver al menú principal.\n");
			System.out.print("=> ");
			try {
				opcion2 = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("\nIntroduce un número válido\n");
				opcion2 = 0;
			}
		} while (opcion2 < 1 || opcion2 > 5);
		
		switch (opcion2) {
		case 1: 
			anadirLibro();
			break;
		case 2:
			modificarLibro(null, null);
			break;
		case 3:
			eliminarLibro();
			break;
		case 4:
			inventarioLibros();
			break;
		case 5:
			main(null);
			break;
		default:
			break;
		}
		scan.close();
	}
	
	/**
	 * Método que se encarga del asistente para añadir un libro
	 */
	private static void anadirLibro() {
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		Scanner scan = new Scanner(System.in);
		System.out.println("\n\nIntroduce a continuación los datos del libro a insertar:\n");
		System.out.print("Nombre: ");
		String nombre = scan.nextLine();
		//1scan.nextLine();
		System.out.print("ISBN: ");
		String ISBN = scan.nextLine();
		while (ISBN.length() != 13 || !isNumeric(ISBN)) {
			System.out.println("Introduce un ISBN válido");
			System.out.print("ISBN: ");
			ISBN = scan.nextLine();
		}
		System.out.print("Autor: ");
		String autor = scan.nextLine();
		System.out.print("Genero: ");
		String genero = scan.nextLine();
		System.out.print("Temática: ");
		String tematica = scan.nextLine();
		System.out.println(ISBN);
		
		Libro libro = new Libro (ISBN, nombre, autor, genero, tematica);
		
		System.out.println("\n\n\nEl libro a insertar es:\n");
		System.out.println(libro.toString());
		String ver;
		do {
			System.out.println("\n\n ¿Es correcta esta información? (si/no)\n");
			System.out.print("=> ");
			ver = scan.nextLine();
			if (ver.equals("si")) {
				library.insertarLibro(libro);
				gestionarLibros();
			} else if (ver.equals("no")) {
				anadirLibro();
			}
		} while (ver != "si" || ver != "no");
		scan.close();
	}
	
	/**
	 * Método que se encarga del asistente para modificar libro.
	 * Si se llama desde el main se le pasa (null,null) ya que al incluir la opción de modificar varios campos
	 * se vuelve a pasar por parámetro el libro que se está modificando y el ISBN original.
	 * @param libro Libro
	 * @param ISBN El ISBN del Libro
	 */
	public static void modificarLibro(Libro libro, String ISBN) {
		Scanner scan = new Scanner(System.in);
		if (libro == null && ISBN == null) {
			System.out.println();
			System.out.println();
			System.out.println("---------------------------------------");
			System.out.println("\n\nIntroduce a continuación el ISBN del libro a modificar:\n");
			System.out.print("=> ");
			ISBN = scan.nextLine();
			while (ISBN.length() != 13 || !isNumeric(ISBN)) {
				System.out.println("Introduce un ISBN válido");
				System.out.print("ISBN: ");
				ISBN = scan.nextLine();
			}
			
			libro = library.retrieveLibroPorISBN(ISBN);
			if (libro != null) {
				System.out.println("\nEl libro correspondiente al ISBN introducido es:\n");
				System.out.println(libro.toString());
			} else {
				String v;
				do {
					System.out.println("\nEl ISBN introducido no es correcto.");
					System.out.println("\n¿Deseas volver a intentarlo?(si/no)\n");
					System.out.print("=> ");
					v = scan.nextLine();
					if (v.equals("si")) {
						modificarLibro(null, ISBN);
					} else if (v.equals("no")) {
						gestionarLibros();
					}
				} while (v != "si" || v != "no");
			}
		} else {
			System.out.println();
			System.out.println(libro.toString());
		}
		System.out.println("\nSelecciona el campo a modificar (introducir número):\n");
		System.out.println("  1. ISBN  |  2. Nombre  |  3. Autor  |  4. Género  |  5. Temática");
		System.out.print("\n=> ");
		int campo;
		do {
			try {
				campo = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("\nIntroduce un número válido\n");
				campo = 0;
			}
		} while (campo < 1 || campo > 5);
		
		String ISBN_nuevo, nombre, autor, genero, tematica, cambios;
		switch (campo) {
		case 1:
			System.out.println("\nIntroduce el nuevo ISBN:\n");
			System.out.print("=> ");
			ISBN_nuevo = scan.nextLine();
			while (ISBN_nuevo.length() != 13 || !isNumeric(ISBN_nuevo)) {
				System.out.println("\nIntroduce un ISBN válido:\n");
				System.out.print("=> ");
				ISBN_nuevo = scan.nextLine();
			}
			libro.setISBN(ISBN_nuevo);
			do {
				System.out.println("\n¿Deseas realizar más cambios?(si/no)\n");
				System.out.print("=> ");
				cambios = scan.nextLine();
				if (cambios.equals("si")) {
					modificarLibro(libro, ISBN);
				} else if (cambios.equals("no")) {
					break;
				}
			} while (cambios != "si" || cambios != "no");
			break;
		case 2:
			System.out.println("\nIntroduce el nuevo Nombre:\n");
			System.out.print("=> ");
			nombre = scan.nextLine();
			libro.setNombre(nombre);
			do {
				System.out.println("\n¿Deseas realizar más cambios?(si/no)\n");
				System.out.print("=> ");
				cambios = scan.nextLine();
				if (cambios.equals("si")) {
					modificarLibro(libro, ISBN);
				} else if (cambios.equals("no")) {
					break;
				}
			} while (cambios != "si" || cambios != "no");
			break;
		case 3:
			System.out.println("\nIntroduce el nuevo Autor:\n");
			System.out.print("=> ");
			autor = scan.nextLine();
			libro.setAutor(autor);
			do {
				System.out.println("\n¿Deseas realizar más cambios?(si/no)\n");
				System.out.print("=> ");
				cambios = scan.nextLine();
				if (cambios.equals("si")) {
					modificarLibro(libro, ISBN);
				} else if (cambios.equals("no")) {
					break;
				}
			} while (cambios != "si" || cambios != "no");
			break;
		case 4:
			System.out.println("\nIntroduce el nuevo Genero:\n");
			System.out.print("=> ");
			genero = scan.nextLine();
			libro.setGenero(genero);
			do {
				System.out.println("\n¿Deseas realizar más cambios?(si/no)\n");
				System.out.print("=> ");
				cambios = scan.nextLine();
				if (cambios.equals("si")) {
					modificarLibro(libro, ISBN);
				} else if (cambios.equals("no")) {
					break;
				}
			} while (cambios != "si" || cambios != "no");
			break;
		case 5:
			System.out.println("\nIntroduce la nueva Temática:\n");
			System.out.print("=> ");
			tematica = scan.nextLine();
			libro.setTematica(tematica);
			do {
				System.out.println("\n¿Deseas realizar más cambios?(si/no)\n");
				System.out.print("=> ");
				cambios = scan.nextLine();
				if (cambios.equals("si")) {
					modificarLibro(libro, ISBN);
				} else if (cambios.equals("no")) {
					break;
				}
			} while (cambios != "si" || cambios != "no");
			break;
		default:
			break;
		}
		System.out.println("\nEl libro modificado es:\n");
		System.out.println(libro.toString());
		do {
			System.out.println("\n¿Confirmar cambios?(si/no)");
			System.out.print("=> ");
			cambios = scan.nextLine();
			if (cambios.equals("si")) {
				library.modificarLibro(libro, ISBN);
				gestionarLibros();
			} else if (cambios.equals("no")) {
				System.out.println("\nNo se ha modificado el libro.\n");
				gestionarLibros();
			}
		} while (cambios != "si" || cambios != "no");
		scan.close();
	}
	
	/**
	 * Método que contiene el asistente para eliminar libros
	 */
	public static void eliminarLibro() {
		Scanner scan = new Scanner(System.in);
		String ISBN;
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		System.out.println("\n\nIntroduce a continuación el ISBN del libro a eliminar:\n");
		System.out.print("=> ");
		ISBN = scan.nextLine();
		while (ISBN.length() != 13 || !isNumeric(ISBN)) {
			System.out.println("Introduce un ISBN válido");
			System.out.print("ISBN: ");
			ISBN = scan.nextLine();
		}
		
		Libro libro = library.retrieveLibroPorISBN(ISBN);
		if (libro != null) {
			System.out.println("\nEl libro correspondiente al ISBN introducido es:\n");
			System.out.println(libro.toString());
		} else {
			String v;
			do {
				System.out.println("\nEl ISBN introducido no es correcto.");
				System.out.println("\n¿Deseas volver a intentarlo?(si/no)\n");
				System.out.print("=> ");
				v = scan.nextLine();
				if (v.equals("si")) {
					modificarLibro(null, ISBN);
				} else if (v.equals("no")) {
					gestionarLibros();
				}
			} while (v != "si" || v != "no");
		}
		String a;
		do {
			System.out.println("\n¿Deseas eliminarlo?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				library.eliminarLibro(libro);
				gestionarLibros();
			} else if (a.equals("no")) {
				gestionarLibros();
			}
		} while (a != "si" || a != "no");	
		scan.close();
	}
	
	/**
	 * Método que muestra por consola el inventario de libros
	 */
	public static void inventarioLibros() {
		Scanner scan = new Scanner(System.in);
		String tabla = library.mostrarInventario();
		System.out.println("\n\n---------------------------------------\n\n");
		if (tabla == null) {
			System.out.println("\nNo existe ningún libro en el inventario todavía.\nPor favor, inserta primeramente un libro\n");
			System.out.println("\n---------------------------------------\n\n");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gestionarLibros();
		}
		System.out.println("\nEl inventario de libros se muestra a continuación:\n");
		System.out.println(tabla);
		String a;
		do {
			System.out.println("\n¿Volver?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				gestionarLibros();
			} 
		} while (a != "si" || a != "no");
		scan.close();
	}
	
	//Mostrar biblioteca
	
	/**
	 * Método que muestra por consola la biblioteca de libros
	 */
	public static void showBiblioteca() {
		Scanner scan = new Scanner(System.in);
		String biblioteca = library.toString();
		System.out.println("\n\n---------------------------------------\n\n");
		if (biblioteca == null) {
			System.out.println("\nNo existe ningún libro en la biblioteca todavía.\nPor favor, inserta primeramente un libro\n");
			System.out.println("\n---------------------------------------\n\n");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			main(null);
		}
		System.out.println("\nMostrando los libros actualmente en la biblioteca:\n");
		System.out.println(biblioteca);
		String a;
		do {
			System.out.println("\n¿Volver?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				main(null);
			} 
		} while (a != "si" || a != "no");
		scan.close();
	}
	
	//Mostrar libros extraidos
	
	/**
	 * Método que muestra los libros extraidos de la biblioteca
	 */
	public static void showLibrosExtraidos() {
		Scanner scan = new Scanner(System.in);
		String extracted = library.mostrarLibrosExtraidos();
		System.out.println("\n\n---------------------------------------\n\n");
		if (extracted == null) {
			System.out.println("\nNo existe ningún libro extraido todavía.\nPor favor, extrae primeramente un libro\n");
			System.out.println("\n---------------------------------------\n\n");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			main(null);
		}
		System.out.println("\nMostrando los libros extraidos de la biblioteca:\n");
		System.out.println(extracted);
		String a;
		do {
			System.out.println("\n¿Volver?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				main(null);
			} 
		} while (a != "si" || a != "no");
		scan.close();
	}
	
	//Extraer Libros
	
	/**
	 * Método que permite realizar la operación de extraerLibro
	 */
	public static void extractLibro() {
		Scanner scan = new Scanner(System.in);
		String ISBN;
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		System.out.println("\n\nPor favor, introduce a continuación el ISBN del libro a extraer de la bibilioteca:\n");
		System.out.print("=> ");
		ISBN = scan.nextLine();
		while (ISBN.length() != 13 || !isNumeric(ISBN)) {
			System.out.println("Introduce un ISBN válido");
			System.out.print("=> ");
			ISBN = scan.nextLine();
		}
		Libro libro = library.retrieveLibroPorISBN(ISBN);
		boolean ok = library.extraerLibro(libro);
		if (ok == true) System.out.println("\nSe ha extraido el libro con éxito!\n");
		String a;
		do {
			System.out.println("\n¿Volver?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				main(null);
			} 
		} while (a != "si" || a != "no");
		scan.close();
	}
	
	//Devolver Libros
	
	/**
	 * Método que permite realizar la operación de devolverLibro
	 */
	public static void returnLibro() {
		Scanner scan = new Scanner(System.in);
		String ISBN;
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		System.out.println("\n\nPor favor, introduce a continuación el ISBN del libro a devolver a la bibilioteca:\n");
		System.out.print("=> ");
		ISBN = scan.nextLine();
		while (ISBN.length() != 13 || !isNumeric(ISBN)) {
			System.out.println("Introduce un ISBN válido");
			System.out.print("=> ");
			ISBN = scan.nextLine();
		}
		Libro libro = library.retrieveLibroPorISBN(ISBN);
		boolean ok = library.devolverLibro(libro);
		if (ok == true) System.out.println("\nSe ha devuelto el libro con éxito!\n");
		String a;
		do {
			System.out.println("\n¿Volver?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				main(null);
			} 
		} while (a != "si" || a != "no");
		scan.close();
	}
	
	//Opciones Avanzadas
	
	/**
	 * Menú de Opciones Avanzadas
	 */
	public static void opcionesAvanzadas() {
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		System.out.println("\n\nHas entrado en el modo de configuración avanzada.\nPor favor, ten cuidado con lo que haces.\nSelecciona una opción:");
		System.out.println("\n\n1. Eliminar TODA la base de datos de la biblioteca. (¡CUIDADO!)");
		System.out.println("2. Ejecutar una query SQL.\n");
		System.out.println("3. Volver.");
		
		int opcion;
		do {
			try {
				System.out.print("=> ");
				opcion = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("\nIntroduce un número válido\n");
				opcion = 0;
			}
		} while (opcion < 1 || opcion > 3);
		
		switch (opcion) {
		case 1:
			eliminarDB();
			break;
		case 2:
			menuSQL();
			break;
		case 3:
			main(null);
			break;
		}
		scan.close();
	}
	
	/**
	 * Método que permite borrar todos los datos de la DB
	 */
	public static void eliminarDB() {
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		System.out.println("\n\nVas a eliminar TODOS LOS DATOS de la Base de Datos. Esta acción es irreversible.");
		String a;
		do {
			System.out.println("\n\n¿Seguro que quieres proceder?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				boolean ok = library.truncateDB();
				if (ok) System.out.println("Se han eliminado correctamente TODOS LOS DATOS de la Base de Datos!");
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				main(null);
			} else if (a.equals("no")) {
				opcionesAvanzadas();
			}
		} while (a != "si" || a != "no");
		scan.close();
	}
	
	/**
	 * Menú de las consultas SQL
	 */
	public static void menuSQL() {
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		System.out.println("\n\nPor favor, selecciona el tipo de statement SQL a ejecutar:\n");
		System.out.println("1. INSERT, UPDATE, DELETE.\n");
		System.out.println("2. Volver.");
		
		int opcion;
		do {
			try {
				System.out.print("=> ");
				opcion = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("\nIntroduce un número válido\n");
				opcion = 0;
			}
		} while (opcion < 1 || opcion > 3);
		
		switch (opcion) {
		case 1:
			executeUpdateSQL();
			break;
		case 2:
			main(null);
			break;
		}
		scan.close();
	}
	
	/**
	 * Método que se encarga de obtener una cadena SQL por consola de INSERT, UPDATE o DELETE
	 */
	public static void executeUpdateSQL() {
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------");
		System.out.println("\n\nPor favor, introduce a continuación la cadena SQL a ejecutar (INSERT, UPDATE, DELETE):");
		System.out.print("\n=> ");
		String query = scan.nextLine();
		boolean ok = library.executeUpdatSQL(query);
		if (ok) System.out.println("La operación se realizó con éxito!");
		String a;
		do {
			System.out.println("\n¿Volver?(si/no)");
			System.out.print("=> ");
			a = scan.nextLine();
			if (a.equals("si")) {
				menuSQL();
			} 
		} while (a != "si" || a != "no");
		scan.close();
	}
	
	
	/**
	 * Método de clase que sirve para verificar si un String determinado es un número
	 * @param strNum
	 * @return boolean
	 */
	private static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        @SuppressWarnings("unused")
			double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
}
