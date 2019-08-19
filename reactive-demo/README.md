### This is simple example Spring Reactive

Start application and go to browser on

    http://localhost:8080/start
    
An object with a name field will be created every 3 seconds.

You can see how the data will come as they become available.

The first object will be created and after 3 seconds we will get it.

Instead of sending a repeated request for an object in 6 seconds, Spring will do it for us, he will give us the second object. Then after another 3 seconds, we get the third object, etc.

Those, a stream will be created in which we will receive data (Spring will notify us of this, we do not need to resend a request to receive all data)
