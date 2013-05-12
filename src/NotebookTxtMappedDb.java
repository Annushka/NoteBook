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
    private LinkedHashMap<String, String> notebookCache;
    private NotebookTxtDb storage;


    NotebookTxtMappedDb(final String fileName) throws IOException {
        super(fileName);
        notebookCache = new LinkedHashMap<String, String>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                notebookCache.put(line, line = br.readLine());
            }
        } finally {
            br.close();
        }
    }

    public boolean isNameExists(final String name) throws IOException {
        return notebookCache.containsKey(name);
    }

    // запись данных (имя, телефон)  в Map
    public void addRecord(final String name, final String phone) throws IOException {
        notebookCache.put(name, phone);

    }

    public void remove(final String name) {
        notebookCache.remove(name);
    }

    public String searchByName(final String name) throws IOException {
        if (!notebookCache.containsKey(name)) {
            return null;
        }
        return notebookCache.get(name);
    }

    public String searchByPhone(final String phone) throws IOException {
        Iterator it = notebookCache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == phone) {
                return (String) entry.getKey();
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

}
