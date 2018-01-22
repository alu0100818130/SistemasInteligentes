package view;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.AlmacenVersiones;

@SuppressWarnings("serial")
public class GlobalAnalizePanel extends JPanel{
	private int actualVersionToShow = 0;
	private SubAnalizePanel sAP;
	private GlobalDataPanel gDP;
	private ImageIcon icon = new ImageIcon();
	private AlmacenVersiones actualAnalizedCard = null;
	private String URLstring;
	private MainFrame mainFrame;
	public GlobalAnalizePanel(MainFrame mf) {
		setMainFrame(mf);
	    java.net.URL imgURL = getClass().getResource("Card.png");
	    if (imgURL != null) { 
	    	icon = new ImageIcon(imgURL, "ImageToAnalize"); 
	    	setURLstring(imgURL.toString().replaceAll("file:/", ""));
	    } 
	    else { System.out.println("Couldn't find file: " + "Exmples/Card.png"); }
		setsAP(new SubAnalizePanel(this));
		setgDP(new GlobalDataPanel(this));
		this.setLayout(new BorderLayout(30, 30));
		this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
		this.add(getsAP(), BorderLayout.WEST);
		this.add(getgDP(), BorderLayout.CENTER);
	}
	public int getActualVersionToShow() { return actualVersionToShow; }
	public void setActualVersionToShow(int actualVersionToShow) { this.actualVersionToShow = actualVersionToShow; }
	public SubAnalizePanel getsAP() { return sAP; }
	public void setsAP(SubAnalizePanel sAP) { this.sAP = sAP; }
	public GlobalDataPanel getgDP() { return gDP; }
	public void setgDP(GlobalDataPanel gDP) { this.gDP = gDP; }
	public ImageIcon getIcon() { return icon; }
	public void setIcon(ImageIcon icon) { this.icon = icon; }
	public AlmacenVersiones getActualAnalizedCard() { return actualAnalizedCard; }
	public void setActualAnalizedCard(AlmacenVersiones actualAnalizedCard) { this.actualAnalizedCard = actualAnalizedCard; }
	public String getURLstring() { return URLstring; }
	public void setURLstring(String uRLstring) { URLstring = uRLstring; }
	public MainFrame getMainFrame() { return mainFrame; }
	public void setMainFrame(MainFrame mainFrame) { this.mainFrame = mainFrame; }
}
