import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class DataManage
{
    public static void main(String[] args) throws IOException
    {
        FileWriter fw = new FileWriter("WriteTest.csv");
        PrintWriter out = new PrintWriter(fw);
        // ',' divides the word into columns
        out.print("This");// first row first column
        out.flush();
        out.print(",");
        out.flush();
        out.print("is");// first row second column
        out.flush();
        out.print(",");
        out.flush();
        out.println("amazing");// first row third column
        out.flush();


        //Flush the output to the file
       // out.flush();

        //Close the Print Writer
        out.close();

        //Close the File Writer
        fw.close();


    }
}