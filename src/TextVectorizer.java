public class TextVectorizer {

    // Metni 26 boyutlu vektöre çevirir (sadece a-z harfleri sayılır)
    public static double[] vectorize(String text) {
        double[] vector = new double[26]; // a-z
        text = text.toLowerCase();//buyuk kucuk harf farkını kaldırır

        for (char c : text.toCharArray()) {//Her karakteri sırayla kontrol eder
            if (c >= 'a' && c <= 'z') {
                vector[c - 'a'] += 1;//letter karşılık gelen kutuya 1 eklenir
            }
        }

        return normalize(vector);
    }

    // Vektörü normalize eder: v / |v|
    public static double[] normalize(double[] vector) {
        double norm = 0.0;
        for (double val : vector) {
            norm += val * val;
        }
        norm = Math.sqrt(norm);

        if (norm == 0) return vector; // boş metin gibi durumlar için

        double[] normalized = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            normalized[i] = vector[i] / norm;
        }

        return normalized;
    }
}
