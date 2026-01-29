package vista;

import java.time.LocalDate;

import javax.swing.*;
import modelo.dao.GestionRutas;
import modelo.javabean.Camion;
import modelo.javabean.Empleado;
import modelo.javabean.Furgoneta;
import modelo.javabean.Ruta;
import modelo.javabean.Vehiculo;

public class MainFrame extends JFrame {

    public static GestionRutas gestion;

    public MainFrame() {

        gestion = new GestionRutas();
        cargarRutas();

        setTitle("Sistema de Gestión de Rutas");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();

        pestañas.addTab("➕ Crear Ruta", new PanelAltaRuta(gestion));
        pestañas.addTab("🔍 Buscar Rutas", new PanelListadoRutas(gestion));

        add(pestañas);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() ->
                new MainFrame().setVisible(true)
        );
    }
    
	public static void cargarRutas() {
		
		Vehiculo cam1 = new Camion("7145GZM", "Volvo", "FH", 100000, 11, 15000, 8);
		Vehiculo cam2 = new Camion("7688HVS", "Volvo", "FM", 13500, 9.8, 17000, 9);
		Vehiculo cam3 = new Camion("0573KMS", "Mercedes", "Actros", 85000, 14.8, 10000, 10);
		Vehiculo cam4 = new Camion("2387LMN", "Scania", "XT", 127000, 16.5, 12000, 8);
		Vehiculo cam5 = new Camion("1798FJM", "MAN", "TGX", 150000, 18, 10000, 8);
		Vehiculo furg1 = new Furgoneta("4732MNP", "Citroen", "Berlingo", 19000, 7, 10);
		Vehiculo furg2 = new Furgoneta("3765JVM", "Ford", "Transit", 45000, 8.5, 12);
		Vehiculo furg3 = new Furgoneta("8734LKS", "Peugeot", "Partner", 125000, 9, 14);
		Vehiculo furg4 = new Furgoneta("1765CDS", "Mercedes", "Citan", 210000, 11, 15);
		Vehiculo furg5 = new Furgoneta("7854KVC", "Renault", "Kangoo", 180000, 9, 8);
		Empleado emp1 = new Empleado("04356712G", "Pepito", "Mangas", "pepito@gmail.com", "h");
		Empleado emp2 = new Empleado("83495960J", "Maria", "Santos", "m.santos@gmail.com", "M");
		Empleado emp3 = new Empleado("62849500K", "Manolo", "Perez", "manolo@gmail.com", "h");
		Empleado emp4 = new Empleado("89345875M", "Virginia", "Lambas", "virgi.lam@gmail.com", "M");
		Empleado emp5 = new Empleado("83947639F", "Jesus", "Montes", "j.montes@gmail.com", "h");
		
		Ruta ruta1 = new Ruta(101, LocalDate.of(2024, 12, 01), "Madrid", "Barcelona", cam1, emp1, 650, 8000);
		Ruta ruta2 = new Ruta(102, LocalDate.of(2025, 6, 10), "Sevilla", "Madrid", cam2, emp2, 550, 7000);
		Ruta ruta3 = new Ruta(103, LocalDate.of(2025, 8, 12), "Zaragoza", "Malaga", furg1, emp3, 700, 1500);
		Ruta ruta4 = new Ruta(104, LocalDate.of(2025, 02, 17), "Pontevedra", "Madrid", furg2, emp4, 500, 1800);
		Ruta ruta5 = new Ruta(105, LocalDate.of(2025, 8, 5), "Murcia", "Toledo", cam3, emp5, 600, 9000);
		Ruta ruta6 = new Ruta(106, LocalDate.of(2026, 01, 12), "Madrid", "Malaga", cam4, emp2, 520, 12000);
		gestion.addRuta(ruta1);
		gestion.addRuta(ruta2);
		gestion.addRuta(ruta3);
		gestion.addRuta(ruta4);
		gestion.addRuta(ruta5);
		gestion.addRuta(ruta6);
	}
    
}
