package view;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GlobalDataPanel extends JPanel{
	private SubGlobalDataPanel sGDP;
	public GlobalDataPanel(GlobalAnalizePanel father){
		setsGDP(new SubGlobalDataPanel(father));
		this.setLayout(new BorderLayout(30, 30));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(getsGDP());
	}
	public SubGlobalDataPanel getsGDP() { return sGDP; }
	public void setsGDP(SubGlobalDataPanel sGDP) { this.sGDP = sGDP; }
}
