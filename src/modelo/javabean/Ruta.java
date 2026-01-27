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
	private double kmRecorridos;
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
		this.kmRecorridos = kmRecorridos;
		this.cargaTransportadaKg = cargaTransportadaKg;
		
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
		return kmRecorridos;
	}

	public void setKmRecorridos(double kmRecorridos) {
		this.kmRecorridos = kmRecorridos;
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
	    return "\n" + "========== DETALLES DE LA RUTA ===========\n" +
	           "ID Ruta:             " + idRuta + "\n" +
	           "Fecha:               " + fecha + "\n" +
	           "Origen:              " + origen + "\n" +
	           "Destino:             " + destino + "\n" +
	           "Km Recorridos:       " + kmRecorridos + " km\n" +
	           "Carga Transportada:  " + cargaTransportadaKg + " kg\n" +
	           "Vehículo:            " + "\n" + vehiculoUsado + "\n" +
	           "Empleado:            " + empleado + "\n"+
	           "==========================================" + "\n";
	}
	
	
	
	public boolean isCargaCorrecta() {
		return this.cargaTransportadaKg <= this.vehiculoUsado.cargaDisponible();
	}
	
	public void modificarKilometrosYConsumoVehiculo() {
		this.vehiculoUsado.aumentarKilometraje(kmRecorridos);
		this.vehiculoUsado.consumoLitros100km += this.getVehiculoUsado().getPorcentajeCarga()/20; //Aumenta 1L de consumo por cada 20% de carga
	
	}
	
	public String getOrigenDestino() {
		return "Ruta con origen en: " + origen + ", con destino en: " + destino + ", con un total de Km recorridos de: " + kmRecorridos;
	}
	
	public String tipoRuta() {
		if(kmRecorridos >= 0 && kmRecorridos <=150)
			return "Corta";
		else if (kmRecorridos >= 150 && kmRecorridos <=300)
			return "Media";
		else
			return "Larga";
		
	}
	
	public double calcularConsumoEstimado() {
		return vehiculoUsado.consumoLitros100km * (kmRecorridos/100);
	}

}
