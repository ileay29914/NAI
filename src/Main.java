import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Verileri yükle
            List<LabeledText> trainData = CSVReader.loadData("lang.train.csv");
            List<LabeledText> testData = CSVReader.loadData("lang.test.csv");

            // Veride geçen dilleri belirle
            Set<String> languages = new HashSet<>();
            for (LabeledText lt : trainData) {
                languages.add(lt.language);
            }

            // LanguageDetector oluştur (linear aktivasyon, öğrenme oranı 0.1)
            LanguageDetector detector = new LanguageDetector(languages, true, 0.1);

            // Eğit
            System.out.println("Eğitim başlıyor...");
            detector.train(trainData, 10);
            System.out.println("Eğitim tamamlandı.");

            // Test doğruluğunu hesapla
            double accuracy = detector.evaluate(testData);
            System.out.printf("Test doğruluğu: %.2f%%\n", accuracy);

            // Kullanıcıdan metin al ve tahmin et
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nBir metin girin (çıkmak için 'exit'): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;

                String prediction = detector.predict(input);
                System.out.println("Tahmin edilen dil: " + prediction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
