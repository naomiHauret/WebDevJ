import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MyBrowser extends JPanel {
	
	Vector<String> vectorAdresses;
	static int position;
   Browser browser;        
   BrowserView browserView;
   
   JTextField textfieldUrl;
   JPanel panelUrl;
   JPanel panelBoutons;   
   JButton buttonAvant;
   JButton buttonArriere;
   
   public MyBrowser(){
	   this.browser= new Browser();
	   this.browserView= new BrowserView(browser); 
	   this.setLayout(new BorderLayout());
	   this.vectorAdresses= new Vector();
	   position=-1;

	   construireFenetre();	
	   this.ajouteUrl("http://www.teamdev.com/jxbrowser");

   }
   
   public void construireFenetre(){
	   
	   this.panelBoutons= new JPanel(new FlowLayout());
	   this.buttonArriere= new JButton(new ImageIcon("arriere.png"));
	   this.buttonAvant= new JButton(new ImageIcon("avant.png"));
	  
	   this.panelBoutons.add(this.buttonArriere);
	   this.panelBoutons.add(this.buttonAvant);
	   this.panelBoutons.add(new JLabel("URL: "));
	   this.buttonArriere.setMaximumSize(new Dimension(20,20));
	   this.buttonAvant.setMaximumSize(new Dimension(20,20));
	   
	   this.panelUrl = new JPanel(new BorderLayout());
	   this.panelUrl.add(this.panelBoutons, BorderLayout.WEST);
	   this.textfieldUrl = new JTextField("http://www.teamdev.com/jxbrowser");
       this.panelUrl.add(this.textfieldUrl, BorderLayout.CENTER);
       
       
       this.add(panelUrl, BorderLayout.NORTH);
       this.add(browserView, BorderLayout.CENTER);

       browser.loadURL(textfieldUrl.getText());
        buttonAvant.setEnabled(false);
       buttonArriere.setEnabled(false);

       textfieldUrl.addActionListener(new Clic()); 
       this.buttonAvant.addActionListener(new Clic()); 
       this.buttonArriere.addActionListener(new Clic()); 
       

      
   }
   
   public void ajouteUrl(String uneURL){
	   vectorAdresses.addElement(uneURL);
	   position++;
	   if(position<=0){
		   this.buttonArriere.setEnabled(false);
	   }
	   else{
		   	   this.buttonArriere.setEnabled(true);

	   }
	   this.buttonAvant.setEnabled(false);
   }
   
   public class Clic implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==textfieldUrl  ){
			 browser.loadURL(textfieldUrl.getText());
			 ajouteUrl(textfieldUrl.getText());
		}
		
		if(e.getSource()== buttonAvant){
			textfieldUrl.setText(vectorAdresses.get(position+1));
			browser.loadURL(vectorAdresses.get(position+1));
			position--;
			
			if(position!= vectorAdresses.size()){
				buttonAvant.setEnabled(false);
			}
			
			buttonArriere.setEnabled(true);
			
		}
		
		if(e.getSource()== buttonArriere){
			textfieldUrl.setText(vectorAdresses.get(position-1));

			browser.loadURL(vectorAdresses.get(position-1));
			position--;
			
			if(position==0){
				buttonArriere.setEnabled(false);
			}
			
			buttonAvant.setEnabled(true);

		}
	      
	           
	}
	   
   }
}