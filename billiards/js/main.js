function main () {
	Canva = new CCanva();
	Sound = new CSound();
	Mouse = new CMouse();
	Game = new CGame();

	setInterval(
		function () {
			switch(Game.now()) {
			case Game.BEGIN:
				Game.doBegin();
				break;
			case Game.CONTINUE:
				Game.doContinue();
				break;
			case Game.END:
				Game.doEnd();
				break;
			}
		}, 
		INTERVAL
	);
}