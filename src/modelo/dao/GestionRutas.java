package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.javabean.Camion;
import modelo.javabean.Furgoneta;
import modelo.javabean.Ruta;

public class GestionRutas implements IGestionRutas{
	
	
	private List<Ruta> rutas;
	
	
	public GestionRutas() {
		super();
		rutas = new ArrayList<Ruta>();
		}

	
	
	
	@Override
	public void addRuta(Ruta ruta) {
		rutas.add(ruta);
		
		//cargo el vehiculo usado, aumento kilometraje y aumento consumo (1l cada 20% de carga) al añadir la ruta
		
		if (ruta.getVehiculoUsado() instanceof Camion c)
			c.setCargaOcupadaKg(ruta.getCargaTransportadaKg());
		else if (ruta.getVehiculoUsado() instanceof Furgoneta f)
			f.setVolumenOcupadoM3(ruta.getCargaTransportadaKg() * 0.003);
		
		ruta.getVehiculoUsado().aumentarKilometraje(ruta.getKmRecorridos());
		double incrementoConsumo = ruta.getVehiculoUsado().getPorcentajeCarga() / 20;
		ruta.getVehiculoUsado().setConsumoLitros100km(ruta.getVehiculoUsado().getConsumoLitros100km() + incrementoConsumo);
	}

	
	@Override
	public void eliminarRuta(Ruta ruta) {
		rutas.remove(ruta);
		
	}

	@Override
	public void eliminarRuta(int idRuta) {	
		for (Ruta r: rutas) {
			if (r.getIdRuta() == idRuta)
			rutas.remove(r);	
		}
		
	}

	@Override
	public List<Ruta> rutasPorEmpleado(String dni) {
		List<Ruta> rutasEmpleadosDni = new ArrayList<Ruta>();
		for (Ruta r: rutas) {
			if (r.getEmpleado().getDni().equalsIgnoreCase(dni))
				rutasEmpleadosDni.add(r);			
		}
		return rutasEmpleadosDni;
	}

	@Override
	public List<Ruta> rutasPorVehiculo(String matricula) {
		
		List<Ruta> rutasVehiculoMatricula = new ArrayList<Ruta>();
		for (Ruta r: rutas) {
			if (r.getVehiculoUsado().getMatricula().equalsIgnoreCase(matricula)) 
				rutasVehiculoMatricula.add(r);
		}
		return rutasVehiculoMatricula;
	}

	@Override
	public Map<String, Long> totalKmPorVehiculo() {
		
		Map<String,Long> totalKmVehiculo = new HashMap<String, Long>();
		for (Ruta r: rutas) {
			totalKmVehiculo.merge(r.getVehiculoUsado().getMatricula(), (long) r.getVehiculoUsado().getKilometrosTotales(), Long::sum);
		}
		return totalKmVehiculo;
	}

	@Override
	public List<Ruta> rutasPorDestino(String destino) {
		
		List<Ruta> rutasPorDestino = new ArrayList<Ruta>();
		for (Ruta r: rutas) {
			if (r.getDestino().equalsIgnoreCase(destino)) 
				rutasPorDestino.add(r);	
		}
		return rutasPorDestino;
	}

	@Override
	public List<Ruta> rutasIntervaloFechas(LocalDate inicio, LocalDate fin) {

		List<Ruta> rutasIntervaloFechas = new ArrayList<Ruta>();
		
		for (Ruta r : rutas) {
			if (r.getFecha().isAfter(inicio) && r.getFecha().isBefore(fin))
				rutasIntervaloFechas.add(r);
		}

		return rutasIntervaloFechas;
	}

	@Override
	public Map<String, Long> totalKmPorTipoVehiculo() {
		
		Map<String,Long> totalKmTipoVehiculo = new HashMap<String, Long>();
		
		for (Ruta r: rutas) {
			if(r.getVehiculoUsado() instanceof Furgoneta) 
				totalKmTipoVehiculo.merge("FURGONETA", (long) r.getVehiculoUsado().getKilometrosTotales(), Long::sum);
			else
				totalKmTipoVehiculo.merge("CAMION", (long) r.getVehiculoUsado().getKilometrosTotales(), Long::sum);
		}
		return totalKmTipoVehiculo;
	}

}
