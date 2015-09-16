CCue_ball.prototype = {
	// a time interval of power gauge which decrease hit gauge
	GAUGE_INTERVAL : 50,
	// force / GAUGE_RATIO is the truely velocity of cue ball
	GAUGE_RATIO : 4,
	// max x point of cue ball can put
	X0 : 222,
};

function CCue_ball (ball) {
	var _this = this;
	this.self = ball;
	// the radian of cue ball aim to
	this.radian = 0;
	// the velocity of hit cue ball
	this.force = MAX_V * this.GAUGE_RATIO;

	this.getX = function () {
		return this.self.x;
	}

	this.getY = function () {
		return this.self.y;
	}

	this.setXY = function (x, y) {
		this.self.setXY(x, y);
	}

	this.setV = function (x, y) {
		this.self.setV(x, y);
	}

	// rotate the pool cue by mouse's position
	this.aim = function (x, y) {
		// distance between cue ball and mouse of x direction
		var dX = x - this.getX();
		var dY = y - this.getY();
		if (dX == 0) {
			this.radian = dY > 0 ? Math.PI / 2 : -Math.PI / 2;
		} else if (dY == 0) {
			this.radian = dX > 0 ? 0 : Math.PI;
		} else {
			this.radian = Math.atan(dY / dX);
			if (dX < 0)
				this.radian += Math.PI;
		}
	}

	// return true,if (x, y) can put down the cue ball
	this.canPut = function (x, y) {	
		if (x > this.X0)
			return false;
		this.setXY(x, y);
		if (Balls.isCollideWall(0))
			return false;
		for (var i = 1; i < Balls.get().length; i++)
			if (Balls.get()[i].isExist() && Balls.isCollideBall(0, i))
				return false;
		if (this.self.isPocket())
			return false;
		return true;
	}

	// set cue ball's status
	this.put = function (x, y) {
		this.setXY(x, y);
		this.setV(0, 0);
	}

	// change the white ball's velocity when release mouse
	this.bat = function () {
		if (typeof _doBat_defined === 'undefined') {
			_doBat_defined = 'defined';
			_doBat_flag = ON;
			_doBat_timer = 0;
		}
		if (_doBat_flag == ON) {
			_doBat_flag = OFF;
			_doBat_timer = setInterval(
				// decrease force to hit cue ball
				function () {
					if (_this.force > 0)
						_this.force--;
				},
				this.GAUGE_INTERVAL
			);
		} else {
			_doBat_flag = ON;
			clearInterval(_doBat_timer);
			this.setV(
				this.force / this.GAUGE_RATIO * Math.cos(this.radian),
				this.force / this.GAUGE_RATIO * Math.sin(this.radian)
			);
			this.force = MAX_V * this.GAUGE_RATIO;
		}	
	}
}