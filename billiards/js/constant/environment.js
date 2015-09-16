// --------------------- public method ---------------------
// square distance between (x1, y1) and (x2, y2)
function squareDistance(x1, y1, x2, y2) {
	return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
}

// --------------------- singleton ---------------------
// PImg
// PSound
// Cue_ball
// Balls
// Mouse
// Players
// Sound
// Canva
// Game

// --------------------- control ---------------------
// a state mean do nothing
var NONE = 0;
// mean two state of flag variable
var ON = true;
var OFF = false;
var FRICTION = 0.12;
var FORECAST_ACCURACY = 50;
// main time interval of this game
var INTERVAL = 20;
// ball minimal move time interval 
var DELTA_T = 1 / FORECAST_ACCURACY;	
// ball radius
var R = 13; 			 
// ball max velocity
var MAX_V = 20;

// --------------------- position and size ---------------------
// The pool table's width
var TABLE_WIDTH = 800;						
var TABLE_HEIGHT = 433;	
var BORDER_LEFT = 44;
var BORDER_RIGHT = 800 - 44;
var BORDER_TOP = 38;
var BORDER_BOTTOM = 433 - 38;
// ball pocket's radius
var POCKET_R1 = 20;
var POCKET_R2 = 25;
// left and top
var POCKET_X1 = 42;
var POCKET_Y1 = 42;
// right and top
var POCKET_X2 = 800 - 42;
var POCKET_Y2 = 42;
// right and bottom
var POCKET_X3 = 800 - 42;
var POCKET_Y3 = 433 - 42;
// left and bottom
var POCKET_X4 = 42;
var POCKET_Y4 = 433 - 42;
// middle and top
var POCKET_X5 = 400;
var POCKET_Y5 = 19;
// middle and bottom
var POCKET_X6 = 400;
var POCKET_Y6 = 433 - 19;