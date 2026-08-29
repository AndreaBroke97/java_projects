/*
Variabili:
-cos'è una variabile
-dichiarazione, assegnazione e inizializzazione
-variabili con diversi tipi di dati
-nomenclatura delle variabili */

/* 
public class Main {
   
    public static void main(String[] args) {
        int x; //dichiarazione
        String nome;
        double temperatura;

        x = 45; //assegnazione
        nome = "carlo";
        temperatura = 34.23;

        System.out.println(x);
        System.out.println(nome);
        System.out.println(temperatura);
        
    }
}*/




/*
tipi di dati primitive e reference
-boolean | true : false | 1 bit
-byte | -128 : 127 | 1 byte
-short | -32768 _ 32767 | 2 bytes
-int | -2 miliardi: 2 miliardi | 4 bytes
-long | -9 quintilioni: 9 quintilioni | 8 bytes

-float | number with 6-7 decimal figures(cifre) 5.123526f | 4 bytes
-double | number with 15 decimal figures(cifre) 5.123526236701522f | 8 bytes

-char | single char/lecter/ASCII 'f' | 2 bytes
-String | sequence of char "hello by java" | variable


-difference between primitive and reference (initial and disponible attributes)
 */
/* 
public class Main {

    public static void main(String[] args) {
        boolean luce = true;
        System.out.println(luce);
    }
}*/





/*
importare java.util.Scanner
creare oggetto Scanner
creare domande per utente: nome, cognome, eta, città */

/* 
import java.util.Scanner; //e una libreria che ci permette di usare uno scanner

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //avvio dello scanner

        System.out.println("Qual'è il tuo nome?");
        String nome = scanner.nextLine(); // richiamiamo lo scanner facendolo spuntare nella prossima riga

        System.out.println("Qual'è il tuo cognome?");
        String cognome = scanner.nextLine();

        System.out.println("Qual'è la tua età?");
        int eta = scanner.nextInt(); // non fa una nuova riga nextInt
        scanner.nextLine(); //così si vedrà
        
        System.out.println("In che città vivi?\n ");
        String citta = scanner.nextLine();

        
        System.out.println("Ciao " + nome + " " + cognome);
        System.out.println("Hai " + eta + " anni");
        System.out.println("Vivi a " + citta);
    }
}*/


/*
operator aritmetic: + - / * % 
shortHand +=, *= 
increment and decrement   x++, x--
example of expressions
*/
/* 
public class Main {
    public static void main(String[] args) {
        int x = 10;
        int y = 1;

        int z = x % y;

        System.out.println(z);
    }
}*/



/*
operatori logici: &&(and), ||(or), !(not)
*/
/* 
public class Main {
    public static void main(String[] args) {
        
        boolean x = 3 < 10 || 3 % 2 == 0;

        System.out.println(!(3 < 10)); //così sto invertendo la condizione praticamente facendo not


    }
}*/



/*
ternary operator
*/
/* 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        
        do {

            System.out.println("Sei online? (true/false): ");
            boolean isOnline = scanner.nextBoolean();

            if(isOnline) {
                System.out.println("è online");
            } else {
                System.out.println("non è online");
            }

            System.out.println("Press 0 for exit, for continue press any key: ");
            choice = scanner.nextInt();

        } while (choice != 0); 
        
            scanner.close();
    }
}*/


/* 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        
        do {

            System.out.println("Insert two numbers: ");
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            if(a > b) {
                System.out.println("a is major of b");
            } else {
                System.out.println("b is major of a");
            }

            System.out.println("Press 0 for exit, for continue press any key: ");
            choice = scanner.nextInt();

        } while (choice != 0); 
        
            scanner.close();
    }
}*/