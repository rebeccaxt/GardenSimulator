

import java.nio.file.StandardOpenOption;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.Files; 
import java.nio.file.Paths; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List;

//this is a helper class for reading and writing to files
//code for this Class is modified from Java67.com (https://www.java67.com/2016/07/how-to-read-text-file-into-arraylist-in-java.html)
public class FilesUtil {
    
//reads a text file line by line and adds each line to an array which is returned
    public static ArrayList<String> readTextFileByLines(String fileName) throws IOException {


        BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
        ArrayList<String> listOfLines = new ArrayList<>(); 
        String line = bufReader.readLine(); 

        while (line != null) { 

            listOfLines.add(line); 
            line = bufReader.readLine(); 
            
        } 

        bufReader.close();
        return listOfLines;

    }
//writes a string to a text file of a chosen name
    public static void writeToTextFile(String fileName, String content) throws IOException {

        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);

    }
}