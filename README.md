1) Clone the git repository
2) Create a new workspace in Eclipse EE
3) Import project from the git repository into the workspace
4) Download Apache TomEE 7.0.2 and unzip it
5) Unzip serverlibs.zip from the git repository into the 'lib' subfolder of tomee
6) Make sure the project/workspace are using JDK 1.8
7) Add a new server runtime pointing to the TomEE installation
8) Add this server to the workspace
9) Bind the server and its libs to the project, if necessary
10) Set it to take control of tomee, set deploy path to webapps and HTTP/1.1 port to 8084
11) Add the project to the server
12) Run the server
13) Should be working at http://localhost:8084/isamrs/home.jsp