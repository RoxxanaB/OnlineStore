package p2convert;

import p0file.connection.CsvFile;

import java.io.*;
import java.util.List;

public abstract class Converter<T> {

    public abstract List<T> convertLine(String fileName);

    public static boolean readFile(String fileName) {
        CsvFile csvFile = new CsvFile(fileName);
        if (csvFile.checkTheExistenceOfFile()) {
            return true;
        } else {
            String fileError = csvFile.getPath() + "FileErrors.txt";
            File file = new File(fileError);
            System.out.println("Fisier inexistent! Erorile au fost salvate in: '" + file +"'.");
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(file));)
            {
                bw.write(csvFile.getMessage());
                bw.newLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
