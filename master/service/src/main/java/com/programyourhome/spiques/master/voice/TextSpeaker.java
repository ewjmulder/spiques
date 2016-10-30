package com.programyourhome.spiques.master.voice;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO: from some kind of shared lib? / microservice?
@Component
public class TextSpeaker {

    private static final String ENCODING_UTF8 = "UTF-8";
//    private static final String GOOGLE_SPEECH_TTS_URL = "http://translate.google.com/translate_tts?q=%s&tl=%s&ie=%s";
    private static final String GOOGLE_SPEECH_TTS_URL = "https://translate.google.nl/translate_tts?ie=UTF-8&q=%s&tl=%s&total=1&idx=0&textlen=17&tk=818406.671627&client=t&prev=input";

    private static final String RESPONSIVE_VOICE_TTS_URL = "https://code.responsivevoice.org/getvoice.php?t=%s&tl=%s&pitch=0.5&rate=0.5&vol=1";

//    @Value("${googleSpeechTts.userAgent}")
    private String googleSpeechUserAgent;

    @Autowired
    private AudioPlayer audioPlayer;

    public TextSpeaker() {
    	//TODO: get from props?
		this.googleSpeechUserAgent = "Mozilla/5.0";
	}
    
    // TODO: implement caching!!

    public void say(final String text, final String locale) {
// Option 1: command line 'say'
//    	Runtime.getRuntime().exec(new String[] {"say", text});
    	
// Option 2: Google TTS
/* TOO BAD: Google has completely secured this stuff!
    	// Create the full url by filling in the text, locale and encoding.
        final String urlString = String.format(GOOGLE_SPEECH_TTS_URL,
                URLEncoder.encode(text, ENCODING_UTF8), locale, ENCODING_UTF8);
        final URLConnection urlConnection = new URL(urlString).openConnection();
        // Set the user agent to a sane value to prevent a 403 response.
        urlConnection.addRequestProperty("User-Agent", this.googleSpeechUserAgent);
        urlConnection.addRequestProperty("Referer", "http://translate.google.com/");
        // Input stream of the response is an mp3 stream with the given text as audio.
        this.audioPlayer.playMp3(urlConnection.getInputStream());
*/
// Option 3: responsive voice
    	try {
	    	// Create the full url by filling in the text, locale and encoding.
	        final String urlString = String.format(RESPONSIVE_VOICE_TTS_URL,
	                URLEncoder.encode(text, ENCODING_UTF8), locale, ENCODING_UTF8);
	        final URLConnection urlConnection = new URL(urlString).openConnection();
	        // Set the user agent to a sane value to prevent a 403 response.
	        urlConnection.addRequestProperty("User-Agent", this.googleSpeechUserAgent);
	        // Input stream of the response is an mp3 stream with the given text as audio.
	        this.audioPlayer.playMp3(urlConnection.getInputStream());
    	} catch (IOException e) {
    		throw new IllegalStateException("IOException during say", e);
    	}
    }

}