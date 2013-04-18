package NoteBook;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 13.04.13
 * Time: 6:38
 * To change this template use File | Settings | File Templates.
 */
public class Create_Test {
    @Test  //если вызваем команду open 2 раза, открывает файл тоже 2 раза
    public void doubleOpen() throws Exception {
        final NoteBook  c = new NoteBook ();
        c.Open();
        c.Open();
    }

    @Test  // вводим имя или номер, который уже есть в файле. Ошибка.
    public void AddTheSame() throws Exception {
        final NoteBook  c = new NoteBook ();
        c.Add();

    }

    @Test  // вводим имя или номер для удаления, которого нет в файле. Ошибка.
    public void Remove() throws Exception {
        final NoteBook  c = new NoteBook ();


    }

    @Test  // поиск по имени или номеру. Такого контакта нет. Ошибка.
    public void Search() throws Exception {
        final NoteBook  c = new NoteBook ();


    }

    @Test  // Нет такой команды. Ошибка.
    public void Commander() throws Exception {
        final NoteBook  c = new NoteBook ();


    }
}
