CGame.prototype = {
	BEGIN : 1,
	CONTINUE : 2,
	END : 3,
	// one round means the time between before player hit cue ball from all balls stoped
	// put cue ball
	ROUND_PUT : 1,
	// aim to
	ROUND_AIM : 2,
	// any ball move
	ROUND_MOVE : 3,
	// not pause game, but all ball stoped
	ROUND_STOP : 4,
};

function CGame () {
	var _this = this;
	var _state;
	var _round_state;

	this.now = function () {
		return _state;
	}

	this.doBegin = function () {
		newGame();
	}

	this.doContinue = function () {
		gameCheck();
	}

	this.doEnd = function () {
		gameover();
	}

	this.isRoundPut = function () {
		return _round_state == _this.ROUND_PUT;
	}

	this.isRoundAim = function () {
		return _round_state == _this.ROUND_AIM;
	}
	
	this.isRoundMove = function () {
		return _round_state == _this.ROUND_MOVE;
	}
	
	this.isRoundStop = function () {
		return _round_state == _this.ROUND_STOP;
	}

	this.roundPut = function () {
		_round_state = _this.ROUND_PUT;
	}

	this.roundAim = function () {
		_round_state = _this.ROUND_AIM;
	}

	this.roundMove = function () {
		_round_state = _this.ROUND_MOVE;
	}

	this.roundStop = function () {
		_round_state = _this.ROUND_STOP;
	}
	
	var newGame = function () {
		Players = new CPlayers();
		Balls = new CBalls();
		_state = _this.CONTINUE;
		_round_state = _this.ROUND_PUT;
	}

	// do all check
	var gameCheck = function () {
		Canva.drawAll();

		if (!Balls.isAllBallStop())
			_this.roundMove();
		else if (_this.isRoundMove())
			_this.roundStop();
		// _round_state change flow :
		// PUT-->AIM-->MOVE-->STOP
		//	|	  |			   |
		//	<-----<-------------
		switch(_round_state) {
		case _this.ROUND_PUT:	
			doRoundPut();
			break;
		case _this.ROUND_AIM:
			doRoundAim();
			break;
		case _this.ROUND_MOVE:
			doRoundMove();
			break;
		case _this.ROUND_STOP:
			doRoundStop();
			break;
		}
	}

	var gameover = function () {
		// showScores();
		// showCombo();
		// restartOrExit();
		_state = _this.BEGIN;
	}

	// most of the put down work finished in mouse_listener.js
	var doRoundPut = function () {
		if (Cue_ball.canPut(Mouse.x, Mouse.y)) 
			Canva.changeCursor(PImg.CUR_HAND);
		else
			Canva.changeCursor(PImg.CUR_HANDF);
	}

	// 
	var doRoundAim = function () {
		Canva.changeCursor(PImg.CUR_AIM);
	}

	// do some check with ball
	var doRoundMove = function () {
		Canva.changeCursor(PImg.CUR_NORMAL);
		Balls.moveAndCheck();
		if (Balls.getHitTimes())
			Sound.playHit();
		if (Balls.getPocketTimes()) {
			var bs = Balls.getHitBalls();
			var b = Balls.get()[bs[bs.length - 1][0]];
			Sound.playPocket(squareDistance(b.vX, b.vY, 0, 0));
		}
	}

	// do some check with game rule
	var doRoundStop = function () {
		var crtP = Players.current();

		// check black ball
		if (!Balls.get()[8].isExist()) {
			// when all other ball get in and no foul, then Players.current player win
			if (Balls.hitRightColor(Players.current().getColor()) && Balls.get()[0].isExist()) {
				Players.current().incCombo();
				Players.win();
			} else {
				Players.lose();
			}
			_state = _this.END;
		// if cue ball get in
		} else if (!Balls.get()[0].isExist()) {
			Players.foul();
			Players.change();
			_this.roundPut();
			Canva.playAnimation(PImg.BD_FOUL);		
		// if foul
		} else if (!Balls.hitRightColor(Players.current().getColor())) {
			Players.foul();
			Players.change();
			Canva.playAnimation(PImg.BD_FOUL);
			_this.roundAim();
		// if get in right ball
		} else if (Balls.pocketRightColor(Players.current().getColor())) {
			Players.current().incCombo();
			Players.current().gainDoubleHit();
			var inxB = Balls.remain(crtP.getColor());
			crtP.setColor(inxB[0]);
			// choose the color
			if (!Players.isColorDecided()) {
				Players.decideColor(Balls.firstPocketColor());
				Players.current().dropDoubleHit();
				Canva.playAnimation(PImg.BD_CHOOSE);
				_this.roundAim();
			} else {
				Players.current().dropDoubleHit();
				Canva.playAnimation(PImg.BD_COMBO);
				_this.roundAim();
			}
		// double hit or change player
		} else {
			if (Players.current().haveDoubleHit()) {
				Players.current().dropDoubleHit();
				Canva.playAnimation(PImg.BD_COMBO);
			} else {
				Players.change();
				Canva.playAnimation(PImg.BD_NORMAL);
			}
			_this.roundAim();
		}

		Balls.recordHitBalls();
	}

	_state = _this.BEGIN;
}