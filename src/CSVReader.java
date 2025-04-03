import java.io.*;
import java.util.*;

public class CSVReader {
    //	Dönüş tipi: LabeledText türünden bir liste
    public static List<LabeledText> loadData(String filePath) throws IOException {
        List<LabeledText> data = new ArrayList<>(); // Okunan her satırdan elde edilen LabeledText nesneleri bu listeye eklenecek.
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;//Geçici olarak bir satırı tutacak line değişkeni. Döngüde her satır buraya atanacak.

        while ((line = reader.readLine()) != null) {// Dosyanın sonuna kadar satır satır oku. readLine() her seferinde bir satır verir.Eğer dosyanın sonuna geldiyse null döner ve döngü biter.
            // Virgüllü metinler olabilir, bu yüzden sadece ilk virgülde ayır dıl metın
            String[] parts = line.split(",", 2);
            if (parts.length == 2) {
                String language = parts[0].trim();
                String text = parts[1].trim();
                data.add(new LabeledText(language, text));// Bu metni ve dilini LabeledText nesnesi olarak oluşturup data listesine ekliyoruz.
            }
        }

        reader.close();
        return data;
    }
}
