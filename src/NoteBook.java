import java.io.*;

public class NoteBook {
    private NotebookTxtDb n;

    public NoteBook() throws IOException {
        n = new NotebookTxtDb("New.txt");
    }


    //добавление данных в файл file формат txt
    public void Add() throws Exception {
        String name = "";
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(System.in));
        while (n.isNameExists(name) || name.equals("")) {
            System.out.print("What is your name? ");
            name = reader.readLine();
        }
        String phone;
        System.out.print("Please, enter your phone ");
        phone = reader.readLine();
        n.addRecord(name, phone);


    }

    // удаление выбранных данных
    public void remove() throws IOException {
        String nameToRemove;
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the contact to remove -> ");
        nameToRemove = br1.readLine();
        n.remove(nameToRemove);
    }

    //поиск номера по имени. Метод возвращает номер, записанный в файле на строчку ниже, чем имя.
    public String searchByName() throws IOException {
        String name;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please, enter the name to search -> ");
        name = br.readLine();
        return n.searchByName(name);
    }

    public String searchByPhone() throws IOException {
        String phone;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please, enter the phone to search -> ");
        phone = br.readLine();
        return n.searchByPhone(phone);
    }

    public void Commander() throws Exception {
        final NoteBook c = new NoteBook();
        String command;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the command -> ");
        command = br.readLine();
        if (command.equals("open")) {
            n.Open();
        }
        if (command.equals("searchByName")) {
            System.out.println(c.searchByName());
        }
        if (command.equals("searchByPhone")) {
            System.out.println(c.searchByPhone());
        }
        if (command.equals("remove")) {
            c.remove();
        }
        if (command.equals("add")) {
            c.Add();
        }
        if (!command.equals("add") && !command.equals("remove") && !command.equals("open") && !command.equals("searchByName") && !command.equals("searchByPhone")) {
            System.out.println("your command is impossible. " +
                    "Possible commands: open, searchByName, searchByPhone, remove or add. Please,enter the command again -> ");
            c.Commander();
        }
    }


    public static void main(String[] args) throws Exception {
        final NoteBook c = new NoteBook();
       // c.Commander();
      // c.Commander();
        c.Add();
         c.remove();
        // c.searchByPhone();

    }
}
