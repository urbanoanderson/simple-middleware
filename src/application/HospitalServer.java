package application;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import extra.Constant;
import naming.NamingServiceProxy;

public class HospitalServer extends JPanel
{	
	private static final long serialVersionUID = 3658763986096752526L;

	public static void main(String [] args)
	{
		//Invoker for attending to requests
		HospitalInvoker hospital_invoker = new HospitalInvoker(2001);
		
		//Add Hospital Service to Naming Server
		NamingServiceProxy naming_server = new NamingServiceProxy();
		naming_server.AddService("hospital",
								 Constant.HOSPITAL_SERVER_HOST,
								 Constant.HOSPITAL_SERVER_PORT,
								 Constant.HOSPITAL_SERVER_PUBLIC_KEY);
		
		//Window for Server controlling
		JLabel label1 = new JLabel("Server Status");
	    label1.setText("SERVER IS ON PORT " + String.valueOf(hospital_invoker.GetServicePort()));
	    label1.setHorizontalAlignment(SwingConstants.CENTER);
	    label1.setVerticalAlignment(SwingConstants.CENTER);
		
		JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(300, 200);
	    frame.setTitle("Hospital Server");
	    frame.add(label1);
	    frame.setVisible(true);
	    
	    //Server loop for responding to requests
	    while(frame.isVisible())
	    {	
			hospital_invoker.Invoke();
	    }
	}
}
