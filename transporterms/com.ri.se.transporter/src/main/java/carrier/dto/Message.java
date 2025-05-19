package carrier.dto;

public class Message{

	private String message = null;
	public Message (){

	}
	public Message(String message ){
		this.message = message; 
	}
	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return this.message;
	}


}
