Player.prototype = {
	WIN : 1,
	LOSE : 2,
	DOUBLE_HIT : 3,
};

function Player () {
	var color = NONE;
	var maxCombo = 0;
	// counter of current combo
	var crtCombo = 0;
	var score = 0;
	var winOrLose = NONE;
	var doubleHit = NONE;

	this.haveDoubleHit = function () {
		return doubleHit == this.DOUBLE_HIT;
	}

	this.gainDoubleHit = function () {
		doubleHit = this.DOUBLE_HIT;
	}

	this.dropDoubleHit = function () {
		doubleHit = NONE;
	}

	this.getColor = function () {
		return color;
	}

	this.setColor = function (nColor) {
		color = nColor;
	}

	this.getCombo = function () {
		return crtCombo;
	}

	this.setCombo = function (combo) {
		crtCombo = combo;
	}

	this.getMaxCombo = function () {
		return maxCombo;
	}

	this.incCombo = function () {
		crtCombo++;
		if (crtCombo > maxCombo)
			maxCombo = crtCombo;
	}

	this.getScore = function () {
		return score;
	}

	this.setScore = function (nScore) {
		score = nScore;
	}

	this.win = function () {
		winOrLose = this.WIN;
		score = maxCombo - 2;
		if (score < 0)
			score = 0;
		score += 3;
	}

	this.lose = function () {
		winOrLose = this.LOSE;
		score = maxCombo - 2;
		if (score < 0)
			score = 0;
		score += -1;
	}

	this.isWin = function () {
		return winOrLose == this.WIN;
	}

	this.isLose = function () {
		return winOrLose == this.LOSE;
	}
}