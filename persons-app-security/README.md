### Persons application with Spring Security JWT

You can safely receive data with a GET request without using a token.

But you cannot create a user (or Position) without a token.

Send request via Postman

    Post
    http://localhost:8080/persons/createPerson
    
    {
    	"sex":"MAN",
    	"firstName":"new",
    	"lastName":"new",
    	"age":18,
    	"interests":"new"
    }

You will see a message

    CSRF Token has been associated to this client


You'll need to go to

    https://auth0.com/signup

After signing up for your Auth0 account, you will need to create an API on Auth0 to represent your Spring Boot API and to be able to configure it to authenticate requests.
To do this, head to the APIs section on your Auth0 dashboard and click on the Create API button. 
After that, the dashboard will show you a form where you will have to enter:

- a name for your API (this can be something like "Persons API");
- an identifier (in this case, it can be `https://localhost:8080/persons` or anything that resembles a valid URL);
- and the signing algorithm (for this field, make sure you choose RS256).

For all settings, we use the `SecurityConfig` class.

In `application.yml` file you can see

    jwk-set-uri: https://dev-0if651u7.auth0.com/.well-known/jwks.json
    issuer-uri: https://dev-0if651u7.auth0.com/

Where

    https://dev-0if651u7.auth0.com/

this is the address of my domain on the site

    https://auth0.com/signup

in the API tab in the menu on the left.

Go to the `API` section in your menu, click on the API you created before, and then click on the Test section of this API. There, you will find a button called `Copy Token`. 
Click on this button to copy an access token to your clipboard

Substitute the token in `Postman`:

Select `Authorization` - `Bearer Token` and paste the token you copied.

Now send the same request with the same parameters and you will see that your Person was successfully created.

To verify this, go to the console running MongoDB and run there:

    use dbpersons

    show collections

    db.persons.find ()

and you will see the result.

