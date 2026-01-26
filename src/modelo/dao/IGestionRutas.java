package modelo.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import modelo.javabean.Ruta;

public interface IGestionRutas {
	
	void addRuta(Ruta ruta);
	void eliminarRuta(Ruta ruta);
	void eliminarRuta(int idRuta);
	List<Ruta> rutasPorEmpleado(String dni);
	List<Ruta> rutasPorVehiculo(String matricula);
	Map<String, Long> totalKmPorVehiculo();
	List<Ruta> rutasPorDestino(String destino);
	List<Ruta> rutasIntervaloFechas(LocalDate inicio, LocalDate fin);
	Map<String, Long> totalKmPorTipoVehiculo();
	
	

	

}
