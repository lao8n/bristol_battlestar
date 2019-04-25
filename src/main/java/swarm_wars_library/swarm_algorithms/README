swarm_algorithms

Contains concrete implementation of 
* BoidsFlock
* DefensiveShell
* ScoutShell

These classes extend the abstract class AbstractSwarmAlgorithm which provides
concrete implementation of the following method:
* avoidEdge()

And implement the methods:
* applySwarmAlgorithm()
* seekMotherShip()

They also use composition of swarm rules. For example, BoidsFlock uses rules 
* SeparateRule 
* AlignRule
* CoheseRule 
to get its behaviour.

However, each of these rules themselves extend the class AbstractSwarmRule 
which gives concrete implementation of:
* iterateOverSwarm()
and provide concrete implementation of 
* neighbourCountRule()
* swarmRule()

These methods are then used in the 'engine' by SwarmLogic which itself is 
used, via composition, in the entity class Bot.

I think the implementation can be made much more efficient by only requiring 
one pass over the bots even if an algorithm implements multiple rules.