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

import static junit.framework.Assert.*;

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

    //==================================================================
    // Name tests
    //==================================================================
    // Search

    @Test
    public void nameSearchEmpty() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        assertNull(notebookDb.searchByName("name"));
    }

    @Test
    public void nameSearchNonexistent() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        assertNull(notebookDb.searchByName("name2"));
    }

    @Test
    public void nameSearchExistent() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        final String info = "name1 phone1 address1 19";
        notebookDb.addRecord(info);
        final String actual = notebookDb.searchByName("name1");
        assertEquals(info, actual);
    }

    @Test
    public void nameSearchNonexistentLikeExistedAddress() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        final String info = "name1 phone1 address1 19";
        notebookDb.addRecord(info);
        assertNull(notebookDb.searchByName("address1"));
    }

    @Test
    public void nameSearchNonexistentLikeExistedPhone() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        final String info = "name1 phone1 address1 19";
        notebookDb.addRecord(info);
        assertNull(notebookDb.searchByName("phone1"));
    }

    //------------------------------------------------------------------
    // Add

    @Test(expected = Exception.class)
    public void nameAddDuplicate1() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        final String info = "name1 phone1 address1 19";
        notebookDb.addRecord(info);
        notebookDb.addRecord(info);
    }

    @Test(expected = Exception.class)
    public void nameAddDuplicate2() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        notebookDb.addRecord("name1 phone2 address2 20");
    }

    //------------------------------------------------------------------
    // Remove

    @Test
    public void nameRemoveNonexistent() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        notebookDb.remove("name2");
        assertNotNull(notebookDb.searchByName("name1"));
    }

    @Test
    public void nameRemoveExistent() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        notebookDb.remove("name1");
        assertNull(notebookDb.searchByName("name1"));
        assertNull(notebookDb.searchByPhone("phone1"));
    }

    @Test
    public void nameRemoveExistentWithMoreRecords() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        notebookDb.addRecord("name2 phone2 address2 20");
        notebookDb.addRecord("name3 phone3 address3 21");
        notebookDb.remove("name2");
        assertNull(notebookDb.searchByName("name2"));
        assertNull(notebookDb.searchByPhone("phone2"));

        assertNotNull(notebookDb.searchByName("name1"));
        assertNotNull(notebookDb.searchByPhone("phone1"));
        assertNotNull(notebookDb.searchByName("name3"));
        assertNotNull(notebookDb.searchByPhone("phone3"));
    }

    @Test
    public void nameRemoveLikeAddress1() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        notebookDb.addRecord("name2 phone2 address2 20");
        notebookDb.addRecord("name3 phone3 address3 21");

        notebookDb.remove("address1");
        // check that all records still in database
        for (int i = 1; i <= 3; ++i) {
            assertNotNull(notebookDb.searchByName(String.format("name%s", i)));
            assertNotNull(notebookDb.searchByPhone(String.format("phone%s", i)));
        }
    }

    @Test
    public void nameRemoveLikeAddress2() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        notebookDb.addRecord("name2 phone2 address2 20");
        notebookDb.addRecord("name3 phone3 address3 21");

        notebookDb.remove("address2");
        // check that all records still in database
        for (int i = 1; i <= 3; ++i) {
            assertNotNull(notebookDb.searchByName(String.format("name%s", i)));
            assertNotNull(notebookDb.searchByPhone(String.format("phone%s", i)));
        }
    }

    // =================================================================
    // Phone tests

    @Test
    public void phoneSearchEmpty() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        assertNull(notebookDb.searchByPhone("999"));
    }

    @Test
    public void phoneSearchNonexistent() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        assertNull(notebookDb.searchByPhone("phone2"));
    }

    @Test
    public void phoneSearchExistent() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        final String info = "name1 phone1 address1 19";
        notebookDb.addRecord(info);
        assertEquals("name1", notebookDb.searchByPhone("phone1"));
    }

    @Test
    public void phoneSearchNonexistentLikeExistedAddress() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        final String info = "name1 phone1 address1 19";
        notebookDb.addRecord(info);
        assertNull(notebookDb.searchByPhone("address1"));
    }

    @Test
    public void phoneSearchNonexistentLikeExistedName() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        final String info = "name1 phone1 address1 19";
        notebookDb.addRecord(info);
        assertNull(notebookDb.searchByName("name1"));
    }

    // =================================================================
    // IsNameExists test


    @Test
    public void isNameExistsEmpty() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        assertFalse(notebookDb.isNameExists("name1"));
    }

    @Test
    public void isNameExistsAfterAdd() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        assertTrue(notebookDb.isNameExists("name1"));
    }

    @Test
    public void isNameExistsAfterRemove() throws Exception {
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("name1 phone1 address1 19");
        notebookDb.remove("name1");
        assertFalse(notebookDb.isNameExists("name1"));
    }

    // =================================================================
    // Reopen tests

    @Test
    public void reopenSearchByName() throws Exception {
        final String dbFileName = folder.newFile("db.txt").getName();
        final String info = "name1 phone1 address1 19";
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            db1.addRecord(info);
        }
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            assertEquals(info, db1.searchByName("name1"));
        }
    }

    @Test
    public void reopenSearchByPhone() throws Exception {
        final String dbFileName = folder.newFile("db.txt").getName();
        final String info = "name1 phone1 address1 19";
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            db1.addRecord(info);
        }
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            assertEquals("name1", db1.searchByPhone("phone1"));
        }
    }

    @Test
    public void reopenIsNameExists() throws Exception {
        final String dbFileName = folder.newFile("db.txt").getName();
        final String info = "name1 phone1 address1 19";
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            db1.addRecord(info);
        }
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            assertTrue(db1.isNameExists("name1"));
        }
    }

    @Test
    public void reopenAfterRemove() throws Exception {
        final String dbFileName = folder.newFile("db.txt").getName();
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            db1.addRecord("name1 phone1 address1 19");
            db1.addRecord("name2 phone2 address2 20");
            db1.addRecord("name3 phone3 address3 21");
            db1.remove("name1");
        }
        {
            final NotebookDb db1 = facotry.create(dbFileName);
            assertNull(db1.searchByName("name1"));
            assertNull(db1.searchByPhone("phone1"));
            assertFalse(db1.isNameExists("name1"));

            assertNotNull(db1.searchByName("name2"));
            assertNotNull(db1.searchByName("name3"));
        }
    }

    // =================================================================
    // Other tests

    @Test
    public void samePhonesTest() throws Exception {
        //3.существует 2 контакта с одинаковыми номерами. Результатом поиска по телефону
        // является первый из них.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("olimp 95 grise 44");
        notebookDb.addRecord("pasha 95 moscow 55");
        assertEquals("olimp", notebookDb.searchByPhone("95"));
        notebookDb.remove("olimp");
        notebookDb.remove("pasha");
    }

    @Test
    public void noExistContactTest() throws Exception {
        //4.поиск по имени. Такого контакта нет.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        assertEquals(null, notebookDb.searchByName("anna"));

    }

    @Test
    public void addSameContact() throws Exception {
        //5.Проверка существования 2х контактов с одинаковыми номерами. Контакт должен быть 1 и номер
        // его является последним из добавленных с таким именем.
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("j 11 adress age");
        notebookDb.addRecord("j 111 adress2 age2");
        assertEquals("j 111 adress2 age2", notebookDb.searchByName("j"));
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
        assertEquals("Q 8 QueenYard 23", notebookDb.searchByName("Q"));

        notebookDb.remove("Q");
        notebookDb.remove("q");
    }

    @Test
    public void removeTest() throws Exception {
        //8.Проверка удаления контакта
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("misha 516 Mayami 79");
        assertTrue(notebookDb.isNameExists("misha"));
        notebookDb.remove("misha");
        assertFalse(notebookDb.isNameExists("misha"));
    }

    @Test
    public void removeTest1() throws Exception {
        //8.Проверка удаления контакта
        final File dbFile = folder.newFile("filename.txt");
        final NotebookDb notebookDb = facotry.create(dbFile.getName());
        notebookDb.addRecord("misha1 516 misha 79");
        notebookDb.addRecord("pasha 95 moscow 55");
        assertTrue(notebookDb.isNameExists("misha1"));
        assertTrue(notebookDb.isNameExists("pasha"));
        notebookDb.remove("misha");
        //  assertTrue(notebookDb.isNameExists("misha1"));
        //   assertTrue(notebookDb.isNameExists("pasha"));
        //   notebookDb.remove("misha1");
        //   notebookDb.remove("pasha");
    }

    public Param_Test(final NotebookDbFactory factory) {
        this.facotry = factory;
    }

    private NotebookDbFactory facotry;
}

