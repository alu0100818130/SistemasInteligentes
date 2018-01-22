package view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class TablePanel extends JPanel{
	public TablePanel(JTable tabla) {
		this.setLayout(new BorderLayout(30, 30));
		tabla.setEnabled(false);
		this.add(new JScrollPane(tabla));
	}
}
