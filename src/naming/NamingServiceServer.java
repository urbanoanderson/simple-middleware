package naming;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class NamingServiceServer extends JPanel
{	
	private static final long serialVersionUID = 3658763986086752526L;

	public static void main(String [] args)
	{
		//Invoker for attending to requests
		NamingServiceInvoker naming_service_invoker = new NamingServiceInvoker();

		//Window for Server controlling
		JLabel label1 = new JLabel("Naming Server Status");
	    label1.setText("NAMING SERVER IS ON PORT " + String.valueOf(naming_service_invoker.GetServicePort()));
	    label1.setHorizontalAlignment(SwingConstants.CENTER);
	    label1.setVerticalAlignment(SwingConstants.CENTER);
		
		JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(300, 200);
	    frame.setTitle("Naming Service Server");
	    frame.add(label1);
	    frame.setVisible(true);
	    
	    //Server loop for responding to requests
	    while(frame.isVisible())
	    {	
	    	naming_service_invoker.Invoke();
	    }
	}
}
