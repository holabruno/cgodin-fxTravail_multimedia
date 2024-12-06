import java.sql.*;
import java.util.Scanner;

public class programme {

	public static void main(String[] args) {
		//connection a la base de données
		String url = "jdbc:mysql://localhost:3306/cours3"; 
        String user = "root"; 
        String password = "pass123"; 
	
        Scanner scan = new Scanner(System.in);
        
        String callProcGetAllTeachers = "{CALL GetEnseignants()}";
        String callProcGetTeacherById = "{CALL GetEnseignantById(?)}";
        String callProcGetTeacherByName = "{CALL GetEnseignantById(?, ?)}";

        try (Connection cn = DriverManager.getConnection(url, user, password);
             CallableStatement csAllTeachers = cn.prepareCall(callProcGetAllTeachers)) {

            // Set the parameters for the stored procedure
            //callableStatement.setString(1, "john");
            //callableStatement.setString(2, "doe");

            // Execute the stored procedure
            ResultSet resultSet = csAllTeachers.executeQuery();

            // Check if the result set is not empty
            while (resultSet.next()) {
                int idProf = resultSet.getInt("idProf");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom");
                System.out.println("Professor ID: " + idProf + " Prénom: " + prenom + " Nom: " + nom);
            }
            
            System.out.println("Quel Id d'enseignant voulez vous sélectionner?");
            int id = scan.nextInt();
            
            System.out.println("Quelle opération voulez vous effectuer sur l'enseignant #" + id + " ? - 1. Effacer, 2. Mettre à jour, 3. Afficher" );
            int operation = scan.nextInt();
            
            switch(operation) {
            case 1:
            	//Effacer
            	effacerEnseignant(id, cn);
            	break;
            case 2:
            	//Mettre à jour
            	mettre_a_jourEnseignant(id, scan, cn);
            	
            	break;
            case 3: 
            	//Afficher
            	afficherEnseignant(id, cn);
            	break;
            }
            
            
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la connexion à la base de données ou en exécutant la procédure stockée.");
            e.printStackTrace();
        }
        scan.close();
        
        System.out.println("Programme terminé avec succès!");
	}
	
	public static void afficherEnseignant(int id, Connection cn) {
		
		String selectQuery = "SELECT * FROM enseignants WHERE idProf = ?";
		try {
			PreparedStatement prepState = cn.prepareStatement(selectQuery);
			prepState.setInt(1, id); 
		    ResultSet rs = prepState.executeQuery();
		    
		    if(rs.next()) {
		    System.out.println("idProf: " + rs.getInt("idProf") + " Prenom: " + rs.getString("prenom") + " Nom: " + rs.getString("nom"));
		    }else {
		    	System.out.println("Pas d'enseignant trouvé avec l'idProf: " + id);
		    }
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
		
			
	public static void effacerEnseignant(int id, Connection cn) {
		String deleteQuery = "DELETE FROM enseignants WHERE idProf = ?";
		
		try {
			PreparedStatement prepState = cn.prepareStatement(deleteQuery);
			prepState.setInt(1, id);
			int rowsAffected = prepState.executeUpdate(); 
	        
			if (rowsAffected > 0) {
	            System.out.println("L'enseignant avec l'idProf = " + id + " a bien été effacé.");
	        } else {
	            System.out.println("Aucun enseignant trouivé avec l'idProf = " + id);
	        }
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void mettre_a_jourEnseignant(int id, Scanner scan, Connection cn) {
		System.out.println("Entrez le nouveau prénom de l'enseignant #" + id);
		String prenom = scan.next();
		System.out.println("Entre le nouveau nom de l'enseignant #" + id);
		String nom = scan.next();
		
		String updateQuery = "UPDATE enseignants SET prenom = ?, nom = ? WHERE idProf = " + id;
		
		try {
			PreparedStatement prepState = cn.prepareStatement(updateQuery);
			// Set parameters for the query
	        prepState.setString(1, prenom);
	        prepState.setString(2, nom);
	
	        // Execute the update
	        int rowsAffected = prepState.executeUpdate();
	
	        if (rowsAffected > 0) {
	            System.out.println("Mise à jour effectuée avec succès: " + rowsAffected + " rangée modifiée.");
	            afficherEnseignant(id, cn);
	        } else {
	            System.out.println("Aucun enseignant trouvé avec l'ID " + id);
	        }
	
	    } catch (SQLException e) {
	        System.out.println("Une erreur est survenue en se connectant à la base de données ou en mettant à jour l'enregistrement #" + id);
	        e.printStackTrace();
	    }
		
	}

}
