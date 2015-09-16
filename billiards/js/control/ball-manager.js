CBalls.prototype = {
	// x direction datum point of regular triangle
	X0 : 580,
	COLOR_PURE : 1,
	COLOR_MULT : 2,
	COLOR_WHITE : 4,
	COLOR_BLACK : 8,
};

function CBalls () {
	var _this = this;
	var _balls; 	 
	// record index and color of get in pocket ball by order
	// but _hit_balls[0] record the cue ball hit first
	var _hit_balls;
	var _cntHit;
	var _cntPocket;

	this.get = function () {
		return _balls;
	}

	this.getHitBalls = function () {
		return _hit_balls;
	}

	this.recordHitBalls = function () {
		_hit_balls = new Array();
		_hit_balls[0] = [NONE, NONE];
	}

	this.getBallColor = function (i) {
		if (i == 0)
			return _this.COLOR_WHITE;
		if (i >= 1 && i <= 7)
			return _this.COLOR_PURE
		if (i >= 9 && i <= 15)
			return _this.COLOR_MULT;
		if (i == 8)
			return _this.COLOR_BLACK;
	}

	this.getHitTimes = function () {
		return _cntHit;
	}

	this.getPocketTimes = function () {
		return _cntPocket;
	}

	this.hitRightColor = function (color) {
		if (_hit_balls[0][1] == NONE) {
			return false;
		}
		return color == NONE || _hit_balls[0][1] == color;
	}

	this.pocketRightColor = function (color) {
		if (color == NONE) {
			return _hit_balls.length > 1;
		}
		for (var i = 1; i < _hit_balls.length; i++)
			if (color == _hit_balls[i][1]) {
				return true;
			}
		return false;
	}

	this.firstHitColor = function () {
		return _hit_balls[0][1];
	}

	this.firstPocketColor = function () {
		if (typeof _hit_balls[1] === 'undefined') {
			return NONE;
		}
		return _hit_balls[1][1];
	}

	this.remain = function (color) {
		var rm = new Array();
		if (color == NONE) {
			rm[0] = NONE;
		} else if (color == _this.COLOR_BLACK) {
			rm[0] = _this.COLOR_BLACK;
			rm[1] = 8;
		} else if (color == _this.COLOR_PURE) {
			rm[0] = _this.COLOR_PURE;
			for (var i = 1; i <= 7; i++)
				if (_balls[i].isExist()) {
					rm[rm.length] = i;
				}
			if (rm.length == 1)
				rm[0] = _this.COLOR_BLACK;
		} else if (color == _this.COLOR_MULT) {
			rm[0] = _this.COLOR_MULT;
			for (var i = 9; i <= 15; i++)
				if (_balls[i].isExist()) {
					rm[rm.length] = i;
				}
			if (rm.length == 1)
				rm[0] = _this.COLOR_BLACK;
		}
		return rm;
	}

	this.isCollideWall = function (i) {
		var b = _balls[i];
		return (b.x < BORDER_LEFT + R) || (b.x > BORDER_RIGHT - R) 
		|| (b.y < BORDER_TOP + R) || (b.y > BORDER_BOTTOM - R); 
	}

	// if collision wall, then change ball's status and return true
	this.doCollideWall = function (i) {
		var b = _balls[i];
		if (b.x < BORDER_LEFT + R) {
			// repair position,equals (BORDER_LEFT + R - b.x) * 2 + b.x
			b.x = (BORDER_LEFT + R) * 2 - b.x;
			b.vX = -b.vX;
		} else if (b.x > BORDER_RIGHT - R) {
			b.x = (BORDER_RIGHT - R) * 2 - b.x;
			b.vX = -b.vX;
		}
		if (b.y < BORDER_TOP + R) {
			b.y = (BORDER_TOP + R) * 2 - b.y;
			b.vY = -b.vY;
		} else if (b.y > BORDER_BOTTOM - R) {
			b.y = (BORDER_BOTTOM - R) * 2 - b.y;
			b.vY = -b.vY;
		}
	}

	// check collision between _balls[i] and _balls[j]
	this.isCollideBall = function (i, j) {
		var b1 = _balls[i];
		var b2 = _balls[j];
		if (Math.abs(b1.x - b2.x) >= 2 * R)
			return false;
		if (Math.abs(b1.y - b2.y) >= 2 * R)
			return false;
		var sd = squareDistance(b1.x, b1.y, b2.x, b2.y);
		if (sd > Math.pow(2 * R, 2))
			return false;
		return sd;
	}

	// change _balls[i] and _balls[j] status and position to after collision
	this.doCollideBall = function (i, j) {
		var b1 = _balls[i];
		var b2 = _balls[j];
		// ax + by + c = 0, the linear equation of velocity direction
		var a = b1.vY;
		var b = -b1.vX;
		var c = b1.y * b1.vX - b1.x * b1.vY;
		// square of distance
		var sqrD1 = Math.pow(a * b2.x + b * b2.y + c, 2) / (a * a + b * b);
		var sqrD2 = Math.pow(2 * R, 2) - sqrD1;
		var sqrD3 = squareDistance(b1.x, b1.y, b2.x, b2.y) - sqrD1;
		// distance between collision position and current position
		var deltaD = Math.sqrt(sqrD3) - Math.sqrt(sqrD2);
		var radian = b1.getRadian();
		var sqrRate = b1.vX * b1.vX + b1.vY * b1.vY;
		// time when collision happen
		var time = (Math.sqrt(sqrRate) - Math.sqrt(sqrRate - 2 * FRICTION * deltaD)) / FRICTION;
		var dVX = Math.abs(FRICTION * Math.cos(radian) * time);
		var dVY = Math.abs(FRICTION * Math.sin(radian) * time);
		// position when collision
		var x2 = b1.x + deltaD * Math.cos(radian);
		var y2 = b1.y + deltaD * Math.sin(radian);
		// velocity when collision
		var vX2;
		var vY2;
		// friction
		if (Math.abs(b1.vX) <= dVX)
			vX2 = 0;
		else
			vX2 = b1.vX - (b1.vX > 0 ? dVX : -dVX);
		if (Math.abs(b1.vY) <= dVY)
			vY2 = 0;
		else 
			vY2 = b1.vY - (b1.vY > 0 ? dVY : -dVY);
		b1.setXY(x2, y2);
		b1.setV(vX2, vY2);
		_elasticCollide(b1, b2);
		// remain time after collision 
		b1.move(DELTA_T - time);
	}

	this.isAllBallStop = function () {
		for (var i = 0; i < _balls.length; i++)
			if (!_balls[i].isStop())
				return false;
		return true;
	}

	// move and change attribute of all _balls
	this.moveAndCheck = function () {
		_cntHit = 0;
		_cntPocket = 0;
		for (var k = 0; k < FORECAST_ACCURACY; k++) {
			for (var i = 0; i < _balls.length; i++) {
				if (!_balls[i].isExist() || _balls[i].isStop()) {
					continue;
				}
				_balls[i].save();
				_balls[i].move(DELTA_T);
				if (_balls[i].isPocket()) {
					_cntPocket++;
					_hit_balls[_hit_balls.length] = [i, _this.getBallColor(i)];
					continue;
				}
				if (_this.isCollideWall(i))
					_this.doCollideWall(i);
				// ball to ball collision
				var len = 0;
				// index of ball, square distance
				var qb = new Array();
				// save all collision with _balls[i]
				for (var j = 0; j < _balls.length; j++) {
					if (j == i || !_balls[j].isExist())
						continue;
					var sd = _this.isCollideBall(i, j);
					if (sd !== false)
						qb[len++] = [j, sd];
				}
				// no collision happen
				if (len <= 0)
					continue;
				var idx = qb[0][0];
				var min = qb[0][1];
				// find the most close ball which will collision
				for (var j = 1; j < len; j++) {
					if (qb[j][1] >= min)
						continue;
					idx = qb[j][0];
					min = qb[j][1];
				}
				// restore _balls[i]' position before collision
				_balls[i].restore();
				_this.doCollideBall(i, idx);
				_cntHit++;
				if (_hit_balls[0][1] == NONE) {
					if (i == 0)
						_hit_balls[0] = [idx, _this.getBallColor(idx)];
					else if (idx == 0)
						_hit_balls[0] = [i, _this.getBallColor(i)];
				}
			}
		}
	}

	// perfectly elastic collision
	var _elasticCollide = function (b1, b2) {
		var dX = b1.x - b2.x;
		var dY = b1.y - b2.y;
		var dVX = b1.vX - b2.vX;
		var dVY = b1.vY - b2.vY;
		var sqrDis = dX * dX + dY * dY;
		var dVX2 = (dVX * dX * dX + dVY * dX * dY) / sqrDis;
		var dVY2 = (dVY * dY * dY + dVX * dX * dY) / sqrDis;
		var vX1 = b1.vX - dVX2;
		var vY1 = b1.vY - dVY2;
		var vX2 = b2.vX + dVX2;
		var vY2 = b2.vY + dVY2;
		b1.setV(vX1, vY1);
		b2.setV(vX2, vY2);
	}

	var _init = function () {
		if (typeof _this._init_defined === 'undefined') {
			_this._init_defined = 'defined';
			//a calibration value of two ball's position
			_PCV = 0.1;
		}
		_balls = new Array();
		// put down all _balls in right position
		var x = _this.X0;
		var y = TABLE_HEIGHT / 2;
		var dX = Math.sqrt(3) * (R + _PCV);
		var dY = R + _PCV;
		_balls[0] = new Ball(-1000, -1000);
		_balls[1] = new Ball(x, y);
		_balls[9] = new Ball(x + dX, y + dY);
		_balls[7] = new Ball(x + dX, y - dY);
		_balls[8] = new Ball(x + 2 * dX, y);
		_balls[6] = new Ball(x + 2 * dX, y + 2 * dY);
		_balls[10] = new Ball(x + 2 * dX, y - 2 * dY);
		_balls[11] = new Ball(x + 3 * dX, y + dY);
		_balls[2] = new Ball(x + 3 * dX, y - dY);
		_balls[13] = new Ball(x + 3 * dX, y + 3 * dY);
		_balls[12] = new Ball(x + 3 * dX, y - 3 * dY);
		_balls[4] = new Ball(x + 4 * dX, y);
		_balls[15] = new Ball(x + 4 * dX, y + 2 * dY);
		_balls[14] = new Ball(x + 4 * dX, y - 2 * dY);
		_balls[5] = new Ball(x + 4 * dX, y + 4 * dY);
		_balls[3] = new Ball(x + 4 * dX, y - 4 * dY);
		for (var i = 0; i <= 15; i++) {
			var img = new Image();
			img.src = PImg.BALL[i];
			_balls[i].setImg(img);
		}
		Cue_ball = new CCue_ball(_balls[0]);
		_this.recordHitBalls();
	}

	_init();
}