package by.maryrgb;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String command = "";
        Scanner scanner = new Scanner(System.in);

        try {
            Worker worker = new Worker();

            System.out.print("Enter action: ");

            while ((command = scanner.nextLine()).compareTo("exit") != 0) {
                switch (command) {
                    case "create":
                        try {
                            worker.create(scanner);
                        }
                        catch (OverFlowException ofe) {
                            ofe.printStackTrace();
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }
                        break;
                    case "info":
                        try {
                            System.out.println(worker.getInfo(scanner));
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }
                        break;
                    case "delete":
                        worker.delete(scanner);
                        break;
                }

                System.out.print("Enter action: ");
            }

            worker.endWork();
        }
        catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
    }
}
