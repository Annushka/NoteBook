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
    private LinkedHashMap<String, Record> notebookCache;
    private NotebookTxtDb storage;


    NotebookTxtMappedDb(final String fileName) throws IOException {
        super(fileName);
        notebookCache = new LinkedHashMap<String, Record>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String checkVar;
            while ((checkVar = br.readLine()) != null) {
                String line = checkVar + " ";                           // читаем в файле num строчек по порядку,записываем в 1 строчку
                for (int i = 0; i < Record.num - 1; i++) {
                    line += br.readLine() + " ";
                }
                Record rec = new Record(line);                       //записываем данные в notebookCache (ключ , значение)
                notebookCache.put(rec.getName(), rec);

            }

        } finally {
            br.close();
        }
    }


    public boolean isNameExists(final String name) throws IOException {
        return notebookCache.containsKey(name);
    }

    // запись данных (имя, телефон)  в Map
    public void addRecord(final String data) throws IOException {
        Record rec = new Record(data);
        notebookCache.put(rec.getName(), rec);

    }

    public void remove(final String name) {
        notebookCache.remove(name);
    }


    public Record searchByName(final String name) throws IOException {
        if (!notebookCache.containsKey(name)) {
            return null;
        }
        return notebookCache.get(name);
    }

    public Record searchByPhone(final String phone) throws IOException {
        Iterator it = notebookCache.values().iterator();
        while (it.hasNext()) {
            Record rec = new Record(it.next().toString());
            if (rec.getPhone().equals(phone)) {
                return rec;
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


    public static void main(String[] args) throws IOException {
        NotebookTxtMappedDb m = new NotebookTxtMappedDb("filename");
        m.addRecord("lina 4444 Kiev 44");
        // System.out.println(m.notebookCache.values());
        // m.addRecord("misha 67574 Moscow 54");
        // m.addRecord("pasha 9999 Volgograd 98");
        System.out.println(m.notebookCache.values());
        //  m.sistemCall();
        //  System.out.println(m.isNameExists("anna") + " - test isNameExists");
        System.out.println(m.searchByName("lina") + " - by name anna");
        //System.out.println(m.searchByPhone("4444") + " - by phone 4444");
        //System.out.println(m.notebookCache.values());
        m.remove("pasha");
        System.out.println(m.notebookCache.values());
        //System.out.println(m.notebookCache.values());
        //m.Open();

    }

}
