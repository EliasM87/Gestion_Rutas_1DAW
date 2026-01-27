package modelo.javabean;

public class Furgoneta extends Vehiculo {

	private double volumenCargaM3;
	private double volumenOcupadoM3;
	
	
	public Furgoneta(String matricula, String marca, String modelo, double kilometrosTotales, double consumoLitros100km,
			double volumenCargaM3) {
		super(matricula, marca, modelo, kilometrosTotales, consumoLitros100km);
		this.volumenCargaM3 = volumenCargaM3;
	}


	public Furgoneta(String matricula, String marca, String modelo, double kilometrosTotales,
			double consumoLitros100km) {
		super(matricula, marca, modelo, kilometrosTotales, consumoLitros100km);
	}


	public double getVolumenCargaM3() {
		return volumenCargaM3;
	}


	public void setVolumenCargaM3(double volumenCargaM3) {
		this.volumenCargaM3 = volumenCargaM3;
	}


	public double getVolumenOcupadoM3() {
		return volumenOcupadoM3;
	}


	public void setVolumenOcupadoM3(double volumenOcupadoM3) {
		this.volumenOcupadoM3 = volumenOcupadoM3;
	}


	@Override
	public String toString() {
		return "Furgoneta [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", kilometrosTotales="
				+ kilometrosTotales + ", consumoLitros100km=" + consumoLitros100km + ", volumenCargaM3="
				+ volumenCargaM3 + ", volumenOcupadoM3=" + volumenOcupadoM3 + "]";
	}


	@Override
	public double cargaDisponible() {
		return volumenCargaM3 - volumenOcupadoM3;
	}


	@Override
	public double getPorcentajeCarga() {
		return (volumenOcupadoM3 / volumenCargaM3) * 100;
	}


	@Override
	public boolean isLleno() {
		return cargaDisponible() == 0;
	}





	
	
	
	
}
