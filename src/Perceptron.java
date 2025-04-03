import java.util.Random;

public class Perceptron {
    private final double[] weights;//	Harfler için 26 tane ağırlık değeri
    private double bias;//Nöronun kaydırma değeri (sabit eklenen değer)
    private final String language; // Bu perceptron'un temsil ettiği dil en
    private final boolean useLinear; // true: linear, false: step

    public Perceptron(String language, boolean useLinear) {
        this.language = language;
        this.useLinear = useLinear;
        this.weights = new double[26];
        this.bias = 0.0;
        initWeights();
    }

    private void initWeights() {
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble() * 0.1 - 0.05; // -0.05 ile 0.05 arası
        }
        bias = rand.nextDouble() * 0.1 - 0.05;
    }

    public double activate(double[] input) {
        double sum = bias;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * input[i];// perceptron formulu weıgt multıply ınputsum = weights • input + bias
        }

        return useLinear ? sum : (sum >= 0 ? 1.0 : 0.0);
    }

    public void train(double[] input, int target, double learningRate) {
        double output = activate(input);//Girdi vektörünü kullanarak perceptron’un mevcut çıktısını bulur 0, 1 ya da raw sayı olabilir (linear mode’da)


        double error = target - output;

        // Ağırlıkları ve bias'ı güncelle
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * error * input[i];
        }
        bias += learningRate * error;
    }

    public String getLanguage() {
        return language;
    }
}
