package view;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import controller.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private JPanel analizeTab = new JPanel();
	private JPanel dataBaseTab = new JPanel();
	// private JPanel fastSearchDBTab = new JPanel();    POSIBLE IMPLEMENTACIÓN EXTRA
	private JTabbedPane pestanas = new JTabbedPane();
	private Conexion cn;
	public MainFrame() throws IOException {
        cn = new Conexion();
        cn.AccederBD(true);
        this.addWindowListener(new java.awt.event.WindowAdapter() { // CLAVE
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
		// ---- INITIAL CONFIG ----
		setAnalizeTab(new GlobalAnalizePanel(this));
		setDataBaseTab(new GlobalDataBasePanel());
		this.getPestanas().addTab("Analize", getAnalizeTab());
		this.getPestanas().addTab("Base de Datos", getDataBaseTab());
		// this.getPestanas().addTab("Búsqueda Rápida en BDD", getFastSearchDBTab()); POSIBLE IMPLEMENTACIÓN EXTRA
		getContentPane().add(this.getPestanas());
		// Detalles de Ventana
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// this.setMaximumSize(new Dimension(1080, 720));
		this.setMinimumSize(new Dimension(1080, 720));
		this.setResizable(false);
		this.setVisible(true);
	}
	public JPanel getAnalizeTab() { return analizeTab; }
	public void setAnalizeTab(JPanel analizeTab) { this.analizeTab = analizeTab; }
	public JPanel getDataBaseTab() { return dataBaseTab; }
	public void setDataBaseTab(JPanel dataBaseTab) { this.dataBaseTab = dataBaseTab; }
	// public JPanel getFastSearchDBTab() { return fastSearchDBTab; }										POSIBLE IMPLEMENTACIÓN EXTRA
	// public void setFastSearchDBTab(JPanel fastSearchDBTab) { this.fastSearchDBTab = fastSearchDBTab; }   POSIBLE IMPLEMENTACIÓN EXTRA
	public JTabbedPane getPestanas() { return pestanas; }
	public void setPestanas(JTabbedPane pestanas) { this.pestanas = pestanas; }
}
