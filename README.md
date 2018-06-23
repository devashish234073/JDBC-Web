# JDBC-Web
A java application to browse sql db in html-gui or as json-api

The jar file can be run in command line. It takes 1 argument , which is the port no where you want to run the application.
If no argument is provided , it is defaulted to port 8888.

Below shown is the landing page "http://locahost:8888" 
![e1](https://user-images.githubusercontent.com/20777854/41811676-acd7bb32-7731-11e8-8e90-5f01a46e412f.png)
## Note : The project includes needs a jdbc driver for the type of sql db you are using. For my case I used "mysql" connector.

After login below is the page to select the db
![e2](https://user-images.githubusercontent.com/20777854/41811677-b0290c78-7731-11e8-8caa-79e0e894c829.png)

#### In the previous page, when the "_html" in the url is changed to "_json" following is the response:
![e2_j](https://user-images.githubusercontent.com/20777854/41811678-b4a5831c-7731-11e8-9f57-e27fa1be5831.png)

## Below is the next page to select db and its corresponding json response
![e3](https://user-images.githubusercontent.com/20777854/41811681-b761e924-7731-11e8-9f25-bab1006f2fba.png)

![e3_j](https://user-images.githubusercontent.com/20777854/41811682-bd3d9834-7731-11e8-86f6-04cdc2433f40.png)

## Below is the response when one of the table is selected and its corresponding json response
![e4](https://user-images.githubusercontent.com/20777854/41811684-bfb7e7d6-7731-11e8-996d-1a5e097e03f8.png)
![e4_j](https://user-images.githubusercontent.com/20777854/41811685-c2f94dcc-7731-11e8-9724-c7156eaa0627.png)

