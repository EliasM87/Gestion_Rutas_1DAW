package modelo.javabean;

public class Camion extends Vehiculo{
	
	
	private double capacidadCargaKg;
	private double cargaOcupadaKg;
	private int numeroEjes;
	
	

	
	
	public Camion(String matricula, String marca, String modelo, double kilometrosTotales, double consumoLitros100km) {
		super(matricula, marca, modelo, kilometrosTotales, consumoLitros100km);
	}
	public Camion(String matricula, String marca, String modelo, double kilometrosTotales, double consumoLitros100km,
			double capacidadCargaKg, int numeroEjes) {
		super(matricula, marca, modelo, kilometrosTotales, consumoLitros100km);
		this.capacidadCargaKg = capacidadCargaKg;
		this.numeroEjes = numeroEjes;
	}
	
	
	public double getCapacidadCargaKg() {
		return capacidadCargaKg;
	}
	public void setCapacidadCargaKg(double capacidadCargaKg) {
		this.capacidadCargaKg = capacidadCargaKg;
	}
	public double getCargaOcupadaKg() {
		return cargaOcupadaKg;
	}
	public void setCargaOcupadaKg(double cargaOcupadaKg) {
		this.cargaOcupadaKg = cargaOcupadaKg;
	}
	public int getNumeroEjes() {
		return numeroEjes;
	}
	public void setNumeroEjes(int numeroEjes) {
		this.numeroEjes = numeroEjes;
	}
	@Override
	public double cargaDisponible() {
		return capacidadCargaKg - cargaOcupadaKg;
	}
	@Override
	public double getPorcentajeCarga() {
		return (cargaOcupadaKg / capacidadCargaKg)*100;
	}
	
	@Override
	public boolean isLleno() {
		return cargaDisponible() == 0;
	}

	@Override
	public String toString() {
		return "Camion [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", kilometrosTotales="
				+ kilometrosTotales + ", consumoLitros100km=" + consumoLitros100km + ", capacidadCargaKg="
				+ capacidadCargaKg + ", cargaOcupadaKg=" + cargaOcupadaKg + ", numeroEjes=" + numeroEjes + "]";
	}
	
	
	@Override
	public void modificarConsumoLitros100km() {
		this.consumoLitros100km += getPorcentajeCarga()/20;//aumenta 1L cada 20% de carga
		
	}
	


}
