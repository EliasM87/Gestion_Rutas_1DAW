package vista;

import javax.swing.*;
import java.awt.*;
import modelo.dao.GestionRutas;

public class MainFrame extends JFrame {

    private GestionRutas gestion;
    private JPanel panelCentral;

    public MainFrame() {

        gestion = new GestionRutas();

        setTitle("Gestión de Rutas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelCentral = new JPanel(new BorderLayout());
        add(panelCentral);

        crearMenu();
    }

    private void crearMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menuAltas = new JMenu("Altas");
        JMenu menuRutas = new JMenu("Rutas");

        JMenuItem altaEmpleado = new JMenuItem("Alta Empleado");
        JMenuItem altaVehiculo = new JMenuItem("Alta Vehículo");
        JMenuItem crearRuta = new JMenuItem("Crear Ruta");
        JMenuItem verRutas = new JMenuItem("Visualizar Rutas");

        altaEmpleado.addActionListener(e ->
                cambiarPanel(new PanelAltaEmpleado(gestion)));

        altaVehiculo.addActionListener(e ->
                cambiarPanel(new PanelAltaVehiculo(gestion)));

        crearRuta.addActionListener(e ->
                cambiarPanel(new PanelAltaRuta(gestion)));

        verRutas.addActionListener(e ->
                cambiarPanel(new PanelListadoRutas(gestion)));

        menuAltas.add(altaEmpleado);
        menuAltas.add(altaVehiculo);
        menuRutas.add(crearRuta);
        menuRutas.add(verRutas);

        menuBar.add(menuAltas);
        menuBar.add(menuRutas);

        setJMenuBar(menuBar);
    }

    private void cambiarPanel(JPanel panel) {
        panelCentral.removeAll();
        panelCentral.add(panel, BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new MainFrame().setVisible(true));
    }
}
