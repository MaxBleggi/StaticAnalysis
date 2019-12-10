package com.xumo.xumo.model;

import java.util.List;

public class AmazonADRequest {
    private ContentBean content;

    public static class ContentBean {
        private List<String> category;
        private List<String> genre;
        private String id;
        private String length;
        private String rating;

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public String getRating() {
            return this.rating;
        }

        public void setRating(String str) {
            this.rating = str;
        }

        public String getLength() {
            return this.length;
        }

        public void setLength(String str) {
            this.length = str;
        }

        public List<String> getGenre() {
            return this.genre;
        }

        public void setGenre(List<String> list) {
            this.genre = list;
        }

        public List<String> getCategory() {
            return this.category;
        }

        public void setCategory(List<String> list) {
            this.category = list;
        }
    }

    public ContentBean getContent() {
        return this.content;
    }

    public void setContent(ContentBean contentBean) {
        this.content = contentBean;
    }
}
