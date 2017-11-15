package sample.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sample.clases.listaNombres;
import sample.clases.setImage;
import sample.modelos.ModelEvento;
import sample.modelos.ModelPaciente;
import sample.objetos.Evento;
import sample.objetos.Paciente;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;


public class ControllerPevento implements Initializable {

    public TextField PeBuscarPaciente, enfermeraEvento, descEvento;
    public static int id;
    public Label labelNombrePx, labelEdadPx, labelHabitacionPx, labelCamaPx;
    public DatePicker fechaEvento;

    @FXML
    public ImageView fotoPx;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("/sample/fotos/p1.jpg", 145,135,false,false);
        fotoPx.setImage(image);
    }

    public void SearchPaciente() throws Exception {
        listaNombres d = new listaNombres();
        Map<String,Integer> possWords = d.nombres();
        Object[] nombreApellidos = new Object[possWords.size()];
        nombreApellidos = possWords.keySet().toArray();
        String[] stringArray = Arrays.copyOf(nombreApellidos, nombreApellidos.length, String[].class);
        AutoCompletionBinding<String> bind = TextFields.bindAutoCompletion(PeBuscarPaciente, stringArray);
        bind.setOnAutoCompleted(event -> {
            try {
                PeBuscarPaciente.setText(event.getCompletion());
                id = possWords.get(event.getCompletion());
                fillData(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void fillData(int id){
        ModelPaciente d = new ModelPaciente();
        Paciente p = d.getPaciente(id);

        labelNombrePx.setText(p.getNombre() + " " + p.getApellido());
        labelEdadPx.setText(String.valueOf(p.getEdad()));
        labelHabitacionPx.setText(String.valueOf(p.getNumero_cuarto()));
        labelCamaPx.setText(String.valueOf(p.getNumero_cama()));
        if(p.getPath() != null){
            setImage dd = new setImage();
            String path = p.getPath();
            Image img = dd.setUpImage(path);
            fotoPx.setImage(img);
        }else{
            Image image = new Image("/sample/fotos/p1.jpg", 145,135,false,false);
            fotoPx.setImage(image);
        }

    }

    public void agregarEvento(){
        ModelEvento e = new ModelEvento();
        LocalDate date = fechaEvento.getValue();
        String desc = descEvento.getText();
        String enfe = enfermeraEvento.getText();
        Evento ev = new Evento(date.toString(), enfe, desc);
        e.insertar(ev, id);
    }

}
