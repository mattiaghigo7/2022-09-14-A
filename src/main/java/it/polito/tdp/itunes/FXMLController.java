/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doComponente(ActionEvent event) {
    	Album a = this.cmbA1.getValue();
    	if(a==null) {
    		this.txtResult.setText("Inserire un valore per la Durata e scegliere un Album!");
    		return;
    	}
    	this.txtResult.setText("Componente connessa - "+a+"\n");
    	this.txtResult.appendText("Dimensione componente connessa = "+this.model.calcolaConnesse(a)+"\n");
   		this.txtResult.appendText("Durata componente = "+this.model.pesoConnesse(a)+"\n");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.cmbA1.getItems().clear();
    	String input = this.txtDurata.getText();
    	if(input=="") {
    		this.txtResult.setText("Inserire un valore per la Durata!");
    		return;
    	}
    	try {
    		Double durata = Double.parseDouble(input);
    		model.creaGrafo(durata);
    		this.txtResult.setText("Grafo creato!\n");
    		this.txtResult.appendText("# Vertici: "+this.model.getVerticiSize()+"\n");
    		this.txtResult.appendText("# Archi: "+this.model.getArchiSize()+"\n");
    		this.cmbA1.getItems().addAll(this.model.getVertici());
    	} catch(NumberFormatException e){
    		this.txtResult.setText("Valore inserito non valido!");
    		return;
    	}
    }

    @FXML
    void doEstraiSet(ActionEvent event) {
    	Album al = this.cmbA1.getValue();
    	if(al==null) {
    		this.txtResult.setText("Inserire un valore per la Durata e scegliere un Album!");
    		return;
    	}
    	String input = this.txtX.getText();
    	if(input=="") {
    		this.txtResult.setText("Inserire un valore per la Soglia(dTOT)!");
    		return;
    	}
    	try {
    		Double durata = Double.parseDouble(input);
    		Set<Album> best = model.calcolaSet(al, durata);
    		this.txtResult.setText("Ricorsione eseguita!\n");
    		if(best==null) {
    			this.txtResult.appendText("Nessun album!\n");
    		} else {
    			for(Album a : best) {
        			this.txtResult.appendText(a+"\n");
        		}
        		this.txtResult.appendText("\nDurata totale: "+best.size());
    		}
    	} catch(NumberFormatException e){
    		this.txtResult.setText("Valore inserito non valido!");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
