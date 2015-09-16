function CSound () {
	var _this = this;
	// array of resource
	var _res;
	// a _flag to control turn on or off music
	var _flag;


	this.loop = function (name) {
	    _res[name].addEventListener('ended', loopHandler, false);
	    function loopHandler (e) {
		    _res[name].play();
	    }
	    _res[name].play();
	}

	this.turnOn = function () {
		_flag = ON;
		this.play('background');
	}

	this.turnOff = function () {
		_flag = OFF;
		this.stop('background');
	}

	this.play = function (name) {
		if (_flag == ON)
			_res[name].play();
	}

	this.stop = function (name) {
		_res[name].pause();
	}

	this.playHit = function () {
		this.play('collide');
	}

	this.playPocket = function (sv) {
		var seg = Math.pow(MAX_V, 2) / 4;
		var num = Math.floor(sv / seg);
		var name = 'pocket' + num;
		this.play(name);
	}

	var _add = function (filePath) {
		var folderPath = filePath.substr(0, filePath.lastIndexOf('.'));
		var audio = document.createElement('audio');
		var source= document.createElement('source');
		audio.preload = true;
		// for compatibility
		if (audio.canPlayType('audio/ogg')) {
			source.type= 'audio/ogg';
			source.src = folderPath + '.ogg';
		} else {
			source.type= 'audio/mpeg';
			source.src = folderPath + '.mp3';
	    }
	    audio.appendChild(source);
	    document.getElementById('sound_container').appendChild(audio);
	    return audio;
	}

	var _load = function () {
		_res = new Array();
		_res['background'] = _add(PSound.CAROMHALL);
		_res['collideEdge'] = _add(PSound.COLLIDEEDGE);
		_res['collide'] = _add(PSound.COLLIDE);
		_res['pocket0'] = _add(PSound.POCKET0);
		_res['pocket1'] = _add(PSound.POCKET1);
		_res['pocket2'] = _add(PSound.POCKET2);
		_res['pocket3'] = _add(PSound.POCKET3);
	}

	var _init = function () {
		_load();
		_this.turnOn();
		// default loop background music
	    _this.loop('background');
	}

	_init();
}