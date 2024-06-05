
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BMIServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5550);
            System.out.println("Server is running. Waiting for connections...");

            while (true) {
            	// İstemciden gelen bağlantıyı kabul ediyor.
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");
                
                // Veri akışlarını oluşturuyoruz.
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                try {
                    // Receive the user data from the client
                    double height = dis.readDouble();
                    double weight = dis.readDouble();

                    double bmi = BMICalculator.calculate(height, weight);
                    String bmiCategory = BMICalculator.BMItoCategory(bmi);

                    // Send the result back to the client
                    dos.writeDouble(bmi);
                    dos.writeUTF(bmiCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    dis.close();
                    dos.close();
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
