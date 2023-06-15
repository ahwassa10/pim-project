package program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {
    private boolean start = true;
    
    @FXML
    private Text output;
    
    private long operand1; 
    
    private String operator = "";
    
    private Model model = new Model();
    
    @FXML
    private void processNumpad(ActionEvent event) {
        if (start) {
            output.setText("");
            start = false;
        }
        
        String value = ((Button) event.getSource()).getText();
        
        output.setText(output.getText() + value);
    }
    
    @FXML
    private void processOperator(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        
        if (!value.equals("=")) {
            if (!operator.isEmpty()) {
                return;
            }
            operator = value;
            operand1 = Long.parseLong(output.getText());
            output.setText("");
            
        } else {
            if (operator.isEmpty()) {
                return;
            }
            
            long result = model.calculate(operand1, Long.parseLong(output.getText()), operator);
            output.setText(String.valueOf(result));
            
            operator = "";
            start = true;
        }
    }
}
