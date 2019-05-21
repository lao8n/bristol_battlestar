# Game Server

## Network basic
The basic computer network has seven layers (OSI model)

```
****************
* Application  * -> HTTP, FTP, SMTP, Self-defined
****************
* Presentation * -> HTML, JPEG, TIFF
****************
*    Session   * -> RPC, SQL, AppleTalk
****************
*   Transport  * -> TCP, UDP
**************** 
*    Network   * -> IP
****************
*   Data-link  * -> IEEE 802.3
****************
*   Physical   * -> V.35, V.24, Ethernet
****************
```

We designed our protocol on Application layer, and used TCP on Transport layer.

## Java Network IO
What we used: NIO - Non-block I/O
Why: There are two I/O mode in Java, Block I/O and Non-block I/O
Normal I/O creates one thread once a new I/O request comes in, for example

```java
public final class ServerNormal {
	// Default port
	private static int DEFAULT_PORT = 12345;
	public static void main(String[] args) throws IOException{
		if(server != null) return;
		try{
			server = new ServerSocket(port);
			System.out.println("Server started, port：" + port);
			while(true){
			// Blocked, wait for new connections
				Socket socket = server.accept();
				new Thread(new Runnable() {
				    @Override
				    public void run() {
				        // Do something
				        // Every time we create a new thread
				        // Many resource is cost
				    }
				}).start();
			}
		}finally{
			System.out.println("Server Shut Down");
		}
	}
}
```

However Non-block I/O has a different way to handle connections

Think this as the time when you wait for paying in Tesco. You have a pay request, waiting in line. Now one staff comes to you, saying "Cashier 8 please".

In this case, you are the client, waiting for the staff (selector) to allocate you an idle worker (cashier). If there is no idle worker, the staff will tell you now you can go somewhere else, like to buy more things or chat with you friend for a little bit, instead of let you wait there for a free worker.

The worker is always there, if this situation is in traditional block I/O, this will be, you have a pay request, the manager comes to you, saying "ok now wait, let me hire a staff who can finish your payment" (Starts a new thread). After a long waiting, you finally finished your payment, then the manager fires the one they just hired!

This is a simplified example. But in practice, there will be acceptor works in an infinite loop listening to allocated port. Once a request comes, acceptor will give this request to a dispatcher, dispatcher registers this request to an available worker (most of the time we create a thread pool for workers).
All read/write is buffer-oriented, and the channel is bidirectional. 

![NIO_Example](https://oscimg.oschina.net/oscnet/fa1e0aedc8e9d3a96f69daa6383dd9b24df.jpg)

## Netty & Protocol
Even though Non-block I/O has powerful ability to handle hundreds of thousands of connection at a time, the native APIs provided by Java is very hard to use. That is the reason why Netty was born.
Netty encapsulate all Non-block APIs, provides a pipeline work style. This is the way how we designed our network framework

```

+----------------------------------------------------------+
|              Server Logic - Application Layer            |
|           **************************************         |
|       Client_A             Server            Client_B    |
|           |    1.CONNECT      |                  |       |
|           | ----------------> |                  |       |
|           |    2.SETUP        |                  |       |
|           | ----------------> |    3. CONNECT    |       |
|           |                   | <--------------- |       |
|           |    4. START       |                  |       |
|           | ----------------> |    5. SETUP      |       |
|           |                   | <--------------- |       |
|           |    6. START       |                  |       |
|           | ----------------> |                  |       |
|           |    7. START(SEED) |  7. START(SEED)  |       |
|           | <---------------- | ---------------> |       |
|           |                   |                  |       |
|           |    7. SETUP       |     7. SETUP     |       |
|           |  Turret Locations | Turret Locations |       |
|           | <---------------- | ---------------> |       |
|           |                   |                  |       |
|           |   8. OPERATION    |  8. OPERATION    |       |
|           | ----------------> | <--------------- |       |
|           |  9. Update_Turret |                  |       |
|           | ----------------> | 9. Update_Turret |       |
|           | <---------------- | ---------------> |       |
|           |                   |                  |       |
|           |   10. OPERATION   |  10. OPERATION   |       |
|           | <---------------- | ---------------> |       |
|           |                   |                  |       |
|           |     11. END       |                  |       |
|           | ----------------> |                  |       |
|           |                   |     11. END      |       |
|           |                   | ---------------> |       |
|           |                   |                  |       |
|           -                   -                  -       |
|             /|\                            |             |
+--------------+-----------------------------+-------------+
               |                             |              
+--------------+--------------+              |
|      Protocol Processor     |              |
+--------------+--------------+              |
              /|\                            |              
+--------------+-----------------------------+-------------+
|              |        ChannelPipeline      |             |
|              |                             |             |
|   **************************               |             |
|   * ProtocolProcessHandler *               |             |
|   **************************               |             |
|             /|\                            |             |
|              |                            \|/            |
|   **************************  ************************** |
|   *       JSON Decoder     *  *      JSON Encoder      * |
|   **************************  ************************** |
|             /|\                            |             |
|              |                             |             |
|   **************************               |             |
|   *  LineBasedFrameDecoder *               |             |
|   **************************               |             |
|             /|\                            |             |
+--------------+-----------------------------+-------------+
               |                            \|/  
               |                +------------+-------------+
               |                |    *******************   |
               |                |    *      Queue      *   |
               |                |    *[ Server Buffer ]*   |
               |                |    *******************   |
               |                |            |             |   
               |                |           \|/            |                     
               |                |    *******************   |
               |                |    *    HashMap      *   |
               |                |    * [ Channel Map ] *   |            
               |                |    *******************   |
               |                +------------+-------------+
               |                             |
+--------------+-----------------------------+-------------+
|              |                            \|/            |
|       [ Socket Read ]               [ Socket Write ]     |
|                       Transport Layer                    |
+----------------------------------------------------------+
```

1. Client A sends a CONNECT package to server
2. Client A has set up all swarm logic and is ready for playing
3. Client B sends a CONNECT package to server
4. Client A tries to start the game, however, client B is not ready, the game fails to start
5. Client B has set up all swarm logic and is ready for playing
6. Client A tries to start the game again
7. Server detects all conditions met, game starts, telling all clients the game could be started, also sends all swarm logic with TYPE: SETUP, in this package there is also all turrets starting locations and a random seed to keep everything synchronized
8. Client A and B sends operation input (Game is running)
9. Client A killed a turret, the server generate a new location to both sides
10. Server broadcasts all operation
11. Client A detects win/lose / Client B detects win/lose

## How we keep data synchronized

**Frame**: Server maintains a frame count variable, each operation package is tagged with a frame before the package is broadcasted. One player always waits for the other side in order to be in the same frame

**Locations**: Turrets locations are generated by the server, players starting locations are generated some way by using the same random seed 

## Package Design

1. Information firstly is put in a HashMap with various Header - Message pair.
2. This HashMap is converted into a JSON string
3. Then a length is added to the head, ends with a new line sign 

```
+---------------------------------------------------------------------------+
|                           Game Protocol Object                            |
|   +----------+--------------------------------------------------+-------+ |
|   |          |           "JSON-lized" Format String             |       | |
|   |          |  +--------------------------------------------+  |       | |  
|   |  Length  |  |                Package Map                 |  |  /n   | |
|   |          |  |     < Headers , Constants/Information >    |  |       | |
|   |          |  +--------------------------------------------+  |       | |
|   +----------+--------------------------------------------------+-------+ |
+---+----------+--------------------------------------------------+---------+


```



