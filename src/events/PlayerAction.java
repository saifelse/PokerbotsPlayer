package events;


public abstract class PlayerAction extends Action {
	private int pid;
	public PlayerAction(ActionType type, int pid, String line){
		super(type, line);
		this.pid = pid;
	}
	public int getPID(){
		return pid;
	}
}
