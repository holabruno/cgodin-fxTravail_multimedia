import java.util.Scanner;

public class programme {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Tapez 1 pour l'exercice OR, ou 2 pour l'exercie AND");
		
		short entree = scan.nextShort();
		
		switch(entree) {
		case 1:
			Or or = new Or();
			or.run();
			break;
		case 2:
			And and = new And();
			and.run();
			break;
		}

	}

}
