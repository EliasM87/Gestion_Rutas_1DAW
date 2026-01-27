package modelo.javabean;

import java.util.Objects;

public abstract class Vehiculo {
	
	protected String matricula;
	protected String marca;
	protected String modelo;
	protected double kilometrosTotales;
	protected double consumoLitros100km;
	
	public abstract double cargaDisponible();
	public abstract double getPorcentajeCarga();
	public abstract boolean isLleno();
	
	
	
	public void aumentarKilometraje(double kilometros) {
		kilometrosTotales += kilometros;
	};
	

	
	public Vehiculo(String matricula, String marca, String modelo, double kilometrosTotales,
			double consumoLitros100km) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.kilometrosTotales = kilometrosTotales;
		this.consumoLitros100km = consumoLitros100km;
	}
	public Vehiculo() {
		super();
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public double getKilometrosTotales() {
		return kilometrosTotales;
	}
	public void setKilometrosTotales(double kilometrosTotales) {
		this.kilometrosTotales = kilometrosTotales;
	}
	public double getConsumoLitros100km() {
		return consumoLitros100km;
	}
	public void setConsumoLitros100km(double consumoLitros100km) {
		this.consumoLitros100km = consumoLitros100km;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehiculo other = (Vehiculo) obj;
		return Objects.equals(matricula, other.matricula);
	}

	

}
