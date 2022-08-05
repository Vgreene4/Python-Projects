import copy
import random
# Consider using the modules imported above.

class Hat:
  def __init__(self,**kwargs):
    hatlist= []
    contents= []
    self.contents= contents
    self.hatlist= hatlist
    for (k,v) in kwargs.items():
      hatlist.append((k,v))
    for i in range(0, len(hatlist)):
      for _ in range(hatlist[i][1]):
        contents.append(hatlist[i][0])
  def draw(self, ballsdrawn):
    retlist= []
    self.retlist= retlist
    if ballsdrawn > len(self.contents):
      ballsdrawn= len(self.contents)
    for _ in range(ballsdrawn):
      rando= random.randint(0, len(self.contents)-1)
      retlist.append(self.contents[rando])
      self.contents.remove(self.contents[rando])
    return retlist

    
   
   
def experiment(hat, expected_balls, num_balls_drawn, num_experiments):
  M=0
  compdict= {}
  count2= 0
  for _ in range(num_experiments):
    other_hat= copy.deepcopy(hat)
    balls_drawn= other_hat.draw(num_balls_drawn)
    for colors in balls_drawn:
      count= balls_drawn.count(colors)
      if (colors not in compdict.keys()) and (colors in expected_balls.keys()):
          compdict[colors]= count
    for keys in compdict.keys():
      if compdict[keys] > expected_balls[keys]:
        compdict[keys] = expected_balls[keys]
    if compdict == expected_balls:
      M+=1
      compdict = {}
    else:
      compdict = {}
  return M/num_experiments
  
  
  
