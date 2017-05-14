package translatetry;

import java.util.List;

import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class RequestHandler {

	public static void main(String... args) throws Exception {
		// Instantiates a client
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		// The text to translate
		for (SpeechRecognitionResult result : SpeechHandler.getAudioToVoice()) {
			List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
			for (SpeechRecognitionAlternative alternative : alternatives) {
				String text = alternative.getTranscript();
				Translation translation = translate.translate(text, TranslateOption.sourceLanguage("en"),
						TranslateOption.targetLanguage("hi"));
				System.out.printf("Text: %s%n", text);
				System.out.printf("Translation: %s%n", translation.getTranslatedText());
			}
		}
	}
	// String text =

	// Translates some text into Russian
	// Translation translation =
	// translate.translate(
	// text,
	// TranslateOption.sourceLanguage("en"),
	// TranslateOption.targetLanguage("hi"));

}
