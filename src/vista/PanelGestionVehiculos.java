package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.dao.GestionRutas;
import modelo.javabean.*;

public class PanelGestionVehiculos extends JPanel {

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private GestionRutas gestion;
    
    // Campos comunes
    private JTextField txtMatricula;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtKmTotales;
    private JTextField txtConsumo;
    private JComboBox<String> comboTipo;
    
    // Campos específicos
    private JTextField txtCapacidad;  // Para Camión
    private JTextField txtEjes;       // Para Camión
    private JTextField txtVolumen;    // Para Furgoneta
    
    private JLabel lblCapacidad;
    private JLabel lblEjes;
    private JLabel lblVolumen;

    public PanelGestionVehiculos(GestionRutas gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior: Formulario
        JPanel panelFormulario = crearPanelFormulario();
        add(panelFormulario, BorderLayout.NORTH);

        // Panel central: Tabla
        JPanel panelTabla = crearPanelTabla();
        add(panelTabla, BorderLayout.CENTER);

        // Cargar vehículos iniciales
        actualizarTabla();
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Datos del Vehículo"));

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(8, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtMatricula = new JTextField();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtKmTotales = new JTextField();
        txtConsumo = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"Camión", "Furgoneta"});
        txtCapacidad = new JTextField();
        txtEjes = new JTextField();
        txtVolumen = new JTextField();

        // Labels
        lblCapacidad = new JLabel("Capacidad (kg):");
        lblEjes = new JLabel("Nº Ejes:");
        lblVolumen = new JLabel("Volumen (m³):");

        panelCampos.add(new JLabel("Tipo:"));
        panelCampos.add(comboTipo);
        panelCampos.add(new JLabel("Matrícula:"));
        panelCampos.add(txtMatricula);
        panelCampos.add(new JLabel("Marca:"));
        panelCampos.add(txtMarca);
        panelCampos.add(new JLabel("Modelo:"));
        panelCampos.add(txtModelo);
        panelCampos.add(new JLabel("Km Totales:"));
        panelCampos.add(txtKmTotales);
        panelCampos.add(new JLabel("Consumo (L/100km):"));
        panelCampos.add(txtConsumo);
        panelCampos.add(lblCapacidad);
        panelCampos.add(txtCapacidad);
        panelCampos.add(lblEjes);
        panelCampos.add(txtEjes);

