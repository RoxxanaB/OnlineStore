package p3validation;

import p1structure.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

public class InvalidData<T> {
    private String fileName;
    private int number;

    public void writeErrorsToFile(String inputFile, Validator<T> validator){
        validator.validateModel(inputFile);

        List<Model> invalidModels = validator.getInvalidList();
        fileName = checkFile(inputFile)+"\\Err_" + validator + ".txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            for(Model m: invalidModels){
                writer.write( "Row" + m.getId() + ": " + m.getMessage() + " for id "
                        + m.getId() + " in field '" + m.getFieldNameError() + "'");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDuplicateToLogFile(String id)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName(),true)))
        {
            writer.write( "Row" + id + ": Duplicate row found for " + id);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File checkFile(String inputFile){
        File file = new File(getPath(inputFile)+ "\\Logs");
        if(!file.exists()){
            file.mkdir();
            System.out.println("Successfully created file: '" + file.getName() + "'");
        }
        return file;
    }

    public static String getPath(String inputFile){
        File file = new File(inputFile);
        return inputFile.substring(0,inputFile.lastIndexOf(file.getName()));
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
