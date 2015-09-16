function Ball (x, y) {
	this.x = x;
	this.y = y;
	this.vX = 0;
	this.vY = 0;
	// backup of attribute
	var bX = x;
	var bY = y;
	var bVX = 0;
	var bVY = 0;
	var img = null;
	var exist = true;

	this.setImg = function (nImg) {
		img = nImg;
	}

	this.setXY = function (x, y) {
		this.x = x;
		this.y = y;
	}

	this.setV = function (vX, vY) {
		this.vX = vX;
		this.vY = vY;
	}

	this.save = function () {
		bX = this.x;
		bY = this.y;
		bVX = this.vX;
		bVY = this.vY;
	}

	// recover status
	this.restore = function () {
		this.setXY(bX, bY);
		this.setV(bVX, bVY);
	}

	// get velocity's radian, equals atan(vY / vX)
	this.getRadian = function () {
		if (this.vX == 0)
			return this.vY > 0 ? Math.PI / 2 : -Math.PI / 2;
		return Math.atan(this.vY / this.vX);
	}

	this.isExist = function () {
		return exist;
	}

	this.isStop = function () {
		return this.vX == 0 && this.vY == 0;
	}

	this.isPocket = function () {
		var res = false;
		// left and top
		if (squareDistance(this.x, this.y, POCKET_X1, POCKET_Y1) < Math.pow(R / 2 + POCKET_R1, 2))
			res = true;
		// right and top
		if (squareDistance(this.x, this.y, POCKET_X2, POCKET_Y2) < Math.pow(R / 2 + POCKET_R1, 2))
			res = true;
		// right and bottom
		if (squareDistance(this.x, this.y, POCKET_X3, POCKET_Y3) < Math.pow(R / 2 + POCKET_R1, 2))
			res = true;
		// left and bottom
		if (squareDistance(this.x, this.y, POCKET_X4, POCKET_Y4) < Math.pow(R / 2 + POCKET_R1, 2))
			res = true;
		// middle and top
		if (squareDistance(this.x, this.y, POCKET_X5, POCKET_Y5) < Math.pow(R + POCKET_R2, 2))
			res = true;
		// middle and bottom
		if (squareDistance(this.x, this.y, POCKET_X6, POCKET_Y6) < Math.pow(R + POCKET_R2, 2))
			res = true;
		if (res) {
			this.vX = this.vY = 0;
			exist = false;
		} else 
			exist = true;
		return res;
	}

	this.move = function (time) {
		var radian = this.getRadian(); 
		// velocity change by friction of x direction
		var dVX = Math.abs(FRICTION * Math.cos(radian) * time);
		var dVY = Math.abs(FRICTION * Math.sin(radian) * time);
		var x1 = this.x;
		var y1 = this.y;
		var vX1 = this.vX;
		var vY1 = this.vY;
		var vX2;
		var vY2;
		// friction
		if (Math.abs(vX1) <= dVX)
			vX2 = 0;
		else
			vX2 = vX1 - (vX1 > 0 ? dVX : -dVX);
		if (Math.abs(vY1) <= dVY)
			vY2 = 0;
		else 
			vY2 = vY1 - (vY1 > 0 ? dVY : -dVY);
		var x2 = x1 + (vX1 + vX2) * time / 2;
		var y2 = y1 + (vY1 + vY2) * time / 2;
		this.setXY(x2, y2);
		this.setV(vX2, vY2);
	}

	this.draw = function (ctx) {
		ctx.drawImage(img, this.x - R, this.y - R, 2 * R, 2 * R);
	}
}
