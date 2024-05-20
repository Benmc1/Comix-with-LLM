# Creating Comics with LLMs
By Ruhao Yang, Ben Mc Dowell and Zeina Khattab.

The goal of the project is to be able to automatically generate comic strips with minimal human input. The only user input should be prompting for a topic. After that the story, characters and visuals will all be generated by calls to the OpenAI api.


## Sprint 1

The Comix-with-LLM is a Java application that provides an interface to interact with the OpenAI API.
This application supports 2 functionalities: Chat Completion and Text Embedding Retrieval.
It allows users to specify their API credentials and input data for performing these tasks.

This consists of : API.java which contains methods for interacting with OpenAI API.
Main.java which has the main method to execute the application, asking user for input and invoking API methods.
pom.xml which is the Maven configuration file that specifies dependencies and build settings.

To run the application, compile the project using Maven (mvn package).
After that, go to the target directory and execute the JAR file using the following command:
java -jar Comix-with-LLM-1.0-SNAPSHOT-jar-with-dependencies.jar YOUR_OPENAI_API_KEY    (Replace YOUR_OPENAI_API_KEY with the actual API key)
Follow the prompts and input the correct information and the application will perform the tasks.

## Sprint 3
For Sprint 3 we added to the lines generator from the previous sprint to create the rhyming lines.
Then we added a narrator class to generate the captions for the comic. It generates the lines based off the topic a randomly chosen character
and a pair of pro+anti lines. For the XML we have a comic serializer class that takes a comic and pulls all the data from it to create a string
with the relevent information then writes it to a file.

Make sure to add your API key to the config file.

## Sprint 4

In this sprint we added the panel and character class to hold the data of each component and to give easier access.
Creating and retrieving the embedding data is handled by the EmbeddingData class. The prompt used for the embedding is a concatination of the 3 different pose descriptions + the name of the pose.
If the embedding is unavailable it will look for the plain csv file and get the embedding from there.
Both these files are specified in the configuration file. Choosing which embedding to use to handled by the EmbeddingSelector class. You pass the prompt and the embedding data of thing you want, and it returns the name of the answer.
Finally, we added some more tests for the new classes.

## Sprint 5

In the sprint we changed the embedding storage to avoid having all embeddings in memory we did this with a RandomAccessFile and an index database to store what was where.
This index file is kept in memory as a hashtable for the desc+type -> value pairs and in an arraylist for the index's.
We also got the AI to recommend poses and settings by adding to the lines class. Some class were moved into different packages.
Cosine similarity equates "Lighting a memorial candle" to "terrorizing" so it's not perfect.

## Sprint 6

For this sprint we decided to add a new type of comic. In the comic a professor and student are brought to a given time period and discuss all the things they see around them.
Both comic types are still available via a mode selection at startup. Aswell with the history mode we added Opening and closing panels to make it more like a story. These are preset in the panels class. There is also a character creator where you can choose the appearance of the student character.

## Sprint 7
In the final sprint most of the changes were to improve reliability and some QoL changes for the user.
To improve reliability we added checks on the generators to ensure the result was of the correct form. If this failed the model would try at most 3 more times.
The captions were the most sensitive so for the best chance we just reset the chat every try instead of asking it to try again.

### To run "java -jar Comix-with-LLM-1.0-SNAPSHOT-jar-with-dependencies.jar"