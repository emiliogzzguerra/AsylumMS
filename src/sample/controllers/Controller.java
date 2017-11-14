package sample.controllers;


import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sample.clases.Pdespliega;
import sample.clases.Preceta;
import sample.clases.RegistroPx;
import sample.clases.listaNombres;
import sample.controllers.ControllerPdespliega;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;

import static sample.controllers.ControllerPdespliega.*;

public class Controller {

    public Button buscarButton;
    public TextField textBuscar;
    public CheckBox TcheckBox;




    public void advancedSearch() throws Exception {
        listaNombres d = new listaNombres();
        Map<String,Integer> possWords = d.nombres();
        Object[] nombreApellidos = new Object[possWords.size()];
        nombreApellidos = possWords.keySet().toArray();
        String[] stringArray = Arrays.copyOf(nombreApellidos, nombreApellidos.length, String[].class);
        AutoCompletionBinding<String> bind = TextFields.bindAutoCompletion(textBuscar, stringArray);
        bind.setOnAutoCompleted(event -> {
            try {
                textBuscar.setText(event.getCompletion());
                Integer id = possWords.get(event.getCompletion());
                abrirPdespliega(id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void buscarPx () {
        String nombrePx = textBuscar.getText();
        buscarButton.setText(nombrePx);
        textBuscar.setText("");


    }

    public void abrirRegistraPx () throws Exception {
        RegistroPx registroPx = new RegistroPx();
        Stage stage = new Stage();
        try {
            registroPx.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void abrirPdespliega(Integer id) throws Exception {

        try {
            Pdespliega d = new Pdespliega();
            Stage stage = new Stage();
            d.start(stage);
            ControllerPdespliega e = new ControllerPdespliega();
            //e.cambiar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void selectionCB (){
        if(TcheckBox.isSelected()){
            String nm = "checkbox";
            buscarButton.setText(nm);
        }
    }

    public void abrirPreceta() throws Exception {
        Preceta preceta = new Preceta();
        Stage stage = new Stage();
        try {
            preceta.start(stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void abrirEmergencias (int val) throws Exception {

    }




    public Statement conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //Get connection with MySQL database
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_asilo?useSSL=false",
                    "root", "1212");
            Statement stmt = mycon.createStatement();

            return stmt;
        } catch (Exception e) {
            System.out.println("Error with database connection");
            e.printStackTrace();
            return null;
        }
    }

}




