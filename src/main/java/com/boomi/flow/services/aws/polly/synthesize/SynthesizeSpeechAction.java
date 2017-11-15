package com.boomi.flow.services.aws.polly.synthesize;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.actions.Action;

@Action.Metadata(name = "Synthesize Speech", summary = "Synthesize text to speech, and return a URL to the generated audio", uri = "synthesize/speech")
public class SynthesizeSpeechAction implements Action {
    public static class Input {
        @Action.Input(name = "Text", contentType = ContentType.String)
        private String text;

        public String getText() {
            return text;
        }
    }

    public static class Output {
        @Action.Output(name = "Audio URL", contentType = ContentType.String)
        private String audioUrl;

        public Output(String audioUrl) {
            this.audioUrl = audioUrl;
        }

        public String getAudioUrl() {
            return audioUrl;
        }
    }
}
