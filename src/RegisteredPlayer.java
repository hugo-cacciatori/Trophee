import java.util.concurrent.atomic.AtomicInteger;

public class RegisteredPlayer extends Participant{
    private Player player;
    private int clubID;
    private int id;
    private static final AtomicInteger ID_FACTORY = new AtomicInteger(1);

    
    public RegisteredPlayer(Player p, int clubID){
        this.player = p;
        this.setNom(this.player.getNom());
        this.clubID = clubID;
        this.id = ID_FACTORY.getAndIncrement();
    }

    public Player getPlayer() 
    {
        return this.player;
    }

    public void setParticipant(Player p) 
    {
        this.player = p;
    }
}
