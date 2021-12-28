A program I made using ENUM classes and created a sorting alorigthm to sort user input.

This project was inspired after learning about Google Guava's MultiMap and Enum classes. I found it very helpful that MultiMap is unique in the sense that it can store duplicate keys. Additionally, after learning about Enum classes and their potential, I wanted to create a mini Dictionary program that allowed me to play with both.


** Google Guava must be added to the IDE project structure in order to run.


~ How it Works ~    
    
I created only 5 constant words in the Dictionary by making them instances in the Enum class. The words and their definitions (sorted by part of speech) are loaded into the "database" here utilizing the MultiMap. Many of the individual parts of speech have duplicate definitions, so upon user request, the duplicate definitions can be excluded. Furthermore, the user can request certain parts of speech and additionally for the definitions to printed in the reverse order. Each user search can have up to four parameters: word to be searched, specific part of speech, reverse order, and distinct definitions. In order to handle the user requests, I had to create and implement a sorting algorithm that would work as a "streamline".


~ Obstacles I faced && Overcoming those Obstacles  ~

MultiMap:: I structured this project so each enum word would have its own MultiMap. The keys would be the parts of speech, and the values would be the definitions, allowing me to take advantage of the duplicate keys. However, since some parts of speech have the same exact definitions, I learned that MultiMap would not store duplicate values IF the keys were also the same. I had a few ideas to fix, this, however all of my ideas ended up with methods in which I would not be using MultiMap's functionality of duplicate keys. Since gaining that knowledge was the whole purpose of the project, I ended up using a somewhat "hacky" way of fixing this; for duplicate definitions, I simply added extra spaces so that technically the definitions were not 100% identical. However this posed my next problem. Sorting out duplicate definitions when technically they were no longer the same. So I simply adjusted the sorting algorithm to ignore extra spaces for definitions, allowing identical strings to be sorted throu

Reverse display:: I anticipated simply looping through MultiMap backwards in order to display all definitions in the revered order. This posed to be a bigger issue than expected. There were a few options I had in mind; I ended up storing the requested definitions into a TreeMultiMap. The provided functions through Google Guava allowed more reverse printing flexibility than MultiMap, and by using this, I got to familiarize another data structure within Guava.

Sorting Algorithm::  Originally I designed this program with no required order to the user parameters. However, I wanted to further challenge myself by requiring the user input to be in a specific order: word to be search must come first, then a part of speech, then reverse, then distinct requirements. **If user part of speech == true, then go to the next parameters.** However with the parameter listing, there are so many ways to have parameters. If the user does not put in a part of speech, then the next should be either a reverse parameter, then a distinct parameter requirement. However, the distinct cannot be before reverse, etc. I had to take all of this into account when editing how the user input was sorted through. I adjusted the sorting algorithm to more of a streamlined effected, shifting through the input until the user request was customarily printed. If parameters were not presented in the correct order, an error message would then need to be printed informing the user that the request x at position x was not in correct according to the set input rules.