        panel.add(panelCampos, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton btnAgregar = new JButton("➕ Agregar");
        JButton btnModificar = new JButton("✏️ Modificar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnLimpiar = new JButton("🔄 Limpiar");

        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnModificar.setBackground(new Color(33, 150, 243));
        btnModificar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        // ========== EVENTOS ==========
        comboTipo.addActionListener(e -> actualizarCamposSegunTipo());
        btnAgregar.addActionListener(e -> agregarVehiculo());
        btnModificar.addActionListener(e -> modificarVehiculo());
        btnEliminar.addActionListener(e -> eliminarVehiculo());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Inicializar campos según tipo
        actualizarCamposSegunTipo();

        return panel;
    }

    private void actualizarCamposSegunTipo() {
        boolean esCamion = comboTipo.getSelectedItem().equals("Camión");
        
        txtCapacidad.setEnabled(esCamion);
        txtEjes.setEnabled(esCamion);
        txtVolumen.setEnabled(!esCamion);
        
        lblCapacidad.setEnabled(esCamion);
        lblEjes.setEnabled(esCamion);
        lblVolumen.setEnabled(!esCamion);
        
        if (esCamion) {
            txtVolumen.setText("");
        } else {
            txtCapacidad.setText("");
            txtEjes.setText("");
        }
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Lista de Vehículos"));

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Matrícula");
        modeloTabla.addColumn("Marca");
        modeloTabla.addColumn("Modelo");
        modeloTabla.addColumn("Km Totales");
        modeloTabla.addColumn("Consumo");
        modeloTabla.addColumn("Detalles");

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);

        // Al seleccionar una fila, cargar datos en el formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarVehiculoEnFormulario();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void agregarVehiculo() {
        try {
            String matricula = txtMatricula.getText().trim();
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String kmTotalesStr = txtKmTotales.getText().trim();
            String consumoStr = txtConsumo.getText().trim();

            // Validaciones básicas
            if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty() || 
                kmTotalesStr.isEmpty() || consumoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, complete todos los campos obligatorios", 
                    "Campos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si el vehículo ya existe
            if (gestion.buscarVehiculoPorMatricula(matricula) != null) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe un vehículo con esa matrícula", 
                    "Matrícula duplicada", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            double kmTotales = Double.parseDouble(kmTotalesStr);
            double consumo = Double.parseDouble(consumoStr);

            Vehiculo vehiculo;
            
            if (comboTipo.getSelectedItem().equals("Camión")) {
                String capacidadStr = txtCapacidad.getText().trim();
                String ejesStr = txtEjes.getText().trim();
                
                if (capacidadStr.isEmpty() || ejesStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Complete los campos específicos del camión", 
                        "Campos incompletos", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                double capacidad = Double.parseDouble(capacidadStr);
                int ejes = Integer.parseInt(ejesStr);
                
                vehiculo = new Camion(matricula, marca, modelo, kmTotales, consumo, capacidad, ejes);
                
            } else {
                String volumenStr = txtVolumen.getText().trim();
                
                if (volumenStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Complete el volumen de la furgoneta", 
                        "Campo incompleto", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                double volumen = Double.parseDouble(volumenStr);
                vehiculo = new Furgoneta(matricula, marca, modelo, kmTotales, consumo, volumen);
            }

            gestion.addVehiculo(vehiculo);

            JOptionPane.showMessageDialog(this, 
                "Vehículo agregado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            actualizarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese valores numéricos válidos", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al agregar vehículo: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarVehiculo() {
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un vehículo de la tabla", 
                "Sin selección", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String matriculaOriginal = (String) tabla.getValueAt(filaSeleccionada, 1);
            String matricula = txtMatricula.getText().trim();
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            double kmTotales = Double.parseDouble(txtKmTotales.getText().trim());
            double consumo = Double.parseDouble(txtConsumo.getText().trim());

            Vehiculo vehiculo;
            
            if (comboTipo.getSelectedItem().equals("Camión")) {
                double capacidad = Double.parseDouble(txtCapacidad.getText().trim());
                int ejes = Integer.parseInt(txtEjes.getText().trim());
                vehiculo = new Camion(matricula, marca, modelo, kmTotales, consumo, capacidad, ejes);
            } else {
                double volumen = Double.parseDouble(txtVolumen.getText().trim());
                vehiculo = new Furgoneta(matricula, marca, modelo, kmTotales, consumo, volumen);
            }

            gestion.modificarVehiculo(matriculaOriginal, vehiculo);

            JOptionPane.showMessageDialog(this, 
                "Vehículo modificado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            actualizarTabla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al modificar vehículo: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarVehiculo() {
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un vehículo de la tabla", 
                "Sin selección", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) tabla.getValueAt(filaSeleccionada, 1);
        String descripcion = tabla.getValueAt(filaSeleccionada, 2) + " " + 
                           tabla.getValueAt(filaSeleccionada, 3);

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el vehículo " + descripcion + " (" + matricula + ")?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                gestion.eliminarVehiculo(matricula);
                
                JOptionPane.showMessageDialog(this, 
                    "Vehículo eliminado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();
                actualizarTabla();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar vehículo: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarVehiculoEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            String tipo = (String) tabla.getValueAt(fila, 0);
            comboTipo.setSelectedItem(tipo);
            
            txtMatricula.setText((String) tabla.getValueAt(fila, 1));
            txtMarca.setText((String) tabla.getValueAt(fila, 2));
            txtModelo.setText((String) tabla.getValueAt(fila, 3));
            txtKmTotales.setText(tabla.getValueAt(fila, 4).toString());
            txtConsumo.setText(tabla.getValueAt(fila, 5).toString());
            
            String detalles = (String) tabla.getValueAt(fila, 6);
            
            if (tipo.equals("Camión")) {
                // Parsear detalles del camión
                String[] partes = detalles.split(" - ");
                if (partes.length >= 2) {
                    txtCapacidad.setText(partes[0].replaceAll("[^0-9.]", ""));
                    txtEjes.setText(partes[1].replaceAll("[^0-9]", ""));
                }
            } else {
                // Parsear detalles de la furgoneta
                txtVolumen.setText(detalles.replaceAll("[^0-9.]", ""));
            }
        }
    }

    private void limpiarCampos() {
        txtMatricula.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtKmTotales.setText("");
        txtConsumo.setText("");
        txtCapacidad.setText("");
        txtEjes.setText("");
        txtVolumen.setText("");
        comboTipo.setSelectedIndex(0);
        tabla.clearSelection();
        actualizarCamposSegunTipo();
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<Vehiculo> vehiculos = gestion.obtenerTodosVehiculos();
        
        for (Vehiculo v : vehiculos) {
            String detalles;
            if (v instanceof Camion c) {
                //Camion c = (Camion) v;
                detalles = c.getCapacidadCargaKg() + " kg - " + c.getNumeroEjes() + " ejes";
            } else {
                Furgoneta f = (Furgoneta) v;
                detalles = f.getVolumenCargaM3() + " m³";
            }
            
            modeloTabla.addRow(new Object[]{
                v.getTipo(),
                v.getMatricula(),
                v.getMarca(),
                v.getModelo(),
                v.getKilometrosTotales(),
                v.getConsumoLitros100km(),
                detalles
            });
        }
    }
}