package vista;

import javax.swing.*;
import modelo.dao.GestionRutas;

public class PanelAltaEmpleado extends JPanel {

    public PanelAltaEmpleado(GestionRutas gestion) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField txtId = new JTextField(10);
        JTextField txtNombre = new JTextField(20);
        JTextField txtCategoria = new JTextField(15);

        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("ID Empleado"));
        add(txtId);
        add(new JLabel("Nombre"));
        add(txtNombre);
        add(new JLabel("Categoría"));
        add(txtCategoria);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();
                String categoria = txtCategoria.getText();

                boolean ok = gestion.altaEmpleado(id, nombre, categoria);

                if (ok)
                    JOptionPane.showMessageDialog(this, "Empleado creado");
                else
                    JOptionPane.showMessageDialog(this, "El empleado ya existe");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos incorrectos");
            }
        });
    }
}


