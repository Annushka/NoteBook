import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 24.04.13
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTxtMappedDb extends NotebookTxtDb {
    private File file;
    private  NotebookTxtDb nb_txt;
   private LinkedHashMap<String, Record> notebookCache = new LinkedHashMap<String, Record>();
   // private static LinkedHashMap<String, Record> saver ;//= new LinkedHashMap<String, Record>();
    private NotebookTxtDb storage = new NotebookTxtDb("storage");



    NotebookTxtMappedDb(final String fileName ) throws IOException {
       super(fileName);
        nb_txt = new NotebookTxtDb(fileName);


        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String checkVar;
            while ((checkVar = br.readLine()) != null) {
                String line = checkVar + " ";                           // читаем в файле num строчек по порядку,записываем в 1 строчку
                for (int i = 0; i < Record.num - 1; i++) {
                    line += br.readLine() + " ";
                }
                Record rec = new Record(line);                       //записываем данные в notebookCache (ключ , значение)
                notebookCache.put(rec.name, rec);

            }

        } finally {
            br.close();
        }

        }


    public boolean isNameExists(final String name) throws IOException {
        return notebookCache.containsKey(name);
    }

    // запись данных (имя, телефон)  в Map
    public void addRecord(final String data) throws Exception {

        Record rec = new Record(data);
        if (isNameExists(rec.name)) {
            remove(rec.name);
            nb_txt.remove(rec.name);
            throw new Exception("This name already exists.");
        }
        notebookCache.put(rec.name, rec);
        nb_txt.addRecord(rec.toString());

    }

    public void remove(final String name) {
        notebookCache.remove(name);
        nb_txt.remove(name);
    }


    public String searchByName(final String name) throws IOException {
        if (!notebookCache.containsKey(name)) {
            return null;
        }
        return notebookCache.get(name).toString();
    }

    public String searchByPhone(final String phone) throws IOException {
        for (Record record : notebookCache.values()) {
            Record rec = new Record(record.toString());
            if (rec.phone.equals(phone)) {
                return rec.name;
            }
        }
        return null;
    }




    public void Open() throws IOException {
        Iterator it = notebookCache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println(entry.getKey() + "  -  " + entry.getValue());
        }
    }


    public static void main(String[] args) throws Exception {
        //  NotebookTxtMappedDb m = new NotebookTxtMappedDb("filename");

        {
            NotebookTxtMappedDb m = new NotebookTxtMappedDb("filename");
            m.addRecord("name1 phone1 address1 19");
            m.addRecord("name2 phone2 address2 20");
            m.addRecord("name3 phone3 address3 21");
            m.remove("name1");
        }
        {
            NotebookTxtMappedDb m = new NotebookTxtMappedDb("filename");
            System.out.println(m.searchByName("name1"));
            System.out.println(m.searchByPhone("phone1"));
            System.out.println(m.isNameExists("name1"));

            System.out.println(m.searchByName("name2"));
            System.out.println(m.searchByName("name3"));
        }

        //  final String info1 = "name1 phone1 address1 19";


    }

}
