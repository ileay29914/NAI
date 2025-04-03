import java.util.*;

public class LanguageDetector {
    private final Map<String, Perceptron> perceptrons = new HashMap<>();//Her dil için bir perceptron tutar
    private final boolean useLinear;
    private final double learningRate;

    public LanguageDetector(Set<String> languages, boolean useLinear, double learningRate) {
        this.useLinear = useLinear;
        this.learningRate = learningRate;
        for (String lang : languages) {
            perceptrons.put(lang, new Perceptron(lang, useLinear));
        }
    }

    // Eğitim fonksiyonu
    public void train(List<LabeledText> trainingData, int epochs) {//Tüm veri kaç kez kullanılacak?
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (LabeledText lt : trainingData) {
                double[] input = TextVectorizer.vectorize(lt.text);//Metin → 26 boyutlu vektöre çevrilir
                for (Map.Entry<String, Perceptron> entry : perceptrons.entrySet()) {
                    int target = entry.getKey().equals(lt.language) ? 1 : 0;
                    entry.getValue().train(input, target, learningRate);
                }
            }
        }
    }

    // Tahmin: En yüksek skoru alan dil
    public String predict(String text) {
        double[] input = TextVectorizer.vectorize(text);
        String bestLang = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (Map.Entry<String, Perceptron> entry : perceptrons.entrySet()) {
            double score = entry.getValue().activate(input);
            if (score > bestScore) {
                bestScore = score;
                bestLang = entry.getKey();
            }
        }

        return bestLang;
    }

    // Test doğruluğu
    public double evaluate(List<LabeledText> testData) {
        int correct = 0;

        for (LabeledText lt : testData) {
            String predicted = predict(lt.text);
            if (predicted.equals(lt.language)) {
                correct++;
            }
        }

        return 100.0 * correct / testData.size();
    }
}
