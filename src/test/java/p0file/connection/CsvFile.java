package p0file.connection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvFile {
    protected String fileName;
    protected String name;
    protected String path;
    protected File file;
    protected String message = "";

    public CsvFile(String fileName) {
        this.fileName = fileName;
    }

    public boolean checkTheExistenceOfFile(){
        file = new File(fileName);
        name = file.getName();
        path = fileName.substring(0,fileName.lastIndexOf(name));
        if(file.isDirectory() ){
           message = "Directory founded. Please add a file name.";
            return false;
        }
        if(!file.exists()){
            message = "File " + name + " does not exists.";
            return false;
        }

       /* if(name.endsWith(".txt")){
            convertTxtToCsv(path, name);
            return true;
        }*/
        if(!file.getName().endsWith(".csv")){
            message = "Wrong type file. Not a'.csv' file.";
            return false;
        }
        return true;
    }

    public void convertTxtToCsv(String path, String name) {
        List<String> output = new ArrayList<>();
        BufferedWriter writer = null;
        try(Scanner scanner = new Scanner(file)) {
            String line = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(line.contains(",")) {
                    convertTxtWithCommaToCsv(file.getName());
                    break;
                } else {
                    output.add(line.trim());
                }
            }
            if(output != null) {
                file = new File(path + name.substring(0,name.lastIndexOf("."))+".csv");
                writer = new BufferedWriter(new FileWriter(file));
                for (String s : output) {
                    writer.write(s);
                    writer.newLine();
                }
            }
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer!=null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File convertTxtWithCommaToCsv(String name)
    {
        File txtFile = new File( name ) ;
        String csvName = name.substring( 0, name.lastIndexOf( "." ) ) + ".csv" ;
        File csvFile = new File( csvName ) ;
        txtFile.renameTo( csvFile ) ;
        return  csvFile;
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
