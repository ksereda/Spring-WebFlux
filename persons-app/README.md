## Persons service

This is small example how to create reactive REST API with SpringBoot and MongoDB.

Here I want to show the simplest option using the `Controller`.
In the second part (`person-app-2`), we will use a `Router` and a `Handler` instead of a `Controller`.

Also here, for clarity, 2 slightly different options for `Person` and `Position` in service layer.
____

### Start application.

Then run MongoDB.
I run MongoDB in a docker container for speed and convenience.

If you already have MongoDB installed, skip this section.

You can do this in the following way (I use Linux OS ubuntu).
First install docker.

In terminal

    sudo apt-get update
    sudo apt install docker.io

That the docker service automatically started at system startup

    sudo systemctl start docker
    sudo systemctl enable docker

Check if everything is installed correctly

    docker --version

You should see the currently installed version, for example

    Docker version 18.09.7, build 2d0083d

Assuming you have docker installed, let's proceed

    docker pull mongo

Docker will merge the current version with docker-hub.

We can check like this

    docker images

As a result, we will see all the current images that you have downloaded (at the moment you should have it only one, provided that you have not used docker before)

Start `Mongo`:
(the default port is 27017, so we can omit it)

    docker run mongo

or you can explicitly specify the port

    docker run mongo --port 27017

Open a new console, write

    mongo

You hit the mongo shell

For a more detailed introduction, you can read my article, where I described how to use several docker images with MongoDB

    https://medium.com/@kirill.sereda/%D0%B7%D0%B0%D0%BF%D1%83%D1%81%D0%BA-%D0%BD%D0%B5%D1%81%D0%BA%D0%BE%D0%BB%D1%8C%D0%BA%D0%BE-mongodb-%D0%B2-docker-7667c04d8e7d


____

### Launch and testing

We have a Person and Position.

Create a couple of users through Postman:

    Post
    http: // localhost: 8080 / persons / createPerson
    
    {
    "sex": "MAN",
    "firstName": "Kirill",
    "lastName": "Sereda",
    "age": 30,
    "interests": "programming"
    }
    
    {
    "sex": "MAN",
    "firstName": "Petr",
    "lastName": "Masloy",
    "age": 32,
    "interests": "music"
    }
    
    {
    "sex": "WOMEN",
    "firstName": "Nataly",
    "lastName": "Ivanova",
    "age": 25,
    "interests": "travel"
    }


Then create some positions

    Post
    http: // localhost: 8080 / position / createPosition
    
    {
    "positionName": "developer",
    "description": "java backend",
    "createdAt": 1505383305602
    }
    
    {
    "positionName": "QA",
    "description": "test automation",
    "createdAt": 1505383305602
    }
    
    {
    "positionName": "developer",
    "description": "react frontend",
    "createdAt": 1505383305602
    }


Then go back to the terminal with `MongoDB`

    use dbpersons

Because these settings are specified in application.properties

    show collections

you must see

    persons
    positions

to get data use

    db.persons.find ()
    db.positions.find ()
    
_____

### Server Side Events
 
- If you will go to

        
        http://localhost:8080/position/stream/positions
    
Positions are Sent to the client as Server Sent Events.

In code

        // Positions are Sent to the client as Server Sent Events
        @GetMapping(value = "/stream/positions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<Position> streamAllPositions() {
            return positionRepository.findAll();
        }

- If you will go to

    
        http://localhost:8080/position/stream/persons
        
Persons are Sent to the client as Server Sent Events

In code

         // Persons are Sent to the client as Server Sent Events
         @GetMapping(value = "/stream/persons", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
         public Flux<Person> streamAllPersons() {
             return personRepository.findAll();
         }   

- If you will go to

   
         http://localhost:8080/position/stream/persons/default
         
You will get default value every 1 second

In code

        // Get default value every 1 second
        @GetMapping(value = "/stream/persons/default", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<Person> emitPersons() {
            return Flux.interval(Duration.ofSeconds(1))
                    .map(val -> new Person( "" + val, Sex.MAN, "default", "default", "", "default"));
        }

- If you will go to 

   
         http://localhost:8080/position/stream/persons/delay
    
You will Get all Persons from the DB (every 1 second you will receive 1 record from the DB)

In code

         // Get all Persons from the database (every 1 second you will receive 1 record from the DB)
        @GetMapping(value = "/stream/persons/delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<Person> streamAllPersonsDelay() {
            return personRepository.findAll().delayElements(Duration.ofSeconds(1));
        }
 
_____

### CommandLineRunner

You can also add this in main class for automatically creation Persons when application starting

	@Bean
	CommandLineRunner run(PersonRepository personRepository) {
		return args -> {
			personRepository.deleteAll()
					.thenMany(Flux.just(
							new Person("1", Sex.MAN, "Kirill", "Sereda", "30", "programming"),
							new Person("2", Sex.MAN, "Mike", "Nikson", "28", "music"),
							new Person("3", Sex.MAN, "Oliver", "Spenser", "33", "sport"),
							new Person("4", Sex.WOMEN, "Olga", "Ivanova", "25", "movie"),
							new Person("5", Sex.WOMEN, "Anna", "Karenina", "23", "dance"),
                            new Person("6", Sex.WOMEN, "Olga", "Petrova", "27", "programming")

					)
							.flatMap(personRepository::save))
					.thenMany(personRepository.findAll())
					.subscribe(System.out::println);

		};
	}

______

### Router and handler

See `person-app-2` on github
