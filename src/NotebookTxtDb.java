package NoteBook;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 16.04.13
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTxtDb {
    private File file;

    NotebookTxtDb(final String fileName) {
        file = new File(fileName);
    }

    boolean isNameExists(final String name) {
        return false;
    }

    void addRecord(final String name, final String phone) {

    }

    void remove(final String name) {

    }

    String searchByName(final String name) {
        return null;
    }

    String searchByPhone(final String phone) {
        return null;
    }
}
