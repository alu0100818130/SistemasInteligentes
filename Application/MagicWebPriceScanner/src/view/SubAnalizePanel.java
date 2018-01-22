package view;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.AlmacenVersiones;

@SuppressWarnings("serial")
public class SubAnalizePanel extends JPanel{
	private boolean error = false;
	private boolean found = false;
	private JLabel text;
	private JTextField nombreCarta = new JTextField("-", 27);
	private JComboBox<String> versionList;
	public SubAnalizePanel(GlobalAnalizePanel father) {
		Vector<String> versions = new Vector<String>();
		nombreCarta.setEnabled(false);
		if(father.getActualAnalizedCard() != null) {
			nombreCarta.setText(father.getActualAnalizedCard().getNombreCarta());
			int y = 2;
			for(int i = 0; i < father.getActualAnalizedCard().getNumVersiones(); i++) {
				if(versions.contains(father.getActualAnalizedCard().getVersiones().get(i).getExpansion())) {
					versions.add(father.getActualAnalizedCard().getVersiones().get(i).getExpansion() + " " + y);
					y++;
				}
				else { 
					y = 2;
					versions.add(father.getActualAnalizedCard().getVersiones().get(i).getExpansion()); 
				}
			}
		}
		// --------------------- REVISAR CONTROLADOR DE IMAGEN ----------------------
		setVersionList(new JComboBox<String>(versions));
	    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	    setText(new JLabel());
		getText().setSize(312, 445);
		Icon icono = new ImageIcon(father.getIcon().getImage().getScaledInstance(312, 445, Image.SCALE_DEFAULT));
		getText().setIcon(icono);
		add(getText());
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(new JLabel("                                                                     Imagen a analizar"));
		// --------------------------------------------------------------------------
		add(Box.createRigidArea(new Dimension(0, 20)));
		// --------------------------------------------------------------------------
		getNombreCarta().setMaximumSize(new Dimension(1000, 30));
		getNombreCarta().setHorizontalAlignment(JTextField.CENTER); 
		JPanel namePanel = new JPanel();
		namePanel.setMaximumSize(new Dimension(1000, 30));
		namePanel.add(new JLabel("     "));
		namePanel.add(getNombreCarta());
		this.add(namePanel);
		add(Box.createRigidArea(new Dimension(0, 20)));
		// --------------------------------------------------------------------------
		getVersionList().setMaximumSize(new Dimension(630, 30));
		add(getVersionList());
		// --------------------------------------------------------------------------
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(new JLabel("                                                                                   Versiones"));
		setController(father);
	}
	public void setController(GlobalAnalizePanel father) {
		// ---- CONTROLADOR DE LA FOTO ----
		getText().addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				openFileChooser().ifPresent( (File file) -> { lee(file, father); });
				//-----
				if(isFound()) {
					try { 
						//String cmd = "cmd /c start C:\\cygwin64\\bin\\mintty.exe -e bash ../../Programa/ocropy/Analizaimagen.sh " + "../../../" + father.getURLstring();
						//Files.deleteIfExists(Paths.get("C:\\Users\\Perry\\Desktop\\Programa\\ocropy\\a.txt"));
						String cmd = "cmd /c start /min /wait C:\\Windows\\Sysnative\\bash.exe ../../Programa/ocropy/Analizaimagen.sh " + "../../../" + father.getURLstring();
						Process pr = Runtime.getRuntime().exec(cmd); 
						pr.waitFor();
						String nuevaCarta;
						FileReader f = new FileReader("C:\\Users\\Perry\\Desktop\\Programa\\ocropy\\a.txt");
						BufferedReader b = new BufferedReader(f);
						if((nuevaCarta = b.readLine())!=null) {
							String nombreCarta2 = nuevaCarta.replaceAll(" ", "+");
							String urlGeneral = "https://www.cardmarket.com/es/Magic/Cards/" + nombreCarta2;
							String line = new String(urlGeneral.getBytes("ISO-8859-1"), "UTF-8");
							AlmacenVersiones x = new AlmacenVersiones(line, nuevaCarta);
						    father.setActualAnalizedCard(x);
						}
						b.close();
					} 
					catch (IOException | InterruptedException e1) { e1.printStackTrace(); }
					//-----
					father.setsAP(new SubAnalizePanel(father));
					father.setgDP(new GlobalDataPanel(father));
					Component[] componentList = father.getComponents();
					for(Component c : componentList){
					    if(c instanceof SubAnalizePanel || c instanceof GlobalDataPanel){ father.remove(c); }
					}
					father.add(father.getsAP(), BorderLayout.WEST);
					father.add(father.getgDP(), BorderLayout.CENTER);
					setFound(false);
				}
				father.getMainFrame().toFront();
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		
		// --------------------------------
		getVersionList().addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				father.setActualVersionToShow(getVersionList().getSelectedIndex());
				father.getgDP().getsGDP().getMinField().setText(Double.toString(father.getActualAnalizedCard().getVersiones().get(father.getActualVersionToShow()).getMinimoPrecio()));
				father.getgDP().getsGDP().getTendField().setText(Double.toString(father.getActualAnalizedCard().getVersiones().get(father.getActualVersionToShow()).getTendenciaPrecio()));
				father.getgDP().getsGDP().getFoilField().setText(Double.toString(father.getActualAnalizedCard().getVersiones().get(father.getActualVersionToShow()).getFoilPrecio()));
			}
		});
	}
	
	public Optional<File> openFileChooser() {
		JFileChooser fileSelector = new JFileChooser();
		fileSelector.setDialogTitle("Seleccionar Imagen");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png", "image-png");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter("JPG", "jpg", "image-png");
		fileSelector.setFileFilter(filter);
		fileSelector.setFileFilter(filter2);
		fileSelector.setAcceptAllFileFilterUsed(false);
		int flag = fileSelector.showOpenDialog(null);
		if (flag == JFileChooser.APPROVE_OPTION) {
			setFound(true);
			return Optional.of(fileSelector.getSelectedFile());
		} else {
			setFound(false);
			return Optional.empty();
		}
	}
	public void lee(File file, GlobalAnalizePanel father) {
		String fileName = file.getName();
		fileName=fileName.substring(fileName.lastIndexOf(".")+1);
		switch(fileName){
		case "png":
			try {
				BufferedImage image = ImageIO.read(file); 
				ImageIcon example = new ImageIcon(image);
				father.setIcon(example);
				String cadenaURL = file.getAbsolutePath().replaceAll("\\\\", "\\/");
				cadenaURL = cadenaURL.replaceAll("C:/Users/Perry/", "");
				father.setURLstring(cadenaURL);
			} catch (IOException e) {
				System.err.println("La imagen .PNG no sirve.");
				error=true;
			}	
		break;
		case "jpg":
			try {
				BufferedImage image = ImageIO.read(file); 
				ImageIcon example = new ImageIcon(image);
				father.setIcon(example);
				String cadenaURL = file.getAbsolutePath().replaceAll("\\\\", "\\/");
				cadenaURL = cadenaURL.replaceAll("C:/Users/Perry/", "");
				father.setURLstring(cadenaURL);
			} catch (IOException e) {
				System.err.println("La imagen .JPG no sirve 2.");
				error=true;
			}
		break;	
		default:
			System.err.println("No soportamos dicho formato");
			error=true;
        break;	
		}
	}
	public boolean returnState(){
		if(!error)
		  return true;
		else
		  return false;
	}
	public JLabel getText() { return text; }
	public void setText(JLabel text) { this.text = text; }
	public JComboBox<String> getVersionList() { return versionList; }
	public void setVersionList(JComboBox<String> versionList) { this.versionList = versionList; }
	public JTextField getNombreCarta() { return nombreCarta; }
	public void setNombreCarta(JTextField nombreCarta) { this.nombreCarta = nombreCarta; }
	public boolean isFound() { return found; }
	public void setFound(boolean found) { this.found = found; }
}
