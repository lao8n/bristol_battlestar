# Battlestar - Swarm Wars
## University of Bristol - MSc Computer Science - Software Group Project

# Steps
1. Install processing - make sure that processing-jar is accessible in the command line e.g. symlink etc. https://github.com/processing/processing/wiki/Command-Line & https://processing.org/download/
2. Install gradle (3.5.3 is fine)
3. Install github. 
4. Find the processing 'sketchbook/libraries' folder (on Linux it is in my home folder for example), then do 
`git clone https://github.com/lao8n/battlestar.git` This is the new folder that you will code in because it doesn't work unless it is in the libraries folder. The old git folder you can delete. Then create a new local branch to write code to locally `git checkout -b example_branch`. Make some changes on your branch and `git add -A`, `git commit -m "some message"` and `git push origin example_branch` which will make a repo version of your branch. Make sure if you want to do any merges, you do it by pulling development branch, locally merging and then committing/pushing back to development (not to master) from branch (see https://nvie.com/posts/a-successful-git-branching-model/).
5. Then `cd battlestar` to get inside and run `gradle build` - this should return 'BUILD SUCCESSFUL'
6. To run processing you have 3 options 1. just open processing 2. install visual studio code and this package  https://marketplace.visualstudio.com/items?itemName=Tobiah.language-pde and press Ctrl-Shift-B or 3. manually type in `processing-java --sketch=/full/path/to/your/sketch/dir --force --run` https://github.com/processing/processing/wiki/Command-Line. 

I appreciate the above documentation is pretty vague. Happy to help if you run into problems - it may be different on a MAC!
