import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 16.04.13
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTxtDb {
    private File file;


    NotebookTxtDb(final String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("New file " + fileName + " has been created in the current directory");
        }
    }

    boolean isNameExists(final String name) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals(name)) {
                    return true;
                }
                line = br.readLine();
            }
            return false;

        } finally {
            br.close();
        }

    }

    // запись данных (имя, телефон) в файле в столбик
    void addRecord(final String name, final String phone) throws IOException {
        String content = "";
        String lineSeparator = System.getProperty("line.separator");   // перевод на след. строку в файле
        content += name + lineSeparator + phone + lineSeparator;
        //content += name+ lineSeparator;
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();

    }

    void remove(final String name) {
        try {
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(file.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            try {
                String line;
                //Read from the original file and write to the new
                //unless content matches data to be removed.
                while ((line = br.readLine()) != null) {
                    if (!line.trim().equals(name)) {
                        pw.println(line);
                        pw.flush();
                    } else {
                        line = br.readLine();
                    }
                }

            } finally {
                pw.close();
                br.close();
            }

            //Delete the original file
            if (!file.delete()) {
                System.out.println("Could not delete file");
                java.nio.file.Files.delete(file.toPath());
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

    String searchByName(final String name) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {

            if (line.trim().equals(name)) {
                return br.readLine();
            }
        }
        System.out.println(" your contact is not found :( ");
        return null;
    }

    String searchByPhone(final String phone) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        String line2 = null;
        while ((line2 = br.readLine()) != null) {
            if (line2.trim().equals(phone)) {
                return line;
            }
            line = line2;
        }
        System.out.println(" your contact is not found :( ");
        return null;
    }

    void Open() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) throws IOException {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.addRecord("olimp", "95");
        n.addRecord("olimp", "11");
        n.addRecord("olimp", "0");
        System.out.println(n.searchByName("olimp"));

    }
}
