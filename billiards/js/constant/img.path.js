function PImg () {
	this.DIR = 'img/';
	this.CUR_DIR = this.DIR + 'cursor/';
	this.BALL_DIR = this.DIR + 'ball/';
	this.BOARD_DIR = this.DIR + 'board/';
	this.PLAYER_DIR = this.DIR + 'player/';

	this.TABLE = this.DIR + 'table.png';
	this.POOL_CUE = this.DIR + 'cue_stick.png';

	this.CUR_AIM = this.CUR_DIR + 'aim.cur';
	this.CUR_HAND = this.CUR_DIR + 'hand.cur';
	this.CUR_HANDF = this.CUR_DIR + 'handf.cur';
	this.CUR_NORMAL = this.CUR_DIR + 'normal.cur';

	this.BD_FOUL = this.BOARD_DIR + 'board_foul.png';
	this.BD_COMBO = this.BOARD_DIR + 'board_combo.png';
	this.BD_NORMAL = this.BOARD_DIR + 'board_normal.png';
	this.BD_CHOOSE = this.BOARD_DIR + 'board_choose.png';
	this.BD_RESTART = this.BOARD_DIR + 'board_restart.png';

	this.PLAYER_L = this.PLAYER_DIR + 'playerL.gif';
	this.PLAYER_R = this.PLAYER_DIR + 'playerR.gif';

	this.BALL = new Array();
	this.NEXT = new Array();
	this.BALL[0] = this.BALL_DIR + 'ball0.png';
	this.NEXT[0] = this.BALL_DIR + 'unknow.png';
	for (var i = 1; i <= 15; i++) {
		this.BALL[i] = this.BALL_DIR + 'ball' + i + '.png';
		this.NEXT[i] = this.BALL_DIR + 'next' + i + '.png';
	}	

	this.ARROW_L = new Array();
	this.ARROW_R = new Array();
	for (var i = 0; i <= 1; i++) {
		this.ARROW_L[i] = this.PLAYER_DIR + 'arrowL' + i + '.png';
		this.ARROW_R[i] = this.PLAYER_DIR + 'arrowR' + i + '.png';
	}	
}
this.PImg = new PImg();