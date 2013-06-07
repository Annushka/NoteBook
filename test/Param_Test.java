import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertFalse;


@RunWith(Parameterized.class)
public class Param_Test {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private interface NotebookDbFactory {
        NotebookDb create(final String fileName) throws IOException;
    }

    @Parameters
    public static Collection getParameters() throws IOException {
        Object[] p1 = {new NotebookDbFactory() {
            @Override
            public NotebookDb create(String fileName) throws IOException {
                return new NotebookTxtDb(fileName);
            }
        }};
        Object[] p2 = {new NotebookDbFactory() {
            @Override
            public NotebookDb create(String fileName) throws IOException {
                return new NotebookTxtMappedDb(fileName);
            }
        }};

        return Arrays.asList(p1, p2);
    }


    @Test
    public void phoneSearchTest() throws Exception {
        //1. поиск по номеру. Такого контакта нет.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        Assert.assertEquals(null, notebookDb.searchByPhone("999"));
    }

    @Test
    public void reopen() throws Exception {
        final String dbFileName = folder.newFile("db.txt").getName();
        final String name = "name1";
        final String phone = "phone1";
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            db1.addRecord(name+" "+phone+" adress age");
        }
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            Assert.assertTrue(db1.isNameExists(name));
            Assert.assertEquals(name+" "+phone+" adress age", db1.searchByName(name));
        }
    }

    @Test
    public void existContactTest() throws Exception {
        //2.такой контакт есть.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("JJ 111 Vlavivostok 10");
        Assert.assertEquals("JJ", notebookDb.searchByPhone("111"));

        notebookDb.remove("JJ");
    }

    @Test
    public void samePhonesTest() throws Exception {
        //3.существует 2 контакта с одинаковыми номерами. Результатом поиска по телефону
        // является первый из них.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("olimp 95 grise 44");
        notebookDb.addRecord("pasha 95 moscow 55");
        Assert.assertEquals("olimp", notebookDb.searchByPhone("95"));
        notebookDb.remove("olimp");
        notebookDb.remove("pasha");
    }

    @Test
    public void noExistContactTest() throws Exception {
        //4.поиск по имени. Такого контакта нет.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        Assert.assertEquals(null, notebookDb.searchByName("anna"));

    }

    @Test
    public void addSameContact() throws Exception {
        //5.Проверка существования 2х контактов с одинаковыми номерами. Контакт должен быть 1 и номер
        // его является последним из добавленных с таким именем.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("j 11 adress age");
        notebookDb.addRecord("j 111 adress2 age2");
        Assert.assertEquals("j 111 adress2 age2", notebookDb.searchByName("j"));
        notebookDb.remove("j");
    }

    @Test
    public void notSameContactsTest() throws Exception {
        //6.Контакты, у которых у одного имя записано с заглавной буквы, а у другого
        // с маленькой - это разные контакты
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("Q 8 QueenYard 23");
        notebookDb.addRecord("q 7 Vena 22");
        Assert.assertEquals("Q 8 QueenYard 23", notebookDb.searchByName("Q"));

        notebookDb.remove("Q");
        notebookDb.remove("q");
    }

    @Test
    public void removeTest() throws Exception {
        //8.Проверка удаления контакта
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("misha 516 Mayami 79");
        notebookDb.remove("misha");
        assertFalse(notebookDb.isNameExists("misha"));

    }


    public Param_Test(final NotebookDbFactory factory) {
        this.facotry = factory;
    }

    private NotebookDbFactory facotry;
}

