package org.JXLibraryManager.App;

/**
 * Clase que se encarga de representar a una persona
 * @author jesusxd88
 */
public class Persona implements Comparable<Persona> {
	
	private int idPersona; //El ID de la persona
	private String nombre; //Nombre(s)
	private String apellidos; //Apellidos
	
	/**
	 * @param idPersona El ID de la persona
	 * @param nombre Nombre(s)
	 * @param apellidos Apellidos
	 */
	public Persona (int idPersona, String nombre, String apellidos) {
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	/**
	 * @return idPersona
	 */
	public int getIdPersona() {
		return this.idPersona;
	}
	
	/**
	 * @param idPersona
	 */
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	
	/**
	 * @return Nombre(s)
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * @param Nombre(s)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return Apellidos
	 */
	public String getApellidos() {
		return this.apellidos;
	}
	
	/**
	 * @param Apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	/**
	 * Método ToString
	 */
	@Override
	public String toString() {
		return "idPersona:\t\t" + this.idPersona + "\n" 
				+ "Nombre:\t\t" + this.nombre + "\n"
				+ "Apellidos:\t\t" + this.apellidos + "\n";
	}
	
	/**
	 * Método compareTo
	 */
	@Override
	public int compareTo(Persona persona) {
		return this.idPersona == persona.idPersona ? this.idPersona : this.idPersona > persona.idPersona ? this.idPersona : persona.idPersona;
	}
	
	
	
}
