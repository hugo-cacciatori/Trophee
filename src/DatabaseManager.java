import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {

    private String url= "jdbc:mysql://127.0.0.1:3306/teambuilder";
    private String login = "root";
    private String passwd="";

	private Connection connection;

    private static DatabaseManager instance = null;
    
    public static DatabaseManager getInstance(){
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    public DatabaseManager(){
		openBdd();
    }
    
    private void openBdd(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(this.url, this.login, this.passwd);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	public void refreshConnection(){
		try {
			this.connection = DriverManager.getConnection(this.url, this.login, this.passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cleanUp(){
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public ResultSet authentificate(String id, String pw){
        try {
			String sql = "SELECT * FROM users WHERE id = ? AND pw = ? AND isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, pw);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

	public ResultSet getAllSports(){
        try {
			String sql = "SELECT * FROM sports;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getAllPlayers(){
        try {
			String sql = "SELECT * FROM players, clubplayers, clubs WHERE players.id = clubplayers.playerID AND clubplayers.clubID = clubs.id AND players.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getAllTeams(){
        try {
			String sql = "SELECT * FROM teams WHERE teams.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setTeamAvailable(int teamID){
        try {
			String sql = "UPDATE teams SET isAvailable = 1 WHERE teams.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setTeamUnavailable(int teamID){
        try {
			String sql = "UPDATE teams SET isAvailable = 0 WHERE teams.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getAllEvents(){
        try {
			String sql = "SELECT * FROM events;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

    public boolean checkPw(int userTag, String pw){
        try {
			String sql = "SELECT * FROM users WHERE users.tag = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userTag);
			ResultSet rs = statement.executeQuery();
			if(rs.next() && pw.equals(rs.getString("pw")))return true;
            else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

	public void setClubSport(int clubID, int sportID){
		try 
		{
			String sql = "UPDATE clubs SET sportID = ? WHERE clubs.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, sportID);
			statement.setInt(2, clubID);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public ResultSet getUser(int userTag){
        try {
			String sql = "SELECT * FROM users WHERE users.tag = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userTag);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public ResultSet getUserClubs(int userTag){
        try {
			String sql = "SELECT * FROM clubs, users WHERE clubs.userTag = users.tag AND users.tag = ? AND clubs.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userTag);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public ResultSet getAllUserClubs(int userTag){
        try {
			String sql = "SELECT * FROM clubs, users WHERE clubs.userTag = users.tag AND users.tag = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userTag);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public int countUserClubs(int userTag){
		int count = 0;
        try {
			String sql = "SELECT * FROM clubs, users WHERE clubs.userTag = users.tag AND users.tag = ? AND clubs.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userTag);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				count++;
			}
            return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
    }

    public ResultSet getClubPlayers(int clubID){
        try {
			String sql = "SELECT * FROM players, clubplayers, clubs WHERE players.id = clubplayers.playerID AND clubplayers.clubID = clubs.id AND clubs.id = ? AND players.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public int countClubPlayers(int clubID){
		int count = 0;
        try {
			String sql = "SELECT * FROM players, clubplayers, clubs WHERE players.id = clubplayers.playerID AND clubplayers.clubID = clubs.id AND clubs.id = ? AND players.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				count++;
			}
            return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
    }

	public Sport getSport(int sportID){
        try {
			String sql = "SELECT * FROM sports WHERE sports.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, sportID);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) return new Sport(rs.getInt("sports.id"), rs.getString("sports.nom"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }

	public Club getClub(int clubID){
		try {
			String sql = "SELECT * FROM clubs WHERE clubs.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			ResultSet rs = statement.executeQuery();
			if(rs.next())return new Club(clubID, rs.getString("clubs.nom"), rs.getString("clubs.adresse"), rs.getString("clubs.tel"), rs.getString("clubs.mail"), rs.getString("clubs.description"), rs.getInt("clubs.sportID"), rs.getInt("clubs.userTag"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getClubProfilePic(int clubID){
		try {
			String sql = "SELECT clubs.imgPath FROM clubs WHERE clubs.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getPlayerSports(int playerID){
		try {
			String sql = "SELECT DISTINCT * FROM sports, players, clubs, clubplayers WHERE sports.id = clubs.sportID AND players.id = clubplayers.playerID AND clubplayers.clubID = clubs.id AND players.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, playerID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getPlayerTeams(int playerID){
		try {
			String sql = "SELECT DISTINCT * FROM teams, players, teamplayers WHERE teams.id = teamplayers.teamID AND players.id = teamplayers.playerID AND players.id = ? AND teams.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, playerID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getTeamPlayers(int teamID){
		try {
			String sql = "SELECT DISTINCT * FROM teams, players, teamplayers WHERE teams.id = teamplayers.teamID AND players.id = teamplayers.playerID AND teams.id = ? AND players.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getTeamHistorique(int teamID){
		try {
			String sql = "SELECT DISTINCT * FROM teams, teamevents, events WHERE teams.id = teamevents.teamID AND teamevents.eventID = events.id AND teams.id = ? AND events.isOpen = 0;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getTeamHistoriqueSize(int teamID){
		int count = 0;
		try {
			String sql = "SELECT DISTINCT * FROM teams, teamevents, events WHERE teams.id = teamevents.teamID AND teamevents.eventID = events.id AND teams.id = ? AND events.isOpen = 0;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				count++;
			}
            return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
	}

	public int getTeamNbPlayers(int teamID){
		int count = 0;
		try {
			String sql = "SELECT DISTINCT * FROM teams, players, teamplayers WHERE teams.id = teamplayers.teamID AND players.id = teamplayers.playerID AND teams.id = ? AND players.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				count++;
			}
            return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
	}

	public int getPlayerNBTeams(int playerID){
		int count = 0;
		try {
			String sql = "SELECT DISTINCT * FROM teams, players, teamplayers WHERE teams.id = teamplayers.teamID AND players.id = teamplayers.playerID AND players.id = ? AND teams.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, playerID);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				count++;
			}
            return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
	}

    public ResultSet getClubTeams(int clubID){
        try {
			String sql = "SELECT * FROM teams, clubs WHERE teams.clubID = clubs.id AND clubs.id = ? AND teams.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public int countClubTeams(int clubID){
		int count = 0;
        try {
			String sql = "SELECT * FROM teams, clubs WHERE teams.clubID = clubs.id AND clubs.id = ? AND teams.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				count++;
			}
            return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
    }

    public ResultSet getClubHistorique(int clubID){
        try {
			String sql = "SELECT * FROM events, players, teams, clubs, clubplayers, teamevents, playerevents WHERE players.id = clubplayers.playerID AND clubplayers.clubID = clubs.id AND teams.clubID = clubs.ID AND teamevents.teamID = teams.id AND playerevents.playerID = players.ID AND clubs.id = ? AND events.isOpen = 0;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public ResultSet getAllUsers(){
        try {
			String sql = "SELECT * FROM users WHERE users.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public boolean isUserNameAvailable(String userName){
        try {
			String sql = "SELECT * FROM users WHERE users.nom = ? AND users.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return false;
            else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public boolean isUserIDAvailable(String userID){
        try {
			String sql = "SELECT * FROM users where users.id = ? AND users.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return false;
            else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public String getPW(int userTag){
        try {
			String sql = "SELECT * FROM users where users.tag = ? AND users.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userTag);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return rs.getString("users.pw");
            else return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public String getUserID(int userTag){
        try {
			String sql = "SELECT users.tag FROM users where users.tag = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userTag);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return rs.getString("users.tag");
            else return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createClub(String nom, String adresse, String tel, String mail, String description, int sportID, int userTag){
		try 
		{
			String sql = "INSERT INTO clubs (nom, adresse, tel, mail, description, sportID, userTag) VALUES (?, ?, ?, ?, ?, ?, ?); ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nom);
			statement.setString(2, adresse);
			statement.setString(3, tel);
			statement.setString(4, mail);
			statement.setString(5, description);
			statement.setInt(6, sportID);
			statement.setInt(7, userTag);
			
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createClub(String nom, String adresse, String tel, String mail, String description, int sportID, int userTag, String imgPath){
		try 
		{
			String sql = "INSERT INTO clubs (nom, adresse, tel, mail, description, sportID, userTag) VALUES (?, ?, ?, ?, ?, ?, ?, ?); ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nom);
			statement.setString(2, adresse);
			statement.setString(3, tel);
			statement.setString(4, mail);
			statement.setString(5, description);
			statement.setInt(6, sportID);
			statement.setInt(7, userTag);
			statement.setString(8, imgPath);
			
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

    public boolean isClubNameAvailable(String clubName){
        try {
			String sql = "SELECT * FROM clubs where clubs.nom = ? AND clubs.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, clubName);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return false;
            else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public boolean isClubMailAvailable(String clubMail){
        try {
			String sql = "SELECT * FROM clubs where clubs.mail = ? AND clubs.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, clubMail);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return false;
            else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public boolean isPlayerMailAvailable(String playerMail){
        try {
			String sql = "SELECT * FROM players where players.mail = ? AND players.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, playerMail);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return false;
            else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public boolean isTeamNameAvailable(String teamName){
        try {
			String sql = "SELECT * FROM teams where teams.nom = ? AND teams.isActive = 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teamName);
			ResultSet rs = statement.executeQuery();
            if(rs.next())return false;
            else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public void createUser(String nom, String id, String pw){
        try 
		{
			String sql = "INSERT INTO users (nom, id, pw) VALUES (?, ?, ?); ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nom);
			statement.setString(2, id);
			statement.setString(3, pw);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	public void createPlayer(String nom, String a, boolean x, String t, String m, int c){
		try 
		{
			String sql = "INSERT INTO players (nom, age, sexe, tel, mail) VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, nom);
			statement.setString(2, a);
			statement.setBoolean(3, x);
			statement.setString(4, t);
			statement.setString(5, m);

			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				String sql2 = "INSERT INTO clubplayers (clubID, playerID) VALUES (?, ?); ";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				statement2.setInt(1, c);
				statement2.setInt(2, generatedKeys.getInt(1));
				statement2.executeUpdate();
			}
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createTeam(String nom, int clubID, ArrayList<Player> teamComp, int capitaineID){
		try 
		{
			String sql = "INSERT INTO teams (nom, clubID, capitaineID) VALUES (?, ?, ?); ";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, nom);
			statement.setInt(2, clubID);
			statement.setInt(3, capitaineID);

			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				String sql2 = "INSERT INTO teamplayers (teamID, playerID) VALUES (?, ?); ";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				for(Player p:teamComp){
					statement2.setInt(1, generatedKeys.getInt(1));
					statement2.setInt(2, p.getID());
					statement2.executeUpdate();
				}
			}
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void createEvent(String nom, int sportID, int clubID, ArrayList<Participant> participants, String description, String date, boolean Individuel){
		try 
		{
			String sql = "INSERT INTO events (nom, description, dateDebut, sportID, clubID) VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, nom);
			statement.setString(2, description);
			statement.setString(3, date);
			statement.setInt(4, sportID);
			statement.setInt(5, clubID);

			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				if(Individuel){
					String sql2 = "INSERT INTO playerevents (eventID, playerID) VALUES (?, ?); ";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					int genkeys = generatedKeys.getInt(1);
					for(Participant p:participants){
						statement2.setInt(1, genkeys);
						statement2.setInt(2, ((RegisteredPlayer) p).getPlayer().getID());
						statement2.executeUpdate();
					}
				}
				else{
					String sql2 = "INSERT INTO teamevents (eventID, teamID) VALUES (?, ?); ";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					for(Participant p:participants){
						statement2.setInt(1, generatedKeys.getInt(1));
						statement2.setInt(2, p.getID());
						statement2.executeUpdate();
					}
				}
				}
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getClubPlayersNotInTeam(int clubID, int teamID){
		try {
			String sql = "SELECT * FROM players, clubplayers, clubs WHERE players.id = clubplayers.playerID AND clubplayers.clubID = clubs.id AND clubs.id = ? AND players.isActive = 1 AND players.id NOT IN (SELECT teamplayers.playerID from teamplayers, teams, players WHERE teamplayers.teamID = ? AND teamplayers.playerID = players.id AND teams.isActive = 1 AND players.isActive = 1);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			statement.setInt(2, teamID);
			ResultSet rs = statement.executeQuery();
            return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	} 

	public void setProfilePic(int clubID, String imgPath){
		try 
		{
			String sql = "UPDATE clubs SET imgPath = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, imgPath);
			statement.setInt(2, clubID);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setPW(int userTag, String pw){
		try 
		{
			String sql = "UPDATE users SET users.pw = ? WHERE users.tag = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, pw);
			statement.setInt(2, userTag);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setPlayerName(int playerID, String nom){
		try 
		{
			String sql = "UPDATE players SET nom = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nom);
			statement.setInt(2, playerID);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setUserName(int userTag, String nom){
		try 
		{
			String sql = "UPDATE users SET nom = ? WHERE tag = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nom);
			statement.setInt(2, userTag);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setClubName(int clubID, String nom){
		try 
		{
			String sql = "UPDATE clubs SET nom = ? WHERE clubs.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nom);
			statement.setInt(2, clubID);
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setClubMail(int clubID, String mail){
		try 
		{
			String sql = "UPDATE clubs SET mail = ? WHERE clubs.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, mail);
			statement.setInt(2, clubID);
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setClubAdresse(int clubID, String adresse){
		try 
		{
			String sql = "UPDATE clubs SET adresse = ? WHERE clubs.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, adresse);
			statement.setInt(2, clubID);
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setClubTel(int clubID, String tel){
		try 
		{
			String sql = "UPDATE clubs SET tel = ? WHERE clubs.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, tel);
			statement.setInt(2, clubID);
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setClubDes(int clubID, String des){
		try 
		{
			String sql = "UPDATE clubs SET description = ? WHERE clubs.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, des);
			statement.setInt(2, clubID);
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTeamName(int teamID, String nom){
		try 
		{
			String sql = "UPDATE teams SET nom = ? WHERE teams.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nom);
			statement.setInt(2, teamID);
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setPlayerMail(int playerID, String mail){
		try 
		{
			String sql = "UPDATE players SET mail = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, mail);
			statement.setInt(2, playerID);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setPlayerTel(int playerID, String tel){
		try 
		{
			String sql = "UPDATE players SET tel = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, tel);
			statement.setInt(2, playerID);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setPlayerAge(int playerID, String age){
		try 
		{
			String sql = "UPDATE players SET age = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, age);
			statement.setInt(2, playerID);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAccount(int userTag){
		try 
		{
			String sql = "UPDATE users, clubs SET users.isActive = 0, clubs.isActive = 0 WHERE users.tag = ? AND clubs.userTag = users.tag;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userTag);
			statement.executeUpdate();
			ResultSet userClubsRS = getAllUserClubs(userTag);
			while(userClubsRS.next()){ 
				System.out.println(userClubsRS.getInt("clubs.id"));
				String sql2 = "UPDATE players, clubplayers, clubs, teams, teamplayers SET players.isActive = 0, teams.isActive = 0 WHERE clubplayers.clubID = clubs.id AND clubplayers.playerID = players.id AND teams.clubID = clubs.id AND teamplayers.teamID = teams.id AND teamplayers.playerID = players.id AND clubs.id = ?;";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				statement2.setInt(1, userClubsRS.getInt("clubs.id"));
				statement2.executeUpdate();
			}
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public void deleteClub(int clubID){
        try 
		{
			String sql = "UPDATE clubs SET isActive = 0 WHERE clubs.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clubID);
			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void deletePlayer(int playerID){
        try 
		{
			String sql = "UPDATE players SET isActive = 0 WHERE players.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, playerID);
			statement.executeUpdate();
			String sql2 = "UPDATE teams SET capitaineID = null WHERE capitaineID = ?;";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setInt(1, playerID);
			statement2.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void deleteTeam(int teamID){
        try 
		{
			String sql = "UPDATE teams SET isActive = 0 WHERE teams.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			statement.executeUpdate();
			String sql2 = "UPDATE teams SET capitaineID = null WHERE teams.id = ?;";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setInt(1, teamID);
			statement2.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	public void deleteCapitaine(int teamID){
		try {
			String sql = "UPDATE teams SET capitaineID = 0 WHERE teams.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTeamCapitaine(int teamID, int playerID){
		try 
		{
			String sql = "UPDATE teams SET capitaineID = ? WHERE teams.id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, playerID);
			statement.setInt(2, teamID);

			statement.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTeamPlayers(int teamID, ArrayList<Player> newPlayerComp){
		try 
		{
			String sql1 = "DELETE FROM teamplayers WHERE teamplayers.teamID = ?";
			String sql2 = "INSERT INTO teamplayers (teamID, playerID) VALUES (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql1);
			statement.setInt(1, teamID);
			statement.executeUpdate();
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			for(Player p:newPlayerComp){
				statement2.setInt(1, teamID);
				statement2.setInt(2, p.getID());
				statement2.executeUpdate();
			}
		} 
		catch (SQLIntegrityConstraintViolationException erreur)
		{
			erreur.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getPlayer(int playerID){
		try {
			String sql = "SELECT * FROM players WHERE players.id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, playerID);
			ResultSet rs = statement.executeQuery();
			if(rs.next())return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean isCapitaine(int playerID, int teamID){
		try 
		{
			ResultSet capitaineRS = getTeamCapitaine(teamID);
			ResultSet playerRS = getPlayer(playerID);
			if(capitaineRS.next() && playerRS.next() && capitaineRS.getInt("capitaineID") == playerRS.getInt("players.id")) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet getTeamCapitaine(int teamID){
		try 
		{
			String sql = "SELECT teams.capitaineID, players.nom FROM teams, players, teamplayers WHERE teams.id = ? AND players.id = teams.capitaineID AND players.id = teamplayers.playerID AND teamplayers.teamID = teams.id;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, teamID);

			ResultSet rs = statement.executeQuery();
			return rs;
		} 
	 	catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getPlayerWinner(int eventID){
		try 
		{
			String sql = "SELECT events.playerWinnerID from events WHERE eventID = ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, eventID);
			ResultSet rs = statement.executeQuery();
			return rs;
		} 
	 	catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getTeamWinner(int eventID){
		try 
		{
			String sql = "SELECT events.teamWinnerID from events WHERE eventID = ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, eventID);
			ResultSet rs = statement.executeQuery();
			return rs;
		} 
	 	catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setPlayerWinner(int playerID, int eventID){
		try 
			{
				String sql = "UPDATE events SET playerWinnerID = ? WHERE events.id = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, playerID);
				statement.setInt(2, eventID);
				
				statement.executeUpdate();
			} 
			catch (SQLIntegrityConstraintViolationException erreur)
			{
				erreur.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void setTeamWinner(int teamID, int eventID){
		try 
			{
				String sql = "UPDATE events SET teamWinnerID = ? WHERE events.id = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, teamID);
				statement.setInt(2, eventID);
				
				statement.executeUpdate();
			} 
			catch (SQLIntegrityConstraintViolationException erreur)
			{
				erreur.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void addTeamEvents(int teamID, int eventID){
		try 
			{
				String sql = "INSERT INTO teamevents (teamID, eventID) VALUES (?, ?); ";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, teamID);
				statement.setInt(2, eventID);
				statement.executeUpdate();
			} 
			catch (SQLIntegrityConstraintViolationException erreur)
			{
				erreur.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void addPlayerEvents(int teamID, int eventID){
		try 
			{
				String sql = "INSERT INTO playersevents (playerID, eventID) VALUES (?, ?); ";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, teamID);
				statement.setInt(2, eventID);
				statement.executeUpdate();
			} 
			catch (SQLIntegrityConstraintViolationException erreur)
			{
				erreur.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
