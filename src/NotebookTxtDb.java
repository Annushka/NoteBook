import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 16.04.13
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTxtDb implements NotebookDb {
    private File file;
    public int num = 4;
    public String[] contactData = DataManage.DElem;
    DataManage DM = new DataManage();

    // создаем txt файл. определяем сколько данных будет у 1го контакта (телефон, адрес....)
    NotebookTxtDb(final String fileName) throws IOException {
        //num  = DataManage.DElem.length;
        num = contactData.length;
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("New file " + fileName + " has been created in the current directory");
        }
    }

    public boolean isNameExists(final String name) throws IOException {
        if((sistemCall(name))!= null){
            return true;
        }
        else {
            return false;
        }

    }

    // запись данных (имя, телефон) в файле в столбик. параметр data это строка, в которой записываюстся данные 1контакта.
    public void addRecord(String data) throws IOException {
        DM.OutOfString(data);
        if (isNameExists(DataManage.name)) {
            remove(DataManage.name);
        }
        contactData = DataManage.DElem;
        String content = "";
        String lineSeparator = System.getProperty("line.separator");   // перевод на след. строку в
        for (int i = 0; i < num; i++) {
            content += contactData[i] + lineSeparator;
        }
        // System.out.println(content+" = content");
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
                //Read from the original file and write to the new
                //unless content matches data to be removed.
                while ((line = br.readLine()) != null) {
                    if (!line.trim().equals(name)) {
                        pw.println(line);
                        pw.flush();
                    } else {
                        for (int i = 0; i < num - 1; i++) {
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
        return  sistemCall(name);
    }

    public String searchByPhone(final String phone) throws IOException {
        return sistemCall(phone);
    }

    public void Open() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
    public String sistemCall(final String data)throws IOException{
    BufferedReader br = new BufferedReader(new FileReader(file));
    try {
        String checkVar;
        while((checkVar = br.readLine()) != null){
        String line = checkVar+" ";                           // читаем в файле num строчек по порядку,записываем в строчку
        for(int i =0;i<num-1; i++){
        line += br.readLine()+" ";
        }

        Record rec = new Record(line);
        if(rec.phone.equals(data)){
            return rec.name;
        }
        if(rec.name.equals(data)) {
            return rec.phone+" "+rec.address+" "+rec.age;
        }
        }

        return null;

    } finally {
        br.close();
    }
}


    public static void main(String[] args) throws IOException {
        NotebookTxtDb n = new NotebookTxtDb("filename");
        n.addRecord("Anna 1222 moscow 19");
        n.addRecord("Mapap 966 dreamlend 120");
        n.addRecord("Joy 767 lvov 19");
        //System.out.println(n.sistemCall("767"));
        System.out.println(n.searchByName("Joy"));
        System.out.println(n.searchByPhone("966"));
        System.out.println(n.isNameExists("Mapap"));
        System.out.println(n.isNameExists("Misha"));
        // n.addRecord("12", "рпрпр");
        // n.Open();
    }
}
