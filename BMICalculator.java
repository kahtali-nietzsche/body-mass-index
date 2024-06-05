
public class BMICalculator {
	public static double calculate(double height, double weight) {
		height = height / 100;
		return weight / (height*height);
	}
	
	public static String BMItoCategory(double BMIvalue) {
		if (BMIvalue < 18.5) return "Underweight";
		else if (BMIvalue >= 18.5 && BMIvalue < 24.9) return "Healthy";
		else if (BMIvalue >= 24.9 && BMIvalue < 29.9) return "Overweight";
		else return "Obese";
	}
}