**Middleware project for class IF711 (2017.1)**

**Professor**:	Nelson Rosa (nsr@cin.ufpe.br)

**Students**:	
			- Anderson Urbano (aafu@cin.ufpe.br)  
			- Isabela Rangel (igr@cin.ufpe.br)  
			- Italo Roberto (irss@cin.ufpe.br)  
			- Pedro Queiroz (phql@cin.ufpe.br)

#-------------------------------------------
#DESCRIPTION
#-------------------------------------------

It consists of a hospital application in which
user can retrieve their medical records from
server but comunication utilizes an Object Oriented
Middleware. 

This Project is implemented in Java with Eclipse

#-------------------------------------------
#HOW TO RUN
#-------------------------------------------

Open project with eclipse, start the naming service
by running 'NamingServiceServer.java' in the 'naming'
package. It will open a window to indicate that the
naming server is running.

Next, you have to start the application server by 
running 'HospitalServer.java' in the 'application'
package. Another window will pop.

Finally, you can run the client program to make 
requests by running 'HospitalClient.java' in the 
'application' package. It will make a request and print
the received output from HospitalServer to the console.