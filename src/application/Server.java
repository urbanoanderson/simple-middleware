package application;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import middleware.HospitalInvoker;

public class Server extends JPanel
{	
	private static final long serialVersionUID = 1L;

	public static void main(String [] args)
	{
		//Window for Server controlling
		JFrame frame = new JFrame();
	    frame.getContentPane().add(new Server());

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(200, 200);
	    frame.setTitle("Hospital Server");
	    
	    JLabel label1 = new JLabel("Server Status");
	    label1.setText("SERVER IS ON");
	    label1.setHorizontalAlignment(SwingConstants.CENTER);
	    label1.setVerticalAlignment(SwingConstants.CENTER);
	    frame.add(label1);
	    
	    frame.setVisible(true);
	    
	    //Server loop to respond to requests
	    while(frame.isVisible())
	    {	
			HospitalInvoker hospital_invoker = new HospitalInvoker();
			hospital_invoker.Invoke();
	    }
	}
}
