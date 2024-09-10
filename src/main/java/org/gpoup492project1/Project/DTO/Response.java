package org.gpoup492project1.Project.DTO;

    public class Response <T>{

        private T body;
        private String error;

        public Response(T body, String error) {
            this.body = body;
            this.error = error;
        }

        public T getBody() {
            return body;
        }

        public String getError() {
            return error;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "body=" + body +
                    ", error='" + error + '\'' +
                    '}';
        }
    }

