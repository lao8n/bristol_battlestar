graphics

Contains class to manage Rendering, which is called 
* RenderLayers

And only has two public methods
* RenderLayers() constructor
* update()

These are called in SwarmWars.

Previous versions of the code had rendering embedded as a class inside each 
entity. This is usually fine, and would work well for something like sound, 
but does not work with processing graphics because there ordering matters, for 
the layering and appearance of objects.

For that reason, we have pulled rendering out completely from game engine and
entities. RenderLayers then gets all the information it needs from Comms. Most 
details are intuitive, except that 
1. CollisionDetection cannot update Comms directly because a new CommsPacket 
   overrides any changes every frame (and currently we have 1. collissions 
   2. entities update 3. render). Therefore CollisionDetection makes updates by 
   directly updating entity values.
2. To get the timing of explosions, we have three STATEs: 1. ALIVE 2. DEAD 3.
   EXPLODE. Thus when an object is killed it doesnt die, but first explodes, 
   and then the next frame after dies, this gives the renderer a way for the 
   renderer to know whether to draw an explosion, without having to count 
   frames since death.

RenderLayers uses the following ordering.
1. renderBackgroundLayer()
2. renderEntitiesLayer()
3. renderDisplaysLayer()
4. renderMiniMapEntitiesLayer

It uses numerous concrete classes for mini-map objects and map objects where
these inherit core implementation from 
* AbstractRenderMiniMapObject
* AbstractRenderMapObject 

Where AbstractRenderMapObject gives concrete implementation of
* update()
* updateExplosion()
* setParticleExplosionMapLocation()
* setObjectRenderLocation()

Leaving the inheriting class to implement 
* renderMapObject()
* renderMapObjectExplosion()

And similarly, for AbstractRenderMiniMapObject gives concrete implementation of
* update()
* setObjectRenderLocation()

Leaving the inheriting to class to implement
* renderMiniMapObject()

The health and score bars are implemented independently with no larger 
structure, potentially as the number of classes increases this will need to 
change.

Finally, there is also some graphics specific physics to calculate the 
explosion effects in 
* Particle 
* ParticleExplosion
(Valia I think somehow, I've made the effects look not as cool as before, 
please advise on how to fix!!)