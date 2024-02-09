import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    /*
     * Guarda la fecha de ingreso del trabajador, ya sea en formato DD/MM/YYYY o en forma de texto
    */
    String date = "";

    /*
     * Instancia de la variable que leera los datos ingresados por teclado 
    */
    Scanner resp = new Scanner(System.in);

    /*
     * Expresiones que verifican el formato DD/MM/YYYY y YYYY/MM/DD 
    */
    String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
    String regex2 = "^\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])";

    Pattern patron = Pattern.compile(regex);

    /*
     * Muestra un mensaje inicial que indica el proposito del programa
    */
    public void showStartMessage() {
        /*
         * Se encarga de limpiar la pantalla de la consola
        */
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            /* No hacer nada */
        }

        System.out.println("*******************************************************************************");
        System.out.println("********** PROGRAMA QUE REALIZA EL CALCULO DE LOS DIAS DE VACACIONES **********");
        System.out.println("*******************************************************************************\n");
    }

    /**
     * * Se encarga de separar las fechas por numero, convertirlos a enteros y calcular la diferencia de la fecha dada con la fecha actual. Recibe como parametro 1 variable que es la fecha de entrada a laborar.
     * @param dateOfAdmission
     * @return Period period
    */
    public Period computeDifferenceOfDates(String dateOfAdmission) {
        String date[] = dateOfAdmission.split("\\/");

        int day   = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year  = Integer.parseInt(date[2]);

        LocalDate admissionDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(admissionDate, currentDate);

        return period;
    }

    /**
     * * Se encarga de construir el mensaje de salida despues de calcular el numero de dias de vacaciones con base en los anios transcurrios desde la entrada a laborar hasta la fecha presente.
     * @param yearsPassed
     * @param monthsPassed
     * @return String
     */
    public String getMessageOfVacationsDays(int yearsPassed, int monthsPassed) {
        int vacationsDays = 0;

        if (yearsPassed == 0) {
            String messageMonth = monthsPassed > 1 ? "meses trabajando." : "mes trabajando.";

            return (monthsPassed == 0)
                    ? "Lo siento, aun no te corresponden vacaciones, recien acabas de ingresar a laborar. "
                    : "Lo siento, aun no te corresponden vacaciones, apenas llevas " + monthsPassed + " "
                            + messageMonth;
        } else {
            vacationsDays = switch (yearsPassed) {
                case 1 -> 12;
                case 2 -> 14;
                case 3 -> 16;
                case 4 -> 18;
                case 5 -> 20;
                default -> 0;
            };

            if (yearsPassed >= 6 && yearsPassed <= 10) {
                vacationsDays = 22;
            }

            if (yearsPassed >= 11 && yearsPassed <= 15) {
                vacationsDays = 24;
            }

            if (yearsPassed >= 16 && yearsPassed <= 20) {
                vacationsDays = 26;
            }

            if (yearsPassed >= 21 && yearsPassed <= 25) {
                vacationsDays = 28;
            }

            if (yearsPassed >= 26 && yearsPassed <= 30) {
                vacationsDays = 30;
            }

            if (yearsPassed >= 31 && yearsPassed <= 35) {
                vacationsDays = 32;
            }
        }

        return "De acuerdo al calculo, te corresponden " + vacationsDays + " dias de vacaciones.";
    }

    /**
     * * Realiza la lectura de la fecha de ingreso a laborar.
     * @return String
    */
    public String readEntryDate() {
        System.out.print("Para poder realizar el calulo, ingresa la fecha en formato DD/MM/YYYY o YYYY/MM/DD: ");
        date = resp.next();

        return date;
    }
    
    /*
     * Realiza la evaluacion para que el patron ingresado por consola se cumpla con la expresion regular.
    */
    public void computeDateOfEntry() {
        String dateOfWorkStarted = ""; 
        boolean isMatching = false;

        do {
            dateOfWorkStarted = readEntryDate();
            Matcher match = patron.matcher(dateOfWorkStarted);
            isMatching = match.find();

            if (!isMatching) {
                System.out.println("Lo siento, la fecha que ingresaste tiene un formato erroneo, por favor, ingresa una fecha correcta.");
            } else {
                isMatching = true;
            }
        } while (!isMatching);

        int passedYears = computeDifferenceOfDates(dateOfWorkStarted).getYears();
        int passedMonths = computeDifferenceOfDates(dateOfWorkStarted).getMonths();

        String message = getMessageOfVacationsDays(passedYears, passedMonths);

        System.out.println(message);
    }

    /*
     * Funcion principal donde se ejecutan todos los metodos.
    */
    public void main() {

        showStartMessage();

        int option = 0;
        
        while (option != 2) {
            computeDateOfEntry();
            System.out.print("\nDeseas realizar el calculo de otra fecha? 1- Si / 2- No: ");
            option = resp.nextInt();
        }
    }

    /*
     * Funcion principal donde se ejecuta el metodo principal de la clase.
    */
    public static void main(String[] args) {
        App app = new App();
        app.main();
    }
}