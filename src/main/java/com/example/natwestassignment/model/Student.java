package com.example.natwestassignment.model;

public class Student {
    private long rollNumber;
    private String name;
    private long scienceMarks;
    private long mathsMarks;
    private long englishMarks;
    private long computerMarks;
    private String eligible;

    public long getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(long rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScienceMarks() {
        return scienceMarks;
    }

    public void setScienceMarks(long scienceMarks) {
        this.scienceMarks = scienceMarks;
    }

    public long getMathsMarks() {
        return mathsMarks;
    }

    public void setMathsMarks(long mathsMarks) {
        this.mathsMarks = mathsMarks;
    }

    public long getEnglishMarks() {
        return englishMarks;
    }

    public void setEnglishMarks(long englishMarks) {
        this.englishMarks = englishMarks;
    }

    public long getComputerMarks() {
        return computerMarks;
    }

    public void setComputerMarks(long computerMarks) {
        this.computerMarks = computerMarks;
    }

    public String isEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }

    public Student(long rollNumber, String name, long scienceMarks, long mathsMarks, long englishMarks, long computerMarks, String eligible) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.scienceMarks = scienceMarks;
        this.mathsMarks = mathsMarks;
        this.englishMarks = englishMarks;
        this.computerMarks = computerMarks;
        this.eligible = eligible;
    }

    public Student() {
    }
}
