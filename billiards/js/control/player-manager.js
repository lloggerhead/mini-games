function CPlayers () {
	var _players;

	var _init = function () {
		_players = [new Player(), new Player()];
		_change_flag = ON;
	}

	this.change = function () {
		if (_change_flag == ON) {
			_players[0].setCombo(0);
			_change_flag = OFF;
		} else if (_change_flag == OFF) {
			_players[1].setCombo(0);
			_change_flag = ON;
		} 
	}

	// current player
	this.current = function () {
		if (_change_flag == ON) {
			return _players[0];
		}
		if (_change_flag == OFF) {
			return _players[1];
		}
	}

	this.opponent = function () {
		if (_change_flag == ON) {
			return _players[1];
		}
		if (_change_flag == OFF) {
			return _players[0];
		}
	}

	// player[0] is the left one
	this.isLeftOne = function () {
		return _change_flag == ON;
	}

	this.leftOne = function () {
		return _players[0];
	}

	// player[1] is the right one
	this.isRightOne = function () {
		return _change_flag == OFF;
	}

	this.rightOne = function () {
		return _players[1];
	}

	this.foul = function () {
		this.current().setCombo(0);
		this.current().dropDoubleHit();
		this.opponent().gainDoubleHit();
	}

	this.win = function () {
		this.current().win();
		this.opponent().lose();
	}

	this.lose = function () {
		this.current().lose();
		this.opponent().win();
	}

	this.isColorDecided = function () {
		return this.current().getColor() != NONE;
	}

	this.decideColor = function (color) {
		this.current().setColor(color);
		if (color == Balls.COLOR_MULT)
			this.opponent().setColor(Balls.COLOR_PURE);
		else
			this.opponent().setColor(Balls.COLOR_MULT);
	}

	// not check the black ball
	this.isRightBall = function (bColor) {
		var pColor = this.current().getColor();
		if (pColor == NONE)
			return true;
		return pColor == bColor;
	}

	_init();
}