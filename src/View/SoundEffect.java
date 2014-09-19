package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.sound.sampled.*;

public class SoundEffect extends JFrame implements Serializable {

	AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;

//	public static void main(String args[]) {
//		new SoundEffect();
//	}// end main
		// -------------------------------------------//

	public SoundEffect() {// constructor
		playAudio();

	}// end constructor
		// -------------------------------------------//

	// This method plays back audio data from an
	// audio file whose name is specified in the
	// text field.
	private void playAudio() {
		try {
			File soundFile = new File(System.getenv("ahmed") + "ExtraLife.au");
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			audioFormat = audioInputStream.getFormat();
//			System.out.println(audioFormat);

			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, audioFormat);

			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			// Create a thread to play back the data and
			// start it running. It will run until the
			// end of file, or the Stop button is
			// clicked, whichever occurs first.
			// Because of the data buffers involved,
			// there will normally be a delay between
			// the click on the Stop button and the
			// actual termination of playback.
			new PlayThread().start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}// end catch
	}// end playAudio

	// =============================================//
	// Inner class to play back the data from the
	// audio file.
	class PlayThread extends Thread {
		byte tempBuffer[] = new byte[10000];

		public void run() {
			try {
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();

				int cnt;
				// Keep looping until the input read method
				// returns -1 for empty stream or the
				// user clicks the Stop button causing
				// stopPlayback to switch from false to
				// true.
				while ((cnt = audioInputStream.read(tempBuffer, 0,
						tempBuffer.length)) != -1) {
					if (cnt > 0) {
						// Write data to the internal buffer of
						// the data line where it will be
						// delivered to the speaker.
						sourceDataLine.write(tempBuffer, 0, cnt);
					}// end if
				}// end while
					// Block and wait for internal buffer of the
					// data line to empty.
				sourceDataLine.drain();
				sourceDataLine.close();

				// Prepare to playback another file
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}// end catch
		}// end run
	}// end inner class PlayThread
		// ===================================//

}// end outer class AudioPlayer02.java