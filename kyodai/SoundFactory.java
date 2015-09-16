package kyodai;

import kyodai.*;
import java.applet.*;

public interface SoundFactory {
	String DIR = "sound/";
	AudioClip ELEC = Applet.newAudioClip(SoundFactory.class.getResource(DIR+"elec.wav"));
	AudioClip BEGIN = Applet.newAudioClip(SoundFactory.class.getResource(DIR+"begin.wav"));
	AudioClip ITEMBOOM = Applet.newAudioClip(SoundFactory.class.getResource(DIR+"itemboom.wav"));
	AudioClip SEL = Applet.newAudioClip(SoundFactory.class.getResource(DIR+"sel.wav"));
	AudioClip SHUFFLE = Applet.newAudioClip(SoundFactory.class.getResource(DIR+"shuffle.wav"));
	AudioClip TIMENOTIFY = Applet.newAudioClip(SoundFactory.class.getResource(DIR+"timenotify.wav"));
	AudioClip[] WIN = { 
			Applet.newAudioClip(SoundFactory.class.getResource(DIR+"jianjiao.wav")),
			Applet.newAudioClip(SoundFactory.class.getResource(DIR+"koushao.wav")),
			Applet.newAudioClip(SoundFactory.class.getResource(DIR+"zhangsheng.wav")) 
	};
}
