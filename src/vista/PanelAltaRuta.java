package vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import modelo.dao.GestionRutas;
import modelo.javabean.*;

public class PanelAltaRuta extends JPanel {

    public PanelAltaRuta(GestionRutas gestion) {

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(0,2,8,8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // ---- Ruta ----
        JTextField txtIdRuta = new JTextField();
        JTextField txtOrigen = new JTextField();
        JTextField txtDestino = new JTextField();
        JTextField txtKm = new JTextField();
        JTextField txtCarga = new JTextField();

        // ---- Empleado ----
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellidos = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtGenero = new JTextField();

        // ---- Vehículo ----
        JTextField txtMatricula = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtKmTotales = new JTextField();
        JTextField txtConsumo = new JTextField();

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Camion","Furgoneta"});
        JTextField txtCapacidad = new JTextField();
        JTextField txtEjes = new JTextField();
        JTextField txtVolumen = new JTextField();

        // ---- Añadir campos ----
        panelFormulario.add(new JLabel("ID Ruta"));
        panelFormulario.add(txtIdRuta);
        panelFormulario.add(new JLabel("Origen"));
        panelFormulario.add(txtOrigen);
        panelFormulario.add(new JLabel("Destino"));
        panelFormulario.add(txtDestino);
        panelFormulario.add(new JLabel("Km"));
        panelFormulario.add(txtKm);
        panelFormulario.add(new JLabel("Carga transportada"));
        panelFormulario.add(txtCarga);

        panelFormulario.add(new JLabel("DNI Empleado"));
        panelFormulario.add(txtDni);
        panelFormulario.add(new JLabel("Nombre"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellidos"));
        panelFormulario.add(txtApellidos);
        panelFormulario.add(new JLabel("Email"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Género"));
        panelFormulario.add(txtGenero);

        panelFormulario.add(new JLabel("Tipo Vehículo"));
        panelFormulario.add(comboTipo);
        panelFormulario.add(new JLabel("Matrícula"));
        panelFormulario.add(txtMatricula);
        panelFormulario.add(new JLabel("Marca"));
        panelFormulario.add(txtMarca);
        panelFormulario.add(new JLabel("Modelo"));
        panelFormulario.add(txtModelo);
        panelFormulario.add(new JLabel("Km Totales"));
        panelFormulario.add(txtKmTotales);
        panelFormulario.add(new JLabel("Consumo"));
        panelFormulario.add(txtConsumo);

        panelFormulario.add(new JLabel("Capacidad Kg (Camión)"));
        panelFormulario.add(txtCapacidad);
        panelFormulario.add(new JLabel("Nº Ejes (Camión)"));
        panelFormulario.add(txtEjes);
        panelFormulario.add(new JLabel("Volumen m3 (Furgoneta)"));
        panelFormulario.add(txtVolumen);

        add(panelFormulario, BorderLayout.CENTER);

        // ---- Botones ----
        JPanel panelBotones = new JPanel();

        JButton btnCrear = new JButton("Crear Ruta");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        comboTipo.addActionListener(e -> {
            boolean esCamion = comboTipo.getSelectedItem().equals("Camion");
            txtCapacidad.setEnabled(esCamion);
            txtEjes.setEnabled(esCamion);
            txtVolumen.setEnabled(!esCamion);
        });

        comboTipo.setSelectedIndex(0);

        // ---- Crear Ruta ----
        btnCrear.addActionListener(e -> {
            try {
                Empleado emp = new Empleado(
                        txtDni.getText(),
                        txtNombre.getText(),
                        txtApellidos.getText(),
                        txtEmail.getText(),
                        txtGenero.getText()
                );

                Vehiculo v;
                if(comboTipo.getSelectedItem().equals("Camion")) {
                    v = new Camion(
                            txtMatricula.getText(),
                            txtMarca.getText(),
                            txtModelo.getText(),
                            Double.parseDouble(txtKmTotales.getText()),
                            Double.parseDouble(txtConsumo.getText()),
                            Double.parseDouble(txtCapacidad.getText()),
                            Integer.parseInt(txtEjes.getText())
                    );
                } else {
                    v = new Furgoneta(
                            txtMatricula.getText(),
                            txtMarca.getText(),
                            txtModelo.getText(),
                            Double.parseDouble(txtKmTotales.getText()),
                            Double.parseDouble(txtConsumo.getText()),
                            Double.parseDouble(txtVolumen.getText())
                    );
                }

                Ruta r = new Ruta(
                        Integer.parseInt(txtIdRuta.getText()),
                        LocalDate.now(),
                        txtOrigen.getText(),
                        txtDestino.getText(),
                        v,
                        emp,
                        Double.parseDouble(txtKm.getText()),
                        Double.parseDouble(txtCarga.getText())
                );

                gestion.addRuta(r);
                JOptionPane.showMessageDialog(this, "Ruta creada correctamente");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos incorrectos");
            }
        });

        // ---- Limpiar ----
        btnLimpiar.addActionListener(e -> {
            for(Component c : panelFormulario.getComponents()) {
                if(c instanceof JTextField)
                    ((JTextField)c).setText("");
            }
        });
    }
}

