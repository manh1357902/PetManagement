package com.project.petmanagement.payloads.responses;

public class HealthRecordErrorResponse {
    private int status;
    private Message message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public class Message {
        private String checkUpDate;
        private String weight;
        private String exerciseLevel;
        private String symptoms;
        private String diagnosis;
        private String note;
        private String petId;

        public String getCheckUpDate() {
            return checkUpDate;
        }

        public void setCheckUpDate(String checkUpDate) {
            this.checkUpDate = checkUpDate;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getExerciseLevel() {
            return exerciseLevel;
        }

        public void setExerciseLevel(String exerciseLevel) {
            this.exerciseLevel = exerciseLevel;
        }

        public String getSymptoms() {
            return symptoms;
        }

        public void setSymptoms(String symptoms) {
            this.symptoms = symptoms;
        }

        public String getDiagnosis() {
            return diagnosis;
        }

        public void setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getPetId() {
            return petId;
        }

        public void setPetId(String petId) {
            this.petId = petId;
        }
    }
}

