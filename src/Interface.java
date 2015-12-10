 import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.awt.BorderLayout;
import java.awt.Color;import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interface extends JFrame implements CustomColor {
	
	//Dimensions de l'écran
    private static Rectangle dimension =  GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    private static int height = (int)dimension.getHeight();
    private static int width  = (int)dimension.getWidth();
	
    //outils
    int nbTabsHtml, nbTabsCss, nbTabsHs;
    private Vector<Fichier> vectorFichiersHtml;
    private Vector<Fichier> vectorFichiersCss;
    private Vector<Fichier> vectorFichiersJs;
    
    //widgets
    //-MENU
    private JMenuBar menu;
    private JMenu fichier;
    private JMenu editer;
    private JMenu naviguer;
    private JMenu afficher;
    private JMenu aide;
    
    //--item fichier 
    private JMenuItem item_nouveau;
    private JMenuItem item_ouvrir;
    private JMenuItem item_enregistrer;
    private JMenuItem item_enregistrer_sous;
    private JMenuItem item_quitter;
    
    //--item editer
    private JMenuItem item_copier;
    private JMenuItem item_couper;
    private JMenuItem item_coller;
    private JMenuItem item_annuler;
    private JMenuItem item_restaurer;
    private JMenuItem item_trouver_remplacer;
    
    //--item naviguer
    private JMenuItem item_html;
    private JMenuItem item_css;
    private JMenuItem item_js;
    private JMenuItem item_navigateur;
    
    //--item afficher
    private JCheckBox item_cbHtml;
    private JCheckBox item_cbCss;
    private JCheckBox item_cbJs;
    private JCheckBox item_cbNavigateur;
    private JCheckBox item_cbToolBar;
    
    //--item aide
    private JMenuItem item_aide;
    
    //-POPUPMENU
    private JPopupMenu popupMenu;
    
    //-NAVIGATEUR
   //MyBrowser browser;
    //-CONTENEURS
    private JPanel panelNorth; //contient les barres d'outils
    private JPanel panelCenter; //contient les panneaux HTML, CSS, Javascript et Navigateur
    private JPanel panelSouth; //contient l'affichage des erreurs
    
    //--PANELNORTH
    //---toolBar
    private JToolBar toolBar;
    private JButton button_nouveau;
    private JButton button_ouvrir;
    private JButton button_enregistrer;
    private JButton button_enregistrer_sous;
    private JButton button_copier;
    private JButton button_couper;
    private JButton button_coller;
    private JButton button_annuler;
    private JButton button_restaurer;
    private JButton button_aide;
    private JButton button_html;
    private JButton button_css;
    private JButton button_js;
    private JButton button_navigateur;
    
    //PANELCENTER
    private JSplitPane split1;
    private JSplitPane split2;
    private JSplitPane split3;
    
    private ClosableTabbedPane tabbedHtml;
    private ClosableTabbedPane tabbedCss;    
    private ClosableTabbedPane tabbedJs;

    private JPanel navigateur;
    
    //PANELSOUTH
    private JLabel erreur;
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
    //CONSTRUCTEUR
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

	Interface(){
			this.setTitle("WebDevJ");
	        this.setResizable(true);
	        this.setLayout(new BorderLayout());
	        this.setLocationRelativeTo(null);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setSize(this.width, this.height);
	        this.setDefaultLookAndFeelDecorated(true);
	        this.setExtendedState(this.MAXIMIZED_BOTH);
	        this.construireFenetre();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	//CONSTRUIREFENETRE()
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void construireFenetre(){
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//-MENU
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
				
		//--item fichier
		this.item_nouveau= new JMenuItem("Nouveau...");
		this.item_nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK)); //CTRL+N
		
		this.item_ouvrir= new JMenuItem("Ouvrir...");
		this.item_ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK)); //CTRL+O
		
		this.item_enregistrer= new JMenuItem("Enregistrer");
		this.item_enregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK)); //CTRL+S
		
		this.item_enregistrer_sous= new JMenuItem("Enregistrer sous...");
		this.item_enregistrer_sous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK)); //MAJ+CTRL+S
		
		this.item_quitter= new JMenuItem("Quitter WebDevJ");
		this.item_quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK)); //CTRL+Q
		
		this.fichier= new JMenu("Fichier");
	    this.fichier.add(this.item_nouveau);
	    this.fichier.add(this.item_ouvrir);
	    this.fichier.addSeparator();
	    this.fichier.add(this.item_enregistrer);
	    this.fichier.add(this.item_enregistrer_sous);
	    this.fichier.addSeparator();
	    this.fichier.add(this.item_quitter);
		
	    //--item editer
		this.item_copier= new JMenuItem("Copier");
		this.item_copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK)); //CTRL+C
		
		this.item_couper= new JMenuItem("Couper");
		this.item_couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK)); //CTRL+X
		
		this.item_coller= new JMenuItem("Coller");
		this.item_coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK)); //CTRL+V
		
		this.item_annuler= new JMenuItem("Annuler");
		this.item_annuler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK)); //CTRL+Z
		
		this.item_restaurer= new JMenuItem("Restaurer");
		this.item_restaurer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_MASK)); //CTRL+Y
		
		this.item_trouver_remplacer= new JMenuItem("Trouver/Remplacer...");
		this.item_trouver_remplacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK)); //CTRL+F
		
		this.editer=new JMenu("Editer");
	    this.editer.add(this.item_copier);
	    this.editer.add(this.item_couper);
	    this.editer.add(this.item_coller);
	    this.editer.addSeparator();
	    this.editer.add(this.item_annuler);
	    this.editer.add(this.item_restaurer);
	    this.editer.addSeparator();
	    this.editer.add(this.item_trouver_remplacer);

	    //--item naviguer
		this.item_html= new JMenuItem("HTML");
		this.item_css= new JMenuItem("CSS");
		this.item_js= new JMenuItem("Javascript");
		this.item_navigateur= new JMenuItem("Navigateur");
	
		this.naviguer= new JMenu("Naviguer");
	    this.naviguer.add(this.item_html);
	    this.naviguer.addSeparator();
	    this.naviguer.add(this.item_css);
	    this.naviguer.addSeparator();
	    this.naviguer.add(this.item_js);
	    this.naviguer.addSeparator();
	    this.naviguer.add(this.item_navigateur);

	    //--item afficher
		this.item_cbHtml= new JCheckBox("HTML", true);
		this.item_cbCss= new JCheckBox("CSS",true);
		this.item_cbJs= new JCheckBox("Javascript",true);
		this.item_cbNavigateur= new JCheckBox("Navigateur",true);
		this.item_cbToolBar= new JCheckBox("Barre d'outils",true);

		this.afficher= new JMenu("Afficher");
		
		this.afficher.add(this.item_cbHtml);
		this.afficher.addSeparator();
		this.afficher.add(this.item_cbCss);
		this.afficher.addSeparator();
		this.afficher.add(this.item_cbJs);
		this.afficher.addSeparator();
		this.afficher.add(this.item_cbNavigateur);
		this.afficher.addSeparator();
		this.afficher.add(this.item_cbToolBar);

	    //--item aide
		this.item_aide= new JMenuItem("Aide...");
		this.item_aide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)); //F1
		
		this.aide= new JMenu("Aide");
		this.aide.add(this.item_aide);
		
		this.menu= new JMenuBar();
		this.menu.add(this.fichier);
		this.menu.add(this.editer);
		this.menu.add(this.naviguer);
		this.menu.add(this.afficher);
		this.menu.add(this.aide);
		this.setJMenuBar(menu);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//-POPUPMENU
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		this.popupMenu= new JPopupMenu();
		this.popupMenu.add(this.item_nouveau);
		this.popupMenu.add(this.item_ouvrir);
		this.popupMenu.add(this.item_enregistrer);
		this.popupMenu.addSeparator();
		this.popupMenu.add(this.item_copier);
		this.popupMenu.add(this.item_couper);
		this.popupMenu.add(this.item_coller);
		this.popupMenu.addSeparator();
		this.popupMenu.add(this.item_annuler);
		this.popupMenu.add(this.item_restaurer);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
		//-CONTENEURS
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//--PANELNORTH
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		this.panelNorth= new JPanel();
		this.panelNorth.setLayout(new BorderLayout());
		
		this.button_nouveau= new JButton(new ImageIcon("nouveau.png"));
		this.button_nouveau.setMaximumSize(new Dimension(78,58));
		this.button_nouveau.setToolTipText("Nouveau...");

		this.button_ouvrir= new JButton(new ImageIcon("ouvrir.png"));
		this.button_ouvrir.setMaximumSize(new Dimension(78,58));
		this.button_ouvrir.setToolTipText("Ouvrir...");

		this.button_enregistrer= new JButton(new ImageIcon("enregistrer.png"));
		this.button_enregistrer.setMaximumSize(new Dimension(78,58));
		this.button_enregistrer.setToolTipText("Enregistrer");
		
		this.button_enregistrer_sous= new JButton(new ImageIcon("enregistrer_sous.png"));
		this.button_enregistrer_sous.setMaximumSize(new Dimension(78,58));
		this.button_enregistrer_sous.setToolTipText("Enregistrer sous...");

		this.button_copier= new JButton(new ImageIcon("copier.png"));
		this.button_copier.setMaximumSize(new Dimension(78,58));
		this.button_copier.setToolTipText("Copier");

		this.button_couper= new JButton(new ImageIcon("couper.png"));
		this.button_couper.setMaximumSize(new Dimension(78,58));
		this.button_couper.setToolTipText("Couper");

		this.button_coller= new JButton(new ImageIcon("coller.png"));
		this.button_coller.setMaximumSize(new Dimension(78,58));
		this.button_coller.setToolTipText("Coller");

		this.button_annuler= new JButton(new ImageIcon("annuler.png"));
		this.button_annuler.setMaximumSize(new Dimension(78,58));
		this.button_annuler.setToolTipText("Annuler");

		this.button_restaurer= new JButton(new ImageIcon("restaurer.png"));
		this.button_restaurer.setMaximumSize(new Dimension(78,58));
		this.button_restaurer.setToolTipText("Restaurer");

		this.button_aide= new JButton(new ImageIcon("aide.png"));
		this.button_aide.setMaximumSize(new Dimension(78,58));
		this.button_aide.setToolTipText("Aide...");

		this.button_html= new JButton(new ImageIcon("html.png"));
		this.button_html.setMaximumSize(new Dimension(78,58));

		this.button_css= new JButton(new ImageIcon("css.png"));	
		this.button_css.setMaximumSize(new Dimension(78,58));

		this.button_js= new JButton(new ImageIcon("js.png"));
		this.button_js.setMaximumSize(new Dimension(78,58));

		this.button_navigateur= new JButton(new ImageIcon("navigateur.png"));
		this.button_navigateur.setMaximumSize(new Dimension(78,58));

		this.toolBar= new JToolBar();
		this.toolBar.setFloatable(false);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_nouveau);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_ouvrir);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_enregistrer);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_enregistrer_sous);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.addSeparator();
		this.toolBar.add(this.button_copier);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_couper);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_coller);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_annuler);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_restaurer);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.addSeparator();
		this.toolBar.add(this.button_html);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_css);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_js);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.add(this.button_navigateur);
		this.toolBar.addSeparator(new Dimension(5,0));
		this.toolBar.addSeparator();
		this.toolBar.add(this.button_aide);

		this.panelNorth.add(this.toolBar);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//--PANELCENTER
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		this.panelCenter= new JPanel();
		this.panelCenter.setLayout(new BorderLayout());
			  	
	    this.tabbedHtml= new ClosableTabbedPane();
	    this.tabbedCss= new ClosableTabbedPane();
	    this.tabbedJs= new ClosableTabbedPane();

	    this.navigateur= new JPanel();
	    this.navigateur.setLayout(new BorderLayout());
	    this.navigateur.setBorder(new TitledBorder("Navigateur"));
	   
	    this.tabbedHtml.setBackground(Color.ORANGE);
	    
	    this.tabbedCss.setBackground(Color.CYAN);
	    
	    this.tabbedJs.setBackground(Color.YELLOW);
	    
	   // this.browser= new MyBrowser();
	 	
	   // this.navigateur.add(this.browser,BorderLayout.CENTER);
	    this.split3= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.tabbedJs, this.navigateur);
		
	    this.split2= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.tabbedCss, this.split3);
	    this.split1= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.tabbedHtml, this.split2);
	    this.split1.setDividerLocation(width*1/4);
	    this.split2.setDividerLocation(width*1/4);
	    this.split3.setDividerLocation(width*1/4);
    
	    this.panelCenter.add(split1, BorderLayout.CENTER);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//--PANELSOUTH
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		this.panelSouth= new JPanel();
		this.erreur= new JLabel();
		this.panelSouth.add(this.erreur);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//MISE EN PAGE 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		this.add(this.panelNorth, BorderLayout.NORTH);
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelSouth, BorderLayout.SOUTH);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//ECOUTEURS
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//MOUSE
		this.navigateur.addMouseListener(new Souris());

		//BUTTONS
		this.item_nouveau.addActionListener(new Clic());
		this.item_aide.addActionListener(new Clic());
		this.item_annuler.addActionListener(new Clic());
		this.item_coller.addActionListener(new Clic());
		this.item_copier.addActionListener(new Clic());
		this.item_couper.addActionListener(new Clic());
		this.item_enregistrer.addActionListener(new Clic());
		this.item_enregistrer_sous.addActionListener(new Clic());
		this.item_ouvrir.addActionListener(new Clic());
		this.item_quitter.addActionListener(new Clic());
		this.item_restaurer.addActionListener(new Clic());
		this.item_trouver_remplacer.addActionListener(new Clic());
		this.item_cbCss.addActionListener(new Clic());
		this.item_cbHtml.addActionListener(new Clic());
		this.item_cbJs.addActionListener(new Clic());
		this.item_cbNavigateur.addActionListener(new Clic());
		this.item_cbToolBar.addActionListener(new Clic());
		this.item_html.addActionListener(new Clic());
		this.item_css.addActionListener(new Clic());
		this.item_js.addActionListener(new Clic());
		this.item_navigateur.addActionListener(new Clic());

		this.button_aide.addActionListener(new Clic());
		this.button_annuler.addActionListener(new Clic());
		this.button_coller.addActionListener(new Clic());
		this.button_couper.addActionListener(new Clic());
		this.button_copier.addActionListener(new Clic());
		this.button_css.addActionListener(new Clic());
		this.button_enregistrer.addActionListener(new Clic());
		this.button_enregistrer_sous.addActionListener(new Clic());
		this.button_html.addActionListener(new Clic());
		this.button_js.addActionListener(new Clic());
		this.button_navigateur.addActionListener(new Clic());
		this.button_nouveau.addActionListener(new Clic());
		this.button_ouvrir.addActionListener(new Clic());
		this.button_restaurer.addActionListener(new Clic());
		
		//TABS
		this.tabbedCss.addChangeListener(new TabListener());
		this.tabbedHtml.addChangeListener(new TabListener());
		this.tabbedJs.addChangeListener(new TabListener());
		
		this.tabbedCss.addMouseListener(new Souris());
		this.tabbedHtml.addMouseListener(new Souris());
		this.tabbedJs.addMouseListener(new Souris());
	    
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	//METHODES
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//--BOUTONS
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setCopier(boolean b){
		this.button_copier.setEnabled(b);
		this.item_copier.setEnabled(b);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setColler(boolean b){
		this.button_coller.setEnabled(b);
		this.item_coller.setEnabled(b);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setCouper(boolean b){
		this.button_couper.setEnabled(b);
		this.item_couper.setEnabled(b);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setRestaurer(boolean b){
		this.button_restaurer.setEnabled(b);
		this.item_restaurer.setEnabled(b);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setAnnuler(boolean b){
		this.button_annuler.setEnabled(b);
		this.item_annuler.setEnabled(b);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setTrouver(boolean b){
		this.item_trouver_remplacer.setEnabled(b);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setEnregistrer(boolean b){
		this.item_enregistrer.setEnabled(b);
		this.button_enregistrer.setEnabled(b);
		this.item_enregistrer_sous.setEnabled(b);
		this.button_enregistrer_sous.setEnabled(b);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setBoutons(boolean copier, boolean coller, boolean couper, boolean restaurer, boolean annuler, boolean trouver, boolean enregistrer){
		this.setCopier(copier);
		this.setColler(coller);
		this.setCouper(couper);
		this.setRestaurer(restaurer);
		this.setAnnuler(annuler);
		this.setTrouver(trouver);
		this.setEnregistrer(enregistrer);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setEnabledTextPanes(boolean html, boolean css, boolean js){
		int i;
		for(i=0; i<tabbedHtml.getTabCount(); i++){
			JTextPane tp= (JTextPane) tabbedHtml.getComponentAt(i);
			tp.setEnabled(html);
			
			tp= (JTextPane) tabbedCss.getComponentAt(i);
			tp.setEnabled(css);
			
			tp= (JTextPane) tabbedJs.getComponentAt(i);
			tp.setEnabled(js);
			
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	//--BACKGROUNDS
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

	public void setBackgroundHtml( Color c){
		this.button_html.setBackground(c);
		if(c==CustomColor.lightRed || c==CustomColor.LIGHTRED){
			this.button_html.setToolTipText("Afficher panneau HTML");
		}
		else{
			this.button_html.setToolTipText("Cacher panneau HTML");
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

	public void setBackgroundCss( Color c){
		this.button_css.setBackground(c);
		if(c==CustomColor.lightRed || c==CustomColor.LIGHTRED){
			this.button_css.setToolTipText("Afficher panneau CSS");
		}
		else{
			this.button_css.setToolTipText("Cacher panneau CSS");
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	
	public void setBackgroundJs( Color c){
		this.button_js.setBackground(c);
		if(c==CustomColor.lightRed || c==CustomColor.LIGHTRED){
			this.button_js.setToolTipText("Afficher panneau Javascript");
		}
		else{
			this.button_js.setToolTipText("Cacher panneau Javascript");
		}
	}	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setBackgroundNavigateur( Color c){
		this.button_navigateur.setBackground(c);
		if(c==CustomColor.lightRed || c==CustomColor.LIGHTRED){
			this.button_navigateur.setToolTipText("Afficher panneau navigateur");
		}
		else{
			this.button_navigateur.setToolTipText("Cacher panneau navigateur");
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setBackgrounds(Color html, Color css, Color js, Color navigateur){
		this.setBackgroundHtml(html);
		this.setBackgroundCss(css);
		this.setBackgroundJs(js);
		this.setBackgroundNavigateur(navigateur);
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	private JTextPane getTextPane(int index, ClosableTabbedPane tmp){
		return (JTextPane) tmp.getComponentAt(index);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	private ClosableTabbedPane getTabHtml(){
		return this.tabbedHtml;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	private ClosableTabbedPane getTabCss(){
		return this.tabbedCss;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	private ClosableTabbedPane getTabJs(){
		return this.tabbedJs;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	private JPanel getPanelNavigateur(){
		return this.navigateur;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	private ClosableTabbedPane getActiveTabbedPane(){
		ClosableTabbedPane resu;
		if(this.tabbedJs.getComponentAt(0).isEnabled()){
			resu=this.getTabJs();
		}
		
		else if(this.tabbedCss.getComponentAt(0).isEnabled()){
			resu=this.getTabCss();

		}
		
		else{
			resu= this.getTabHtml();
		}
		return resu;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void colorWords(String[] strsToHighlight,String text,final StyledDocument doc, Color color, boolean bold){
		 
	    for(String strToHL : strsToHighlight){
	            Pattern p = Pattern.compile(strToHL);
	            Matcher m = p.matcher(text);
	 
	            while(m.find() == true){                                                
	                    MutableAttributeSet attri = new SimpleAttributeSet();
	                    StyleConstants.setForeground(attri, color);
	                    StyleConstants.setBold(attri, bold);
	 
	                    final int start = m.start();
	                    final int end = m.end();
	                    final int length = end-start;
	                    final MutableAttributeSet style = attri;
	 
	                    SwingUtilities.invokeLater(new Runnable(){
	                        public void run(){
	                                doc.setCharacterAttributes(start, length, style, true);
	                        }
	                    });
	            }
	    }
	}
	 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	 
	public void coloreBalisesHtml() {
		int i;
		String[] attributs={"(\"|\')([^\"])+(\"|\')"};
		String[] balisesAttributs={"(accept(-charset)?|accesskey|action|align|asyn|auto(complete|focus|play|save)?|(bg)?color|border|buffered|challenge|checked|cite|code(base)?|lang|cols(pan)?|contextmenu|disabled|value|id|class|alt|height|width|title|style|rel|href(lang)?|media|type|charset|content(Enabled)?|controls|coords|data(-*)?|datetime|default|defer|dir(name)?|download|draggable|dropzone|encrypte|for(m|maction)?|headers|hidden|high|http-equiv|icon|ismap|itemprop|keytype|kind|label|list|language|loop|low|manifest|max(lenght)?|media|method|min|multiple|name|novalidate|open|optimum|pattern|ping|placeholder|poster|preload|pubdate|radiogroup|readonly|rel|required|rows(pan)?|sandbox|spellcheck|scope(d)?|seamless|selected|shape|size(s)?|span|start|step|summary|tabindex|target|usemap|wrap|src(doc|lang|set)?)([ ])?="};
	    String[] balisesSectionnantes={"<(/)?(header|footer|section|aside|article|nav)(([^>])*)>"};
	    String[] balisesPremierNiveau=   {"<(/)?(html|body|head)>"};
	    String[] balisesCommentaires={"<!--(.)*-->"};
	    String[] balisesEntete={"<(/)?(link|meta|script|style|title)(([^>])*)(/)?([ ])*>"};	  
	    String[] balisesAutres={"<(/)?(a(bbr|ddress|rea|udio)?|b(ase|do|di|lockquote|r|utton)?|c(anvas|aption|ite|ode|ol(group)?|ommand)|d(atalist|d|el|etails|fn|iv|l|t)|e(m|mbed)|f(i(eldset|gcaption|gure)orm)|h(group|r|[1-6])|i(frame|mg|nput|ns)?|k(eygen|bd)|l(egend|i)?|m(a(rk|p)?|e(nu|ter)?)?|noscript|o(bject|l|pt(group|ion)?|utput)?|p(aram|r(e|ogress)?)?|q|r(uby|p|t)|s(amp|elect|mall|ource|pan|trong|tyle|u(b|mmary|p)?)|t(able|body|d|extarea|foot|th(ead)?|ime|r(ack)?)|ul|v(ar|ideo)|wbr|xmp)(([^>])*)>" };
	    
	    for(i=0; i<this.tabbedHtml.getTabCount(); i++){
	    JTextPane textPane = this.getTextPane(i, this.tabbedHtml);
	    String text = textPane.getText().replaceAll("\n", " ");
	    final StyledDocument doc = textPane.getStyledDocument();
	    final MutableAttributeSet normal= new SimpleAttributeSet();
	    StyleConstants.setForeground(normal, Color.black);
	    StyleConstants.setBold(normal, false);
	 
	    SwingUtilities.invokeLater(new Runnable() {
	 
	    public void run() {
	            doc.setCharacterAttributes(0, doc.getLength(), normal, true);
	        }
	    });
	 
	    colorWords(balisesSectionnantes, text, doc, Color.blue, false);
	    colorWords(balisesAutres, text, doc, CustomColor.babyBlue,false);
	    colorWords(balisesPremierNiveau, text, doc, CustomColor.darkMarineBlue, true);
	    colorWords(balisesEntete, text, doc, CustomColor.marineBlue, false );
	    colorWords(balisesAttributs, text, doc, Color.red, false);
	    colorWords(attributs, text, doc, CustomColor.parme, false);
	    colorWords(balisesCommentaires, text, doc, CustomColor.commentGreen, false);

	    }
	    
	  
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void coloreBalisesCss(){
		int i;
		String[] attribut={"(.)+"};
		String[] identifiant={"#([a-zA-Z_0-9])+"};
		String[] classe={"\\.([a-zA-Z_0-9])+"};
		String[] autresSelecteurs={":((active|checked|disabled|empty|enabled|first-(child|of-type)|focus|hover|in-range|invalid|lang|last-(child|of-type)|link|not|nth-(child|last-child|of-type)|only-(of-type|child)|optional|out-of-range|read-(only|write)|required|root|target|valid|valid)|:(after|before|first-l(etter|ine)|selection))"};
	    String[] balisesCommentaires={"/\\*(.)*\\*/"};
	    String[] code={"\\{([^}])*\\}"};
		
	    for(i=0; i<this.tabbedCss.getTabCount(); i++){
	    	JTextPane textPane = this.getTextPane(i, this.tabbedCss);
			String text = textPane.getText().replaceAll("\n", " ");
		    final StyledDocument doc = textPane.getStyledDocument();
		    final MutableAttributeSet normal= new SimpleAttributeSet();
		    StyleConstants.setForeground(normal, Color.black);
		    StyleConstants.setBold(normal, false);
		 
		    SwingUtilities.invokeLater(new Runnable() {
		 
		    public void run() {
		            doc.setCharacterAttributes(0, doc.getLength(), normal, true);
		        }
		    });
		 
		    colorWords(attribut, text, doc, Color.blue, false);
		    colorWords(identifiant, text, doc, CustomColor.orange, false);
		    colorWords(classe, text, doc, CustomColor.darkYellow,false);
		    colorWords(autresSelecteurs, text, doc, CustomColor.parme,false);
		    colorWords(code, text, doc, Color.black, false);
		    colorWords(balisesCommentaires, text, doc, CustomColor.commentGreen, false);
	    }


	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void coloreBalisesJs(){
		int i;
		
		for(i=0; i<this.tabbedJs.getTabCount(); i++){
			JTextPane textPane = this.getTextPane(i, this.tabbedJs);
		String[] balisesCommentaires={"/\\*(.)*\\*/"};
	    

		String text = textPane.getText().replaceAll("\n", " ");
	    final StyledDocument doc = textPane.getStyledDocument();
	    final MutableAttributeSet normal= new SimpleAttributeSet();
	    StyleConstants.setForeground(normal, Color.black);
	    StyleConstants.setBold(normal, false);
	 
	    SwingUtilities.invokeLater(new Runnable() {
	 
	    public void run() {
	            doc.setCharacterAttributes(0, doc.getLength(), normal, true);
	        }
	    });
		
	    colorWords(balisesCommentaires, text, doc, CustomColor.commentGreen, false);
		}
		

	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

	public void coloreBalises(){
		this.coloreBalisesHtml();
		this.coloreBalisesCss();
		this.coloreBalisesJs();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
    public void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
    public void charger(){
    	int index= this.tabbedHtml.getSelectedIndex();
    	int i;
    	File fileToLoad;
    	
    	for(i=0; i<this.tabbedCss.getTabCount(); i++){
    		if(this.vectorFichiersCss.get(i).getStatut()==0){
	    		try {
	    			String content= ((AbstractButton) this.tabbedCss.getComponentAt(i)).getText();
					File tmp= File.createTempFile(this.tabbedJs.getTabTitleAt(i),".css");
				    BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
		    	    bw.write(((AbstractButton) this.tabbedCss.getComponentAt(i)).getText());
		    	    bw.close();
		    	    tmp.deleteOnExit();
	    		} 
	    	
		    	catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    	for(i=0; i<this.tabbedJs.getTabCount(); i++){
    		if(this.vectorFichiersJs.get(i).getStatut()==0){
	    		try {
					File tmp= File.createTempFile(this.tabbedJs.getTabTitleAt(i),".js");
				    BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
		    	    bw.write(((AbstractButton) this.tabbedJs.getComponentAt(i)).getText());
		    	    bw.close();
		    	    tmp.deleteOnExit();
	    		} 
	    	
		    	catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    	
    	if(this.vectorFichiersHtml.get(index).getStatut()==0){
    		try {
				fileToLoad= File.createTempFile(this.tabbedHtml.getTabTitleAt(index),".html");
			    BufferedWriter bw = new BufferedWriter(new FileWriter(fileToLoad));
	    	    bw.write(((AbstractButton) this.tabbedHtml.getComponentAt(index)).getText());
	    	    bw.close();
	    	    fileToLoad.deleteOnExit();
	    	   // this.browser.ajouteUrl(fileToLoad.getAbsolutePath());

    		} 
    	
	    	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	else{
    		try {
    			
    			File f= new File(this.vectorFichiersHtml.get(index).getChemin());
    			File directory= new File(f.getParent());
				fileToLoad= File.createTempFile("tmp",".html", directory);
				BufferedWriter bw = new BufferedWriter(new FileWriter(fileToLoad));
	    	    bw.write(((AbstractButton) this.tabbedHtml.getComponentAt(index)).getText());
	    	    bw.close();
	    	    fileToLoad.deleteOnExit();

	    	//   this.browser.ajouteUrl(fileToLoad.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    	
    	
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
    public void iniVectorsHtml(){
    	this.vectorFichiersHtml=new Vector();

    	int i;
    	for(i=0;i<this.tabbedHtml.getTabCount(); i++){
    		Fichier tmp= vectorFichiersHtml.get(i);
    		Fichier f= new Fichier(0, this.tabbedHtml.getTabTitleAt(i), getTextPane(i,this.tabbedHtml).getText(), "",0 );
    		
    		if(tmp.getStatut()==1){
    			f.setStatut(1);
    			f.setChemin(tmp.getChemin());
    		}
    		
    		this.vectorFichiersHtml.addElement(f);
    	}
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void iniVectorsCss(){
		this.vectorFichiersCss=new Vector();

    	int i;
    	for(i=0;i<this.tabbedCss.getTabCount(); i++){
    		Fichier tmp= vectorFichiersCss.get(i);
    		Fichier f= new Fichier(0, this.tabbedCss.getTabTitleAt(i), getTextPane(i,this.tabbedCss).getText(), "",0 );
    		
    		if(tmp.getStatut()==1){
    			f.setStatut(1);
    			f.setChemin(tmp.getChemin());
    		}
    		this.vectorFichiersCss.addElement(f);
    	}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void iniVectorsJs(){	
		this.vectorFichiersJs=new Vector();

    	int i;
    	for(i=0;i<this.tabbedJs.getTabCount(); i++){
    		Fichier f= new Fichier(0,this.tabbedJs.getTabTitleAt(i),getTextPane(i, this.tabbedJs).getText(),"",0 );
    		Fichier tmp= vectorFichiersJs.get(i);    		
    		if(tmp.getStatut()==1){
    			f.setStatut(1);
    			f.setChemin(tmp.getChemin());
    		}
    		this.vectorFichiersJs.addElement(f);
    	}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void iniAllVectors(){
		this.iniVectorsHtml();
		this.iniVectorsCss();
		this.iniVectorsJs();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void copier(){

	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection data=null ; 
		int selection= 0;
		JTextPane tmpTextPane;
		
		if(this.tabbedHtml.getComponentAt(0).isEnabled()){
			selection=tabbedHtml.getSelectedIndex();
			tmpTextPane=(JTextPane)this.tabbedHtml.getComponentAt(selection);
			 data = new StringSelection(tmpTextPane.getSelectedText());
		}
		
		else if(this.tabbedCss.getComponentAt(0).isEnabled()){
			selection=tabbedCss.getSelectedIndex();
			tmpTextPane=(JTextPane)this.tabbedCss.getComponentAt(selection);
			data = new StringSelection(tmpTextPane.getSelectedText());
			
		}
		
		else if(this.tabbedJs.getComponentAt(0).isEnabled()){
			selection=tabbedJs.getSelectedIndex();
			tmpTextPane=(JTextPane)this.tabbedJs.getComponentAt(selection);
			data = new StringSelection(tmpTextPane.getSelectedText());
			
		}
        clipboard.setContents (data, data);
        
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void coller(){
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        TransferHandler transferHandler =null;
        int selection = 0;
        
        if(this.tabbedHtml.getComponentAt(0).isEnabled()){
        	selection= tabbedHtml.getSelectedIndex();
        	transferHandler= ((JComponent) tabbedHtml.getComponentAt(selection)).getTransferHandler();
        	transferHandler.importData( (JComponent) tabbedHtml.getComponentAt(selection), clipboard.getContents(null));
        	this.coloreBalisesHtml();
        }
                 
        else if(this.tabbedCss.getComponentAt(0).isEnabled()){
        	selection= tabbedCss.getSelectedIndex();
        	transferHandler= ((JComponent) tabbedCss.getComponentAt(selection)).getTransferHandler();
        	transferHandler.importData( (JComponent) tabbedCss.getComponentAt(selection), clipboard.getContents(null));
        	this.coloreBalisesCss();

        }
                 
        else if(this.tabbedJs.getComponentAt(0).isEnabled()){
        	selection= tabbedJs.getSelectedIndex();
        	transferHandler= ((JComponent) tabbedJs.getComponentAt(selection)).getTransferHandler();
        	transferHandler.importData( (JComponent) tabbedJs.getComponentAt(selection), clipboard.getContents(null));
        	this.coloreBalisesJs();
        }

	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void couper(){
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection data=null ;
		int selection = 0;
		
       if(this.tabbedHtml.getComponentAt(0).isEnabled()){
    	   selection= tabbedHtml.getSelectedIndex();
    	   data = new StringSelection(((JTextComponent) this.tabbedHtml.getComponentAt(selection)).getSelectedText());
    	   ((JTextPane) this.tabbedHtml.getComponentAt(selection)).replaceSelection("");
       }
       
       else if(this.tabbedCss.getComponentAt(0).isEnabled()){
    	   selection= tabbedCss.getSelectedIndex();
    	   data = new StringSelection(((JTextComponent) this.tabbedCss.getComponentAt(selection)).getSelectedText());
    	   ((JTextPane) this.tabbedCss.getComponentAt(selection)).replaceSelection("");

       }
       
       else if(this.tabbedJs.getComponentAt(0).isEnabled()){
    	   selection= tabbedJs.getSelectedIndex();
    	   data = new StringSelection(((JTextComponent) this.tabbedJs.getComponentAt(selection)).getSelectedText());
    	   ((JTextPane) this.tabbedJs.getComponentAt(selection)).replaceSelection("");


       }
        clipboard.setContents (data, data);
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void ouvrir(){
		JFileChooser jfc= new JFileChooser();
		String extension=null;
		JTextPane tmpTextPane;
		JScrollPane tmpScrollPane;
		TextLineNumber tmpTextLineNumber;
		
		
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(".css", "css", "Cascade Style Sheets"));
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(".js", "js", "Javascript"));
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(".html", "html", "Hyper Text Markup Language"));
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setMultiSelectionEnabled(false);
		
		int val= jfc.showOpenDialog(this);
		
		  if (val == JFileChooser.APPROVE_OPTION) {
	            File file = jfc.getSelectedFile();
	            String nom= file.getName();
	            extension=FilenameUtils.getExtension(nom);
	            tmpTextPane= new JTextPane();
	           	tmpScrollPane= new JScrollPane(tmpTextPane);
	           	tmpTextLineNumber= new TextLineNumber(tmpTextPane);
	           	tmpScrollPane.setRowHeaderView(tmpTextLineNumber);
	           	

	 
	           	if(extension.equals("html")){
	       
	         	    try {
						String content= IOUtils.toString(new FileReader(file));
						tmpTextPane.setText(content);
						
						boolean trouve =false;
						Fichier f= new Fichier(1, nom, tmpTextPane.getText(), file.getAbsolutePath(),0);
						int i=0;
						
						while(i<vectorFichiersHtml.size() && trouve==false){
							String chemin= vectorFichiersHtml.get(i).getChemin();
							int presence= vectorFichiersHtml.get(i).getPresent();
							if( presence==0 && f.getChemin().equals(chemin)){
								trouve= true;
								JOptionPane.showMessageDialog(this, "Le fichier "+file.getName()+" est déjà ouvert.","Erreur", JOptionPane.ERROR_MESSAGE);
							}

							i++;
						}
						
						if(trouve==false){
								this.vectorFichiersHtml.add(f);
								this.tabbedHtml.addTab(file.getName(), new ImageIcon("icone_html.png"), tmpTextPane);
								this.coloreBalisesHtml();
						}
						
					} 
	         	    
	         	    catch (IOException e) {
						e.printStackTrace();
					}
	         	    

	            }
	            
	            else if(extension.equals("css")){
	            	 
	         	    try {
						String content= IOUtils.toString(new FileReader(file));
						tmpTextPane.setText(content); 
						
						boolean trouve =false;
						Fichier f= new Fichier(1, nom, tmpTextPane.getText(), file.getAbsolutePath(),0);
						int i=0;
						
						while(i<vectorFichiersCss.size() && trouve==false){
							int presence= vectorFichiersHtml.get(i).getPresent();
							String chemin= vectorFichiersCss.get(i).getChemin();
							if( presence==0 && f.getChemin().equals(chemin)){
								trouve= true;
								JOptionPane.showMessageDialog(this, "Le fichier "+file.getName()+" est déjà ouvert.","Erreur", JOptionPane.ERROR_MESSAGE);
							}

							i++;
						}
						
						if(trouve==false){
								this.vectorFichiersCss.add(f);
								this.tabbedCss.addTab(file.getName(), new ImageIcon("icone_css.png"), tmpTextPane);
								this.coloreBalisesCss();
						}
						

					} catch (IOException e) {
						e.printStackTrace();
					}
	         	    
	         	    
	         	   
	            }
	            
	            else if(extension.equals("js")){
	            	 
	         	    try {
						String content= IOUtils.toString(new FileReader(file));
						tmpTextPane.setText(content);
						
						boolean trouve =false;
						Fichier f= new Fichier(1, nom, tmpTextPane.getText(), file.getAbsolutePath(),0);
						int i=0;
						
						while(i<vectorFichiersJs.size() && trouve==false){
							String chemin= vectorFichiersJs.get(i).getChemin();
							int presence= vectorFichiersHtml.get(i).getPresent();
							if( presence==0 && f.getChemin().equals(chemin)){
								trouve= true;
								JOptionPane.showMessageDialog(this, "Le fichier "+file.getName()+" est déjà ouvert.","Erreur", JOptionPane.ERROR_MESSAGE);
							}

							i++;
						}
						
						if(trouve==false){
								this.vectorFichiersJs.add(f);
								this.tabbedJs.addTab(file.getName(), new ImageIcon("icone_js.png"), tmpTextPane);
								this.coloreBalisesJs();
						}
						
					} 
	         	    catch (IOException e) {
						e.printStackTrace();
						
					}
	         	   
	            }
	           
	        } 
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

	public void ajoutOngletHtml(String nom){
		JTextPane tmpTextPane= new JTextPane();
       	JScrollPane tmpScrollPane= new JScrollPane(tmpTextPane);
       	TextLineNumber tmpTextLineNumber= new TextLineNumber(tmpTextPane);
       	tmpScrollPane.setRowHeaderView(tmpTextLineNumber);
       	tmpTextPane.setText(" <!--Votre HTML--> \n <!DOCTYPE html> \n <html> \n \t<head> \n \t \t <title>Exemple</title> \n \t \t <link rel=\"stylesheet\" href=\"exemple.css\" media=\"all\"> \n \t</head> \n \t <body> \n  \t \t  \n <div> <div align=\"dzdzf\"> \t \t \t <header id=\"dze\" class=\"zefze zeffzzzzq fe\"> \n<div> <div align=\"dzdzf\">  <script src='exemple.js'></script> \n \t </body> \n  \n </html>");
       	this.tabbedHtml.addTab(nom, new ImageIcon("icone_html.png"), tmpTextPane); 
       	this.coloreBalisesHtml();
       	
       	Fichier f= new Fichier(0, nom, tmpTextPane.getText(), "",0);
       this.vectorFichiersHtml.addElement(f);

       	tmpTextPane.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==tmpTextPane){
					int i;
					JTextPane tp;
					for(i=0; i<tabbedHtml.getTabCount(); i++){
						 tp= (JTextPane) tabbedHtml.getComponentAt(i);
						tp.setEnabled(true);
					}
					for(i=0; i<tabbedCss.getTabCount(); i++){
						tp= (JTextPane) tabbedCss.getComponentAt(i);
						tp.setEnabled(false);
					}
					
					for(i=0; i<tabbedJs.getTabCount(); i++){
						tp= (JTextPane) tabbedJs.getComponentAt(i);
						tp.setEnabled(false);
					}	
					
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
       		
       	});
       	
     	
      	tmpTextPane.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getSource()==tmpTextPane){
					String text= tmpTextPane.getText();
					vectorFichiersHtml.get(tabbedHtml.getSelectedIndex()).setContenu(text);
					coloreBalisesHtml();
					charger();


				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
      		
      	});
		

	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void ajoutOngletCss(String nom){
		JTextPane tmpTextPane= new JTextPane();
       	JScrollPane tmpScrollPane= new JScrollPane(tmpTextPane);
       	TextLineNumber tmpTextLineNumber= new TextLineNumber(tmpTextPane);
       	tmpScrollPane.setRowHeaderView(tmpTextLineNumber);
		tmpTextPane.setText("/* *** Votre CSS *** */ \n body{ \n }\n header{ }\n #div{ }\n # div { }\n #div { }\n .class{ } \n . class { } ");
       	this.tabbedCss.addTab(nom, new ImageIcon("icone_css.png"), tmpTextPane);
		this.coloreBalisesCss();
		
	 	Fichier f= new Fichier(0, nom, tmpTextPane.getText(), "",0);
	    this.vectorFichiersCss.addElement(f);
	    
      	tmpTextPane.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==tmpTextPane){
					int i;
					JTextPane tp;
					for(i=0; i<tabbedHtml.getTabCount(); i++){
						tp= (JTextPane) tabbedHtml.getComponentAt(i);
						tp.setEnabled(false);
					}
					for(i=0; i<tabbedCss.getTabCount(); i++){

						tp= (JTextPane) tabbedCss.getComponentAt(i);
						tp.setEnabled(true);
					}
					for(i=0; i<tabbedJs.getTabCount(); i++){

						tp= (JTextPane) tabbedJs.getComponentAt(i);
						tp.setEnabled(false);
						
					}
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
       		
       	});
      	
      	tmpTextPane.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
			
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getSource()==tmpTextPane){
					String text= tmpTextPane.getText();
					vectorFichiersCss.get(tabbedCss.getSelectedIndex()).setContenu(text);
					coloreBalisesCss();
					charger();

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
      		
      	});
		
		

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void ajoutOngletJs(String nom){
		JTextPane tmpTextPane= new JTextPane();
       	JScrollPane tmpScrollPane= new JScrollPane(tmpTextPane);
       	TextLineNumber tmpTextLineNumber= new TextLineNumber(tmpTextPane);
       	tmpScrollPane.setRowHeaderView(tmpTextLineNumber);
		tmpTextPane.setText("/* *** Votre Javascript *** */");
       	this.tabbedJs.addTab(nom, new ImageIcon("icone_js.png"), tmpTextPane);
		this.coloreBalisesJs();
	 	Fichier f= new Fichier(0, nom, tmpTextPane.getText(), "",0);
	    this.vectorFichiersJs.addElement(f);
	   
	  
	    
      	tmpTextPane.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==tmpTextPane){
					int i;
					JTextPane tp;
					
					for(i=0; i<tabbedHtml.getTabCount(); i++){
						tp= (JTextPane) tabbedHtml.getComponentAt(i);
						tp.setEnabled(false);
					}
					
					for(i=0; i<tabbedCss.getTabCount(); i++){

						tp= (JTextPane) tabbedCss.getComponentAt(i);
						tp.setEnabled(false);
					}
					
					for(i=0; i<tabbedJs.getTabCount(); i++){

						tp= (JTextPane) tabbedJs.getComponentAt(i);
						tp.setEnabled(true);
						
					}
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
       		
       	});
      	
     	
      	tmpTextPane.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
				
				if(e.getSource()==tmpTextPane){
					String text= tmpTextPane.getText();
					vectorFichiersJs.get(tabbedJs.getSelectedIndex()).setContenu(text);
					coloreBalisesJs();
					charger();

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
      		
      	});
		
       	
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

	public void enregistrer(ClosableTabbedPane tp, int index){
		File file=new File("");
		String file_name= tp.getTabTitleAt(index);
		if(tp.equals(tabbedHtml)){
			 file= new File(this.vectorFichiersHtml.get(index).getChemin());
		}
		else if(tp.equals(tabbedCss)){
			 file= new File(this.vectorFichiersCss.get(index).getChemin());
		}
		
		else if(tp.equals(tabbedJs)){
			 file= new File(this.vectorFichiersJs.get(index).getChemin());
		}
		
		FileWriter fw;
		if(file.exists()){
				
			try {
				fw = new FileWriter(file);
				fw.write(((JTextPane)tp.getComponentAt(index)).getText());
				fw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		else{
			
			int r=0;
			r= JOptionPane.showConfirmDialog(null, "Le fichier "+file_name+" n'existe pas. \n Voulez-vous le créer?", "Fichier inexistant", JOptionPane.YES_NO_OPTION);
			
			if(r==JOptionPane.YES_OPTION){
				
					this.enregistrer_sous(tp,index);
				}
				
			}

		}
		
	

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public void enregistrer_sous(ClosableTabbedPane tp, int index){
		int d=0;
		JFileChooser jfc = new JFileChooser();
		File f;
		String chemin="";
		
		if(tp.equals(this.tabbedHtml)){
			
			chemin= vectorFichiersHtml.get(index).getChemin();
			jfc.addChoosableFileFilter(new FileNameExtensionFilter(".html", "html", "Hyper Text Markup Language"));
		}
		
		else if(tp.equals(this.tabbedCss)){
			chemin= vectorFichiersCss.get(index).getChemin();
			jfc.addChoosableFileFilter(new FileNameExtensionFilter(".css", "css", "Cascade Style Sheets"));

		}
		
		else if(tp.equals(this.tabbedJs)){
			chemin= vectorFichiersJs.get(index).getChemin();
			jfc.addChoosableFileFilter(new FileNameExtensionFilter(".js", "js", "Javascript"));

		}
		
		f=new File(tp.getTitleAt(index), chemin);
		
		
		jfc.setSelectedFile(f);
		jfc.setCurrentDirectory(f);
		jfc.setDialogTitle("Enregistrer "+tp.getTitleAt(index));
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setMultiSelectionEnabled(false);
		
		d=jfc.showSaveDialog(this);
		
		if(d==JFileChooser.APPROVE_OPTION){
			
			String file_name = jfc.getSelectedFile().getName();
			jfc.setCurrentDirectory(jfc.getCurrentDirectory());
			
			if(tp.equals(this.tabbedHtml) && (!file_name.endsWith(".html"))){	
				
				    file_name += ".html";
			}
			
			else if(tp.equals(this.tabbedCss) &&(!file_name.endsWith(".css"))){
				
				    file_name += ".css";
				
			}
			
			else if(tp.equals(this.tabbedJs) &&(!file_name.endsWith(".js"))){
				
				    file_name += ".js";
				
			}
			
			
			try {
				
				f=new File(jfc.getSelectedFile().getAbsolutePath());	
				
				if(f.exists()){
					int r=0;
					r= JOptionPane.showConfirmDialog(null, "Le fichier "+file_name+" existe déjà. \n Voulez-vous le remplacer?", "Confirmer l'enregistrement", JOptionPane.YES_NO_OPTION);
					
					if(r==JOptionPane.YES_OPTION){
									
						if(tp.equals(this.tabbedHtml)){
							
							if(!file_name.endsWith(".html")){
								file_name+=".html";
							}
							
								this.tabbedHtml.setTitleAt(index, file_name);
								this.vectorFichiersHtml.get(index).setStatut(1);
								this.vectorFichiersHtml.get(index).setNom(file_name);
								JTextPane tmp= (JTextPane) tp.getComponentAt(index);
								this.vectorFichiersHtml.get(index).setContenu(tmp.getText());
								this.vectorFichiersHtml.get(index).setChemin(f.getAbsolutePath());

						}
						
						else if(tp.equals(this.tabbedCss)){
							
							if(!file_name.endsWith(".css")){
								file_name+=".css";
							}
							
								this.tabbedCss.setTitleAt(index, file_name);
								this.vectorFichiersCss.get(index).setStatut(1);
								this.vectorFichiersCss.get(index).setNom(file_name);
								JTextPane tmp= (JTextPane) tp.getComponentAt(index);
								this.vectorFichiersCss.get(index).setContenu(tmp.getText());
								this.vectorFichiersCss.get(index).setChemin(f.getAbsolutePath());


						}
						
						else{
							
							if(!file_name.endsWith(".js")){
								file_name+=".js";
							}
							
								this.tabbedJs.setTitleAt(index, file_name);
								this.vectorFichiersJs.get(index).setStatut(1);
								this.vectorFichiersJs.get(index).setNom(file_name);
								JTextPane tmp= (JTextPane) tp.getComponentAt(index);
								this.vectorFichiersJs.get(index).setContenu(tmp.getText());
								this.vectorFichiersJs.get(index).setChemin(f.getAbsolutePath());
								
						}
						
						FileWriter fw = new FileWriter(f);
						fw.write(((JTextPane)tp.getComponentAt(index)).getText());
						fw.close();
					}
					// end if (r==JOptionPane.YES_OPTION)
			            	
				}// end if (f.exists()) 
				
				else{
					
					try{
						if (f.createNewFile()){
							
							
							if(tp.equals(this.tabbedHtml)){
								
								if(!file_name.endsWith(".html")){
									file_name+=".html";
								}
								
									this.tabbedHtml.setTitleAt(index, file_name);
									this.vectorFichiersHtml.get(index).setStatut(1);
									this.vectorFichiersHtml.get(index).setNom(file_name);
									JTextPane tmp= (JTextPane) tp.getComponentAt(index);
									this.vectorFichiersHtml.get(index).setContenu(tmp.getText());
									this.vectorFichiersHtml.get(index).setChemin(f.getAbsolutePath());

							}
							
							else if(tp.equals(this.tabbedCss)){
								
								if(!file_name.endsWith(".css")){
									file_name+=".css";
								}
								
									this.tabbedCss.setTitleAt(index, file_name);
									this.vectorFichiersCss.get(index).setStatut(1);
									this.vectorFichiersCss.get(index).setNom(file_name);
									JTextPane tmp= (JTextPane) tp.getComponentAt(index);
									this.vectorFichiersCss.get(index).setContenu(tmp.getText());
									this.vectorFichiersCss.get(index).setChemin(f.getAbsolutePath());


							}
							
							else{
								
								if(!file_name.endsWith(".js")){
									file_name+=".js";
								}
								
									this.tabbedJs.setTitleAt(index, file_name);
									this.vectorFichiersJs.get(index).setStatut(1);
									this.vectorFichiersJs.get(index).setNom(file_name);
									JTextPane tmp= (JTextPane) tp.getComponentAt(index);
									this.vectorFichiersJs.get(index).setContenu(tmp.getText());
									this.vectorFichiersJs.get(index).setChemin(f.getAbsolutePath());
							}
							
							FileWriter fw = new FileWriter(f);
							fw.write(((JTextPane)tp.getComponentAt(index)).getText());
							fw.close();
							
						}
						else{
							JOptionPane.showMessageDialog(null, "Le fichier "+f.getName()+" n'a pu être créé.", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
					} //end try else
					
					catch (IOException exception){
						System.out.println ("Erreur " + exception.getMessage());
					}
					
				} //end else
				
				
			} 
			
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	//METHODES ECOUTEURS
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private class Souris implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource()==tabbedHtml){
				int i;
				JTextPane tp;
				for(i=0; i<tabbedHtml.getTabCount(); i++){
					tp= (JTextPane) tabbedHtml.getComponentAt(i);
					tp.setEnabled(true);
				}
				
				for(i=0; i<tabbedCss.getTabCount(); i++){

					tp= (JTextPane) tabbedCss.getComponentAt(i);
					tp.setEnabled(false);
				}	
				
				for(i=0; i<tabbedJs.getTabCount(); i++){
					tp= (JTextPane) tabbedJs.getComponentAt(i);
					tp.setEnabled(false);
				}
			}
			
			if(e.getSource()==tabbedCss){
				int i;
				JTextPane tp;
				for(i=0; i<tabbedHtml.getTabCount(); i++){
					tp= (JTextPane) tabbedHtml.getComponentAt(i);
					tp.setEnabled(false);
				}
				
				for(i=0; i<tabbedCss.getTabCount(); i++){

					tp= (JTextPane) tabbedCss.getComponentAt(i);
					tp.setEnabled(true);
				}	
				
				for(i=0; i<tabbedJs.getTabCount(); i++){
					tp= (JTextPane) tabbedJs.getComponentAt(i);
					tp.setEnabled(false);
				}
			}
			
			if(e.getSource()==tabbedJs){
				int i;
				JTextPane tp;
				for(i=0; i<tabbedHtml.getTabCount(); i++){
					tp= (JTextPane) tabbedHtml.getComponentAt(i);
					tp.setEnabled(false);
				}
				
				for(i=0; i<tabbedCss.getTabCount(); i++){

					tp= (JTextPane) tabbedCss.getComponentAt(i);
					tp.setEnabled(false);
				}	
				
				for(i=0; i<tabbedJs.getTabCount(); i++){
					tp= (JTextPane) tabbedJs.getComponentAt(i);
					tp.setEnabled(true);
				}
				
				}
			}
		

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
            showPopup(e);
		}
		
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	private class Clic implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_nouveau || e.getSource()==item_nouveau){
				Principale.ouvrirNouveau();
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_ouvrir){
				ouvrir();
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_enregistrer_sous || e.getSource()==item_enregistrer_sous ){
				enregistrer_sous(getActiveTabbedPane(), getActiveTabbedPane().getSelectedIndex());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_enregistrer || e.getSource()==item_enregistrer){
				enregistrer(getActiveTabbedPane(), getActiveTabbedPane().getSelectedIndex());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_copier || e.getSource()==item_copier){
				copier();

			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_couper  || e.getSource()==item_couper){
				couper();
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_coller  || e.getSource()==item_coller ){
				coller();
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_annuler){
				
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_restaurer){
				
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_aide){
				
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_html){
				ClosableTabbedPane tmpTabHtml=getTabHtml();
				
				if((button_html.getBackground()==CustomColor.lightGreen) || (button_html.getBackground()==CustomColor.LIGHTGREEN)){
					setBackgroundHtml(CustomColor.lightRed);
					split1.remove(tabbedHtml);
				}
				
				else{
					tabbedHtml=tmpTabHtml;
					
					if(tabbedHtml.getTabCount()==0){ 
							JTextPane tmp= new JTextPane();
							tmp.setText(vectorFichiersHtml.lastElement().getContenu());
							tabbedHtml.addTab(vectorFichiersHtml.lastElement().getNom(), new ImageIcon("icone_html.png"),tmp);
							coloreBalisesHtml();
					}	
					
					split1.setLeftComponent(tabbedHtml);
					setBackgroundHtml(CustomColor.lightGreen);
				}
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**	
			if(e.getSource()==button_css){
				
				System.out.println(vectorFichiersHtml.size()+" "+vectorFichiersCss.size()+" "+vectorFichiersJs.size());
				ClosableTabbedPane tmpTabCss=getTabCss();
	
				if((button_css.getBackground()==CustomColor.lightGreen) || (button_css.getBackground()==CustomColor.LIGHTGREEN)){
					setBackgroundCss(CustomColor.lightRed);
					split2.remove(tabbedCss);
				}
				else{
					tabbedCss=tmpTabCss;
					if(tabbedCss.getTabCount()==0){
						JTextPane tmp= new JTextPane();
						tmp.setText(vectorFichiersCss.lastElement().getContenu());
						tabbedCss.addTab(vectorFichiersCss.lastElement().getNom(), new ImageIcon("icone_css.png"),tmp);
					}
					
					setBackgroundCss(CustomColor.lightGreen);
					split2.setLeftComponent(tabbedCss);
					
				}
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**			
			if(e.getSource()==button_js){
				ClosableTabbedPane tmpTabJs=getTabJs();				
								
				if((button_js.getBackground()==CustomColor.lightGreen) || (button_js.getBackground()==CustomColor.LIGHTGREEN)){
					setBackgroundJs(CustomColor.lightRed);
					split3.remove(tabbedJs);
				}
				
				else{
					
					tabbedJs=tmpTabJs;

					if(tabbedJs.getTabCount()==0){
						JTextPane tmp= new JTextPane();
						tmp.setText(vectorFichiersJs.lastElement().getContenu());
						tabbedJs.addTab(vectorFichiersJs.lastElement().getNom(), new ImageIcon("icone_js.png"),tmp);
					}
					
					setBackgroundJs(CustomColor.lightGreen);
					split3.setLeftComponent(tabbedJs);
				}
				
			}
		
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////**
			if(e.getSource()==button_navigateur){
				JPanel tmpNavigateur=getPanelNavigateur();
				if((button_navigateur.getBackground()==CustomColor.lightGreen)||(button_navigateur.getBackground()==CustomColor.LIGHTGREEN)){
					setBackgroundNavigateur(CustomColor.lightRed);
					split3.remove(navigateur);
				}
				else{
					navigateur=tmpNavigateur;
					setBackgroundNavigateur(CustomColor.lightGreen);
					split3.setRightComponent(navigateur);
				}
			}
			
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

	private class TabListener implements ChangeListener{
		 
		@Override
		public void stateChanged(ChangeEvent e) {
			
		
			if(e.getSource()==tabbedHtml){		
				
				int lastSelectedIndex = 0;
				int currentIndex = 0;
				int previousIndex = 0;
				int index = tabbedHtml.getSelectedIndex();
		        if (index != currentIndex){
		          previousIndex = currentIndex;
		          currentIndex = index;
		        
		          if (previousIndex != -1) {
		            String currTabTitle = tabbedHtml.getTitleAt(currentIndex);
		            String prevTabTitle = tabbedHtml.getTitleAt(previousIndex);
		            
		            System.out.println("Previous Tab Title was: " + prevTabTitle );
		            System.out.println("Current Tab Title is: " + currTabTitle);
		            System.out.println();
		          }
		        }
				if(tabbedHtml.getTabCount()==0){
					setBackgroundHtml(CustomColor.lightRed);
					split1.remove(tabbedHtml);
					
				}
				/*
				 
				if ( tabbedHtml.getTabCount()<vectorFichiersHtml.size() && (((JTextPane) tabbedHtml.getTabComponentAt(lastSelectedIndex)).getText() != ((JTextPane) tabbedHtml.getSelectedComponent()).getText()) || (tabbedHtml.getTabCount()>1 && ((JTextPane) tabbedHtml.getTabComponentAt(lastSelectedIndex)).getText() == ((JTextPane) tabbedHtml.getSelectedComponent()).getText())) {
			        System.out.println(vectorFichiersHtml.get(lastSelectedIndex).getChemin());
   					  			           
					vectorFichiersHtml.remove(lastSelectedIndex);
				}
		
				
				
				if(tabbedHtml.getTabCount()>0){
			            	lastSelectedIndex = tabbedHtml.getSelectedIndex();
			    }
				
				else{
					lastSelectedIndex= 0;
				}
			    System.out.println(vectorFichiersHtml.size());*/
		        
	
			}
			

			if(e.getSource()==tabbedCss){
				int lastSelectedIndex = tabbedCss.getSelectedIndex();
			
				  if (tabbedCss.getTabComponentAt(lastSelectedIndex) != tabbedCss.getSelectedComponent()) {
			           					  
			            if ((tabbedCss.getTabCount() < vectorFichiersCss.size())) {           
			            	vectorFichiersCss.remove(lastSelectedIndex);    
			            } 
			            
			            lastSelectedIndex = tabbedCss.getSelectedIndex();
			            System.out.println(vectorFichiersCss.size());
			        }
				  
				if(tabbedCss.getTabCount()==0){
					setBackgroundCss(CustomColor.lightRed);
					split2.remove(tabbedCss);
				}

			}
			
			if(e.getSource()==tabbedJs){
				int lastSelectedIndex = tabbedJs.getSelectedIndex();
				
				  if (tabbedJs.getTabComponentAt(lastSelectedIndex) != tabbedJs.getSelectedComponent()) {
			           					  
			            if ((tabbedJs.getTabCount() < vectorFichiersJs.size())) {           
			            	vectorFichiersJs.remove(lastSelectedIndex);    
			            } 
			            
			            lastSelectedIndex = tabbedJs.getSelectedIndex();
			            System.out.println(vectorFichiersJs.size());

			        }
				if(tabbedJs.getTabCount()==0){
					setBackgroundJs(CustomColor.lightRed);
					split3.remove(tabbedJs);
				}
				
			}
		}
		
	}
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

