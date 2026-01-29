package vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import modelo.dao.GestionRutas;
import modelo.javabean.*;

public class PanelAltaRuta extends JPanel {

    private GestionRutas gestion;
    private JTextField txtIdRuta;
    private JTextField txtFecha;
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JTextField txtKm;
    private JTextField txtCarga;
    private JComboBox<String> comboEmpleado;
    private JComboBox<String> comboVehiculo;
    private JTextArea txtDetallesEmpleado;
    private JTextArea txtDetallesVehiculo;

    public PanelAltaRuta(GestionRutas gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel principal con dos columnas
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 15, 0));

        // Panel izquierdo: Datos de la ruta
        JPanel panelRuta = crearPanelDatosRuta();
        panelPrincipal.add(panelRuta);

        // Panel derecho: Selección de empleado y vehículo
        JPanel panelSeleccion = crearPanelSeleccion();
        panelPrincipal.add(panelSeleccion);

        add(panelPrincipal, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarEmpleados();
        cargarVehiculos();
    }

    private JPanel crearPanelDatosRuta() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Datos de la Ruta"));

        JPanel campos = new JPanel(new GridLayout(6, 2, 10, 10));
        campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtIdRuta = new JTextField();
        txtFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtOrigen = new JTextField();
        txtDestino = new JTextField();
        txtKm = new JTextField();
        txtCarga = new JTextField();

        campos.add(new JLabel("ID Ruta:"));
        campos.add(txtIdRuta);
        campos.add(new JLabel("Fecha (dd/MM/yyyy):"));
        campos.add(txtFecha);
        campos.add(new JLabel("Origen:"));
        campos.add(txtOrigen);
        campos.add(new JLabel("Destino:"));
        campos.add(txtDestino);
        campos.add(new JLabel("Kilómetros:"));
        campos.add(txtKm);
        campos.add(new JLabel("Carga (kg):"));
        campos.add(txtCarga);

        panel.add(campos, BorderLayout.CENTER);

        // Panel informativo
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblInfo = new JLabel("<html><i>💡 Complete los datos de la ruta y seleccione<br>" +
                                    "el empleado y vehículo de las listas</i></html>");
        lblInfo.setForeground(new Color(100, 100, 100));
        panelInfo.add(lblInfo, BorderLayout.CENTER);
        panel.add(panelInfo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelSeleccion() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));

        // Panel de empleados
        JPanel panelEmpleado = new JPanel(new BorderLayout(5, 5));
        panelEmpleado.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Seleccionar Empleado"));

        comboEmpleado = new JComboBox<>();
        comboEmpleado.addActionListener(e -> mostrarDetallesEmpleado());

        txtDetallesEmpleado = new JTextArea(3, 20);
        txtDetallesEmpleado.setEditable(false);
        txtDetallesEmpleado.setBackground(new Color(245, 245, 245));
        txtDetallesEmpleado.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtDetallesEmpleado.setFont(new Font("Monospaced", Font.PLAIN, 11));

        panelEmpleado.add(comboEmpleado, BorderLayout.NORTH);
        panelEmpleado.add(new JScrollPane(txtDetallesEmpleado), BorderLayout.CENTER);

        // Panel de vehículos
        JPanel panelVehiculo = new JPanel(new BorderLayout(5, 5));
        panelVehiculo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Seleccionar Vehículo"));

        comboVehiculo = new JComboBox<>();
        comboVehiculo.addActionListener(e -> mostrarDetallesVehiculo());

        txtDetallesVehiculo = new JTextArea(3, 20);
        txtDetallesVehiculo.setEditable(false);
        txtDetallesVehiculo.setBackground(new Color(245, 245, 245));
        txtDetallesVehiculo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtDetallesVehiculo.setFont(new Font("Monospaced", Font.PLAIN, 11));

        panelVehiculo.add(comboVehiculo, BorderLayout.NORTH);
        panelVehiculo.add(new JScrollPane(txtDetallesVehiculo), BorderLayout.CENTER);

        panel.add(panelEmpleado);
        panel.add(panelVehiculo);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnCrear = new JButton("✅ Crear Ruta");
        JButton btnLimpiar = new JButton("🔄 Limpiar");
        JButton btnRefrescar = new JButton("↻ Refrescar Listas");

        btnCrear.setBackground(new Color(76, 175, 80));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFont(new Font("Arial", Font.BOLD, 14));
        btnCrear.setPreferredSize(new Dimension(150, 35));

        btnLimpiar.setPreferredSize(new Dimension(120, 35));
        btnRefrescar.setPreferredSize(new Dimension(150, 35));

        panel.add(btnCrear);
        panel.add(btnLimpiar);
        panel.add(btnRefrescar);

        // Eventos
        btnCrear.addActionListener(e -> crearRuta());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRefrescar.addActionListener(e -> {
            cargarEmpleados();
            cargarVehiculos();
        });

        return panel;
    }

    private void cargarEmpleados() {
        comboEmpleado.removeAllItems();
        List<Empleado> empleados = gestion.obtenerTodosEmpleados();
        
        if (empleados.isEmpty()) {
            comboEmpleado.addItem("No hay empleados registrados");
            txtDetallesEmpleado.setText("Registre empleados en la pestaña 'Gestión Empleados'");
        } else {
            for (Empleado emp : empleados) {
                comboEmpleado.addItem(emp.getDni() + " - " + emp.getNombre() + " " + emp.getApellidos());
            }
            mostrarDetallesEmpleado();
        }
    }

    private void cargarVehiculos() {
        comboVehiculo.removeAllItems();
        List<Vehiculo> vehiculos = gestion.obtenerTodosVehiculos();
        
        if (vehiculos.isEmpty()) {
            comboVehiculo.addItem("No hay vehículos registrados");
            txtDetallesVehiculo.setText("Registre vehículos en la pestaña 'Gestión Vehículos'");
        } else {
            for (Vehiculo v : vehiculos) {
                comboVehiculo.addItem(v.getMatricula() + " - " + v.getMarca() + " " + v.getModelo());
            }
            mostrarDetallesVehiculo();
        }
    }

    private void mostrarDetallesEmpleado() {
        if (comboEmpleado.getSelectedItem() == null || 
            comboEmpleado.getSelectedItem().toString().contains("No hay")) {
            return;
        }

        String seleccion = comboEmpleado.getSelectedItem().toString();
        String dni = seleccion.split(" - ")[0];
        Empleado emp = gestion.buscarEmpleadoPorDni(dni);

        if (emp != null) {
            txtDetallesEmpleado.setText(
                "DNI: " + emp.getDni() + "\n" +
                "Nombre: " + emp.getNombre() + " " + emp.getApellidos() + "\n" +
                "Email: " + emp.getEmail() + "\n" +
                "Género: " + emp.getGenero()
            );
        }
    }

    private void mostrarDetallesVehiculo() {
        if (comboVehiculo.getSelectedItem() == null || 
            comboVehiculo.getSelectedItem().toString().contains("No hay")) {
            return;
        }

        String seleccion = comboVehiculo.getSelectedItem().toString();
        String matricula = seleccion.split(" - ")[0];
        Vehiculo v = gestion.buscarVehiculoPorMatricula(matricula);

        if (v != null) {
            String detalles = "Matrícula: " + v.getMatricula() + "\n" +
                            "Marca/Modelo: " + v.getMarca() + " " + v.getModelo() + "\n" +
                            "Tipo: " + v.getTipo() + "\n" +
                            "Km Totales: " + v.getKilometrosTotales() + " km\n" +
                            "Consumo: " + v.getConsumoLitros100km() + " L/100km\n";

            if (v instanceof Camion c) {
                //Camion c = (Camion) v;
                detalles += "Capacidad: " + c.getCapacidadCargaKg() + " kg\n" +
                          "Nº Ejes: " + c.getNumeroEjes();
            } else if (v instanceof Furgoneta f) {
               // Furgoneta f = (Furgoneta) v;
                detalles += "Volumen: " + f.getVolumenCargaM3() + " m³";
            }

            txtDetallesVehiculo.setText(detalles);
        }
    }

    private void crearRuta() {
        try {
            // Validar campos vacíos
            if (txtIdRuta.getText().trim().isEmpty() || txtFecha.getText().trim().isEmpty() ||
                txtOrigen.getText().trim().isEmpty() || txtDestino.getText().trim().isEmpty() ||
                txtKm.getText().trim().isEmpty() || txtCarga.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos de la ruta",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar selección de empleado
            if (comboEmpleado.getSelectedItem() == null || 
                comboEmpleado.getSelectedItem().toString().contains("No hay")) {
                JOptionPane.showMessageDialog(this,
                    "Debe registrar y seleccionar un empleado",
                    "Empleado no seleccionado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar selección de vehículo
            if (comboVehiculo.getSelectedItem() == null || 
                comboVehiculo.getSelectedItem().toString().contains("No hay")) {
                JOptionPane.showMessageDialog(this,
                    "Debe registrar y seleccionar un vehículo",
                    "Vehículo no seleccionado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtener datos
            int idRuta = Integer.parseInt(txtIdRuta.getText().trim());
            
            // Parsear fecha
            LocalDate fecha;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = LocalDate.parse(txtFecha.getText().trim(), formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                    "Formato de fecha incorrecto. Use dd/MM/yyyy",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String origen = txtOrigen.getText().trim();
            String destino = txtDestino.getText().trim();
            double km = Double.parseDouble(txtKm.getText().trim());
            double carga = Double.parseDouble(txtCarga.getText().trim());

            // Obtener empleado y vehículo seleccionados
            String dniEmpleado = comboEmpleado.getSelectedItem().toString().split(" - ")[0];
            String matriculaVehiculo = comboVehiculo.getSelectedItem().toString().split(" - ")[0];

            Empleado empleado = gestion.buscarEmpleadoPorDni(dniEmpleado);
            Vehiculo vehiculo = gestion.buscarVehiculoPorMatricula(matriculaVehiculo);

            if (empleado == null || vehiculo == null) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener empleado o vehículo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar capacidad del vehículo
            double capacidadMaxima = 0;
            if (vehiculo instanceof Camion c) {
                capacidadMaxima = c.getCapacidadCargaKg();
            } else if (vehiculo instanceof Furgoneta f) {
                capacidadMaxima = f.getVolumenCargaM3();
            }

            if (carga > capacidadMaxima) {
                int respuesta = JOptionPane.showConfirmDialog(this,
                    "La carga (" + carga + " kg) excede la capacidad del vehículo (" + 
                    capacidadMaxima + " kg).\n¿Desea continuar de todos modos?",
                    "Advertencia de capacidad",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (respuesta != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Crear ruta
            Ruta ruta = new Ruta(idRuta, fecha, origen, destino, vehiculo, empleado, km, carga);
            gestion.addRuta(ruta);

            JOptionPane.showMessageDialog(this,
                "Ruta creada correctamente\n\n" +
                "ID: " + idRuta + "\n" +
                "Origen: " + origen + " → Destino: " + destino + "\n" +
                "Empleado: " + empleado.getNombre() + "\n" +
                "Vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo(),
                "Ruta creada",
                JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese valores numéricos válidos",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al crear ruta: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtIdRuta.setText("");
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtOrigen.setText("");
        txtDestino.setText("");
        txtKm.setText("");
        txtCarga.setText("");
        
        if (comboEmpleado.getItemCount() > 0) {
            comboEmpleado.setSelectedIndex(0);
        }
        if (comboVehiculo.getItemCount() > 0) {
            comboVehiculo.setSelectedIndex(0);
        }
    }
}