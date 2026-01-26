package modelo.javabean;

import java.util.Objects;

public class Empleado {
	
	private String dni;
	private String nombre;
	private String apellidos;
	private String email;
	private String genero;
	
	public Empleado(String dni, String nombre, String apellidos, String email, String genero) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.genero = genero;
	}

	public Empleado() {
		super();
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", genero=" + genero + "]";
	}
	
	public String nombreCompleto() {
		return nombre + " " + apellidos;

	}
	
	public String literalGenero() {
		switch (genero) {
		
		case "h","H":
			return "Hombre";
		case "M","m":
			return "Mujer";
		default:
		return "Genero introducido no valido";
		}
	}

}
