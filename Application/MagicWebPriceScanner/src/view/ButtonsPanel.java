package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class ButtonsPanel extends JPanel {
	private JButton actualizarButton = new JButton("Actualizar");
	public ButtonsPanel(GlobalDataBasePanel father) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(getActualizarButton());
		this.setController(father);
	}
	public void setController(GlobalDataBasePanel father) {
		getActualizarButton().addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				father.setTable(new JTable(father.getOp().getCartas(father.getCon()), father.getColumnas()));
				father.setTablePanel(new TablePanel(father.getTable()));
				father.setButtonsPanel(new ButtonsPanel(father));
				Component[] componentList = father.getComponents();
				for(Component c : componentList){
				    if(c instanceof JPanel){ father.remove(c); }
				}
				father.add(father.getTablePanel(), BorderLayout.CENTER);
				father.add(father.getButtonsPanel(), BorderLayout.EAST);
			}
		});
	}
	public JButton getActualizarButton() { return actualizarButton; }
	public void setActualizarButton(JButton actualizarButton) { this.actualizarButton = actualizarButton; }
}
