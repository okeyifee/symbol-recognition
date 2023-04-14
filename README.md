IMAGE RECOGNITION ALGORITHM

This program takes as an input 10 sequences of 10 symbols “0” or “1”, which represent one symbol starting from the top most line.
With this, the program matches an image of one of the following symbols: C, O, L, I, T, X by recognizing that symbol using the symbol definition provided as below:

Symbols definition
I – filled rectangle.
O – filled rectangle with an unfilled area inside. The borders of the unfilled area should not overlap with filled area and/or merge with the outer unfilled area.
C – filled rectangle with an unfilled area inside. The right border of the unfilled area breaks the border of the right border of the filled area.
L – two rectangles lying on top of each other. Their left border is the same. The right edge of the bottom rectangle advances more to the right than the right edge of the top rectangle.
T – two rectangles, one on top of another one. The left border of the rectangle on the top spans more to the left than the left border of the rectangle on the bottom. The right border of the rectangle on the top spans more to the right than the right border of the rectangle on the bottom.
X – any other configuration.

Compiling and Running the Program
Run with (maven):
mvn clean install exec:java


Java Version:
Java 8

