CCanva.prototype = {
	WIDTH : NONE,
	HEIGHT : NONE,
	// x direction datum point of regular triangle
	BOARD_Y0 : 100,
	PLAYER_X0 : 70,
	PLAYER_Y0 : 0,
};

function CCanva () {
	var _this = this;
	var canvas;	
	var canvas_player;	
	// context of canvas
	var ctx;			
	var ctx_player;			

	this.init = function () {
		canvas = document.getElementById("canvas");
		canvas_player = document.getElementById("canvas_player");
		WIDTH = canvas.width;
		HEIGHT = canvas.height;
		ctx = canvas.getContext('2d');
		ctx_player = canvas_player.getContext('2d');
	}

	this.changeCursor = function (cur) {
		document.body.style.cursor = 'url(' + cur + ')'; 
	}

	this.clear = function () {
		ctx.clearRect(0, 0, ctx.width, ctx.height);
		ctx_player.clearRect(0, 0, canvas_player.width, canvas_player.height);
	}

	this.drawPoint = function (x, y) {
		ctx.strokeStyle = "white";
		ctx.lineWidth = 1;
		ctx.beginPath();
		ctx.moveTo(x, y);
		ctx.lineTo(x + 1, y + 1);
		ctx.closePath();
		ctx.stroke();
	}

	this.isPlayAnimation = function () {
		if (typeof _playAnimation_defined === 'undefined') {
			_playAnimation_defined = 'defined';
			_playAnimation_img = new Image();
			_playAnimation_y = TABLE_HEIGHT / 2;
			_playAnimation_flag = OFF;
		}

		return _playAnimation_flag == ON;
	}

	this.doPlayAnimation = function () {
		if (_playAnimation_y < _this.BOARD_Y0) {
			_playAnimation_y = TABLE_HEIGHT / 2;
			_playAnimation_flag = OFF;
		} else {
			_playAnimation_y--;

			ctx.drawImage(
				_playAnimation_img, 
				(TABLE_WIDTH - _playAnimation_img.width) / 2, 
				_playAnimation_y - _playAnimation_img.height / 2, 
				_playAnimation_img.width, 
				_playAnimation_img.height
			);
		}
	}

	this.playAnimation = function (path) {
		_playAnimation_flag = ON;
		_playAnimation_img.src = path;
		_playAnimation_y = TABLE_HEIGHT / 2;
	}

	this.drawBackground = function () {
		if (typeof _drawBackground_defined === 'undefined') {
			_drawBackground_defined = 'defined';
			_drawBackground_img = new Image();
			_drawBackground_img.src = PImg.TABLE;
		}

		ctx.drawImage(_drawBackground_img, 0, 0, _drawBackground_img.width, _drawBackground_img.height);
	}

	this.drawPoolCue = function () {
		if (typeof _drawPoolCue_defined === 'undefined') {
			_drawPoolCue_defined = 'defined';
			_drawPoolCue_img = new Image();
			_drawPoolCue_img.src = PImg.POOL_CUE;
		}

		var x = Cue_ball.getX();
		var y = Cue_ball.getY();
		var w = _drawPoolCue_img.width;
		var h = _drawPoolCue_img.height;
		ctx.save();
		ctx.translate(x, y);
		ctx.rotate(Math.PI / 2 + Cue_ball.radian);
		ctx.translate(-x, -y);
		ctx.drawImage(_drawPoolCue_img, x - w / 2, y + Cue_ball.force + R, w, h);
		ctx.restore();
	}

	// draw the dotted line between cue ball and the prensent mouse point
	this.drawAim = function () {
		if (typeof _drawAim_defined === 'undefined') {
			_drawAim_defined = 'defined';
			_drawAim_radian = 0;
			_drawAim_x = 0;
			_drawAim_y = 0;
		}

		if (_drawAim_radian != Cue_ball.radian) {
			_drawAim_radian = Cue_ball.radian;
			_drawAim_y = _drawAim_x * Math.tan(Cue_ball.radian);
		}

		var x1 = Cue_ball.getX();
		var y1 = Cue_ball.getY();
		var x2 = Mouse.x;
		var y2 = Mouse.y;
		// dX is the distance in X direction between two points
		var dX = R * Math.cos(Cue_ball.radian);
		var dY = R * Math.sin(Cue_ball.radian);
		var x = x1 + _drawAim_x;
		var y = y1 + _drawAim_y;
		_drawAim_x %= dX;
		_drawAim_y %= dY;
		// draw all discrete point from (x1 + _drawAim_x, y1 + _drawAim_y) to (x2, y2)
		while (Math.abs(x - x1) <= Math.abs(x2 - x1) && Math.abs(y - y1) <= Math.abs(y2 - y1)) {
			this.drawPoint(x, y);
			x += dX;
			y += dY;
		}
		// imitate move effect
		_drawAim_x += dX / 20;
		_drawAim_y += dY / 20;
	}

	this.drawBalls = function () {
		for (var i = Balls.get().length - 1; i >= 0; i--) 
			if (Balls.get()[i].isExist())
				Balls.get()[i].draw(ctx);

		if (Game.isRoundPut())
			Balls.get()[0].draw(ctx);
	}

	this.drawPlayer = function () {
		if (typeof _drawPlayer_defined === 'undefined') {
			_drawPlayer_defined = 'defined';
			_drawPlayer_img = {
				'player' : new Image(),
				'arrow' : new Image(),
				'ball' : new Image(),
			};
			_drawPlayer_inx = 0;
			_drawPlayer_interval = 10;
		}
		if (++_drawPlayer_inx >= _drawPlayer_interval * 2)
			_drawPlayer_inx = 0;

		var x = new Array();
		var y = new Array();
		var dir;
		var bs;
		if (Players.isLeftOne()) {
			_drawPlayer_img['player'].src = PImg.PLAYER_L;
			_drawPlayer_img['arrow'].src = PImg.ARROW_L[Math.floor(_drawPlayer_inx / _drawPlayer_interval)];
			x['player'] = _this.PLAYER_X0;
			x['arrow'] = x['player'] + _drawPlayer_img['player'].width;
			x['ball'] = x['player'] + _drawPlayer_img['player'].width;
			bs = Balls.remain(Players.leftOne().getColor());
			dir = 1;
		} else {
			_drawPlayer_img['player'].src = PImg.PLAYER_R;
			_drawPlayer_img['arrow'].src = PImg.ARROW_R[Math.floor(_drawPlayer_inx / _drawPlayer_interval)];
			x['player'] = canvas_player.width - _this.PLAYER_X0 - _drawPlayer_img['player'].width;
			x['arrow'] = x['player'] - _drawPlayer_img['arrow'].width;
			x['ball'] = x['player'] - 2 * R;
			bs = Balls.remain(Players.rightOne().getColor());
			dir = -1;
		}
		y['player'] = _this.PLAYER_Y0;
		y['arrow'] = _this.PLAYER_Y0 + _drawPlayer_img['player'].height * 1 / 5;
		y['ball'] = _this.PLAYER_Y0 + _drawPlayer_img['player'].height * 2 / 3;

		ctx_player.drawImage(
			_drawPlayer_img['player'], 
			x['player'], 
			y['player'], 
			_drawPlayer_img['player'].width, 
			_drawPlayer_img['player'].height
		);
		ctx_player.drawImage(
			_drawPlayer_img['arrow'], 
			x['arrow'], 
			y['arrow'], 
			_drawPlayer_img['arrow'].width, 
			_drawPlayer_img['arrow'].height
		);
		var i = 0;
		do {
			var j = (bs.length == 1) ? 0 : bs[i + 1];
			_drawPlayer_img['ball'].src = PImg.NEXT[j];
			ctx_player.drawImage(
				_drawPlayer_img['ball'], 
				x['ball'] + dir * i * 2 * R, 
				y['ball'], 
				2 * R,
				2 * R
			);
			i++;
		} while (i < bs.length - 1);
	}

	this.drawAll = function () {
		this.clear();
		this.drawBackground();
		this.drawPlayer();
		this.drawBalls();
		if (Game.isRoundAim()) {
			this.drawAim();
			this.drawPoolCue();
		}
		if (this.isPlayAnimation())
			this.doPlayAnimation();
	}

	this.init();
}