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
        if(!file.getName().endsWith(".csv")){
            message = "Wrong type file. Not a'.csv' file.";
            return false;
        }
        return true;
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
