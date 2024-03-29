# Game Server Usage

This guide will give the simplest guide on how to integrate network module with game logic

## Protocol details

Whole protocol works as following:

```
Client_A             Server            Client_B
   |    1.CONNECT      |                  |
   | ----------------> |                  |
   |    2.SETUP        |                  |
   | ----------------> |    3. CONNECT    |
   |                   | <--------------- |
   |    4. START       |                  |
   | ----------------> |    5. SETUP      |
   |                   | <--------------- |
   |    6. START       |                  |
   | ----------------> |                  |
   |    7. START(SEED) |  7. START(SEED)  |
   | <---------------- | ---------------> |
   |    7. SETUP       |     7. SETUP     |
   |                   |                  |
   |   8. OPERATION    |  8. OPERATION    |
   | ----------------> | <--------------- |
   |   9. OPERATION    |  9. OPERATION    |
   | <---------------- | ---------------> |
   |                   |                  |
   |     10. END       |                  |
   | ----------------> |                  |
   |                   |     11. END      |
   |                   | <--------------- |
   |                   |                  |
   -                   -                  -
```

* **CONNECT**: Inform the server that the client is connected, client enters *CONNECTED* state
* **SETUP**: Gives the server client swarms logic, server stores this package for further use, client enters *SETUP* state
* **START**: Any of the lobby member can send this package to try to start the game, if and only if all **CONNECTED** clients have sent SETUP package and the player number is larger than 1
* **OPERATION**: Contains the operation input from player
* **END**: When client detects win/lose result

1. Client A sends a CONNECT package to server
2. Client A has set up all swarm logic and is ready for playing
3. Client B sends a CONNECT package to server
4. Client A tries to start the game, however, client B is not ready, the game fails to start
5. Client B has set up all swarm logic and is ready for playing
6. Client A tries to start the game again
7. Server detects all conditions met, game starts, telling all clients the game could be started, also sends all swarm logic with TYPE: SETUP
8. Client A and B sends operation input (Game is running)
9. Server broadcasts all operation
10. Client A detects win/lose
11. Client B detects win/lose

## How to start server
```java
new Thread(new Runnable() {
            public void run() {
                try{
                    GameServer.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
```

This code will initialize all required resources

## How to start client
**STEP 1** Let the player enter his/her id (This makes the game not a really online game, because two players have to agree on two different number)

```java
System.out.println("Enter your id");
Scanner scanner = new Scanner(System.in);
int id = scanner.nextInt();
```

**STEP 2** Start the client in a new thread, let the game thread wait until client starts successfully by using CountDownLatch

```java
new Thread(new Runnable() {
            public void run() {
                try {
                    GameClient.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        
GameClient.countDownLatch.await();
```

**STEP 3** How to send and receive packages

```java
while(times < 5) {
            System.out.println("***************");
            System.out.println("Round " + times + " begins");

            // Clean the whole buffer
            MessageHandlerMulti.refreshClientReceiveBuffer();

            // First sends a CONNECT package to server
            // CONNECT means the player is connected to the server, now setting up swarms
            m.put(Headers.TYPE, Constants.CONNECT);
            m.put(Headers.PLAYER, id);
            MessageHandlerMulti.putPackage(m);
            System.out.println("Sent CONNECT");
            Thread.sleep(interval);

            // Then sends a SETUP package to server
            // SETUP means the swarms logic is ready, and is prepared to play the game
            m = new HashMap();
            m.put(Headers.TYPE, Constants.SETUP);
            m.put(Headers.PLAYER, id);
            MessageHandlerMulti.putPackage(m);
            System.out.println("Sent SETUP");
            Thread.sleep(interval);

            // If this is player 0, try to start the game
            // If the game is started successfully
            // The server will broadcast a START package
            // In this package there is a random seed generated by the server
            while (!MessageHandlerMulti.gameStarted && id == 0) {
                m = new HashMap();
                m.put(Headers.TYPE, Constants.START);
                MessageHandlerMulti.putPackage(m);
                System.out.println("Tried to send START, but game not ready");
                Thread.sleep(interval);
            }

            Map rev = null;

            // Then keeps sending OPERATION package to server
            while (!MessageHandlerMulti.gameStarted) {
                System.out.println("Game not started yet");
                Thread.sleep(interval);
            }

            while (frame < 20) {
                m = new HashMap<String, Object>();
                m.put(Headers.TYPE, Constants.OPERATION);
                m.put(Headers.PLAYER, id);
                m.put(Headers.W, 0);
                m.put(Headers.A, 1);
                MessageHandlerMulti.putPackage(m);
                while (rev == null) {
                    System.out.println("Player " + id + " trying to get package with frame number " + frame);
                    rev = MessageHandlerMulti.getPackage(Math.abs(id - 1), frame);
                    if (rev == null) {
                        System.out.println("Did not get wanted package, try again, main game waiting");
                    }
                    Thread.sleep(interval);
                }
                rev = null;
                frame++;
                Thread.sleep(interval);
            }

            m = new HashMap<String, Object>();
            m.put(Headers.TYPE, Constants.END);
            m.put(Headers.PLAYER, id);
            MessageHandlerMulti.putPackage(m);
            System.out.println("Player " + id + " Game ends");

            times++;
            frame = 0;
            Thread.sleep(1000);
        }
```

The code above simulated a game in five rounds following these steps

1. Refresh the receiving buffer at the very beginning of each round

2. Send a CONNECT package to the server

3. After setting up swarm logic, send a SETUP package to the server
4. Player 0 tries to start the game while player 1 waiting to be started
5. Send OPERATION package of local frame `n` (HOWEVER, you do not need to tag the package with your local frame count, the server will do the tagging. What you SHALL do is to keep tracking your own frame and try to get the same frame package from the receiving buffer)
6. When trying to fetch a package from the opposite player, if the wanted package yet arrived, the game thread will wait
7. Game is set to have 20 frames till the end, if reached, send an END package to the server
8. Start another round

## How the server works

* Basically, the whole process is based on Netty. Therefore, we skip the Netty part.

* The server overall maintains a buffer of received packages by using a `Queue`

* When the server receives a CONNECT package, the player count is increased by 1

* When the server receives a SETUP package, it will store the package somewhere, and the ready player count is increased by 1, SETUP package has a frame tag 0

* If a START package arrives, and the ready player count is the same as player count, the server broadcasts a message to all clients saying that the game is ok to start

* The server will broadcast all OPERATION packages (tagged with frame)

* All process is in synchronized mode, i.e. one player is lagged means all player wait (Therefore it's better to do *Operation Prediction* in game)

* When server receives an END package, it will delete the frame count of the player who sent the END package

## How the client works

* Basically, the client maintains two buffers, receiving buffer and sending buffer

* All packages received will be stored in the receiving buffer

* A *Sending* thread will keep watching the sending buffer, tries its best to send all packages there (interval is decided by `Constants.ClientSleep`)

* The client will automatically create receiving buffer of other players once receives a package with a new id, i.e. no existing buffer for this id

* The client will not create the receiving buffer of its own (You have to find your way to do this, currently I am using a visible static field *id*, the code is in `ProtocolProcessor.java`)


