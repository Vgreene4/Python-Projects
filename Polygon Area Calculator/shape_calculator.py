class Rectangle:
    def __init__(self, width, height):
        self.width= width
        self.height= height 
    def __str__(self):
        return "Rectangle(width="+ str(self.width)+", height="+ str(self.height)+")"
    def set_width(self, newwidth):
        self.width= newwidth
    def set_height(self, newheight):
        self.height= newheight
    def get_area(self):
        return self.width*self.height 
    def get_perimeter(self):
        return (2 * self.width + 2 * self.height)
    def get_diagonal(self):
        return ((self.width ** 2 + self.height ** 2) ** .5)
    def get_picture(self):
        line= ""
        if self.width>50 or self.height>50:
            return "Too big for picture."
        else:
            picture= "*"*self.width+"\n"
            picture= picture*self.height
            return picture
    def get_amount_inside(self,shape):
        if self.width>shape.width and self.height>shape.height:
            return self.get_area() // shape.get_area()
        else:
            return 0

class Square(Rectangle):
    def __init__(self, side):
        self.width= side
        self.height= side
    def __str__(self):
      return "Square(side="+str(self.width)+")"
    def set_side(self, sid):
        self.width= sid
        self.height= sid
