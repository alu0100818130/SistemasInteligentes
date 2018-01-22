package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Conexion;
import controller.Operaciones;

@SuppressWarnings("serial")
public class AddToBDDPanel extends JPanel{
	private JTextField cartaField = new JTextField("-", 27);
	private JTextField cantidadField = new JTextField("", 27);
	private Connection con;
	private Operaciones op;
	private Conexion cn;
	private JButton doneButton = new JButton("Añadir a la Base de Datos");
	public AddToBDDPanel(GlobalAnalizePanel father) {
        setCn(new Conexion());
        setOp(new Operaciones());
        setCon(cn.AccederBD(false));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		// --------------------------------------------------------------
		JPanel introPanel = new JPanel();
		introPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		introPanel.add(new JLabel("             Añadir cartas a la Base de Datos             "));
		introPanel.add(new JLabel("(La edición es la seleccionada bajo la carta)"));
		// --------------------------------------------------------------
		JPanel panelGeneral = new JPanel(); 
		panelGeneral.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelGeneral.setBorder(BorderFactory.createEmptyBorder(00, 20, 20, 20));
		// --------------------------------------------------------------
		JPanel Carta = new JPanel();
		Carta.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		getCartaField().setEnabled(false);
		getCartaField().setHorizontalAlignment(JTextField.CENTER); 
		if(father.getActualAnalizedCard() != null) {
			getCartaField().setText(father.getActualAnalizedCard().getNombreCarta());
		}
		Carta.add(new JLabel("        CARTA:    "));
		Carta.add(getCartaField());
		// --------------------------------------------------------------
		JPanel Cantidad = new JPanel();
		Cantidad.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		getCantidadField().setHorizontalAlignment(JTextField.CENTER); 
		Cantidad.add(new JLabel("  CANTIDAD:    "));
		Cantidad.add(getCantidadField());
		// --------------------------------------------------------------
		JPanel donePanel = new JPanel();
		donePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		donePanel.add(getDoneButton());
		// --------------------------------------------------------------
		add(Box.createRigidArea(new Dimension(0, 20)));
		panelGeneral.add(Carta);
		panelGeneral.add(Cantidad);
		panelGeneral.add(donePanel);
		add(introPanel);
		add(panelGeneral);
		add(Box.createRigidArea(new Dimension(0, 30)));
		setController(father);
	}
	public void setController(GlobalAnalizePanel father) {
		getDoneButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(!getCartaField().getText().equals("-")) {
					try{
						int cantidad = Integer.parseInt(getCantidadField().getText());
						getOp().insert("Cartas","NOMBRE, EDICION, CANTIDAD","'" + father.getActualAnalizedCard().getNombreCarta() + "','" + father.getActualAnalizedCard().getVersiones().get(father.getActualVersionToShow()).getExpansion() + "','" + cantidad + "'", getCon());
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null,"Error al añadir entrada a la BDD");
					};
				}
			}
		});
	}
	public JTextField getCartaField() { return cartaField; }
	public void setCartaField(JTextField cartaField) { this.cartaField = cartaField; }
	public JButton getDoneButton() { return doneButton; }
	public void setDoneButton(JButton doneButton) { this.doneButton = doneButton; }
	public JTextField getCantidadField() { return cantidadField; }
	public void setCantidadField(JTextField cantidadField) { this.cantidadField = cantidadField; }
	public Connection getCon() { return con; }
	public void setCon(Connection con) { this.con = con; }
	public Operaciones getOp() { return op; }
	public void setOp(Operaciones op) { this.op = op; }
	public Conexion getCn() { return cn; }
	public void setCn(Conexion cn) { this.cn = cn; }
}
