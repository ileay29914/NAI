import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            //   Load training and test data
            List<LabeledText> trainData = CSVReader.loadData("lang.train.csv");
            List<LabeledText> testData = CSVReader.loadData("lang.test.csv");

            System.out.println(" Training samples: " + trainData.size());
            System.out.println(" Test samples: " + testData.size());

            //   Detect unique languages from training data
            Set<String> languages = new HashSet<>();
            for (LabeledText lt : trainData) {
                languages.add(lt.language);
            }
            System.out.println(" Detected languages: " + languages + "\n");

            //   Create the LanguageDetector
            int epochs = 10;
            double learningRate = 0.1;
            boolean useLinear = true;

            LanguageDetector detector = new LanguageDetector(languages, useLinear, learningRate);

            //   Train the network
            System.out.println(" Training started... (Epochs: " + epochs + ")");
            detector.train(trainData, epochs);
            System.out.println(" Training complete.\n");

            //  5. Evaluate on test data
            double accuracy = detector.evaluate(testData);
            System.out.printf(" Test accuracy: %.2f%%\n", accuracy);

            //   Display incorrect predictions
            System.out.println("\n Incorrect predictions:\n");

            int correct = 0;
            int total = testData.size();

            for (LabeledText lt : testData) {
                String predicted = detector.predict(lt.text);
                if (predicted.equals(lt.language)) {
                    correct++;
                } else {
                    System.out.println("• Text: \"" + lt.text + "\"");
                    System.out.println("   Actual: " + lt.language + ", Predicted: " + predicted + "\n");
                }
            }

            int incorrect = total - correct;
            System.out.println(" Correct predictions: " + correct + "/" + total);
            System.out.println(" Incorrect predictions: " + incorrect + "/" + total);

            // Interactive user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n You can now enter custom text to predict its language.");
            System.out.println("(Type 'exit' to quit.)");

            while (true) {
                System.out.print(" Enter text: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;

                String prediction = detector.predict(input);
                System.out.println(" Predicted language: " + prediction + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

