# Battlestar - Swarm Wars
## University of Bristol - MSc Computer Science - Software Group Project

# Linux steps
1. Install processing - make sure that processing-jar is accessible in the command line e.g. symlink etc. https://github.com/processing/processing/wiki/Command-Line & https://processing.org/download/. 
2. Install gradle (3.5.3 is fine)
3. Install github. 
4. Find the processing 'sketchbook/libraries' folder (on Linux it is in my home folder for example), then do 
`git clone https://github.com/lao8n/battlestar.git` This is the new folder that you will code in because it doesn't work unless it is in the libraries folder. The old git folder you can delete. Then create a new local branch to write code to locally `git checkout -b example_branch`. Make some changes on your branch and `git add -A`, `git commit -m "some message"` and `git push origin example_branch` which will make a repo version of your branch. Make sure if you want to do any merges, you do it by pulling development branch, locally merging and then committing/pushing back to development (not to master) from branch (see https://nvie.com/posts/a-successful-git-branching-model/).
5. Then `cd battlestar` to get inside and run `gradle build` - this should return 'BUILD SUCCESSFUL'
6. To run processing you have 3 options 1. just open processing 2. install visual studio code and this package  https://marketplace.visualstudio.com/items?itemName=Tobiah.language-pde and press Ctrl-Shift-B or 3. manually type in `processing-java --sketch=/full/path/to/your/sketch/dir --force --run` https://github.com/processing/processing/wiki/Command-Line. 

# Mac Installation Steps 
1. Download processing from this link https://processing.org/download/
2. Open processing and in the tools menu at the top, press install processing-java https://atom.io/packages/processing
3. Install gradle - open terminal and write `brew install gradle`. If this doesn't work text Nick.
4. Navigate in terminal to `~/Documents/processing/libraries` and then type `git clone https://github.com/lao8n/battlestar.git`
5. `cd battlestar`
6. `gradle build`it should do some stuff and then say 'BUILD SUCCESSFUL'
7. Then type `processing-java --force --sketch=$PWD/SwarmWars/ --run`

# Windows Installation Steps (pending

# Text Editor choices
Visual Studio Code 
1. Install Visual Studio Code and install this package https://marketplace.visualstudio.com/items?itemName=Tobiah.language-pde
Atom - Pending
Processing - Pending

# Workflow
1. Open text editor in folder `~/Documents/processing/libraries/battlestar/` e.g. Atom, VisualStudio Code etc.
  Atom - File - Add Project folder /Documents/processing/libraries/battlestar/
2. Write java code in `~/Documents/processing/libraries/battlestar/src/main/java/processing/swarm_wars_library/` and the four folder in there for `engine`, `network`, `scene`, `ui`. 
3. Write processing graphics code in `~/Documents/processing/libraries/battlestar/SwarmWars`
4. Open terminal and type `cd ~/Documents/processing/libraries/battlestar/`, then `gradle build`
5. Then type `processing-java --force --sketch=$PWD/SwarmWars/ --run`

# Windows steps (pending!!)

