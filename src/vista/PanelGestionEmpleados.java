package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.dao.GestionRutas;
import modelo.javabean.Empleado;

public class PanelGestionEmpleados extends JPanel {

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private GestionRutas gestion;
    
    // Campos del formulario
    private JTextField txtDni;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JComboBox<String> comboGenero;

    public PanelGestionEmpleados(GestionRutas gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ========== PANEL SUPERIOR: FORMULARIO ==========
        JPanel panelFormulario = crearPanelFormulario();
        add(panelFormulario, BorderLayout.NORTH);

        // ========== PANEL CENTRAL: TABLA ==========
        JPanel panelTabla = crearPanelTabla();
        add(panelTabla, BorderLayout.CENTER);

        // Cargar empleados iniciales
        actualizarTabla();
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Datos del Empleado"));

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtDni = new JTextField();
        txtNombre = new JTextField();
        txtApellidos = new JTextField();
        txtEmail = new JTextField();
        comboGenero = new JComboBox<>(new String[]{"H", "M", "Otro"});

        panelCampos.add(new JLabel("DNI:"));
        panelCampos.add(txtDni);
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Apellidos:"));
        panelCampos.add(txtApellidos);
        panelCampos.add(new JLabel("Email:"));
        panelCampos.add(txtEmail);
        panelCampos.add(new JLabel("Género:"));
        panelCampos.add(comboGenero);

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
        btnAgregar.addActionListener(e -> agregarEmpleado());
        btnModificar.addActionListener(e -> modificarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Lista de Empleados"));

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellidos");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Género");

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);

        // Al seleccionar una fila, cargar datos en el formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarEmpleadoEnFormulario();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void agregarEmpleado() {
        try {
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String email = txtEmail.getText().trim();
            String genero = (String) comboGenero.getSelectedItem();

            // Validaciones básicas
            if (dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, complete todos los campos", 
                    "Campos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si el empleado ya existe
            if (gestion.buscarEmpleadoPorDni(dni) != null) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe un empleado con ese DNI", 
                    "DNI duplicado", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Empleado empleado = new Empleado(dni, nombre, apellidos, email, genero);
            gestion.addEmpleado(empleado);

            JOptionPane.showMessageDialog(this, 
                "Empleado agregado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            actualizarTabla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al agregar empleado: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEmpleado() {
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un empleado de la tabla", 
                "Sin selección", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String dniOriginal = (String) tabla.getValueAt(filaSeleccionada, 0);
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String email = txtEmail.getText().trim();
            String genero = (String) comboGenero.getSelectedItem();

            if (dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, complete todos los campos", 
                    "Campos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            Empleado empleado = new Empleado(dni, nombre, apellidos, email, genero);
            gestion.modificarEmpleado(dniOriginal, empleado);

            JOptionPane.showMessageDialog(this, 
                "Empleado modificado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            actualizarTabla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al modificar empleado: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpleado() {
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un empleado de la tabla", 
                "Sin selección", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dni = (String) tabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) tabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar al empleado " + nombre + " (" + dni + ")?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                gestion.eliminarEmpleado(dni);
                
                JOptionPane.showMessageDialog(this, 
                    "Empleado eliminado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();
                actualizarTabla();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar empleado: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarEmpleadoEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtDni.setText((String) tabla.getValueAt(fila, 0));
            txtNombre.setText((String) tabla.getValueAt(fila, 1));
            txtApellidos.setText((String) tabla.getValueAt(fila, 2));
            txtEmail.setText((String) tabla.getValueAt(fila, 3));
            comboGenero.setSelectedItem(tabla.getValueAt(fila, 4));
        }
    }

    private void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        comboGenero.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<Empleado> empleados = gestion.obtenerTodosEmpleados();
        
        for (Empleado emp : empleados) {
            modeloTabla.addRow(new Object[]{
                emp.getDni(),
                emp.getNombre(),
                emp.getApellidos(),
                emp.getEmail(),
                emp.getGenero()
            });
        }
    }
}