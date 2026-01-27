package modelo.javabean;

import java.time.LocalDate;
import java.util.Objects;

public class Ruta {
	
	private int idRuta;
	private LocalDate fecha;
	private String origen;
	private String destino;
	private Vehiculo vehiculoUsado;
	private Empleado empleado;
	private double KmRecorridos;
	private double cargaTransportadaKg;
	
	public Ruta(int idRuta, LocalDate fecha, String origen, String destino, Vehiculo vehiculoUsado, Empleado empleado,
			double kmRecorridos, double cargaTransportadaKg) {
		super();
		this.idRuta = idRuta;
		this.fecha = fecha;
		this.origen = origen;
		this.destino = destino;
		this.vehiculoUsado = vehiculoUsado;
		this.empleado = empleado;
		this.KmRecorridos = kmRecorridos;
		this.cargaTransportadaKg = cargaTransportadaKg;
		
		if(this.vehiculoUsado instanceof Camion c)
			c.setCargaOcupadaKg(cargaTransportadaKg);
		else if(this.vehiculoUsado instanceof Furgoneta f)
			f.setVolumenOcupadoM3(cargaTransportadaKg * 0.003);
			}

	public Ruta() {
		super();
	}

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Vehiculo getVehiculoUsado() {
		return vehiculoUsado;
	}

	public void setVehiculoUsado(Vehiculo vehiculoUsado) {
		this.vehiculoUsado = vehiculoUsado;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public double getKmRecorridos() {
		return KmRecorridos;
	}

	public void setKmRecorridos(double kmRecorridos) {
		KmRecorridos = kmRecorridos;
	}

	public double getCargaTransportadaKg() {
		return cargaTransportadaKg;
	}

	public void setCargaTransportadaKg(double cargaTransportadaKg) {
		this.cargaTransportadaKg = cargaTransportadaKg;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idRuta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ruta other = (Ruta) obj;
		return idRuta == other.idRuta;
	}

	@Override
	public String toString() {
		return "Ruta [idRuta=" + idRuta + ", fecha=" + fecha + ", origen=" + origen + ", destino=" + destino
				+ ", vehiculoUsado=" + vehiculoUsado + ", empleado=" + empleado + ", KmRecorridos=" + KmRecorridos
				+ ", cargaTransportadaKg=" + cargaTransportadaKg + "]";
	}
	
	public boolean isCargaCorrecta() {
		return this.cargaTransportadaKg <= this.vehiculoUsado.cargaDisponible();
	}
	
	public void modificarKilometrosYConsumoVehiculo() {
		this.vehiculoUsado.aumentarKilometraje(KmRecorridos);
		this.vehiculoUsado.consumoLitros100km += this.getVehiculoUsado().getPorcentajeCarga()/20; //Aumenta 1L de consumo por cada 20% de carga
	
	}
	
	public String getOrigenDestino() {
		return "Ruta con origen en: " + origen + ", con destino en: " + destino + ", con un total de Km recorridos de: " + KmRecorridos;
	}
	
	public String tipoRuta() {
		if(KmRecorridos >= 0 && KmRecorridos <=150)
			return "Corta";
		else if (KmRecorridos >= 150 && KmRecorridos <=300)
			return "Media";
		else
			return "Larga";
		
	}
	
	public double calcularConsumoEstimado() {
		return vehiculoUsado.consumoLitros100km * (KmRecorridos/100);
	}

}
