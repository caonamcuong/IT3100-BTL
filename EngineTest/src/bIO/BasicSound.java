package bIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.TreeMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BasicSound {
	private TreeMap<String, Integer> sizes;
	private TreeMap<String, byte[]> audios;
	private TreeMap<String, AudioFormat> afs;
	private TreeMap<String, DataLine.Info> infos;
	
	public BasicSound() {
		audios = new TreeMap<String, byte[]>();
		sizes = new TreeMap<String, Integer> ();
		afs = new TreeMap<String, AudioFormat> ();
		infos = new TreeMap<String, DataLine.Info> ();
	}
	
	public void loadSound(String s) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(s));
		AudioFormat af = audioStream.getFormat();
		int size = (int) (af.getFrameSize() * audioStream.getFrameLength());
		byte[] audio = new byte[size];
		DataLine.Info info = new DataLine.Info(Clip.class, af, size);
		audioStream.read(audio, 0, size);
		sizes.put(s, size);
		audios.put(s, audio);
		afs.put(s, af);
		infos.put(s, info);
	}
	
	public void playSound(String s) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		if (!afs.containsKey(s)) loadSound(s);
		Clip audioClip = (Clip)AudioSystem.getLine(infos.get(s));
		if (audioClip.isRunning()) {
			audioClip.stop();
		}
		audioClip.setFramePosition(0);
		audioClip.open(afs.get(s), audios.get(s), 0, sizes.get(s));
		audioClip.start();
	}
	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		BasicSound sound_manager = new BasicSound();
		sound_manager.playSound("smb_jump-small.wav");
		Thread.sleep(1000);
	}
}
