package vista;

import javax.swing.*;
import modelo.dao.GestionRutas;
import modelo.javabean.*;

public class PanelAltaRuta extends JPanel {

    public PanelAltaRuta(GestionRutas gestion) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField txtId = new JTextField(5);
        JTextField txtOrigen = new JTextField(15);
        JTextField txtDestino = new JTextField(15);
        JTextField txtKm = new JTextField(10);
        JTextField txtCarga = new JTextField(10);

        JComboBox<Vehiculo> comboVehiculos =
                new JComboBox<>(gestion.listaVehiculos().toArray(new Vehiculo[0]));

        JComboBox<Empleado> comboEmpleados =
                new JComboBox<>(gestion.listaEmpleados().toArray(new Empleado[0]));

        JButton btnCrear = new JButton("Crear Ruta");

        add(new JLabel("ID Ruta"));
        add(txtId);
        add(new JLabel("Origen"));
        add(txtOrigen);
        add(new JLabel("Destino"));
        add(txtDestino);
        add(new JLabel("Vehículo"));
        add(comboVehiculos);
        add(new JLabel("Empleado"));
        add(comboEmpleados);
        add(new JLabel("Km"));
        add(txtKm);
        add(new JLabel("Carga"));
        add(txtCarga);
        add(btnCrear);

        btnCrear.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String origen = txtOrigen.getText();
                String destino = txtDestino.getText();
                double km = Double.parseDouble(txtKm.getText());
                double carga = Double.parseDouble(txtCarga.getText());

                Vehiculo v = (Vehiculo) comboVehiculos.getSelectedItem();
                Empleado emp = (Empleado) comboEmpleados.getSelectedItem();

                boolean ok = gestion.crearRuta(
                        id, origen, destino,
                        v.getMatricula(),
                        emp.getIdEmpleado(),
                        km, carga);

                if (ok)
                    JOptionPane.showMessageDialog(this, "Ruta creada");
                else
                    JOptionPane.showMessageDialog(this, "No se pudo crear la ruta");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos incorrectos");
            }
        });
    }
}

