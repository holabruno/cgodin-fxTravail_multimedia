
public class Or {
	public void run() {
        
        int a = 5;
        int b = 6;
        
        if(a == 5 || test(1)) {
        	// Avec 'or' si la premiere condition est vraie, on sort de la condition if !!!
        	System.out.println("a égale 5");
        }
        
        if(a > 5 || test(2)) {
        	// avec || si la premiere condition est fausse, il passe a la prochaine !!!
        	System.out.println("(false || true) retourne true, donc imprime a l'écran");
        }
	}
	
	public static boolean test(int occurence) {
		System.out.println("test " + occurence + " exécuté");
		return true;
	}
}
