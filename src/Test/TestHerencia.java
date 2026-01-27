package Test;

import java.util.ArrayList;
import java.util.List;

import modelo.javabean.Camion;
import modelo.javabean.Furgoneta;
import modelo.javabean.Vehiculo;

public class TestHerencia {
	
	public static List<Vehiculo> flota = new ArrayList<Vehiculo>();

	public static void main(String[] args) {
		
		cargaDatos();
		
		//listarTodos();
		//mostrarCarga();
		//mostrarCargaDisponible();
		

	}
	
	
	
	
	
	
	
	
	public static void cargaDatos() {
		
		Vehiculo cam1 = new Camion("7145GZM", "Volvo", "FH", 100000, 11, 5000, 8);
		Vehiculo cam2 = new Camion("7688HVS", "Volvo", "FM", 13500, 9.8, 7000, 9);
		Vehiculo cam3 = new Camion("0573KMS", "Mercedes", "Actros", 85000, 14.8, 10000, 10);
		Vehiculo cam4 = new Camion("2387LMN", "Scania", "XT", 127000, 16.5, 9000, 8);
		Vehiculo cam5 = new Camion("1798FJM", "MAN", "TGX", 150000, 18, 10000, 8);
		Vehiculo furg1 = new Furgoneta("4732MNP", "Citroen", "Berlingo", 19000, 7, 10);
		Vehiculo furg2 = new Furgoneta("3765JVM", "Ford", "Transit", 45000, 8.5, 12);
		Vehiculo furg3 = new Furgoneta("8734LKS", "Peugeot", "Partner", 125000, 9, 14);
		Vehiculo furg4 = new Furgoneta("1765CDS", "Mercedes", "Citan", 210000, 11, 15);
		Vehiculo furg5 = new Furgoneta("7854KVC", "Renault", "Kangoo", 180000, 9, 8);
		flota.add(cam1);
		flota.add(cam2);
		flota.add(cam3);
		flota.add(cam4);
		flota.add(cam5);
		flota.add(furg1);
		flota.add(furg2);
		flota.add(furg3);
		flota.add(furg4);
		flota.add(furg5);
	}
	
	public static void listarTodos() {
		
		for (Vehiculo v: flota) {
			System.out.println(v);
		}
	}
	
	public static void mostrarCarga() {

		for (Vehiculo v : flota) {
			if (v instanceof Camion c)
				System.out.println("La carga actual del camion con matricula " + c.getMatricula() + " es de: " + c.getCargaOcupadaKg() + "Kg");
			else if (v instanceof Furgoneta f)
				System.out.println("La carga actual de la furgoneta con matricula " + f.getMatricula() + " es de: " + f.getVolumenOcupadoM3() + "m3");

		}
	}
	
	public static void mostrarCargaDisponible() {
		
		for (Vehiculo v : flota) {
			if (v instanceof Camion c)
				System.out.println("La carga disponible del camion con matricula " + c.getMatricula() + " es de: " + c.cargaDisponible() + "kg" );
			else if (v instanceof Furgoneta f)
				System.out.println("La carga disponible de la furgoneta con matricula " + f.getMatricula() + " es de: " + f.cargaDisponible() + "m3" );

		}
		
	}

}
