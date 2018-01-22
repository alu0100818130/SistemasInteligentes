package view;

import java.awt.BorderLayout;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;

import controller.Conexion;
import controller.Operaciones;

@SuppressWarnings("serial")
public class GlobalDataBasePanel extends JPanel{
	private JTable table;
	private JPanel tablePanel;
	private JPanel buttonsPanel;
	private Connection con;
	private Operaciones op;
	private Conexion cn;
	private String[] columnas = { "NOMBRE", "EDICION", "CANTIDAD" };
	public GlobalDataBasePanel() {
		// --------------------------------------------------
        setCn(new Conexion());
        setOp(new Operaciones());
        setCon(getCn().AccederBD(false));
        setTable(new JTable(getOp().getCartas(getCon()), getColumnas()));
		// --------------------------------------------------
		setTablePanel(new TablePanel(getTable())); 
		setButtonsPanel(new ButtonsPanel(this));
		this.setLayout(new BorderLayout(30, 30));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.EAST);
	}
	public JTable getTable() { return table; }
	public void setTable(JTable table) { this.table = table; }
	public JPanel getTablePanel() { return tablePanel; }
	public void setTablePanel(JPanel tablePanel) { this.tablePanel = tablePanel; }
	public JPanel getButtonsPanel() { return buttonsPanel; }
	public void setButtonsPanel(JPanel buttonsPanel) { this.buttonsPanel = buttonsPanel; }
	public Connection getCon() { return con; }
	public void setCon(Connection con) { this.con = con; }
	public Operaciones getOp() { return op; }
	public void setOp(Operaciones op) { this.op = op; }
	public Conexion getCn() { return cn; }
	public void setCn(Conexion cn) { this.cn = cn; }
	public String[] getColumnas() { return columnas; }
	public void setColumnas(String[] columnas) { this.columnas = columnas; }
}
