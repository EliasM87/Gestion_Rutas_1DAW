package vista;

import javax.swing.*;
import java.util.List;
import modelo.dao.GestionRutas;
import modelo.javabean.Ruta;

public class PanelListadoRutas extends JPanel {

    private JTextArea area;

    public PanelListadoRutas(GestionRutas gestion) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField txtBuscar = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar por Origen");

        area = new JTextArea(20, 70);
        area.setEditable(false);

        add(new JLabel("Origen"));
        add(txtBuscar);
        add(btnBuscar);
        add(new JScrollPane(area));

        mostrar(gestion.listaRutas());

        btnBuscar.addActionListener(e ->
                mostrar(gestion.rutasPorOrigen(txtBuscar.getText())));
    }

    private void mostrar(List<Ruta> rutas) {
        area.setText("");
        for (Ruta r : rutas) {
            area.append(r.toString() + "\n");
        }
    }
}
