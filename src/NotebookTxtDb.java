import javax.naming.NamingException;
import java.io.*;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 16.04.13
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTxtDb implements NotebookDb {
    private File file;

    // создаем txt файл. определяем сколько данных будет у 1го контакта (телефон, адрес....)
    NotebookTxtDb(final String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("New file " + fileName + " has been created in the current directory");
        }
    }

    public boolean isNameExists(final String name) throws IOException {
        return (searchingFile(name,"name")) != null;

    }

    // запись данных (имя, телефон...) в файле в столбик. параметр data это строка, в которой записываюстся данные 1контакта.
    public void addRecord(String data) throws Exception {
        Record rec = new Record(data);
        if (isNameExists(rec.name)) {
            remove(rec.name);
            throw new Exception("This name already exists.");
        }
        String content = "";
        String lineSeparator = System.getProperty("line.separator");   // перевод на след. строку в
        StringTokenizer str = new StringTokenizer(rec.toString());
        while (str.hasMoreTokens()) {
            content += str.nextToken() + lineSeparator;
        }

        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();


    }

    public void remove(final String name) {
        try {
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(file.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            try {
                String line;
                String lineSeparator = System.getProperty("line.separator");
                //Read from the original file and write to the new
                //unless content matches data to be removed.
                while ((line = br.readLine()) != null) {
                    if (!line.trim().equals(name)) {
                        for (int i = 0; i < Record.num - 1; i++) {
                            line += lineSeparator + br.readLine();


                        }
                        pw.println(line);
                        pw.flush();
                    } else {
                        for (int i = 0; i < Record.num - 1; i++) {
                            line = br.readLine();
                        }
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

    public String searchByName(final String name) throws IOException {
        if (searchingFile(name,"name") == null) {
            return null;
        }
        return searchingFile(name,"name").toString();
    }

    public String searchByPhone(final String phone) throws IOException {
        if (searchingFile(phone,"phone") == null) {
            return null;
        }
        return searchingFile(phone,"phone").name;
    }

    public void Open() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Поиск по имени/телефону.
     * <p/>
     * Если совпало по телефону, возвращаем только имя. Если по имени, то возвращаем всю строку.
     *
     * @param data
     * @return
     * @throws IOException
     */                          //(имя/телефон)//
    private Record searchingFile(final String data, final String mark) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String checkVar;
            while ((checkVar = br.readLine()) != null) {
                String line = checkVar + " ";                           // читаем в файле num строчек по порядку,записываем в строчку
                for (int i = 0; i < Record.num - 1; i++) {
                    line += br.readLine() + " ";
                }


                Record rec = new Record(line);
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
        {
            NotebookTxtDb n = new NotebookTxtDb("filename");
            n.addRecord("name1 phone1 address1 19");
        }
        {
            NotebookTxtDb m = new NotebookTxtDb("filename");
            System.out.println( m.isNameExists("name1") );
        }
    }
}
