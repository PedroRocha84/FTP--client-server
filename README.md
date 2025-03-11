# Context

This project was done during the CodeForAll_ Online Bootcamp, as an assignment from week-10 - Networking. This was done in collaboration with @CristinaS0.

# Goal

Build a server and a client application that allows clients to upload and download files using your own version of the File Transfer Protocol (FTP).

# Requirements
Once the server runs, it should respond to the following commonds while the connection is still active. 
The server should offer the following commands and functionality to the user who connects to it:

* BYE         -> Terminate the connection
* DISCONNECT  -> Terminate the connection
* QUIT        -> Terminate the connection
* HELP        -> See available commands
* LS          -> List files available on the server
* PUT         -> Upload a file to the server
* GET         -> Get a file from the server
* MKDIR        -> Create a directory on the server

# Main challenges
The mains challenges addressed were first the connection between two applications and the logic of implementation. This logic required structured and simples methods implementation for easiness troubleshooting and maintenance. For the responsiveness required and the usability of both applications we nested the code into several methods using encapsulation and proper naming convention. It was important to get the hands-on application of Java Socket API in the Java.net package, for both Client and Server. Not less important the exceptions handling and the streams controls were key for our application to run. 

*. **GET** and **PUT** method implementations required the application of Java.Io class, used a combination of classes bufferedReader with FileOutPutStream and the FileInputStream with the bufferedWriter to read and write between apps. <br/><br/>
*. **LS** to allow us to get more practive on this class, we build a file with all the commands, when the client request it by writing LS command and press enter, the server open the file and send to the client each line to be presented. This reduced the hard-coded in our application and let us to implement some flexibility.

## Achievements:
We successfuly deliver what was expected and leveraging our know-how by applying it:
1. The use of OOP Encapsulation concepts;
2. The use of Scanner inputStream to get from the terminal user inputs to allow read commands, and filenames for both methods GET and PUT;
3. Java.net - BufferedReader and BufferredWriter to read and write text to a character-output stream;
4. Java.net - FileInputStream and FileOutputStream to create either a file output, input stream to read or write to the file;
5. Proper exceptions Error Handeling by surrouning specific code  with try-catch statement and outputing error message directly by using the System.err printStream to show each specifics.

