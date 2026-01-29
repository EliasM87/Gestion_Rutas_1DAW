package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.dao.GestionRutas;
import modelo.javabean.Ruta;

public class PanelListadoRutas extends JPanel {

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private GestionRutas gestion;

    public PanelListadoRutas(GestionRutas gestion) {
        this.gestion = gestion;

        setLayout(new BorderLayout());

        // ---------- Panel filtros ----------
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField txtDestino = new JTextField(8);
        JButton btnDestino = new JButton("Destino");

        JTextField txtMatricula = new JTextField(8);
        JButton btnVehiculo = new JButton("Vehículo");

        JTextField txtDni = new JTextField(8);
        JButton btnEmpleado = new JButton("Empleado");

        JButton btnTodas = new JButton("Ver todas");

        panelFiltros.add(new JLabel("Destino"));
        panelFiltros.add(txtDestino);
        panelFiltros.add(btnDestino);

        panelFiltros.add(new JLabel("Matrícula"));
        panelFiltros.add(txtMatricula);
        panelFiltros.add(btnVehiculo);

        panelFiltros.add(new JLabel("DNI"));
        panelFiltros.add(txtDni);
        panelFiltros.add(btnEmpleado);

        panelFiltros.add(btnTodas);

        JScrollPane scrollFiltros = new JScrollPane(panelFiltros);
        scrollFiltros.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollFiltros.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        add(scrollFiltros, BorderLayout.NORTH);

        // ---------- Tabla ----------
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Origen");
        modeloTabla.addColumn("Destino");
        modeloTabla.addColumn("Empleado");
        modeloTabla.addColumn("Tipo Vehículo");
        modeloTabla.addColumn("Matricula");
        modeloTabla.addColumn("Km");
        modeloTabla.addColumn("Carga Ocupada %");

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(22);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ---------- Eventos ----------
        btnDestino.addActionListener(e ->
                cargar(gestion.rutasPorDestino(txtDestino.getText())));

        btnVehiculo.addActionListener(e ->
                cargar(gestion.rutasPorVehiculo(txtMatricula.getText())));

        btnEmpleado.addActionListener(e ->
                cargar(gestion.rutasPorEmpleado(txtDni.getText())));

        btnTodas.addActionListener(e ->
                cargar(gestion.mostrarTodas()));   // Mostrar todas las rutas
    }

    private void cargar(List<Ruta> rutas) {
        modeloTabla.setRowCount(0);

        for (Ruta r : rutas) {
            modeloTabla.addRow(new Object[]{
                    r.getIdRuta(),
                    r.getFecha(),
                    r.getOrigen(),
                    r.getDestino(),
                    r.getEmpleado().getNombre(),
                    r.getVehiculoUsado().getTipo(),
                    r.getVehiculoUsado().getMatricula(),
                    r.getVehiculoUsado().getKilometrosTotales(),
                    r.getVehiculoUsado().getPorcentajeCarga()
            });
        }
    }
}

