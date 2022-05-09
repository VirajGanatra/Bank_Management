# Bank_Management
IB CS Task 1

In IntelliJ (go get the pro version for free, either through github student pack or on jetbrains website, includes pycharm pro etc) this would open as one project
driver ckass is well...the driver lol. each other thing is a class. Account is a superclass - ie. other classes (loan, checking and savings) inherit from it. That's a concept of OOP, called, you guessed it, inheritance

admin and user r just different options for either an admin or a user.

The other two conepts r polymorphism and encapsulation. encapsulation is just keeping things separate and in classes. polymorphism is that a method can take many forms. that might make no sense, lol. what i mean is that i can call triangle.areea() and square.area(), and if i define both in a class of shape  (ie square and triangle r subclasses of class shape) it'll work. also, if u see in user.java, i have two definitions of the constructor (creates object of type) user. one w/ no params, one w/ params. that's the same thing, overriding the method depending on the params provided. it's prob??? best practice to define that blank, no argument constructor, but i dont in account.java for example
