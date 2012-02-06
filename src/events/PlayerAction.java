package events;


public abstract class PlayerAction extends Action {
	private int pid;
	public PlayerAction(ActionType type, int pid){
		super(type);
		this.pid = pid;
	}
	public int getPID(){
		return pid;
	}
}
