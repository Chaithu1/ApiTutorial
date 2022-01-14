package com.linkedin.api.Azure;

import java.util.ArrayList;
import java.util.List;

public class SentimentAnalysisResponse {

    private List<TextDocumentScore> documents = new ArrayList<>();

    public List<TextDocumentScore> getDocuments() {
        return documents;
    }

    public void setDocuments(List<TextDocumentScore> documents) {
        this.documents = documents;
    }

    public static class TextDocumentScore {

        private String id;

        private String sentiment;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSentiment() {
            return sentiment;
        }

        public void setSentiment(String sentiment) {
            this.sentiment = sentiment;
        }

    }

}