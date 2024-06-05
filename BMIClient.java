
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BMIClient extends JFrame implements ActionListener {
    private JTextField heightInput, weightInput;
    private JButton calculateBtn;
    private JLabel resultLabel;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public BMIClient() {
        setLayout(new GridLayout(3, 2));

        heightInput = new JTextField();
        weightInput = new JTextField();
        resultLabel = new JLabel("Result:");

        calculateBtn = new JButton("Calculate");
        calculateBtn.addActionListener(this);

        add(new JLabel("Height (cm):"));
        add(heightInput);
        add(new JLabel("Weight (kg):"));
        add(weightInput);
        add(calculateBtn);
        add(resultLabel);

        setSize(400, 200);
        setTitle("BMI Calculator Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        try {
            // Sunucuya bağlanılıyor ve veri akışları oluşturuluyor.
            socket = new Socket("localhost", 5550);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BMIClient();
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(calculateBtn)) {
            double height = Double.parseDouble(heightInput.getText());
            double weight = Double.parseDouble(weightInput.getText());

            try {
                // Send the user data to the server
                dos.writeDouble(height);
                dos.writeDouble(weight);
                dos.flush();

                // Receive the result from the server
                double bmi = dis.readDouble();
                String bmiCategory = dis.readUTF();

                resultLabel.setText(String.format("Result: %.2f - %s", bmi, bmiCategory));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}


