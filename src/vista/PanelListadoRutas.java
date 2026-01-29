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
    private JLabel lblResultados;

    public PanelListadoRutas(GestionRutas gestion) {
        this.gestion = gestion;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- Panel filtros ----------
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltros.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Filtros de Búsqueda"));

        JTextField txtDestino = new JTextField(10);
        JButton btnDestino = new JButton("🎯 Buscar por Destino");

        JTextField txtMatricula = new JTextField(10);
        JButton btnVehiculo = new JButton("🚚 Buscar por Vehículo");

        JTextField txtDni = new JTextField(10);
        JButton btnEmpleado = new JButton("👤 Buscar por Empleado");

        JButton btnTodas = new JButton("📋 Ver Todas");
        JButton btnLimpiar = new JButton("🔄 Limpiar Filtros");

        // Estilizar botón "Ver Todas"
        btnTodas.setBackground(new Color(33, 150, 243));
        btnTodas.setForeground(Color.WHITE);

        panelFiltros.add(new JLabel("Destino:"));
        panelFiltros.add(txtDestino);
        panelFiltros.add(btnDestino);

        panelFiltros.add(new JLabel("Matrícula:"));
        panelFiltros.add(txtMatricula);
        panelFiltros.add(btnVehiculo);

        panelFiltros.add(new JLabel("DNI Empleado:"));
        panelFiltros.add(txtDni);
        panelFiltros.add(btnEmpleado);

        panelFiltros.add(btnTodas);
        panelFiltros.add(btnLimpiar);

        add(panelFiltros, BorderLayout.NORTH);

        // ---------- Panel de información ----------
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblResultados = new JLabel("Total de rutas: 0");
        lblResultados.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblResultados);
        
        // ---------- Tabla ----------
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Resultados"));
        
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Origen");
        modeloTabla.addColumn("Destino");
        modeloTabla.addColumn("Empleado");
        modeloTabla.addColumn("Tipo Vehículo");
        modeloTabla.addColumn("Matrícula");
        modeloTabla.addColumn("Km Recorridos");
        modeloTabla.addColumn("Carga (kg)");
        modeloTabla.addColumn("% Ocupación");

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);

        // Ajustar anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);  // Fecha
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);  // Origen
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);  // Destino
        tabla.getColumnModel().getColumn(4).setPreferredWidth(150);  // Empleado
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100);  // Tipo
        tabla.getColumnModel().getColumn(6).setPreferredWidth(100);  // Matrícula
        tabla.getColumnModel().getColumn(7).setPreferredWidth(100);  // Km
        tabla.getColumnModel().getColumn(8).setPreferredWidth(80);   // Carga
        tabla.getColumnModel().getColumn(9).setPreferredWidth(100);  // % Ocupación

        JScrollPane scrollTabla = new JScrollPane(tabla);
        panelTabla.add(panelInfo, BorderLayout.NORTH);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        
        add(panelTabla, BorderLayout.CENTER);

        // ---------- Panel de estadísticas ----------
        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 3, 10, 0));
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton btnEstadisticasVehiculo = new JButton("📊 Estadísticas por Vehículo");
        JButton btnEstadisticasTipo = new JButton("📊 Estadísticas por Tipo");
        JButton btnEliminarRuta = new JButton("🗑️ Eliminar Ruta Seleccionada");
        
        btnEliminarRuta.setBackground(new Color(244, 67, 54));
        btnEliminarRuta.setForeground(Color.WHITE);
        
        panelEstadisticas.add(btnEstadisticasVehiculo);
        panelEstadisticas.add(btnEstadisticasTipo);
        panelEstadisticas.add(btnEliminarRuta);
        
        add(panelEstadisticas, BorderLayout.SOUTH);

        // ---------- Eventos ----------
        btnDestino.addActionListener(e -> {
            String destino = txtDestino.getText().trim();
            if (!destino.isEmpty()) {
                cargar(gestion.rutasPorDestino(destino));
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un destino", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnVehiculo.addActionListener(e -> {
            String matricula = txtMatricula.getText().trim();
            if (!matricula.isEmpty()) {
                cargar(gestion.rutasPorVehiculo(matricula));
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese una matrícula", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEmpleado.addActionListener(e -> {
            String dni = txtDni.getText().trim();
            if (!dni.isEmpty()) {
                cargar(gestion.rutasPorEmpleado(dni));
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un DNI", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnTodas.addActionListener(e -> cargar(gestion.mostrarTodas()));
        
        btnLimpiar.addActionListener(e -> {
            txtDestino.setText("");
            txtMatricula.setText("");
            txtDni.setText("");
            modeloTabla.setRowCount(0);
            lblResultados.setText("Total de rutas: 0");
        });
        
        btnEstadisticasVehiculo.addActionListener(e -> mostrarEstadisticasVehiculo());
        btnEstadisticasTipo.addActionListener(e -> mostrarEstadisticasTipo());
        
        btnEliminarRuta.addActionListener(e -> eliminarRutaSeleccionada());

        // Cargar todas las rutas al inicio
        cargar(gestion.mostrarTodas());
    }

    private void cargar(List<Ruta> rutas) {
        modeloTabla.setRowCount(0);

        for (Ruta r : rutas) {
            modeloTabla.addRow(new Object[]{
                    r.getIdRuta(),
                    r.getFecha(),
                    r.getOrigen(),
                    r.getDestino(),
                    r.getEmpleado().getNombre() + " " + r.getEmpleado().getApellidos(),
                    r.getVehiculoUsado().getTipo(),
                    r.getVehiculoUsado().getMatricula(),
                    r.getKmRecorridos() + " km",
                    r.getCargaTransportadaKg() + " kg",
                    String.format("%.1f%%", r.getVehiculoUsado().getPorcentajeCarga())
            });
        }
        
        lblResultados.setText("Total de rutas: " + rutas.size());
    }
    
    private void mostrarEstadisticasVehiculo() {
        var estadisticas = gestion.totalKmPorVehiculo();
        
        if (estadisticas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay datos para mostrar", 
                "Sin datos", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder mensaje = new StringBuilder("Kilómetros totales por vehículo:\n\n");
        estadisticas.forEach((matricula, km) -> 
            mensaje.append(String.format("%-12s: %,d km\n", matricula, km))
        );
        
        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JOptionPane.showMessageDialog(this, 
            new JScrollPane(textArea), 
            "Estadísticas por Vehículo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarEstadisticasTipo() {
        var estadisticas = gestion.totalKmPorTipoVehiculo();
        
        if (estadisticas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay datos para mostrar", 
                "Sin datos", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder mensaje = new StringBuilder("Kilómetros totales por tipo de vehículo:\n\n");
        estadisticas.forEach((tipo, km) -> 
            mensaje.append(String.format("%-12s: %,d km\n", tipo, km))
        );
        
        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JOptionPane.showMessageDialog(this, 
            new JScrollPane(textArea), 
            "Estadísticas por Tipo de Vehículo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminarRutaSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione una ruta de la tabla", 
                "Sin selección", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idRuta = (int) tabla.getValueAt(filaSeleccionada, 0);
        String origen = (String) tabla.getValueAt(filaSeleccionada, 2);
        String destino = (String) tabla.getValueAt(filaSeleccionada, 3);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar la ruta #" + idRuta + "\n" +
            origen + " → " + destino + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestion.eliminarRuta(idRuta);
            cargar(gestion.mostrarTodas());
            
            JOptionPane.showMessageDialog(this, 
                "Ruta eliminada correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

