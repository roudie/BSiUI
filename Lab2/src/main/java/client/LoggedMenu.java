package client;

import java.util.Scanner;

public class LoggedMenu extends Thread{
    private ClientLogic clientLogic;
    private String login = "";
    private boolean end = false;

    public LoggedMenu(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
        this.login = clientLogic.getLogin();
        this.start();
        clientLogic.setLoggedMenuLock(new Object());
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nZalogowano na konto " + login);
        String buffor = "";
        do {
            System.out.println("1. Wyloguj");
            System.out.println("2. Lista uzytkownikow zalogowanych");
            System.out.println("3. Lista uzytkownikow niezalogowanych");
            System.out.println("4. Wyszukaj uzytkownikow zalogowanych");
            System.out.println("5. Wyszukaj uzytkownikow niezalogowanych");
            System.out.println("6. Napisz do uzytkownika");
            int x = scanner.nextInt();
            switch (x) {
                case 1:
                    end = true;
                    clientLogic.Logout(login);
                    break;
                case 2:
                    clientLogic.List(true);
                    try {
                        synchronized (clientLogic.getLoggedMenuLock()) {
                            clientLogic.getLoggedMenuLock().wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    break;
                case 3:
                    clientLogic.List(false);
                    try {
                        synchronized (clientLogic.getLoggedMenuLock()) {
                            clientLogic.getLoggedMenuLock().wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                case 4:
                    System.out.println("Podaj nazwe uzytkownika: ");
                    buffor = scanner.next();
                    clientLogic.find(buffor, true);
                    try {
                        synchronized (clientLogic.getLoggedMenuLock()) {
                            clientLogic.getLoggedMenuLock().wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    System.out.println("Podaj nazwe uzytkownika: ");
                    buffor = scanner.next();
                    clientLogic.find(buffor, false);
                    try {
                        synchronized (clientLogic.getLoggedMenuLock()) {
                            clientLogic.getLoggedMenuLock().wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                case 6:
                    System.out.println("Podaj nazwe uzytkownika: ");
                    String recip = scanner.nextLine();
                    recip = scanner.nextLine();
                    String message = "";
                    System.out.println("Aby zakonczyc konwersacje wpisz '00'");
                    do {
                        message=scanner.nextLine();
                        if(message.equals("00"))
                            break;
                        clientLogic.text(recip, message);
                    } while (true);
                    break;

            }
        } while (!end);
    }
}
