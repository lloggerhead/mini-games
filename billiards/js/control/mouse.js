function CMouse () {
	var _this = this;
	_this.x = -1000;
	_this.y = -1000;

	// if this click is right button of mouse, then return true
	var _isRightButton = function (ev) {
		return ev.button == 2;
	}

	var _getXY = function (ev) {
		var rect = canvas.getBoundingClientRect();
	    _this.x = ev.clientX - rect.left;
	    _this.y = ev.clientY - rect.top;
	}

	// mouse event listener
	var _init = function () {
		canvas.addEventListener('mousedown', _down, false);
		canvas.addEventListener('mousemove', _move, false);
		canvas.addEventListener('mouseup', _up, false);
	}

	var _down = function (ev) {
		// no response to right button
		if(_isRightButton(ev))
			return;
		// mouse can only control or change anything in ROUND_PUT and ROUND_AIM
		if (Game.isRoundPut()) {
			if (Cue_ball.canPut(_this.x, _this.y)) 
				Cue_ball.put(_this.x, _this.y);
		} else if (Game.isRoundAim()) {
			Cue_ball.bat();
		}
	}

	var _move = function (ev) {
		_getXY(ev);
		// if (typeof Balls !== 'undefined') {
		if (Cue_ball) {
			// action in time
			Cue_ball.aim(_this.x, _this.y);
		}

		if (Game.isRoundPut()) {
			// let the cue ball can be correct draw
			Cue_ball.setXY(_this.x, _this.y);
		}
	}

	var _up = function (ev) {
		if(_isRightButton(ev))
			return;

		if (Game.isRoundPut()) {
			if (Cue_ball.canPut(_this.x, _this.y)) 
				Game.roundAim();
		} else if (Game.isRoundAim()) {
			Cue_ball.bat();
		}
	}

	_init();
}