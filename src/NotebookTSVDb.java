import java.io.*;
import java.util.StringTokenizer;

/*
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 06.07.13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
*/

public class NotebookTSVDb implements NotebookDb {
    private File file;

    //определям как будет называться tsv file
    NotebookTSVDb(final String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("New file " + fileName + " has been created in the current directory");
        }
    }

    public boolean isNameExists(String name) throws Exception {
        return (searchingFile(name, "name")) != null;
    }


    public void addRecord(String data) throws Exception {
        Record rec = new Record(data);
        if (isNameExists(rec.name)) {
            remove(rec.name);
            throw new Exception("This name already exists.");
        }
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        String lineSeparator = System.getProperty("line.separator");
        String content = rec.name+","+rec.phone+","+rec.address+","+rec.age + lineSeparator;
        bw.write(content);
        bw.close();
    }

    public void remove(String name) throws Exception {
        try {
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(file.getAbsolutePath() + ".tmp2");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(file + ".tmp2"));
            try {
                String line;
                //Read from the original file and write to the new
                //unless content matches data to be removed.
                while ((line = br.readLine()) != null) {
                    String recformat ="";
                    StringTokenizer str = new StringTokenizer(line,",");
                    while (str.hasMoreTokens()) {
                        recformat += str.nextToken()+" ";

                    }
                    Record rec = new Record(recformat);
                    if (!rec.name.equals(name)) {
                        pw.println(line);
                        pw.flush();
                    }
              }

            } finally {
                pw.close();
                br.close();
            }

            //Delete the original file
            if (!file.delete()) {
                System.out.println("Could not delete file");
              //  java.nio.file.Files.delete(file.toPath());
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(file))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public String searchByName(String name) throws Exception {
        if (searchingFile(name, "name") == null) {
            return null;
        }
        return searchingFile(name, "name").toString();
    }

    public String searchByPhone(String phone) throws Exception {
        if (searchingFile(phone, "phone") == null) {
            return null;
        }
        return searchingFile(phone, "phone").name;
    }


    public void Open() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Record searchingFile(final String data, final String mark) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String recformat ="";
                StringTokenizer str = new StringTokenizer(line,",");
                while (str.hasMoreTokens()) {
                    recformat += str.nextToken()+" ";
                }

                Record rec = new Record(recformat);
                if (rec.phone.equals(data) && mark.equals("phone")) {
                    return rec;
                }
                if (rec.name.equals(data) && mark.equals("name")) {
                    return rec;
                }
            }
            return null;

        } finally {
            br.close();
        }
    }

    public static void main(String[] args) throws Exception {

        NotebookTSVDb n = new NotebookTSVDb("db.csv");
        n.addRecord("name1 phone1 address1 19");
        n.addRecord("name2 phone2 address2 20");
        n.addRecord("name3 phone3 address3 21");
        n.remove("name2");
        System.out.println(n.searchByName("name2"));
        System.out.println(n.searchByPhone("phone2"));

        System.out.println(n.searchByName("name1"));
        System.out.println(n.searchByPhone("phone1"));
        System.out.println(n.searchByName("name3"));
        System.out.println(n.searchByName("phone3"));


    }
}
