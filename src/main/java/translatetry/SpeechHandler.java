package translatetry;

import com.google.cloud.speech.spi.v1.SpeechClient;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SpeechHandler {
	public static List<SpeechRecognitionResult> getAudioToVoice() throws Exception {
		// Instantiates a client
		SpeechClient speech = SpeechClient.create();

		// The path to the audio file to transcribe
		String fileName = "/Users/rishaagarwal/Documents/workspace-eclipse/translate-try/src/main/resources/test.raw";

		// Reads the audio file into memory
		Path path = Paths.get(fileName);
		byte[] data = Files.readAllBytes(path);
		ByteString audioBytes = ByteString.copyFrom(data);

		System.out.println("Sending response now");
		// Builds the sync recognize request
		RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.LINEAR16)
				.setSampleRateHertz(8000).setLanguageCode("en-US").build();
		RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

		// Performs speech recognition on the audio file
		RecognizeResponse response = speech.recognize(config, audio);
		List<SpeechRecognitionResult> results = response.getResultsList();
		
		System.out.println("got results as" + results.toString());

		for (SpeechRecognitionResult result : results) {
			List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
			for (SpeechRecognitionAlternative alternative : alternatives) {
				System.out.printf("Transcription: %s%n", alternative.getTranscript());
			}
		}
		
		speech.close();
		return results;
	}
}
