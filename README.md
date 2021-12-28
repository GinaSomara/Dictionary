A program I made using ENUM classes and created a sorting alorigthm to sort user input.

This project was inspired after learning about Google Guava's MultiMap and Enum classes. I found it very helpful that MultiMap is unique in the sense that it can store duplicate keys. Additionally, after learning about Enum classes and their potential, I wanted to create a mini Dictionary program that allowed me to play with both.


** Google Guava must be added to the IDE project structure in order to run.


~ How it Works ~    
    
I created only 5 constant words in the Dictionary by making them instances in the Enum class. The words and their definitions (sorted by part of speech) are loaded into the "database" here utilizing the MultiMap. Many of the individual parts of speech have duplicate definitions, so upon user request, the duplicate definitions can be excluded. Furthermore, the user can request certain parts of speech and additionally for the definitions to printed in the reverse order. Each user search can have up to four parameters: word to be searched, specific part of speech, reverse order, and distinct definitions. In order to handle the user requests, I had to create and implement a sorting algorithm that would work as a "streamline".

