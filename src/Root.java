import model.*;
import javax.swing.*;

public class Root extends JFrame implements View{	
	private RootModel rootModel;
	
	public static void main(String[] args){
		new Root(new RootModel());
	}

	public Root(RootModel rootModel){
		this.rootModel = rootModel;
		this.rootModel.attach(this);
		build();
		pack();
		setVisible(false);
	}
	
	//this method is called by RootModel after it has stored the USer object from the save file
	public void update(){
		new MainMenu(rootModel);
		if (rootModel.user().balanceBelowThreshold()){
			new NotificationWindow("Warning: Your balance is low");
		}
		if (rootModel.user().aboveSpendingThreshold()){
			new NotificationWindow("Warning: Your spending is high");
		}
		rootModel.user().recurringTransactionsCheck();
		try{
			rootModel.store();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	//this method is used to show the LoginWindow initially
	private void build(){
		new LoginWindow(rootModel);
	}
}