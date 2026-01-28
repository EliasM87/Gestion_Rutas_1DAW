package vista;

import javax.swing.*;
import modelo.dao.GestionRutas;

public class PanelAltaVehiculo extends JPanel {

    public PanelAltaVehiculo(GestionRutas gestion) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JComboBox<String> comboTipo =
                new JComboBox<>(new String[]{"Camion", "Furgoneta"});

        JTextField txtMatricula = new JTextField(10);
        JTextField txtCarga = new JTextField(10);
        JTextField txtEjes = new JTextField(5);
        JCheckBox chkRefrigerada = new JCheckBox("Refrigerada");

        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Tipo"));
        add(comboTipo);
        add(new JLabel("Matrícula"));
        add(txtMatricula);
        add(new JLabel("Carga máxima"));
        add(txtCarga);
        add(new JLabel("Número de ejes"));
        add(txtEjes);
        add(chkRefrigerada);
        add(btnGuardar);

        comboTipo.addActionListener(e -> {
            boolean esCamion = comboTipo.getSelectedItem().equals("Camion");
            txtEjes.setEnabled(esCamion);
            chkRefrigerada.setEnabled(!esCamion);
        });

        comboTipo.setSelectedIndex(0);

        btnGuardar.addActionListener(e -> {
            try {
                String mat = txtMatricula.getText();
                double carga = Double.parseDouble(txtCarga.getText());

                boolean ok;

                if (comboTipo.getSelectedItem().equals("Camion")) {
                    int ejes = Integer.parseInt(txtEjes.getText());
                    ok = gestion.altaCamion(mat, carga, ejes);
                } else {
                    boolean ref = chkRefrigerada.isSelected();
                    ok = gestion.altaFurgoneta(mat, carga, ref);
                }

                if (ok)
                    JOptionPane.showMessageDialog(this, "Vehículo creado");
                else
                    JOptionPane.showMessageDialog(this, "La matrícula ya existe");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos incorrectos");
            }
        });
    }
}
