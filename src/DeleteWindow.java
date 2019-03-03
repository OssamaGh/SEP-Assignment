import model.*;
import java.awt.event.*;
import javax.swing.*;

public class DeleteWindow extends JFrame {
	private MyPanel panel = new MyPanel();
	private RootModel rootModel;
	private Transaction transaction;
	private String type;

	public DeleteWindow(RootModel rootModel, Transaction transaction, String type) {
		this.rootModel = rootModel;
		this.transaction = transaction;
		this.type = type;
		setup();
		build();
		setVisible(true);
	}

	private void setup() {
		setTitle("Delete " + type);
		setSize(200, 100);
		setLocation(350, 400);
	}

	private void build() {
		add(panel);
	}
	
	private void closeWindow(){
     	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
     }

	private class MyPanel extends JPanel {
		private JButton confirm = new JButton("Confirm");
		private JButton cancel = new JButton("Cancel");

		public MyPanel() {
			setup();
			build();

		}

		private void setup() {
			confirm.addActionListener(new MyListener());
			cancel.addActionListener(new MyListener());
		}

		private void build() {
			Box box = Box.createHorizontalBox();
			box.add(Box.createHorizontalStrut(10));
			box.add(confirm);
			box.add(Box.createHorizontalStrut(10));
			box.add(cancel);
			add(box);
		}

		private class MyListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Confirm")){
					rootModel.user().remove(transaction);
					rootModel.user().viewTransactions();
				}
				else if (e.getActionCommand().equals("Cancel")) {
					new ListWindow(rootModel, "Transactions");
				}
				try {
              		rootModel.store();
              	}
            	catch(Exception e1){}
              	closeWindow();
			}
		}
	}
}
