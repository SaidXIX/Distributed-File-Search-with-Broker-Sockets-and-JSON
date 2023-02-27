# Distributed-File-Search-with-Broker-Sockets-and-JSON
A distributed system using Broker-Server-Client architecture, sockets, and JSON to efficiently search and retrieve files based on themes. The Broker processes a folder with subfolders representing themes, initializes servers for each theme, and records detailed operation history.

This program uses a Broker Server Client architecture to efficiently search and retrieve files in a multi-theme directory. The Broker processes a folder containing multiple subfolders where each subfolder represents a theme. The program only reads text files. The Broker creates 'X' servers for each theme, where 'X' is a user-defined number. A client can connect to the Broker and request a file search for a single theme 'T', providing the word(s) to search for. The Broker divides the contents of the files in theme 'T' among the available servers and sends each part to a server along with the search term(s) in JSON format. Each server calculates the number of occurrences of the term(s) in the content received and sends the response back to the Broker, which keeps a detailed history of the operation and sends the total number of occurrences to the client.



## External Libraries

GSON

## IDE 

NetBeans

## Technologies used

The program is implemented in Java, using sockets for network communication between the broker, servers, and clients and the messages are sent in JSON format

## Folder Structure

Folder/ 
      ├── Subfolder/ 
        ├── File1.txt 
        ├── File2.txt 
        └── File3.txt.
        
## How to Use

1. Clone the repository to your local machine.
2. Run the broker, servers, and clients on separate terminals.
3. Follow the program prompts to request files and search for words.

## Credits

This program was developed by [Said Bouziani].
